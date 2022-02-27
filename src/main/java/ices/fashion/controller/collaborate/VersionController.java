package ices.fashion.controller.collaborate;

import ices.fashion.service.ColVersionService;
import ices.fashion.service.dto.collaborate.ColVersionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/collaborate")
public class VersionController {
    @Autowired
    private ColVersionService colVersionService;

    @GetMapping("/getVersion")
    public List<ColVersionDto> getVersion(@RequestParam(value="pid") int pid){
        return colVersionService.getVersion(pid);
    }

    /**
     * 接收from表单
     * @param pid
     * @param canvas
     * @param image
     * @param parent
     */
    @PostMapping("/insertVersion")
    public void insertVersion(Integer pid,String canvas,String image,Integer parent){
        colVersionService.insertVersion(pid, canvas, image, parent);
    }
}
