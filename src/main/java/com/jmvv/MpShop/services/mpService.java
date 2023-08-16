package com.jmvv.MpShop.services;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jmvv.MpShop.models.phoneModel;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.common.AddressRequest;
import com.mercadopago.client.common.IdentificationRequest;
import com.mercadopago.client.common.PhoneRequest;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferencePayerRequest;
import com.mercadopago.client.preference.PreferencePaymentMethodRequest;
import com.mercadopago.client.preference.PreferencePaymentMethodsRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;
import com.mercadopago.resources.payment.PaymentStatus;
import com.mercadopago.resources.preference.Preference;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class mpService {

    
    private static final String description = "Dispositivo móvil de Tienda e-commerce";
    private static final String urls = "/store/process";

    @Value("${url.base}")
    private String baseUrl;

    @Value("${dev.email}")
    private String emailDev;

    @Value("${url.notification}")
    private String urlsNotification;

    @Value("${url.notificationOwen}")
    private String urlNotificationOwen;
    

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getUrlNotification() {
        return urlsNotification;
    }

    public Preference creatPreference(phoneModel phone) {

        PreferenceClient ordenNueva = new PreferenceClient();
        System.out.println("Preference Client");
        System.out.println("integrator id: "+ MercadoPagoConfig.getIntegratorId());
        List<PreferenceItemRequest> items = new ArrayList<>();
        PreferenceItemRequest item = PreferenceItemRequest.builder()
                .id(phone.id())
                .categoryId("cell")
                .title(phone.title())
                .description(description)
                .pictureUrl(baseUrl.concat(phone.img()))
                .quantity(phone.unit())
                .currencyId("USD")
                .unitPrice(phone.price())
                .build();
        items.add(item);
        System.out.println("url picture");
        System.out.println(item.getPictureUrl());
        AddressRequest address = AddressRequest.builder()
                .streetName("calle falsa")
                .zipCode("M5521")
                .streetNumber("123")
                .build();
        PreferencePayerRequest payer = PreferencePayerRequest.builder()
                .name("Lalo")
                .surname("Landa")
                .address(address)
                .email("test_user_36961754@testuser.com")
                .identification(IdentificationRequest.builder()
                        .type("DNI")
                        .number("12123123")
                        .build())
                .phone(PhoneRequest.builder().areaCode("621").number("155999999").build())
                .build();

        PreferenceBackUrlsRequest urlsRequest = PreferenceBackUrlsRequest.builder()
                .success(baseUrl.concat(urls))
                .pending(baseUrl.concat(urls))
                .failure(baseUrl.concat(urls))
                .build();
        PreferencePaymentMethodsRequest paymentMethodRequest = PreferencePaymentMethodsRequest.builder()
                .installments(6)
                .excludedPaymentMethods(
                        List.of(
                                PreferencePaymentMethodRequest.builder().id("visa").build()))
                .build();
        String notificationUrl = baseUrl.contains("localhost")? urlsNotification : baseUrl.concat(urlNotificationOwen);
        PreferenceRequest request = PreferenceRequest.builder()
                .items(items)
                .backUrls(urlsRequest)
                .paymentMethods(paymentMethodRequest)
                .payer(payer)
                .expires(true)
                .expirationDateFrom(OffsetDateTime.now())
                .expirationDateTo(OffsetDateTime.now().plusMinutes(5))
                .externalReference(emailDev)
                .autoReturn("approved")
                .notificationUrl(notificationUrl.concat("?source_news=webhook"))
                .statementDescriptor("Tienda azul")
                .build();
        System.out.println("preference :");

        ObjectMapper mapperJson = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();
        String jsonIn = "error catch send";
        String jsonOut = "error catch retuned";
        Preference recive;
        Map<String,String> inte_Id= new HashMap<>();
        inte_Id.put("x-integrator-id", MercadoPagoConfig.getIntegratorId());
        MPRequestOptions optios=MPRequestOptions.builder()
            .customHeaders(inte_Id)
            .build();
        try {
            recive = ordenNueva.create(request,optios);
            jsonIn = mapperJson.writeValueAsString(request);
            jsonOut = mapperJson.writeValueAsString(recive);
            System.out.println(jsonIn);
            System.out.println("recive preference");
            System.out.println(jsonOut);
            return recive;
        } catch (MPException | JsonProcessingException e) {
            System.out.println("error :" + e.getMessage());
        } catch (MPApiException api) {
            System.out.println("api error :");
            System.out.println(api.getApiResponse().getContent());
        }

        return null;

    }

    public String verifyPayment(String paymentIDString, String preferenceID) {

        PaymentClient client = new PaymentClient();
        PreferenceClient pClient = new PreferenceClient();
        if (!preferenceID.contains("null") && !paymentIDString.contains("null")) {
            Long paymentID = Long.parseLong(paymentIDString);
            try {
                Preference searchPreference = pClient.get(preferenceID);
                Payment searchPayment = client.get(paymentID);
                System.out.println("payment: ");
                System.out.println(searchPayment.getId());
                System.out.println("prefrence:");
                System.out.println(searchPreference.getId());
                return searchPayment.getStatus();
            } catch (MPException e) {
                System.out.println("error :" + e.getMessage());
            } catch (MPApiException api) {
                System.out.println("api error :");
                System.out.println(api.getApiResponse().getContent());
            }
        }
        return PaymentStatus.CANCELLED;
    }

    public Preference getPreference(String preferenceID) {
        PreferenceClient client = new PreferenceClient();
        try {
            Preference response = client.get(preferenceID);
            return response;
        } catch (MPException e) {
            System.out.println("error :" + e.getMessage());
        } catch (MPApiException api) {
            System.out.println("api error :");
            System.out.println(api.getApiResponse().getContent());
        }
        return null;
    }

    public Payment getPayment(Long paymentID) {
        PaymentClient client = new PaymentClient();
        Payment payment;
        try {
            payment = client.get(paymentID);
            return payment;
        } catch (MPException e) {
            System.out.println("error :" + e.getMessage());
        } catch (MPApiException api) {
            System.out.println("api error :");
            System.out.println(api.getApiResponse().getContent());
        }
        return null;
    }
}
