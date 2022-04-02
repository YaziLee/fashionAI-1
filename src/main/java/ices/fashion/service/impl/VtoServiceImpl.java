package ices.fashion.service.impl;

import ices.fashion.constant.ApiResult;
import ices.fashion.constant.QiniuCloudConst;
import ices.fashion.constant.VtoModelConst;
import ices.fashion.service.VtoService;
import ices.fashion.service.dto.VtoDto;
import ices.fashion.util.FileUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;


@Service
public class VtoServiceImpl implements VtoService {


    @Override
    public ApiResult<VtoDto> virtualTryOn(String clothFileName, String modelFileName) throws IOException {

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
        String fileName = tryOn();
//        System.out.println(filePath);

        boolean isSuccess = FileUtil.uploadFile2Cloud(fileName);

        //step3 删除图片
        File vtonFile = new File(System.getProperty("user.dir") + File.separator + "img" + File.separator + fileName);
        FileUtil.deleteFile(cloth, model, vtonFile);

        //step4 返回结果
        if (!isSuccess) {
            return new ApiResult(800, "七牛云上传换装后图像失败");
        }
        FileUtil.setExpireTime(fileName);
        ApiResult<VtoDto> res = new ApiResult(200, "success");
        String fileUrl = QiniuCloudConst.DOMAIN_BUCKET + "/" + fileName;
        res.setData(new VtoDto(fileUrl));
        return res;
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

        String filename = "vton_" + System.currentTimeMillis() + ".png";
        FileUtil.createFile(filename, responseEntity.getBody());
        return filename;
    }
}
