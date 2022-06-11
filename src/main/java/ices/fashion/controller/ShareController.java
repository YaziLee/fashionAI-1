package ices.fashion.controller;

import ices.fashion.constant.ApiResult;
import ices.fashion.entity.TShare;
import ices.fashion.service.ShareService;
import ices.fashion.service.dto.ShowDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/share")
public class ShareController {

    @Autowired
    private ShareService shareService;

    @GetMapping("/getUserShare")
    public ApiResult<ShowDto> getUserShare(@RequestParam(value = "id") String id) {
        return shareService.getUserShare(id);
    }

    @PostMapping("/saveOneShare")
    public ApiResult<Integer> saveOneShare(@RequestBody TShare share) {
        return shareService.saveOneShare(share);
    }

    @DeleteMapping("/deleteOneShare/{id}")
    public ApiResult deleteOneShareById(@PathVariable Integer id) {
        return shareService.deleteOneShareById(id);
    }

}
