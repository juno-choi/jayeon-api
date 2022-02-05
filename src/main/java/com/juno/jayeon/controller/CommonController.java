package com.juno.jayeon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CommonController {
    @GetMapping("/v1/docs")
    public String docs(){
        return "/docs/api.html";
    }
}
