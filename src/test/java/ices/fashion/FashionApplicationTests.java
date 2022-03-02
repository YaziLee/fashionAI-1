package ices.fashion;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import ices.fashion.constant.ApiResult;
import ices.fashion.entity.TMmc;
import ices.fashion.entity.collaborate.ColComment;
import ices.fashion.entity.collaborate.ColCommentQuery;
import ices.fashion.entity.collaborate.ColProject;
import ices.fashion.mapper.MMCGANMapper;
import ices.fashion.mapper.collaborate.ColCommentMapper;
import ices.fashion.mapper.collaborate.ColProjectMapper;
import ices.fashion.mapper.collaborate.ColVersionMapper;
import ices.fashion.service.ColCommentService;
import ices.fashion.service.ColProjectService;
import ices.fashion.service.ColVersionService;
import ices.fashion.service.dto.OutfitGANInitDto;
import ices.fashion.service.dto.collaborate.ColCommentDto;
import ices.fashion.service.dto.collaborate.ColProjectDto;
import ices.fashion.service.dto.collaborate.ColVersionDto;
import ices.fashion.service.*;
import ices.fashion.service.dto.MMCGANCriteria;
import ices.fashion.service.dto.OutfitGANCriteria;
import ices.fashion.service.dto.RenderGANCriteria;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

@SpringBootTest
class FashionApplicationTests {

    @Autowired
    private MMCGANService mmcganService;

    @Autowired
    private OutfitGANService outfitGANService;

    @Autowired
    private RenderGANService renderGANService;

    @Autowired
    private ColCommentMapper colCommentMapper;

    @Autowired
    private ColProjectMapper colProjectMapper;

    @Autowired
    protected ColProjectService colProjectService;

    @Autowired
    private ColVersionService colVersionService;

    @Autowired
    private MMCGANMapper mmcganMapper;

    @Test
    void contextLoads() {
    }

    @Autowired
    ColCommentService colCommentService;

    @Test
    void testColComment() {
        List<ColCommentDto> colCommentList = colCommentService.getCommentbyVersion(1);
        for (ColCommentDto colComment : colCommentList) {
            System.out.println(colComment);
        }
    }

    @Autowired
    private UploadTokenService uploadTokenService;

    @Test
    void testProject(){
        Integer n = new Integer(1);
        List<ColProjectDto> colProjectDtoList = colProjectMapper.selectProjectbyMemberId(n);
        for(ColProjectDto colProjectDto : colProjectDtoList){
            System.out.println(colProjectDto);
        }
    }

    @Test
    void testProjectbyId(){
        ColProjectDto colProjectDto = colProjectService.getProjectbyId(1);
        System.out.print(colProjectDto);
    }

    @Test
    void testInsertProject(){
        ColProject project = colProjectService.insertProject(2,"123","123");
        System.out.println(project);
    }

    @Test
    void testVersion(){
        List<ColVersionDto> colVersionDtoList = colVersionService.getVersion(1);
        for(ColVersionDto colVersionDto : colVersionDtoList){
            System.out.println(colVersionDto);
        }
    }

    @Test
    void testInsertVersion(){
        colVersionService.insertVersion(1,"a","aa",-1);
    }


    @Test
    void testGson() throws IOException {
        MMCGANCriteria mmcganCriteria = new MMCGANCriteria();
        mmcganCriteria.setId(1);
        mmcganCriteria.setOriginalText("Womens Fashion Outerwear Jackets Balenciaga jackets Balenciaga Cropped " +
                "embellished woolblend tweed jacket");
        mmcganCriteria.setTargetCategory("Skirts");
        mmcganCriteria.setTargetText("Womens Fashion Skirts Mini Skirts Helmut Lang mini skirts Helmut Lang " +
                "Layered Skirt");
        mmcganCriteria.setFileName("e8fd2028d0462196fdd3fbc9b27bbbcc.jpg");
        mmcganService.doMMCGAN(mmcganCriteria);
    }

    @Test
    void testOutfitGAN() throws IOException {
        OutfitGANCriteria outfitGANCriteria = new OutfitGANCriteria();
        outfitGANCriteria.setUpperFileName("001aeb1dc1adbcb6a36060961f92843e.jpg");
        outfitGANCriteria.setShoesFileName("001aeb1dc1adbcb6a36060961f92843e_shoes.jpg");
//        outfitGANCriteria.setLowerFileName("001aeb1dc1adbcb6a36060961f92843e_lower.jpg");
        outfitGANCriteria.setBagFileName("001aeb1dc1adbcb6a36060961f92843e_bag.jpg");
        outfitGANService.doOutfitGAN(outfitGANCriteria);
    }

    @Test
    void testRender() throws IOException {
        RenderGANCriteria renderGANCriteria = new RenderGANCriteria();
        renderGANCriteria.setColorFileName("render_1.jpg");
        renderGANCriteria.setSketchFileName("000000221f00000b30e99368f4b72eed.jpg");
        renderGANService.doRenderGenerate(renderGANCriteria);
    }

    @Test
    void testUploadToken() {
        String token = uploadTokenService.getUploadToken();
        System.out.println(token);
    }

    @Test
    void testMMCGANInit() throws IOException {
        ApiResult<List<TMmc>> res =  mmcganService.init();
        System.out.println(res.getData());
        QueryWrapper<TMmc> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("file_name", "e8fd2028d0462196fdd3fbc9b27bbbcc.jpg");
        TMmc cur = mmcganMapper.selectOne(queryWrapper);
        System.out.println(cur);
    }

    @Test
    void testOutfitGANInit() throws IOException {
        ApiResult<OutfitGANInitDto> res = outfitGANService.init();
        System.out.println(res.getData());
    }

    @Test
    void testSplit() {
        String s = "fashion/outfit-gan/upper/001eeda1267459a4c5dfe924c8f0468e.jpg";
        String[] ss = s.split("/");
        System.out.println(ss[ss.length - 1]);
    }

}
