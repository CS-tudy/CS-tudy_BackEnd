package com.cstudy.moduleapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class deployController {

    @GetMapping("/test")
    public String test() {
        return "Deploy";
    }

}
