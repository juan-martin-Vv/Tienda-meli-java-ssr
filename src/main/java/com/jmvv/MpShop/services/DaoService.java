package com.jmvv.MpShop.services;

import java.io.File;
// import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jmvv.MpShop.models.phoneModel;

import jakarta.annotation.PostConstruct;


@Service
public class DaoService {
    
    private File dbJson;
    private List<JsonElement> list;
    private List<phoneModel>  phoneList;

    
    @PostConstruct
    public void PostConstruct() {
        this.phoneList=new ArrayList<>();
        this.list= new ArrayList<>();
        ObjectMapper mapper= new ObjectMapper();
        Gson maper = new Gson();
        System.out.println("Construct finish!");
        try {
            System.out.println("reading....");
            // dbJson = new File("resoucers/data/test.json");
            InputStream dbJsonObj = getClass().getClassLoader().getResourceAsStream("data/test.json");
            if(!dbJsonObj.equals(null)){
                System.out.println("recurso cargado");
           
                    byte data[]=dbJsonObj.readAllBytes();
                    String s= new String(data);
           
                    Object object= JsonParser.parseString(s);
                    JsonObject jsonObject = (JsonObject) object;
           
                    JsonArray productos= jsonObject.get("products").getAsJsonArray();
           
                    productos.forEach((p)->{
                        list.add(p);
           
                        String json= maper.toJson(p);
           
                            try {
           
                                this.phoneList.add(mapper.readValue(json,phoneModel.class));
                                    
                            } catch (JsonMappingException e) {
           
                                e.printStackTrace();
                            } catch (JsonProcessingException e) {
           
                                e.printStackTrace();
                            }
           
            
                    });
           
                    dbJsonObj.close();
                }else System.out.println("no found");                        
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
    }
    //////////////////////
    public List<phoneModel> getAllPhones(){
        if(dbJson!=null)
        {
            System.out.println("file in: "+dbJson.getName());           
            System.out.println("list: ");
            System.out.println(list);
        }else
            System.out.println("vacio");
        
        return  phoneList ;
    }
    public String getHelo(String name){
        return "Holis "+name+" desde el Servicio";
    }
}
