package com.jmvv.MpShop.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;

@RestController
// @RequestMapping("/notification")
@RequestMapping("${url.notificationOwen}")
public class notificationUrlController {

    @Value("${url.notificationOwen}")
    private String url;

    @PostMapping("")
    public ResponseEntity<?> webhooks(@RequestBody Map<String,
            Object> bodyReciveMap,
            @RequestParam Map<String, Object> queryReciveMap,
            HttpServletRequest request) {
        System.out.println("-----------");    
        System.out.println("Notification request: "+request.getRequestURI());
        System.out.println("from host:" + request.getRemoteHost()+" addr: "+request.getRemoteAddr());
        System.out.print("query: ");
        System.out.println(queryReciveMap);
        System.out.print("body Map: ");
        System.out.println(bodyReciveMap);
        System.out.println("-----------");
        return new ResponseEntity<>(bodyReciveMap, HttpStatus.OK);
    }

    @PostConstruct
    void inicio() {
        System.out.println("Notificacion url :" + url);
    }
}
