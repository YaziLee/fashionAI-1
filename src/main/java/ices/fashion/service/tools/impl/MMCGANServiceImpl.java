package ices.fashion.service.tools.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import ices.fashion.constant.ApiResult;
import ices.fashion.constant.GANConst;
import ices.fashion.constant.QiniuCloudConst;
import ices.fashion.entity.tools.TMmc;
import ices.fashion.mapper.tools.MMCGANMapper;
import ices.fashion.service.tools.MMCGANService;
import ices.fashion.service.tools.dto.MMCGANCriteria;
import ices.fashion.service.tools.dto.MMCGANDto;
import ices.fashion.service.tools.dto.MMCGANInitDto;
import ices.fashion.service.tools.dto.MMCGANModelDto;
import ices.fashion.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MMCGANServiceImpl implements MMCGANService {

    @Autowired
    private MMCGANMapper mmcganMapper;

    @Override
    public ApiResult<MMCGANDto> doMMCGAN(MMCGANCriteria mmcganCriteria) throws IOException {

        //step1 下载图片
        String clothFileName = mmcganCriteria.getFileName();
        String clothFinalUrl = FileUtil.concatUrl(clothFileName);

        //step2 图片转string，调用DB初始化，调用模型
        QueryWrapper<TMmc> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("file_name", mmcganCriteria.getFileName());
        TMmc cur = mmcganMapper.selectOne(queryWrapper);
        mmcganCriteria.setOriginalText(cur.getOriginalText());
        mmcganCriteria.setOriginalImage(clothFinalUrl);
        mmcganCriteria.init();
        String fileName = doGenerate(mmcganCriteria);

        ApiResult<MMCGANDto> res = new ApiResult(200, "success");
        res.setData(new MMCGANDto(fileName));
        System.out.println(fileName);
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
        String content = '[' + gson.toJson(mmcganCriteria) + ']';
        System.out.println(content);

        HttpEntity<String> httpEntity = new HttpEntity<>(content, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(generateUrl, HttpMethod.POST, httpEntity, String.class);

        List<MMCGANModelDto> mmcganModelDtoList = gson.fromJson(responseEntity.getBody(),
                new TypeToken<List<MMCGANModelDto>>(){}.getType());

        String filename = mmcganModelDtoList.get(0).getTargetImage();
        System.out.println("[Do generate]: " + filename);
        return filename;

    }
}
