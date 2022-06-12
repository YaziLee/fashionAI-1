package ices.fashion.controller.space;

import ices.fashion.constant.ApiResult;
import ices.fashion.entity.space.TShare;
import ices.fashion.service.space.ShareService;
import ices.fashion.service.space.dto.ShowDto;
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
