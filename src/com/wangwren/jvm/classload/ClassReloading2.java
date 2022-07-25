package com.wangwren.jvm.classload;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

/**
 * 打破双亲委派，实现loadClass方法
 */
public class ClassReloading2 {

    private static class MyLoader extends ClassLoader {
        /**
         * 热部署的原理
         */
        @Override
        public Class<?> loadClass(String name) throws ClassNotFoundException {

            File f = new File("/Users/wwr/IDEA-workspace/JVM/out/production/JVM/" + name.replace(".", "/").concat(".class"));
            //File f = new File("/Users/wwr/IDEA-workspace/JVM/out/production/JVM/Hello.clazz");

            if(!f.exists()) {
                //如果文件不存在，让父加载器加载
                return super.loadClass(name);
            }

            //如果文件存在，重新load
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

            return super.loadClass(name);
        }
    }

    /**
     * 解码
     */
    public static byte[] decode(byte[] bytes) {
        return Base64.getDecoder().decode(bytes);
    }

    public static void main(String[] args) throws Exception {
        MyLoader m = new MyLoader();
        Class clazz = m.loadClass("com.wangwren.jvm.classload.Hello");

        m = new MyLoader();
        Class clazzNew = m.loadClass("com.wangwren.jvm.classload.Hello");

        System.out.println(clazz == clazzNew);
    }

}
