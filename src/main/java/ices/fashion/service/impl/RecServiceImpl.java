package ices.fashion.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import ices.fashion.constant.ApiResult;
import ices.fashion.constant.RecConst;
import ices.fashion.constant.ResultMessage;
import ices.fashion.controller.RecController;
import ices.fashion.entity.TBaseMaterial;
import ices.fashion.entity.TBaseMaterialBrand;
import ices.fashion.entity.TBaseMaterialCategory;
import ices.fashion.entity.TBaseSuit;
import ices.fashion.mapper.TBaseMaterialBrandMapper;
import ices.fashion.mapper.TBaseMaterialCategoryMapper;
import ices.fashion.mapper.TBaseMaterialMapper;
import ices.fashion.mapper.TBaseSuitMapper;
import ices.fashion.service.RecService;
import ices.fashion.service.dto.MaterialPageCriteria;
import ices.fashion.service.dto.RecCriteria;
import ices.fashion.service.dto.RecDto;
import ices.fashion.util.FileUtil;
import javafx.scene.paint.Material;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.print.attribute.standard.Media;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RecServiceImpl implements RecService {
    @Autowired
    private TBaseMaterialBrandMapper tBaseMaterialBrandMapper;
    @Autowired
    private TBaseMaterialCategoryMapper tBaseMaterialCategoryMapper;
    @Autowired
    private TBaseMaterialMapper tBaseMaterialMapper;
    @Autowired
    private TBaseSuitMapper tBaseSuitMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(RecServiceImpl.class);

    /*
    查询所有状态为1的品牌
     */
    @Override
    public List<TBaseMaterialBrand> selectAllBrands() {
        Map<String, Object> conditions = new HashMap<>();
        conditions.put("status", 1);
        return tBaseMaterialBrandMapper.selectByMap(conditions);
    }

    @Override
    public List<TBaseMaterialCategory> selectAllCategories() {
        Map<String, Object> conditions = new HashMap<>();
        conditions.put("status", 1);
        return tBaseMaterialCategoryMapper.selectByMap(conditions);
    }

//    @Override
//    public IPage<TBaseMaterial> selectMaterialsByPage(Integer categoryId, List<Integer> brandIds, Integer currentPage, Integer pageSize) {
//        // 查询对象
//        QueryWrapper<TBaseMaterial> wrapper = new QueryWrapper<>();
//        wrapper.eq("category_id", categoryId);
//        wrapper.in("brand_id", brandIds);
//        wrapper.eq("status", 1);
//        // 分页 param1：第几页，param2：每页记录数
//        Page<TBaseMaterial> page = new Page<>(currentPage, pageSize);
//        IPage<TBaseMaterial> iPage = tBaseMaterialMapper.selectPage(page, wrapper);
//        return iPage;
//    }
    @Override
    public IPage<TBaseMaterial> selectMaterialsByPage(MaterialPageCriteria materialPageCriteria) {
        // 查询对象
        QueryWrapper<TBaseMaterial> wrapper = new QueryWrapper<>();
        wrapper.eq("category_id", materialPageCriteria.getCategoryId());
        wrapper.in("brand_id", materialPageCriteria.getBrandIds());
        wrapper.eq("status", 1);
        // 分页 param1：第几页，param2：每页记录数
        Page<TBaseMaterial> page = new Page<>(materialPageCriteria.getCurrentPage(), materialPageCriteria.getPageSize());
        IPage<TBaseMaterial> iPage = tBaseMaterialMapper.selectPage(page, wrapper);
        return iPage;
    }

    @Override
    public List<TBaseMaterial> selectMaterialsByIds(List<Integer> idsList) {
        return tBaseMaterialMapper.selectBatchIds(idsList);
    }

    @Override
    public ApiResult insertSuit(String name, String description, String materialIds, Integer customerId, BigDecimal price, Integer status, MultipartFile multipartFile) {
        String imgUrl = FileUtil.uploadMultipartFile(multipartFile); // 上传图片
        TBaseSuit tBaseSuit = new TBaseSuit();
        tBaseSuit.setCustomerId(customerId);
        tBaseSuit.setName(name);
        tBaseSuit.setMaterialIds(materialIds);
        tBaseSuit.setImgUrl(imgUrl);
        tBaseSuit.setStatus(status);
        tBaseSuit.setAuditStatus(0);
        tBaseSuit.setPrice(price);
        tBaseSuit.setDescription(description);
        tBaseSuitMapper.insert(tBaseSuit);
        return new ApiResult(ResultMessage.RESULT_SUCCESS_1);
    }

    @Override
    public Map<Integer, String> selectRecCategoryMap() {
        Map<Integer, String> recCategoryMap = new HashMap<>(2);
        List<TBaseMaterialCategory> categories = selectAllCategories();
        for (TBaseMaterialCategory category: categories) {
            if(category.getStatus() != 0 && ! category.getRecCategory().equals("invalid")) {
                // 有效种类
                recCategoryMap.put(category.getId(), category.getRecCategory());
            }
        }
        return recCategoryMap;
    }

    @Override
    /**
     * 请求推荐
     */
    public List<TBaseMaterial> reqRecommendations(List<Integer> itemIds, String matchType) throws IOException {
        // 得到图片地址列表
        List<TBaseMaterial> materials = selectMaterialsByIds(itemIds);
        List<String> urlList = materials.stream().map(TBaseMaterial::getImgUrl).collect(Collectors.toList());
        // 获取对应图片
        List<File> files = new ArrayList<>();
        List<String> images = new ArrayList<>();
        for (String url: urlList) {
            String fileName = FileUtil.url2FileName(url);
            File file = FileUtil.download(url, fileName); // 下载到本地
            files.add(file);
            images.add(FileUtil.pictureFileToBase64String(file));
        }
        if(images.size() != 3) return new ArrayList<>();
        RecCriteria recCriteria = new RecCriteria();
        recCriteria.init(images.get(0), images.get(1), images.get(2), matchType);
        // 调用模型
        RecDto recDto = getRecommended(recCriteria);
        // 删除图片
        FileUtil.deleteFile(files.toArray(new File[files.size()]));
        // 根据链接获取对应素材
        if(recDto == null) {
            return new ArrayList<>();
        }
        Map<String, Object> conditions = new HashMap<>();
        String recUrl = FileUtil.concatUrlwithoutEncoding(recDto.getFilename());
        conditions.put("img_url", recUrl);
        LOGGER.info(conditions.toString());
        return tBaseMaterialMapper.selectByMap(conditions);
    }


    private RecDto getRecommended(RecCriteria recCriteria) {
        String recUrl = RecConst.REC_URL;
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        List<MediaType> mediaTypeList = new ArrayList<>();
        mediaTypeList.add(MediaType.APPLICATION_JSON);
        headers.setAccept(mediaTypeList);
        Gson gson = new Gson();
        String content = '[' + gson.toJson(recCriteria) + ']';
//        System.out.println(content);

        HttpEntity<String> httpEntity = new HttpEntity<>(content, headers);
        long startTime = System.currentTimeMillis();
        ResponseEntity<String> responseEntity = restTemplate.exchange(recUrl, HttpMethod.POST, httpEntity, String.class);
//        LOGGER.info(responseEntity.toString());
        long endTime = System.currentTimeMillis();
        LOGGER.info("Rec Model time: " + (endTime - startTime) / 1000);
        List<RecDto> recDtoList = gson.fromJson(responseEntity.getBody(), new TypeToken<List<RecDto>>(){}.getType());
        return recDtoList.get(0);
    }
}
