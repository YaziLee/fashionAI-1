package ices.fashion.service.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import ices.fashion.constant.ApiResult;
import ices.fashion.constant.GANConst;
import ices.fashion.constant.QiniuCloudConst;
import ices.fashion.service.MMCGANService;
import ices.fashion.service.dto.MMCGANCriteria;
import ices.fashion.service.dto.MMCGANDto;
import ices.fashion.service.dto.MMCGANModelDto;
import ices.fashion.util.FileUtil;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class MMCGANServiceImpl implements MMCGANService {

    @Override
    public ApiResult<MMCGANDto> doMMCGAN(MMCGANCriteria mmcganCriteria) throws IOException {

        //step1 下载图片
        String clothFileName = mmcganCriteria.getFileName();
        String clothFinalUrl = FileUtil.concatUrl(clothFileName);
        File cloth = FileUtil.download(clothFinalUrl, clothFileName);

        //step2 图片转string并初始化并调用模型
        mmcganCriteria.setOriginalImage(FileUtil.pictureFileToBase64String(cloth));
        mmcganCriteria.init();
        String fileName = doGenerate(mmcganCriteria);

        boolean isSuccess = FileUtil.uploadFile2Cloud(fileName);

        //step3 删除图片
        File mmcganFile = new File(System.getProperty("user.dir") + File.separator + "img" + File.separator + fileName);
        FileUtil.deleteFile(cloth, mmcganFile);

        //step4 返回结果
        if (!isSuccess) {
            return new ApiResult(800, "七牛云上传换装后图像失败");
        }
        FileUtil.setExpireTime(fileName);
        ApiResult<MMCGANDto> res = new ApiResult(200, "success");
        String fileUrl = QiniuCloudConst.DOMAIN_BUCKET + "/" + fileName;
        res.setData(new MMCGANDto(fileUrl));
        System.out.println(fileUrl);
        return res;
    }

    private String doGenerate(MMCGANCriteria mmcganCriteria) {

        String generateUrl = GANConst.MMC_GAN_BASE_URL + GANConst.MMC_GAN;
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        List<MediaType> mediaTypeList = new ArrayList<>();
        mediaTypeList.add(MediaType.APPLICATION_JSON);
        headers.setAccept(mediaTypeList);
        Gson gson = new Gson();
        String content = '[' + gson.toJson(mmcganCriteria) + ']';
//        System.out.println(content);

        HttpEntity<String> httpEntity = new HttpEntity<>(content, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(generateUrl, HttpMethod.POST, httpEntity, String.class);

        String filename = "mmc_gan_" + System.currentTimeMillis() + ".png";
        List<MMCGANModelDto> mmcganModelDtoList = gson.fromJson(responseEntity.getBody(),
                new TypeToken<List<MMCGANModelDto>>(){}.getType());

        byte[] base64decodedBytes = FileUtil.base64StringToBytes(mmcganModelDtoList.get(0).getTargetImage());
        FileUtil.createFile(filename, base64decodedBytes);
        return filename;

    }
}
