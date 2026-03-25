package com.spring.internal_working.internal_work.controller;


import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    @RequestMapping("/") // /- a fwd slash represents the root of our website - when a user sends request to the root of our website this will be called
    public String index(Model model) {
        model.addAttribute("name", "Hello World!");
        return "index";
    }

}
