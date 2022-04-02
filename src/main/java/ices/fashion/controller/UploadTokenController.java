package ices.fashion.controller;

import ices.fashion.service.UploadTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/qiniuyun")
public class UploadTokenController {
    @Autowired
    private UploadTokenService uploadTokenService;

    @GetMapping("/uploadToken")
    public String getUploadToken(){
        return uploadTokenService.getUploadToken();
    }
}
