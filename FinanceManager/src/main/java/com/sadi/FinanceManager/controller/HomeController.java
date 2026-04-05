package com.sadi.FinanceManager.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/status","/run"})
public class HomeController {

    @GetMapping
    public String appRunning(){
        return "App Running success";
    }

}
