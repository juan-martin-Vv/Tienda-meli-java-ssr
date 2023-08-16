package com.jmvv.MpShop.models;

import java.time.LocalTime;

import jakarta.servlet.http.HttpServletRequest;

public record responseMELI(String id, LocalTime inDate, HttpServletRequest request) {
   
}