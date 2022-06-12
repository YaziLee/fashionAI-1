package ices.fashion.service.tools.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import ices.fashion.constant.ApiResult;
import ices.fashion.constant.GANConst;
import ices.fashion.constant.QiniuCloudConst;
import ices.fashion.entity.tools.TRender;
import ices.fashion.mapper.tools.RenderMapper;
import ices.fashion.service.tools.RenderGANService;
import ices.fashion.service.tools.dto.RenderGANCriteria;
import ices.fashion.service.tools.dto.RenderGANDto;
import ices.fashion.service.tools.dto.RenderGANModelDto;
import ices.fashion.service.tools.dto.RenderInitDto;
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
public class RenderGANServiceImpl implements RenderGANService {

    @Autowired
    private RenderMapper renderMapper;

    @Override
    public ApiResult<RenderGANDto> doRenderGenerate(RenderGANCriteria renderGANCriteria) throws IOException {

        //step1 sketchFileName得到字段，下载图片
        String[] originFileNameArray = renderGANCriteria.getOriginFileName().split("/");
        String sketchFileName = GANConst.RENDER_SKETCH_FILE_NAME_PREFIX
                + originFileNameArray[originFileNameArray.length - 1];
        String sketchFinalUrl = FileUtil.concatUrl(sketchFileName);
        File sketch = FileUtil.download(sketchFinalUrl, sketchFileName);
        String colorFileName = renderGANCriteria.getColorFileName();
        String colorFinalUrl = FileUtil.concatUrl(colorFileName);
        File color = FileUtil.download(colorFinalUrl, colorFileName);

        //step2 图片转string并初始化并调用模型
        renderGANCriteria.setSketch(FileUtil.pictureFileToBase64String(sketch));
        renderGANCriteria.setColor(FileUtil.pictureFileToBase64String(color));
        renderGANCriteria.init();
        String fileName = doGenerate(renderGANCriteria);

        boolean isSuccess = FileUtil.uploadFile2Cloud(fileName);

        //step3 删除图片
        File renderFile = new File(System.getProperty("user.dir") + File.separator + "img" + File.separator + fileName);
        FileUtil.deleteFile(sketch, color, renderFile);

        //step4 返回结果
        if (!isSuccess) {
            return new ApiResult(800, "七牛云上传换装后图像失败");
        }
        FileUtil.setExpireTime(fileName);
        ApiResult<RenderGANDto> res = new ApiResult(200, "success");
        String fileUrl = QiniuCloudConst.DOMAIN_BUCKET + "/" + fileName;
        res.setData(new RenderGANDto(fileUrl));
        System.out.println(fileUrl);
        return res;
    }

    @Override
    public ApiResult<RenderInitDto> init() {
        QueryWrapper<TRender> tRenderQueryWrapper = new QueryWrapper<>();
        tRenderQueryWrapper.eq("type", "origin");
        List<TRender> tRenderList = renderMapper.selectList(tRenderQueryWrapper);
        RenderInitDto renderInitDto = new RenderInitDto();
        renderInitDto.setBagList(tRenderList.stream().filter(e -> e.getCategory().equals(GANConst.BAG))
                .filter(e -> e.getDeleted() == 0).map(TRender::getFileName).collect(Collectors.toList()));
        renderInitDto.setHatList(tRenderList.stream().filter(e -> e.getCategory().equals(GANConst.HAT))
                .filter(e -> e.getDeleted() == 0).map(TRender::getFileName).collect(Collectors.toList()));
        renderInitDto.setJacketList(tRenderList.stream().filter(e -> e.getCategory().equals(GANConst.JACKET))
                .filter(e -> e.getDeleted() == 0).map(TRender::getFileName).collect(Collectors.toList()));
        renderInitDto.setJeansList(tRenderList.stream().filter(e -> e.getCategory().equals(GANConst.JEANS))
                .filter(e -> e.getDeleted() == 0).map(TRender::getFileName).collect(Collectors.toList()));
        renderInitDto.setShortsList(tRenderList.stream().filter(e -> e.getCategory().equals(GANConst.SHORTS))
                .filter(e -> e.getDeleted() == 0).map(TRender::getFileName).collect(Collectors.toList()));
        renderInitDto.setSkirtList(tRenderList.stream().filter(e -> e.getCategory().equals(GANConst.SKIRT))
                .filter(e -> e.getDeleted() == 0).map(TRender::getFileName).collect(Collectors.toList()));
        renderInitDto.setTopList(tRenderList.stream().filter(e -> e.getCategory().equals(GANConst.TOP))
                .filter(e -> e.getDeleted() == 0).map(TRender::getFileName).collect(Collectors.toList()));

        ApiResult<RenderInitDto> res = new ApiResult(200, "success");
        res.setData(renderInitDto);
        return res;
    }

    private String doGenerate(RenderGANCriteria renderGANCriteria) {

        String generateUrl = GANConst.RENDER_GAN_BASE_URL + GANConst.RENDER_GAN;
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
        String content = '[' + gson.toJson(renderGANCriteria) + ']';
//        System.out.println(content);

        HttpEntity<String> httpEntity = new HttpEntity<>(content, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(generateUrl, HttpMethod.POST, httpEntity, String.class);

        String filename = "render_" + System.currentTimeMillis() + ".jpg";
        List<RenderGANModelDto> renderGANModelDtoList = gson.fromJson(responseEntity.getBody(),
                new TypeToken<List<RenderGANModelDto>>(){}.getType());

        byte[] base64decodedBytes = FileUtil.base64StringToBytes(renderGANModelDtoList.get(0).getImage());
        FileUtil.createFile(filename, base64decodedBytes);
        return filename;

    }
}
