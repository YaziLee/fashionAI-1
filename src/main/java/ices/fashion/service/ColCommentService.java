package ices.fashion.service;

import ices.fashion.entity.collaborate.ColCommentQuery;
import ices.fashion.service.dto.collaborate.ColCommentDto;

import java.util.List;

public interface ColCommentService {
    public List<ColCommentDto> getCommentbyVersion(int vid);

}
