package ices.fashion.controller;

import ices.fashion.constant.ApiResult;
import ices.fashion.entity.TWork;
import ices.fashion.mapper.WorkMapper;
import ices.fashion.service.WorkService;
import ices.fashion.service.dto.DesignCriteria;
import ices.fashion.service.dto.DesignDto;
import ices.fashion.service.dto.WorkDetailCriteria;
import ices.fashion.service.dto.WorkDetailDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/work")
public class WorkController {

    @Autowired
    private WorkService workService;

    @GetMapping("/getUserDesign")
    public ApiResult<DesignDto> getUserDesign(@RequestParam(value="id") String id) {
        return workService.getUserDesign(id);
    }

    @GetMapping("/getWorkDetail")
    public ApiResult<WorkDetailDto> getWorkDetail(@RequestParam(value="wid") Integer wid, @RequestParam(value="uid") String uid) {
        return workService.getWorkDetail(wid, uid);
    }

    @PostMapping("/saveOneWork")
    public ApiResult saveOneWork(TWork work) {
        return workService.saveOneWork(work);
    }
}
