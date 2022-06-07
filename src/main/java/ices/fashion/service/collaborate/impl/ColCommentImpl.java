package ices.fashion.service.collaborate.impl;

import ices.fashion.entity.collaborate.ColCommentQuery;
import ices.fashion.mapper.collaborate.ColCommentMapper;
import ices.fashion.service.collaborate.ColCommentService;
import ices.fashion.service.collaborate.dto.ColCommentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class ColCommentImpl implements ColCommentService {
    @Autowired
    private ColCommentMapper colCommentMapper;

    @Override
    public List<ColCommentDto> getCommentbyVersion(int vid){
        List<ColCommentQuery> colCommentQueryList = colCommentMapper.selectComment();
        List<ColCommentDto> res;
        HashMap<Integer,ColCommentDto> mp =new HashMap<>();
        for(ColCommentQuery colCommentQuery : colCommentQueryList){
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String date = df.format(colCommentQuery.getCreateTime());
            Integer uid = colCommentQuery.getUid();
            if(!mp.containsKey(uid)){
                ColCommentDto colCommentDto = new ColCommentDto(uid,colCommentQuery.getUsername(),colCommentQuery.getCid()
                ,colCommentQuery.getVid(),new ArrayList<>(),colCommentQuery.getStatus(),new ArrayList<>(),new ArrayList<>());
                mp.put(uid,colCommentDto);
            }
            ColCommentDto colCommentDto = mp.get(uid);
            Integer status = colCommentQuery.getStatus();
            if(status == 0) continue;
            else if(status == 3){
                colCommentDto.getCanvas().add(colCommentQuery.getCanvas());
                colCommentDto.getContent().add(colCommentQuery.getContent());
                colCommentDto.getCreateTime().add(date);
            }
            else{
                colCommentDto.setStatus(status);
            }
        }
        res = new ArrayList<>(mp.values());
        return res;
    }
}
