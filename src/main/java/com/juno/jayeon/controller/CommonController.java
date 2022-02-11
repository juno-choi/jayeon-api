package com.juno.jayeon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/v1/docs")
public class CommonController {
    @GetMapping("")
    public String docs(){
        return "/docs/api.html";
    }
}
