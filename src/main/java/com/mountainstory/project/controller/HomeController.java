package com.mountainstory.project.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequestMapping("/home")
@Controller
public class HomeController {


    @GetMapping("")
    public String homePage(){
        return "main/Home";
    }


    @GetMapping("/result")
    public String resultPage(){
        return "main/SearchResult";
    }








}
