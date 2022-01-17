package ices.fashion;

import ices.fashion.entity.collaborate.ColComment;
import ices.fashion.entity.collaborate.ColCommentQuery;
import ices.fashion.entity.collaborate.ColMember;
import ices.fashion.mapper.collaborate.ColCommentMapper;
import ices.fashion.mapper.collaborate.ColProjectMapper;
import ices.fashion.mapper.collaborate.ColVersionMapper;
import ices.fashion.service.ColCommentService;
import ices.fashion.service.ColMemberService;
import ices.fashion.service.ColProjectService;
import ices.fashion.service.ColVersionService;
import ices.fashion.service.dto.collaborate.ColCommentDto;
import ices.fashion.service.dto.collaborate.ColProjectDto;
import ices.fashion.service.dto.collaborate.ColVersionDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class FashionApplicationTests {

    @Autowired
    private ColCommentMapper colCommentMapper;

    @Autowired
    private ColProjectMapper colProjectMapper;

    @Autowired
    private ColVersionService colVersionService;

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

    @Test
    void testProject(){
        Integer n = new Integer(1);
//        List<ColProjectDto> colProjectDtoList = colProjectMapper.selectProjectbyMemberId(n);
//        for(ColProjectDto colProjectDto : colProjectDtoList){
//            System.out.println(colProjectDto);
//        }
        ColProjectDto colProjectDto  = colProjectService.getProjectbyPid(6);
        System.out.println(colProjectDto);
    }

    @Test
    void testVersion(){
        List<ColVersionDto> colVersionDtoList = colVersionService.getVersion(16);
        for(ColVersionDto colVersionDto : colVersionDtoList){
            System.out.println(colVersionDto);
        }
    }

    @Test
    void testInsertVersion(){
        //有父节点
//        colVersionService.insertVersion(1,"a","aa",1);

        //没有父节点
        colVersionService.insertVersion(6,"a","kfhyiu",-1);
    }

    @Autowired
    ColProjectService colProjectService;

    @Autowired
    ColMemberService colMemberService;

    @Test
    void testInsertProject()    {
        int id = colProjectService.insertProject(1,"2022","2022");
        int mid = colMemberService.insertMember(id,1);
        System.out.println(mid);
    }

}
