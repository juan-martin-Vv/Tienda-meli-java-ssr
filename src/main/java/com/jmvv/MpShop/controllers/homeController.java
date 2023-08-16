package com.jmvv.MpShop.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jmvv.MpShop.models.phoneModel;
import com.jmvv.MpShop.services.DaoService;

import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/store")
@AllArgsConstructor
public class homeController {
    
    private final DaoService daoService;
    @GetMapping("")
    public String home(Model model) {
        List<phoneModel> phones;

        phones=daoService.getAllPhones();
        System.out.println("phone render:");
        System.out.println(phones);

        model.addAttribute("phones", phones);
        return "layout/home";
    }
}
