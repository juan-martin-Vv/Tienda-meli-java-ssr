package com.jmvv.MpShop.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth/hooks")
public class hooksController {
    @GetMapping()
    public String getModel(Model model){
        model.addAttribute("respuestas",null);
        return "layout/hooksData";
    }
}
