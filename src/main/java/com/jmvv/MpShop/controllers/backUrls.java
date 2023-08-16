package com.jmvv.MpShop.controllers;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jmvv.MpShop.config.MapperStruct;
import com.jmvv.MpShop.models.phoneModel;
import com.jmvv.MpShop.services.mpService;
import com.mercadopago.net.HttpStatus;
import com.mercadopago.resources.payment.Payment;
import com.mercadopago.resources.preference.Preference;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("${url.backurl}")
public class backUrls {
    private final mpService service;
    
    
    @GetMapping("")
    public String getMethod(@RequestParam Map<String,Object> getReq, Model data){
        getReq.put("Method entry", "GET");
        System.out.println("process GET");
        System.out.println(getReq);
        
        data.addAttribute("titulo", "back urls");
        if(getReq.containsKey("preference_id")&&getReq.containsKey("collection_id")){
            String PaymentID= getReq.get("collection_id").toString();
            String PreferenceID= getReq.get("preference_id").toString();
            String status = service.verifyPayment(PaymentID, PreferenceID);
            data.addAttribute("status",status);
            if (!PreferenceID.contains("null")&&!PaymentID.contains("null")) {
                Preference product = service.getPreference(PreferenceID);
                Payment pago = service.getPayment(Long.parseLong(PaymentID));
                phoneModel phonePay = MapperStruct.INSTANCE.toRecord(product.getItems().get(0));
                System.out.println("payment: ");
                System.out.println(pago.getStatus());
                System.out.println("img :");
                System.out.println(phonePay.img());
                data.addAttribute("phone", phonePay);
                data.addAttribute("payment_method_id", pago.getPaymentMethodId());
                data.addAttribute("payment_id", pago.getId());
                data.addAttribute("external_reference",product.getExternalReference());
                data.addAttribute("status",pago.getStatus());
                data.addAttribute("query_rec", getReq);
            }
        }else
        data.addAttribute("status", "not Data Recive");
        // return new ResponseEntity<Map<String,Object>>(getReq, null, HttpStatus.OK);
        return "layout/back";
    }
    @PostMapping("")
    public ResponseEntity <?> postMethod(@RequestParam Map<String,Object> getReq, @RequestBody Map<String,Object> body){
        getReq.put("Method entry", "POST");
        System.out.println("process POST");
        System.out.println("params :");
        System.out.println(getReq);
        System.out.println("body :");
        System.out.println(body);

        return new ResponseEntity<Map<String,Object>>(getReq, null, HttpStatus.OK);
    }
}
