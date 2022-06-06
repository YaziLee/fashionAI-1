package ices.fashion.service;

import ices.fashion.service.dto.collaborate.ColVersionDto;

import java.util.List;

public interface ColVersionService {
    public List<ColVersionDto> getVersion(int pid);

    public void insertVersion(int pid, String canvas, String image, int parent);

    public void insertVersionDouble(int pid,String canvas,String backCanvas,String frontImage,String backImage,int parent);

    public void updateSaved(int vid, int saved);        //设置version的saved字段

    public void updateSaved(String str,int saved);
}
