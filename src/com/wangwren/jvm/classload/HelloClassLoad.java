package com.wangwren.jvm.classload;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Base64;

public class HelloClassLoad extends ClassLoader {

    public static void main(String[] args) {
        try {
            HelloClassLoad classLoad = new HelloClassLoad();
            Class<?> aClass = classLoad.loadClass("com.wangwren.jvm.classload.Hello");
            //Class<?> aClass = classLoad.findClass("com.wangwren.jvm.classload.Hello");

            //m()方法需要是public修饰
            Method method = aClass.getMethod("m");
            method.invoke(aClass.newInstance());

            System.out.println(new HelloClassLoad().getParent());
            //项目中不能有com.wangwren.jvm.classload.Hello，否则classLoad就是appClassLoad，双亲委派机制执行到app时在classpath下找到了这个类
            System.out.println(aClass.getClassLoader());

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }  catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {

        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("Hello.clazz");
        try {
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes);
            //获取真实的数据
            byte[] decodes = decode(bytes);
            return defineClass(name,decodes,0,decodes.length);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    /**
     * 解码
     */
    public byte[] decode(byte[] bytes) {
        return Base64.getDecoder().decode(bytes);
    }
}
