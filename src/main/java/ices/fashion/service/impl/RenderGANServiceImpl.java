package ices.fashion.service.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import ices.fashion.constant.ApiResult;
import ices.fashion.constant.GANConst;
import ices.fashion.constant.QiniuCloudConst;
import ices.fashion.service.RenderGANService;
import ices.fashion.service.dto.*;
import ices.fashion.util.FileUtil;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class RenderGANServiceImpl implements RenderGANService {

    @Override
    public ApiResult<RenderGANDto> doRenderGenerate(RenderGANCriteria renderGANCriteria) throws IOException {

        //step1 下载图片
        String sketchFileName = renderGANCriteria.getSketchFileName();
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
//        FileUtil.deleteFile(sketch, color, renderFile);

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

    private String doGenerate(RenderGANCriteria renderGANCriteria) {

        String generateUrl = GANConst.RENDER_GAN_BASE_URL + GANConst.RENDER_GAN;
        RestTemplate restTemplate = new RestTemplate();
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
