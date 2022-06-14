package ices.fashion.controller.collaborate;

import ices.fashion.constant.DataType;
import ices.fashion.constant.ParamType;
import ices.fashion.entity.collaborate.ColProject;
import ices.fashion.service.collaborate.ColProjectService;
import ices.fashion.service.collaborate.dto.ColProjectDto;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
    @ApiOperation(value = "根据用户id返回该用户参与（包括创建）的项目")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", dataType = DataType.INT, paramType = ParamType.QUERY,
            value = "当前用户id")})
    public List<ColProjectDto> getProjects(@RequestParam(value="id") int id){
        return colProjectService.getProjectbyMemberId(id);
    }

    //根据项目的id返回项目的信息
    @GetMapping("/getProjectbyId")
    @ApiOperation(value = "根据项目的id返回项目的信息", notes = "id代表项目id")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", dataType = DataType.INT, paramType = ParamType.QUERY,
            value = "当前项目id")})
    public ColProjectDto getProject(@RequestParam(value="id")int id){
        return colProjectService.getProjectbyId(id);
    }

    //新建项目
    @PostMapping("/insertProject")
    @ApiOperation(value = "新建项目")
    @ApiImplicitParams(
            {@ApiImplicitParam(name = "uid", value = "用户id", required = true, dataType = DataType.INT),
                    @ApiImplicitParam(name = "name", value = "项目名称",required = true,dataType = DataType.STRING),
                    @ApiImplicitParam(name="description", value = "项目描述", required = true, dataType = DataType.STRING)
            }
    )
    public ColProject insertProject(Integer uid,String name,String description){
        return colProjectService.insertProject(uid,name,description);
    }

    //修改项目
    @PostMapping("/editProject")
    @ApiOperation(value = "修改项目的信息")
    @ApiImplicitParams(
            {@ApiImplicitParam(name = "id", value = "项目id", required = true, dataType = DataType.INT),
                    @ApiImplicitParam(name = "name", value = "项目名称",required = true,dataType = DataType.STRING),
                    @ApiImplicitParam(name="description", value = "项目描述", required = true, dataType = DataType.STRING)
            }
    )
    public ColProject editProject(Integer id,String name,String description){
        return colProjectService.editProject(id, name, description);
    }

    //删除项目
    @PostMapping("/deleteProject")
    @ApiOperation(value="删除项目")
    @ApiImplicitParam(name = "id", value = "项目id", required = true, dataType = DataType.INT)
    public int deleteProject(Integer id){
        return colProjectService.deleteProject(id);
    }

    @PostMapping("/finishProject")
    @ApiOperation(value="结束项目",notes = "不能在项目上继续进行设计，但是能够看到已经完成的设计")
    @ApiImplicitParam(name = "id", value = "项目id", required = true, dataType = DataType.INT)
    public int finishProject(Integer id){
        return colProjectService.finishProject(id);
    }
}
