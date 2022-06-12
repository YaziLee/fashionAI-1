package ices.fashion.controller.space;

import ices.fashion.constant.ApiResult;
import ices.fashion.entity.space.TComment;
import ices.fashion.service.space.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/comment")
public class WorkCommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/saveOneComment")
    public ApiResult saveOneComment(@RequestBody TComment comment) {
        return commentService.saveOneComment(comment);
    }
}
