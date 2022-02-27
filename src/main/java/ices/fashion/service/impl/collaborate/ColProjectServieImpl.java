package ices.fashion.service.impl.collaborate;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import ices.fashion.entity.collaborate.ColProject;
import ices.fashion.mapper.collaborate.ColProjectMapper;
import ices.fashion.service.ColProjectService;
import ices.fashion.service.dto.collaborate.ColProjectDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ColProjectServieImpl implements ColProjectService {
    @Autowired
    private ColProjectMapper colProjectMapper;

    @Override
    public List<ColProjectDto> getProjectbyMemberId(int id){
        List<ColProjectDto> res = colProjectMapper.selectProjectbyMemberId(id);
        return res;
    }

    @Override
    public ColProjectDto getProjectbyId(int id){
        List<ColProjectDto> res = colProjectMapper.selectProjectbyId(id);
        if(res.isEmpty())
            return null;
        else
            return res.get(0);

    }

    @Override
    public ColProject insertProject(Integer uid,String name,String description){
        ColProject colProject = new ColProject();
        colProject.setPrinciple_id(uid);
        colProject.setName(name);
        colProject.setDescription(description);
        colProject.setStatus(1);
        colProjectMapper.insertProject(colProject);
//        System.out.println(colProject.getId());
        return colProject;
    }

    @Override
    public ColProject editProject(Integer pid,String name,String description){
        ColProject colProject = new ColProject();
        colProject.setId(pid);
        colProject.setName(name);
        colProject.setDescription(description);
//        System.out.println(colProject);
        int row = colProjectMapper.updateById(colProject);
//        System.out.println("row" + row);
        return colProject;
    }

    @Override
    public int deleteProject(Integer pid){
        int row = colProjectMapper.deleteProject(pid);
        return row;
    }

    @Override
    public int finishProject(Integer pid){
        ColProject colProject = new ColProject();
        colProject.setId(pid);
        colProject.setStatus(0);
        int row = colProjectMapper.updateById(colProject);
        return row;
    }
}
