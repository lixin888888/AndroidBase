package com.lixin.xinu.utils;

public class EventBus {
    static EventBus  defaultInstance = null;
    // 单例模式
    public static EventBus getDefault(){
        if(defaultInstance == null ){
            synchronized (EventBus.class){
                if(defaultInstance == null){
                    defaultInstance = new EventBus();
                }
            }
        }
        return defaultInstance;
    }
    // 注册
    public void register(Object subscriber){
        //得到当前注册类的Class对象
        Class<?> subscriberClass = subscriber.getClass();

    }
}
