package ices.fashion.controller.collaborate;

import ices.fashion.service.ColCommentService;
import ices.fashion.service.dto.collaborate.ColCommentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/collaborate")
public class CommentController {
    @Autowired
    ColCommentService colCommentService;

    @GetMapping("/getCommentbyVersion")
    public List<ColCommentDto> getCommentbyVersion(@RequestParam(value = "vid")int vid){
        return colCommentService.getCommentbyVersion(vid);
    }

}
