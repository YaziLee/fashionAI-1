package ices.fashion.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import ices.fashion.constant.ApiResult;
import ices.fashion.constant.GANConst;
import ices.fashion.constant.QiniuCloudConst;
import ices.fashion.entity.TMmc;
import ices.fashion.mapper.MMCGANMapper;
import ices.fashion.service.MMCGANService;
import ices.fashion.service.dto.MMCGANCriteria;
import ices.fashion.service.dto.MMCGANDto;
import ices.fashion.service.dto.MMCGANInitDto;
import ices.fashion.service.dto.MMCGANModelDto;
import ices.fashion.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class MMCGANServiceImpl implements MMCGANService {

    @Autowired
    private MMCGANMapper mmcganMapper;

    @Override
    public ApiResult<MMCGANDto> doMMCGAN(MMCGANCriteria mmcganCriteria) throws IOException {

        //step1 下载图片
        String clothFileName = mmcganCriteria.getFileName();
        String clothFinalUrl = FileUtil.concatUrl(clothFileName);
        File cloth = FileUtil.download(clothFinalUrl, clothFileName);

        //step2 图片转string，调用DB初始化，调用模型
        QueryWrapper<TMmc> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("file_name", mmcganCriteria.getFileName());
        TMmc cur = mmcganMapper.selectOne(queryWrapper);
        mmcganCriteria.setOriginalText(cur.getOriginalText());
        System.out.println(cur.getOriginalText());
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

    @Override
    public ApiResult<MMCGANInitDto> init() throws IOException {

        List<TMmc> mmcList = mmcganMapper.selectList(null);
        MMCGANInitDto mmcganInitDto = new MMCGANInitDto();
        mmcganInitDto.setJacketList(mmcList.stream().filter(e -> e.getCategory().equals(GANConst.JACKET))
                .map(TMmc::getFileName).collect(Collectors.toList()));
        mmcganInitDto.setJeansList(mmcList.stream().filter(e -> e.getCategory().equals(GANConst.JEANS))
                .map(TMmc::getFileName).collect(Collectors.toList()));
        mmcganInitDto.setOuterwearList(mmcList.stream().filter(e -> e.getCategory().equals(GANConst.OUTERWEAR))
                .map(TMmc::getFileName).collect(Collectors.toList()));
        mmcganInitDto.setPantsList(mmcList.stream().filter(e -> e.getCategory().equals(GANConst.PANTS))
                .map(TMmc::getFileName).collect(Collectors.toList()));
        mmcganInitDto.setShortsList(mmcList.stream().filter(e -> e.getCategory().equals(GANConst.SHORTS))
                .map(TMmc::getFileName).collect(Collectors.toList()));
        mmcganInitDto.setSkirtList(mmcList.stream().filter(e -> e.getCategory().equals(GANConst.SKIRT))
                .map(TMmc::getFileName).collect(Collectors.toList()));
        mmcganInitDto.setSweatshirtHoodyList(mmcList.stream().filter(e -> e.getCategory().equals(GANConst.SWEATSHIRT_HOODY))
                .map(TMmc::getFileName).collect(Collectors.toList()));
        mmcganInitDto.setTopList(mmcList.stream().filter(e -> e.getCategory().equals(GANConst.TOP))
                .map(TMmc::getFileName).collect(Collectors.toList()));
        ApiResult<MMCGANInitDto> res = new ApiResult(200, "success");
        res.setData(mmcganInitDto);
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
