package ices.fashion.service.collaborate;

import ices.fashion.service.collaborate.dto.ColCommentDto;

import java.util.List;

public interface ColCommentService {
    public List<ColCommentDto> getCommentbyVersion(int vid);

}
