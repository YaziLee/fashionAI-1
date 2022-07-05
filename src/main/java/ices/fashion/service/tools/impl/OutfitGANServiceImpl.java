package ices.fashion.service.tools.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import ices.fashion.constant.ApiResult;
import ices.fashion.constant.GANConst;
import ices.fashion.constant.QiniuCloudConst;
import ices.fashion.entity.tools.TOutfitBag;
import ices.fashion.entity.tools.TOutfitLower;
import ices.fashion.entity.tools.TOutfitShoes;
import ices.fashion.entity.tools.TOutfitUpper;
import ices.fashion.mapper.tools.TOutfitBagMapper;
import ices.fashion.mapper.tools.TOutfitLowerMapper;
import ices.fashion.mapper.tools.TOutfitShoesMapper;
import ices.fashion.mapper.tools.TOutfitUpperMapper;
import ices.fashion.service.tools.OutfitGANService;
import ices.fashion.service.tools.dto.OutfitGANCriteria;
import ices.fashion.service.tools.dto.OutfitGANDto;
import ices.fashion.service.tools.dto.OutfitGANInitDto;
import ices.fashion.service.tools.dto.OutfitGANModelDto;
import ices.fashion.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OutfitGANServiceImpl implements OutfitGANService {

    @Autowired
    private TOutfitBagMapper tOutfitBagMapper;

    @Autowired
    private TOutfitLowerMapper tOutfitLowerMapper;

    @Autowired
    private TOutfitShoesMapper tOutfitShoesMapper;

    @Autowired
    private TOutfitUpperMapper tOutfitUpperMapper;

    @Override
    public ApiResult<OutfitGANDto> doOutfitGAN(OutfitGANCriteria outfitGANCriteria) throws IOException {

        //step1 下载图片和转码
        String upperFileName = outfitGANCriteria.getUpperFileName();
        String shoesFileName = outfitGANCriteria.getShoesFileName();
        String lowerFileName = outfitGANCriteria.getLowerFileName();
        String bagFileName = outfitGANCriteria.getBagFileName();
        File shoes = null, lower = null, bag = null;
        outfitGANCriteria.init();

        String upperFinalUrl = FileUtil.concatUrl(upperFileName);
        File upper = FileUtil.download(upperFinalUrl, upperFileName);
        outfitGANCriteria.setImage(FileUtil.pictureFileToBase64String(upper));

        if (shoesFileName != null && !shoesFileName.isEmpty()) {
            String shoesFinalUrl = FileUtil.concatUrl(shoesFileName);
            shoes = FileUtil.download(shoesFinalUrl, shoesFileName);
        }
        if (shoes == null) {
            shoes = getRandomMaskImage(GANConst.SHOES);
        }
        outfitGANCriteria.setMaskShoes(FileUtil.pictureFileToBase64String(shoes));

        if (lowerFileName != null && !lowerFileName.isEmpty()) {
            String lowerFinalUrl = FileUtil.concatUrl(lowerFileName);
            lower = FileUtil.download(lowerFinalUrl, lowerFileName);
        }
        if (lower == null) {
            lower = getRandomMaskImage(GANConst.LOWER);
        }
        outfitGANCriteria.setMaskLower(FileUtil.pictureFileToBase64String(lower));

        if (bagFileName != null && !bagFileName.isEmpty()) {
            String bagFinalUrl = FileUtil.concatUrl(bagFileName);
            bag = FileUtil.download(bagFinalUrl, bagFileName);
        }
        if (bag == null) {
            bag = getRandomMaskImage(GANConst.BAG);
        }
        outfitGANCriteria.setMaskBag(FileUtil.pictureFileToBase64String(bag));

        //step2 调用模型
        OutfitGANModelDto outfitGANModelDto = doGenerate(outfitGANCriteria);

        boolean isSuccess = FileUtil.uploadFile2Cloud(outfitGANModelDto.getBagFileName());
        isSuccess = isSuccess & FileUtil.uploadFile2Cloud(outfitGANModelDto.getLowerFileName());
        isSuccess = isSuccess & FileUtil.uploadFile2Cloud(outfitGANModelDto.getShoesFileName());

        //step3 删除图片
        File bagGenFile = new File(System.getProperty("user.dir") + File.separator + "img" + File.separator
                + outfitGANModelDto.getBagFileName());
        File lowerGenFile = new File(System.getProperty("user.dir") + File.separator + "img" + File.separator
                + outfitGANModelDto.getLowerFileName());
        File shoesGenFile = new File(System.getProperty("user.dir") + File.separator + "img" + File.separator
                + outfitGANModelDto.getShoesFileName());
        FileUtil.deleteFile(upper, bag, shoes, lower, bagGenFile, lowerGenFile, shoesGenFile);

        //step4 返回结果
        if (!isSuccess) {
            return new ApiResult(800, "七牛云上传换装后图像失败");
        }
        FileUtil.setExpireTime(outfitGANModelDto.getBagFileName());
        FileUtil.setExpireTime(outfitGANModelDto.getShoesFileName());
        FileUtil.setExpireTime(outfitGANModelDto.getLowerFileName());


        ApiResult<OutfitGANDto> res = new ApiResult(200, "success");
        String bagGenUrl = QiniuCloudConst.DOMAIN_BUCKET + "/" + outfitGANModelDto.getBagFileName();
        String lowerGenUrl = QiniuCloudConst.DOMAIN_BUCKET + "/" + outfitGANModelDto.getLowerFileName();
        String shoesGenUrl = QiniuCloudConst.DOMAIN_BUCKET + "/" + outfitGANModelDto.getShoesFileName();
        res.setData(new OutfitGANDto(bagGenUrl, shoesGenUrl, lowerGenUrl));
        System.out.println(bagGenUrl + "\n" + lowerGenUrl + "\n" + shoesGenUrl);
        return res;
    }

    @Override
    public ApiResult<OutfitGANInitDto> init() {
        List<TOutfitBag> tOutfitBagList = tOutfitBagMapper.selectList(null);
        List<TOutfitLower> tOutfitLowerList = tOutfitLowerMapper.selectList(null);
        List<TOutfitShoes> tOutfitShoesList = tOutfitShoesMapper.selectList(null);
        List<TOutfitUpper> tOutfitUpperList = tOutfitUpperMapper.selectList(null);
        ApiResult<OutfitGANInitDto> res = new ApiResult(200, "success");
        res.setData(new OutfitGANInitDto(
                tOutfitUpperList.stream().filter(e -> e.getDeleted() == 0).map(TOutfitUpper::getFileName)
                        .collect(Collectors.toList()),
                tOutfitShoesList.stream().filter(e -> e.getDeleted() == 0).map(TOutfitShoes::getFileName)
                        .collect(Collectors.toList()),
                tOutfitLowerList.stream().filter(e -> e.getDeleted() == 0).map(TOutfitLower::getFileName)
                        .collect(Collectors.toList()),
                tOutfitBagList.stream().filter(e -> e.getDeleted() == 0).map(TOutfitBag::getFileName)
                        .collect(Collectors.toList())));
        return res;
    }

    private OutfitGANModelDto doGenerate(OutfitGANCriteria outfitGANCriteria) {

        String generateUrl = GANConst.OUTFIT_GAN_BASE_URL + GANConst.OUTFIT_GAN;
        //设置超时时间
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(12000);
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        List<MediaType> mediaTypeList = new ArrayList<>();
        mediaTypeList.add(MediaType.APPLICATION_JSON);
        headers.setAccept(mediaTypeList);
        Gson gson = new Gson();
        String content = '[' + gson.toJson(outfitGANCriteria) + ']';
//        System.out.println(content);

        HttpEntity<String> httpEntity = new HttpEntity<>(content, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(generateUrl, HttpMethod.POST, httpEntity, String.class);

        String bagFileName = "outfit_gan_bag_" + System.currentTimeMillis() + ".jpg";
        String shoesFileName = "outfit_gan_shoes_" + System.currentTimeMillis() + ".jpg";
        String lowerFileName = "outfit_gan_lower_" + System.currentTimeMillis() + ".jpg";

        List<OutfitGANModelDto> outfitGANModelDtoList = gson.fromJson(responseEntity.getBody(),
                new TypeToken<List<OutfitGANModelDto>>(){}.getType());
//        System.out.println(responseEntity.getStatusCode());
//        System.out.println(responseEntity.getHeaders());
        OutfitGANModelDto resp = outfitGANModelDtoList.get(0);

        byte[] shoesBase64decodedBytes = FileUtil.base64StringToBytes(resp.getImageShoes());
        byte[] lowerBase64decodedBytes = FileUtil.base64StringToBytes(resp.getImageLower());
        byte[] bagBase64decodedBytes = FileUtil.base64StringToBytes(resp.getImageBag());
        FileUtil.createFile(bagFileName, bagBase64decodedBytes);
        FileUtil.createFile(shoesFileName, shoesBase64decodedBytes);
        FileUtil.createFile(lowerFileName, lowerBase64decodedBytes);
        resp.setBagFileName(bagFileName);
        resp.setShoesFileName(shoesFileName);
        resp.setLowerFileName(lowerFileName);

        return resp;

    }

    //无掩码图像后端随机给一个数据库中的
    private File getRandomMaskImage(String category) throws UnsupportedEncodingException {


        String fileName = null;

        if (category.equals(GANConst.SHOES)) {
            QueryWrapper<TOutfitShoes> tOutfitShoesQueryWrapper = new QueryWrapper<>();
            tOutfitShoesQueryWrapper.last("limit 1");
            TOutfitShoes tOutfitShoes = tOutfitShoesMapper.selectOne(tOutfitShoesQueryWrapper);
            fileName = tOutfitShoes.getFileName();
        } else if (category.equals(GANConst.BAG)) {
            QueryWrapper<TOutfitBag> tOutfitBagQueryWrapper = new QueryWrapper<>();
            tOutfitBagQueryWrapper.last("limit 1");
            TOutfitBag tOutfitBag = tOutfitBagMapper.selectOne(tOutfitBagQueryWrapper);
            fileName = tOutfitBag.getFileName();
        } else if (category.equals(GANConst.LOWER)) {
            QueryWrapper<TOutfitLower> tOutfitLowerQueryWrapper = new QueryWrapper<>();
            tOutfitLowerQueryWrapper.last("limit 1");
            TOutfitLower tOutfitLower = tOutfitLowerMapper.selectOne(tOutfitLowerQueryWrapper);
            fileName = tOutfitLower.getFileName();
        } else {}
        String finalUrl = FileUtil.concatUrl(fileName);
        return FileUtil.download(finalUrl, fileName);
    }
}
