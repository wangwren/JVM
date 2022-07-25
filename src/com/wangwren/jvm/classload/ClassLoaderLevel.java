package com.wangwren.jvm.classload;

public class ClassLoaderLevel {

    public static void main(String[] args) {
        System.out.println(String.class.getClassLoader());
        System.out.println(sun.net.spi.nameservice.dns.DNSNameService.class.getClassLoader());
        System.out.println(ClassLoaderLevel.class.getClassLoader());

        System.out.println(sun.net.spi.nameservice.dns.DNSNameService.class.getClassLoader().getClass().getClassLoader());
        System.out.println(ClassLoaderLevel.class.getClassLoader().getClass().getClassLoader());
        System.out.println(ClassLoaderLevel.class.getClassLoader().getParent());

        System.out.println(new HelloClassLoad().getParent());
        //系统默认的ClassLoad，自定义的ClassLoad指定partner就是调用的这个方法
        System.out.println(ClassLoader.getSystemClassLoader());
    }

}
