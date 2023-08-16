package com.jmvv.MpShop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;


@Configuration
public class objectMapperConfig {
    @Bean
    public ObjectMapper mapper(){
        ObjectMapper maper = JsonMapper.builder().addModule(new JavaTimeModule()).build();
        return maper;
    }


}
