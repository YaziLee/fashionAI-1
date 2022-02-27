package ices.fashion.service;

import ices.fashion.service.dto.collaborate.ColVersionDto;

import java.util.List;

public interface ColVersionService {
    public List<ColVersionDto> getVersion(int pid);

    public void insertVersion(int pid, String canvas, String image, int parent);
}
