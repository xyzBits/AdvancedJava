package com.dongfang.advanced.annotation;

import org.junit.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;

@MyAnnotation
public class AnnotationDemo {

    @MyAnnotation
    public void testAnnotation() {
    }

    @Test
    public void readSelfDefineAnnotation() {
        try {
            Class clazz = Class.forName(Student.class.getName());
            // 获得类的所有注解
            Annotation[] allAnnotations = clazz.getAnnotations();
            System.out.println("Arrays.deepToString(allAnnotations) = " + Arrays.deepToString(allAnnotations));
            // 获取指定注解并获取值
            TableMapping tableAnnotation = (TableMapping) clazz.getAnnotation(TableMapping.class);
            String value = tableAnnotation.value();
            System.out.println("value = " + value);
            MyAnnotation myAnnotation = (MyAnnotation) clazz.getAnnotation(MyAnnotation.class);
            System.out.println("myAnnotation.schools() = " + Arrays.toString(myAnnotation.schools()));


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void readFieldAnnotation() throws ClassNotFoundException, NoSuchFieldException {
        Class clazz = Class.forName(Student.class.getName());
        Field studentNameField = clazz.getDeclaredField("studentName");
        FieldMapping studentNameFieldAnnotation = studentNameField.getAnnotation(FieldMapping.class);
        String columnName = studentNameFieldAnnotation.columnName();
        System.out.println("columnName = " + columnName);
        // 结合类，注解，第三方程序，读出指定类的注解信息，生成SQL
        // 根据获得的表名，字段信息，获取DDL语句，使用JDBC执行SQL
        System.out.println(studentNameFieldAnnotation.columnName() + " -->" +
                studentNameFieldAnnotation.type() + "-->" +
                studentNameFieldAnnotation.length());
    }
}
