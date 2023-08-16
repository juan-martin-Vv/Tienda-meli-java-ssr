package com.jmvv.MpShop.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jmvv.MpShop.models.phoneModel;
import com.jmvv.MpShop.services.DaoService;
import com.jmvv.MpShop.services.mpService;
import com.mercadopago.resources.preference.Preference;

import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/store/detail")
@AllArgsConstructor
public class detailsController {

    private final mpService mpService;
    private final DaoService daoService;

    @GetMapping
    public String seleccion(phoneModel phone, Model model) {
        model.addAttribute("phone", phone);
        System.out.println(phone);
        // phone.addAllAttributes(phoneSelect);
        return "layout/details";
    }

    // @PostMapping
    // public void prefeCreation(@RequestBody phoneModel phone, Model
    // model,HttpServletResponse response) throws IOException{
    // Preference recive;
    // System.out.println("post: body");
    // System.out.println(phone);
    // response.sendRedirect("/detail/recive");
    // }

    @PostMapping
    public String prefeCreation(@RequestBody phoneModel phone, Model model) throws JsonProcessingException {
        Preference recive = new Preference();
        System.out.println("post: body");
        System.out.println(phone);
        recive = mpService.creatPreference(phone);
        System.out.println("end point a dirgirse");
        if (recive != null)
            System.out.println(recive.getInitPoint());
        return recive.getInitPoint();
    }

    @GetMapping("/recive")
    public ModelAndView recive() {
        System.out.println("recive END POINT");
        return new ModelAndView("layout/recive");
    }

    // @PostMapping
    // public ModelAndView togoogle(ModelMap model){
    // // search?q=gatos
    // model.addAttribute("q", "gatos maullando");
    // return new ModelAndView("redirect:https://www.google.com/search", model);
    // }
    @PostMapping("/data")
    public ResponseEntity<?> dataRecive(@RequestBody phoneModel body) {
        Preference orden = new Preference();
        Map<String, Object> ordenMap = new HashMap<>();
        System.out.println("orden :");
        System.out.println(body);
        phoneModel stockPhoneModel = daoService.getAllPhones().stream().filter(p -> p.id().equals(body.id()))
                .findFirst().get();
        phoneModel pedido = new phoneModel(stockPhoneModel.img(), stockPhoneModel.title(), stockPhoneModel.price(),
                body.unit(), stockPhoneModel.id());
        System.out.println("pedido");
        System.out.println(pedido);
        orden = mpService.creatPreference(pedido);
        ordenMap.put("prefenceId", orden.getId());
        ordenMap.put("phone", pedido);
        return ResponseEntity.ok()
                .body(ordenMap);

    }
}
