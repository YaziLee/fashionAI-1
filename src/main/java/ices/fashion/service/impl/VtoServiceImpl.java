package ices.fashion.service.impl;

import ices.fashion.constant.ApiResult;
import ices.fashion.constant.QiniuCloudConst;
import ices.fashion.constant.VtoModelConst;
import ices.fashion.entity.TMmc;
import ices.fashion.entity.TVton;
import ices.fashion.mapper.TVtonMapper;
import ices.fashion.service.VtoService;
import ices.fashion.service.dto.MMCGANInitDto;
import ices.fashion.service.dto.VtoDto;
import ices.fashion.service.dto.VtonInitDto;
import ices.fashion.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class VtoServiceImpl implements VtoService {

    @Autowired
    private TVtonMapper tVtonMapper;


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
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(90000); //90s
        RestTemplate restTemplate = new RestTemplate(factory);
        ResponseEntity<byte[]> responseEntity = restTemplate.exchange(tryonUrl, HttpMethod.GET, null, byte[].class);

        String filename = "vton_" + System.currentTimeMillis() + ".png";
        FileUtil.createFile(filename, responseEntity.getBody());
        return filename;
    }

    @Override
    public ApiResult<VtonInitDto> init() {
        List<TVton> tVtonList = tVtonMapper.selectList(null);
        VtonInitDto vtonInitDto = new VtonInitDto();
        vtonInitDto.setClothMaleUpperLongList(tVtonList.stream()
                .filter(e -> e.getCategory().equals(VtoModelConst.CLOTH_MALE_UPPER_LONG))
                .collect(Collectors.toList()));
        vtonInitDto.setClothMaleUpperShortList(tVtonList.stream()
                .filter(e -> e.getCategory().equals(VtoModelConst.CLOTH_MALE_UPPER_SHORT))
                .collect(Collectors.toList()));
        vtonInitDto.setImageMaleUpperLongList(tVtonList.stream()
                .filter(e -> e.getCategory().equals(VtoModelConst.IMAGE_MALE_UPPER_LONG))
                .collect(Collectors.toList()));
        vtonInitDto.setImageMaleUpperShortList(tVtonList.stream()
                .filter(e -> e.getCategory().equals(VtoModelConst.IMAGE_MALE_UPPER_SHORT))
                .collect(Collectors.toList()));
        vtonInitDto.setClothFemaleUpperNoList(tVtonList.stream()
                .filter(e -> e.getCategory().equals(VtoModelConst.CLOTH_FEMALE_UPPER_NO))
                .collect(Collectors.toList()));
        vtonInitDto.setClothFemaleUpperShortList(tVtonList.stream()
                .filter(e -> e.getCategory().equals(VtoModelConst.CLOTH_FEMALE_UPPER_SHORT))
                .collect(Collectors.toList()));
        vtonInitDto.setImageFemaleUpperNoList(tVtonList.stream()
                .filter(e -> e.getCategory().equals(VtoModelConst.IMAGE_FEMALE_UPPER_NO))
                .collect(Collectors.toList()));
        vtonInitDto.setImageFemaleUpperShortList(tVtonList.stream()
                .filter(e -> e.getCategory().equals(VtoModelConst.IMAGE_FEMALE_UPPER_SHORT))
                .collect(Collectors.toList()));
        ApiResult<VtonInitDto> res = new ApiResult(200, "success");
        res.setData(vtonInitDto);
        return res;
    }
}
