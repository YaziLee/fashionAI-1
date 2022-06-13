package ices.fashion.controller.space;

import ices.fashion.constant.ApiResult;
import ices.fashion.entity.space.TComment;
import ices.fashion.service.space.CommentService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/comment")
public class WorkCommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/saveOneComment")
    @ApiOperation(value = "保存评论", notes = "id, createTime, deleted不用传参剩下字段需要")
    @ApiImplicitParam(name = "comment", value = "评论对应内容", required = true, dataType = "TComment")
    public ApiResult saveOneComment(@RequestBody TComment comment) {
        return commentService.saveOneComment(comment);
    }
}
