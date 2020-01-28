package com.lixin.xinu.utils;

import java.util.UUID;

public class UUIDutils {
    public static String getUUID(){
        return UUID.randomUUID().toString().substring(15).replace("-","");
//        去掉-
    }
}
