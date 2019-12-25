package com.dongfang.advanced.annotation;

import org.junit.Test;

import java.lang.annotation.ElementType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 从jdk5.0开始引入
 * Annotation的作用，
 *      不是程序本身，可以对程序作出解释，这一点，跟注释没什么区别
 *      可以被其他程序，比如编译器读取，注解信息处理流程，是注解和注释的重大区别
 *      如果没有注解信息处理流程，则注解将毫无意义
 * Annotation的格式：
 *      注解是以@注解名 在代码中存在的，还可以添加一些参数值，例如@SuppressWarnings(value = "unchecked")
 * Annotation 在哪里使用
 *      可以附加在package class method field等上面，相当于给它们添加了额外的辅助信息，我们可以通过反射
 *      机制编程实现对这些元数据的访问
 */
public class JdkAnnotation {

    /**
    * 告诉编译器，这个方法重写了父类的toString()方法，编译器看父类中有没有这个方法
    * 如果写成tostring()再加注解@Override，编译器发现父类没有这个方法，就报错
    *
    * 定义在java.lang.Override中，此注释只适用于修饰方法，表示一个方法声明打算重写超类中的方法
    * */
    @Override
    public String toString() {
        return super.toString();
    }

    /**
     * 定义在java.lang.Deprecated中，此注解可用于修饰方法，属性，类，
     * 表示不鼓励程序员使用这样的元素，通常是因为它很危险或者存在更好的选择
     */
    @Deprecated
    public void testDeprecated() {
        System.out.println("hello ");
    }

    @Test
    public void callTestDeprecated() {
        testDeprecated();
        Date date = new Date();
        long parse = date.parse("2019-12-25");
        System.out.println("parse = " + parse);
    }


    /**
     * 定义在java.lang.SuppressWarnings中，用来抑制编译时的警告信息
     *
     * 与前两个注解有所不同，你需要添加一个参数才能正确使用，这些参数值都是已经定义好了的，我们选择性的使用就好，参数如下
     *      decpreccation   使用了过时的类或方法的警告
     *      unchecked       执行了未检查的转换的警告，如使用集合时未指定泛型
     *      fallthrough     当在switch语句使用时发生case穿透
     *      path
     *      all             关于以上所有情况的警告
     */
    @SuppressWarnings(value = {"all", "unckecked"})
    public void testSuppressWarnings() {
        List list = new ArrayList();
        ElementType[] elementTypes = {ElementType.ANNOTATION_TYPE, ElementType.FIELD};
    }
}
