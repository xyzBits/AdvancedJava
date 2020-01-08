package com.dongfang.advanced.dynamic;

import org.junit.Test;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * 动态编译：
 *          Java 6.0引入了动态编译机制
 *      应用场景：
 *          -- 可以做一个浏览器编写Java代码，上传服务器编译和运行的在线评测系统
 *          -- 服务器动态加载某些类文件进行编译
 *      两种方法：
 *          -- 通过Runtime调用javac，启动新的进程去操作
 *              Runtime run = Runtime.getRuntime();
 *              Process process = run.exec("javac -cp d:/java/HelloWorld.java");
 *          -- 通过JavaCompiler动态编译
 *
 * 动态运行编译好的类
 *      1、通过Runtime.getRuntime()启动一个新的进程
 *              Runtime run = Runtime.getRuntime()
 *              Process process = run.exec("java -cp d:/java/HelloWorld.java");
 *      2、通过反射运行编译好的类
 *           通过反射运行编译好的类
 *
 */
public class DynamicCompiler {

    /**
     * 通过JavaCompiler动态编译
     *      compiler.run(arg1, arg2, arg3, javaSourceCode)
     *          agr1 为编译器提供参数
     *          arg2 得到Java编译器的输出信息
     *          arg3 接收编译器的错误信息
     *          javaSourceCode 可变参数，是String[] 能传入一个或多个Java源文件
     *          返回值 0表示编译成功，非0表示编译失败
     */
    @Test
    public void testJavaCompiler() throws IOException {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        String javaSourceCode = "D:\\lidf\\HelloWorld.java";
        int result = compiler.run(null, null, null, javaSourceCode);
        System.out.println("result = " + result);

    }


    /**
     * 代码在字符串中，如何编译
     *      方法1、存入文件再编译
     */
    @Test
    public void testCompileJavaString() {
        String javaCode = "public class HelloWorld {\n" +
                "\tpublic static void main(String[] args) {\n" +
                "\t\tSystem.out.println(\"Hello World\");\n" +
                "\t\t\n" +
                "\t}\n" +
                "}\n" +
                "\n";

        String[] arr = new String[]{"hello", "world"};
    }

    @Test
    public void testRunClassByRuntime() throws IOException {
        Runtime run = Runtime.getRuntime();
        Process process = run.exec("java D:/lidf/HelloWorld");
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String message = "";
        while ((message = reader.readLine()) != null) {
            System.out.println(message);
        }
    }

    @Test
    public void testRunClassByReflection() throws MalformedURLException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        URL[] urls = {new URL("file:/" + "D:/lidf/")};
        URLClassLoader loader = new URLClassLoader(urls);
        Class<?> aClass = loader.loadClass("HelloWorld");
        // 调用加载类的main方法
        Method main = aClass.getMethod("main", String[].class);
        // 可变参数是jdk5.0之后才有的，不转为object，最后为main.invoke(null, "hello", "world")有三个参数，无法和main匹配
        // main方法要的是字符串数组
        main.invoke(null, (Object) new String[]{"hello", "world"});
    }
 }
