package ices.fashion.controller;

import ices.fashion.entity.TBaseMaterial;
import ices.fashion.service.TestService;
import ices.fashion.service.dto.TestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/test")public class TestController {

    @Autowired
    private TestService testService;

    @GetMapping("/hello")
    public String helloWorld() {
        return "hello world";
    }

    @GetMapping("/get10Material")
    public List<TestDto> get10Material() {
        return testService.get10Material();
    }

    /**
     * 接收from表单
     * @param pwd
     * @return
     */
    @PostMapping("/postName")
    public  String postName(String pwd){
        String ret=pwd+" successfully";
        return  ret;
    }
}
