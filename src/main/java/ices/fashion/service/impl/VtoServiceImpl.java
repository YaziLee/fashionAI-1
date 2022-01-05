package ices.fashion.service.impl;

import ices.fashion.constant.ApiResult;
import ices.fashion.constant.VtoModelConst;
import ices.fashion.service.VtoService;
import ices.fashion.util.FileUtil;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;


@Service
public class VtoServiceImpl implements VtoService {


    @Override
    public void virtualTryOn(String clothFileName, String modelFileName) throws IOException {

        //step1 下载图片到本地
        String clothFinalUrl = FileUtil.concatUrl(clothFileName);
        String modelFinalUrl = FileUtil.concatUrl(modelFileName);
        File cloth = FileUtil.download(clothFinalUrl, clothFileName);
        File model = FileUtil.download(modelFinalUrl, modelFileName);
        MultipartFile mulCloth = FileUtil.fileToMultipartFile(cloth);
        MultipartFile mulModel = FileUtil.fileToMultipartFile(model);

        //step2 调用模型换装
        uploadCloth(mulCloth);
        uploadImage(mulModel);
        String filePath = tryOn();
        /*
        师兄原先代码，要改的
        FileInputStream inputStream = new FileInputStream(filepath);
		byte[] bytes = new byte[inputStream.available()];
		inputStream.read(bytes, 0, inputStream.available());
		return bytes;
         */

        //step3 删除图片

    }

    @Override
    public ApiResult uploadCloth(MultipartFile clothFile) {
        String clothUrl = VtoModelConst.BASE_URL + VtoModelConst.CLOTH_PATH;
        FileUtil.uploadFile(clothFile, clothUrl, "cloth");
        return new ApiResult(200, "success");
    }

    @Override
    public ApiResult uploadImage(MultipartFile imageFile) {
        String imageUrl = VtoModelConst.BASE_URL + VtoModelConst.IMAGE_PATH;
        FileUtil.uploadFile(imageFile, imageUrl, "image");
        return new ApiResult(200, "success");
    }

    @Override
    public String tryOn() {
        String tryonUrl = VtoModelConst.BASE_URL + VtoModelConst.TRY_ON_PATH;
        // 发起 get 请求
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<byte[]> responseEntity = restTemplate.exchange(tryonUrl, HttpMethod.GET, null, byte[].class);
        String filename = "result.png";
        String filepath = FileUtil.createFile(filename, responseEntity.getBody());
        return filepath;
    }
}
