package com.wangwren.jvm.classload;

public class ClassReloading1 {

    public static void main(String[] args) throws ClassNotFoundException {
        HelloClassLoad helloClassLoad = new HelloClassLoad();
        Class clazz = helloClassLoad.loadClass("com.wangwren.jvm.classload.Hello");

        helloClassLoad = null;
        System.out.println(clazz.hashCode());

        helloClassLoad = null;

        helloClassLoad = new HelloClassLoad();
        Class clazz1 = helloClassLoad.loadClass("com.wangwren.jvm.classload.Hello");
        System.out.println(clazz1.hashCode());

        System.out.println(clazz == clazz1);

    }
}
