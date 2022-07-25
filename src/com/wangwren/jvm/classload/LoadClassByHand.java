package com.wangwren.jvm.classload;

public class LoadClassByHand {
    public static void main(String[] args) throws ClassNotFoundException {

        //指定加载的类
        Class clazz = LoadClassByHand.class.getClassLoader().loadClass("com.wangwren.jvm.classload.ClassLoaderLevel");
        System.out.println(clazz.getName());

    }

}
