package com.jmvv.MpShop.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mercadopago.MercadoPagoConfig;

@Configuration
public class mpConfig {
    
    @Value("${dev.accessToken}")
    private static String AccessTokenString;
    @Value("${dev.integratorId}")
    private String IntegratorID;

    @Bean
    public MercadoPagoConfig MercadoPagoConfig(){
        MercadoPagoConfig config = new MercadoPagoConfig();
        config.setAccessToken(AccessTokenString);
        config.setIntegratorId(IntegratorID);
        config.getHttpClient();
        System.out.println("setup MELI API");
        return config;
        }
}
