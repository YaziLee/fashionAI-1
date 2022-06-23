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
        outfitGANCriteria.init();

        String upperFinalUrl = FileUtil.concatUrl(upperFileName);
        outfitGANCriteria.setImage(upperFinalUrl);

        String shoesFinalUrl = null, lowerFinalUrl = null, bagFinalUrl = null;
        if (shoesFileName != null && !shoesFileName.isEmpty()) {
            shoesFinalUrl = FileUtil.concatUrl(shoesFileName);
        } else {
            shoesFinalUrl = getRandomMaskImage(GANConst.SHOES);
        }
        outfitGANCriteria.setMaskShoes(shoesFinalUrl);

        if (lowerFileName != null && !lowerFileName.isEmpty()) {
            lowerFinalUrl = FileUtil.concatUrl(lowerFileName);
        } else {
            lowerFinalUrl = getRandomMaskImage(GANConst.LOWER);
        }
        outfitGANCriteria.setMaskLower(lowerFinalUrl);

        if (bagFileName != null && !bagFileName.isEmpty()) {
            bagFinalUrl = FileUtil.concatUrl(bagFileName);
        } else {
            bagFinalUrl = getRandomMaskImage(GANConst.BAG);
        }
        outfitGANCriteria.setMaskBag(bagFinalUrl);

        //step2 调用模型
        OutfitGANModelDto outfitGANModelDto = doGenerate(outfitGANCriteria);


        ApiResult<OutfitGANDto> res = new ApiResult(200, "success");
        res.setData(new OutfitGANDto(outfitGANModelDto.getBagFileName(), outfitGANModelDto.getShoesFileName(),
                outfitGANModelDto.getLowerFileName()));
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
        System.out.println(content);

        HttpEntity<String> httpEntity = new HttpEntity<>(content, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(generateUrl, HttpMethod.POST, httpEntity, String.class);

        List<OutfitGANModelDto> outfitGANModelDtoList = gson.fromJson(responseEntity.getBody(),
                new TypeToken<List<OutfitGANModelDto>>(){}.getType());
//        System.out.println(responseEntity.getStatusCode());
//        System.out.println(responseEntity.getHeaders());
        OutfitGANModelDto resp = outfitGANModelDtoList.get(0);

        resp.setBagFileName(resp.getImageBag());
        resp.setShoesFileName(resp.getImageShoes());
        resp.setLowerFileName(resp.getImageLower());

        return resp;

    }

    //无掩码图像后端随机给一个数据库中的
    private String getRandomMaskImage(String category) throws UnsupportedEncodingException {


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
        return FileUtil.concatUrl(fileName);
    }
}
