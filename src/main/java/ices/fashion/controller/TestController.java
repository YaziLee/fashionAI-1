package ices.fashion.controller;

import ices.fashion.entity.TBaseMaterial;
import ices.fashion.service.TestService;
import ices.fashion.service.dto.TestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/test")
public class TestController {

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
}
