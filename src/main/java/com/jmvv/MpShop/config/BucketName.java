package com.jmvv.MpShop.config;

public enum BucketName {
    PROFILE_IMAGE("meli-shop-java-2023");
    
    private final String bucketName;
    
    BucketName(String BucketName){
        this.bucketName=BucketName;
    }
    
    public String getBucketName() {
        return bucketName;
    }
}
