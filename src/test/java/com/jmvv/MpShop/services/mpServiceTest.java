package com.jmvv.MpShop.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.test.util.ReflectionTestUtils;

import com.jmvv.MpShop.config.mpConfig;
import com.jmvv.MpShop.models.phoneModel;

import com.mercadopago.resources.payment.Payment;
import com.mercadopago.resources.payment.PaymentStatus;
import com.mercadopago.resources.preference.Preference;


@ExtendWith(MockitoExtension.class)
public class mpServiceTest {

    @InjectMocks
    @Autowired
    mpConfig config;

    @InjectMocks
    @Autowired
    mpService service;
    
    @InjectMocks
    @Autowired
    DaoService daoService;

    //
    private final String preferenceIDTest ="1160706432-d454a18c-9e20-49af-8e72-d8a68d12e184";
    private final String paymentIDStringTest="53432552811";
    @BeforeEach void setup(){
        config.MercadoPagoConfig();
        ReflectionTestUtils.setField(service, "baseUrl", "http://localhost:8080");
        ReflectionTestUtils.setField(service, "urlNotificationOwen", "http://localhost:8080/store/notification");
        ReflectionTestUtils.setField(service, "urlsNotification", "https://eomjxns1j72nfyu.m.pipedream.net");
    }
    @Test
    void testCreatPreference() {
        System.out.println("baseUrl: "+ service.getBaseUrl() );
        daoService.PostConstruct();
        phoneModel phone = daoService.getAllPhones().get(0);
        System.out.println("baseUrl: "+ service.getBaseUrl() );        
        Preference phonPreference = service.creatPreference(phone);
        System.out.println("back url: "+phonPreference.getBackUrls().getSuccess());
        System.out.println("image url: "+phonPreference.getItems().get(0).getPictureUrl());
        assertNotNull(phonPreference);
    }

    @Test
    void testGetPreference() {
        Preference data = service.getPreference(preferenceIDTest);
        System.out.println("items ");
        data.getItems().stream().forEach(it->System.out.println(it.getTitle()));
        System.out.println(data.getItems().get(0).getPictureUrl());
        System.out.println(data.getItems().get(0).getId());
        assertEquals(preferenceIDTest, data.getId());
    }

    @Test
    void testVerifyPayment() {
        assertEquals(PaymentStatus.APPROVED,service.verifyPayment(paymentIDStringTest,preferenceIDTest));
    }
    
    @Test
    void testGetPayment(){
        Payment payment =service.getPayment(Long.parseLong(paymentIDStringTest));
        System.out.println(payment.getPaymentMethodId());
        assertNotEquals("null", payment.getStatus());
    }
    /////////
    @Configuration    
    static class Config {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertiesResolver() {
        return new PropertySourcesPlaceholderConfigurer();
    }

  }

}
