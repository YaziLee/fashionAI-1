package ices.fashion.controller;

import ices.fashion.constant.ApiResult;
import ices.fashion.entity.TWork;
import ices.fashion.service.WorkService;
import ices.fashion.service.dto.ShareWorkCriteria;
import ices.fashion.service.dto.ShowDto;
import ices.fashion.service.dto.WorkDetailDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/work")
public class WorkController {

    @Autowired
    private WorkService workService;

    /*
    id是phone
     */
    @GetMapping("/getUserDesign")
    public ApiResult<ShowDto> getUserDesign(@RequestParam(value="id") String id, @RequestParam(value = "isVisitor") Boolean isVisitor) {
        return workService.getUserDesign(id, isVisitor);
    }

    /*
    id是phone
     */
    @GetMapping("/getWorkDetail")
    public ApiResult<WorkDetailDto> getWorkDetail(@RequestParam(value="wid") Integer wid, @RequestParam(value="uid") String uid) {
        return workService.getWorkDetail(wid, uid);
    }

    @PostMapping("/saveOneWork")
    public ApiResult saveOneWork(@RequestBody TWork work) {
        return workService.saveOneWork(work);
    }

    @PostMapping("/shareWork")
    public ApiResult shareWork(@RequestBody ShareWorkCriteria shareWorkCriteria) {
        return workService.shareWork(shareWorkCriteria);
    }

    @GetMapping("/getAllShareWork")
    public ApiResult<ShowDto> getAllShareWork() {
        return workService.getAllShareWork();
    }
}
