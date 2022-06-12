package ices.fashion.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import ices.fashion.entity.recommendation.TBaseMaterial;
import ices.fashion.mapper.recommendation.TBaseMaterialMapper;
import ices.fashion.service.TestService;
import ices.fashion.service.dto.TestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private TBaseMaterialMapper tBaseMaterialMapper;

    @Override
    public List<TestDto> get10Material() {
        QueryWrapper<TBaseMaterial> tBaseMaterialQueryWrapper = new QueryWrapper<>();
        tBaseMaterialQueryWrapper.last("limit 10");
        List<TBaseMaterial> tBaseMaterialList = tBaseMaterialMapper.selectList(tBaseMaterialQueryWrapper);
        List<TestDto> res = new ArrayList<>();
        for (TBaseMaterial tBaseMaterial : tBaseMaterialList) {
            res.add(new TestDto(tBaseMaterial.getId(), tBaseMaterial.getImgUrl(), tBaseMaterial.getCategoryId(),
                    tBaseMaterial.getBrandId(), tBaseMaterial.getLinkUrl(), tBaseMaterial.getPrice(),
                    tBaseMaterial.getStatus(), tBaseMaterial.getDescription(), tBaseMaterial.getTargetPopulation(),
                    true));
        }
        return res;
    }
}
