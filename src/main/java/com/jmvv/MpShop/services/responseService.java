package com.jmvv.MpShop.services;

import java.io.File;
import java.time.LocalTime;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class responseService {
    private File db;
    @PostConstruct
    private void loadData(){
        try {
            db=new ClassPathResource("resoucers/resultados/test.json").getFile();
            System.out.println("se encontro el archivo: "+db.canRead());
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
    public boolean reciveWebHook(LocalTime atRequest, HttpServletRequest request){
        
        return true;
    }
}
