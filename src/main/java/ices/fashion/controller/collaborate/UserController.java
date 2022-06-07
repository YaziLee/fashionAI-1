package ices.fashion.controller.collaborate;

import ices.fashion.service.collaborate.ColUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/collaborate")
public class UserController {
    @Autowired
    ColUserService colUserService;

    @PostMapping("/login")
    public int login(String phone,String userName){
        return colUserService.login(phone, userName);
    }
}
