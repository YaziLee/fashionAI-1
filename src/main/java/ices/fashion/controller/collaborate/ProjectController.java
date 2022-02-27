package ices.fashion.controller.collaborate;

import ices.fashion.entity.collaborate.ColProject;
import ices.fashion.service.ColProjectService;
import ices.fashion.service.dto.collaborate.ColProjectDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/collaborate")
public class ProjectController {
    @Autowired
    private ColProjectService colProjectService;

    //根据用户id返回该用户参与（包括创建）的项目
    @GetMapping("/getProjectsbyId")
    public List<ColProjectDto> getProjects(@RequestParam(value="id") int id){
        return colProjectService.getProjectbyMemberId(id);
    }

    //根据项目的id返回项目的信息
    @GetMapping("/getProjectbyId")
    public ColProjectDto getProject(@RequestParam(value="id")int id){
        return colProjectService.getProjectbyId(id);
    }

    //新建项目
    @PostMapping("/insertProject")
    public ColProject insertProject(Integer uid,String name,String description){
        return colProjectService.insertProject(uid,name,description);
    }

    //修改项目
    @PostMapping("/editProject")
    public ColProject editProject(Integer id,String name,String description){
        return colProjectService.editProject(id, name, description);
    }

    //删除项目
    @PostMapping("/deleteProject")
    public int deleteProject(Integer id){
        return colProjectService.deleteProject(id);
    }

    @PostMapping("/finishProject")
    public int finishProject(Integer id){
        return colProjectService.finishProject(id);
    }
}
