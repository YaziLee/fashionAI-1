package ices.fashion.service.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import ices.fashion.constant.ApiResult;
import ices.fashion.constant.GANConst;
import ices.fashion.constant.QiniuCloudConst;
import ices.fashion.service.OutfitGANService;
import ices.fashion.service.dto.*;
import ices.fashion.util.FileUtil;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

@Service
public class OutfitGANServiceImpl implements OutfitGANService {

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
        if (shoesFileName != null && !shoesFileName.isEmpty()) {
            String shoesFinalUrl = FileUtil.concatUrl(shoesFileName);
            shoes = FileUtil.download(shoesFinalUrl, shoesFileName);
        }
        if (lowerFileName != null && !lowerFileName.isEmpty()) {
            String lowerFinalUrl = FileUtil.concatUrl(lowerFileName);
            lower = FileUtil.download(lowerFinalUrl, lowerFileName);
        }
        if (bagFileName != null && !bagFileName.isEmpty()) {
            String bagFinalUrl = FileUtil.concatUrl(bagFileName);
            bag = FileUtil.download(bagFinalUrl, bagFileName);
        }

        //三个掩码图片没有选择就从数据库随机分配
        if (shoes == null) {
            shoes = getRandomMaskImage(GANConst.SHOES);
        }
        if (lower == null) {
            lower = getRandomMaskImage(GANConst.LOWER);
        }
        if (bag == null) {
            bag = getRandomMaskImage(GANConst.BAG);
        }
        outfitGANCriteria.setImage(FileUtil.pictureFileToBase64String(upper));
        outfitGANCriteria.setMaskShoes(FileUtil.pictureFileToBase64String(shoes));
        outfitGANCriteria.setMaskLower(FileUtil.pictureFileToBase64String(lower));
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

    private OutfitGANModelDto doGenerate(OutfitGANCriteria outfitGANCriteria) {

        String generateUrl = GANConst.OUTFIT_GAN_BASE_URL + GANConst.OUTFIT_GAN;
        RestTemplate restTemplate = new RestTemplate();
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

    private File getRandomMaskImage(String category) throws UnsupportedEncodingException {
        /*
        todo
        每个样例都要去数据库随机找一个fileName
        目前写死了
         */
        String fileName = null;

        if (category.equals(GANConst.SHOES)) {
            fileName = "001aeb1dc1adbcb6a36060961f92843e_shoes.jpg";
        } else if (category.equals(GANConst.BAG)) {
            fileName = "001aeb1dc1adbcb6a36060961f92843e_bag.jpg";
        } else if (category.equals(GANConst.LOWER)) {
            fileName = "001aeb1dc1adbcb6a36060961f92843e_lower.jpg";
        } else {}
        String finalUrl = FileUtil.concatUrl(fileName);
        return FileUtil.download(finalUrl, fileName);
    }
}
