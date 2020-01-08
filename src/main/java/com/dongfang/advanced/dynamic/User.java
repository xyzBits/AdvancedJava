package com.dongfang.advanced.dynamic;

import com.dongfang.advanced.annotation.TableMapping;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class User {
    private int id;
    private String name;
    private int age;

    public int testGetFields;

    public int getTestGetFields() {
        return testGetFields;
    }

    public void setTestGetFields(int testGetFields) {
        this.testGetFields = testGetFields;
    }

    private User(int testGetFields) {
        this.testGetFields = testGetFields;
    }

    public User(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public User() {
        System.out.println("反射正在调用无参构造器");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    private void testPrivateMethod(Date date) {}

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"id\":")
                .append(id);
        sb.append(",\"name\":\"")
                .append(name).append('\"');
        sb.append(",\"age\":")
                .append(age);
        sb.append('}');
        return sb.toString();
    }

    public void hello(String arg) {
        System.out.println("hello " + arg);
    }

    public Map<Integer, User> testGeneric(Map<String, User> map, List<User> list) {
        System.out.println("testGeneric(Map<String, User> map, List<User> list)");
        return null;
    }

    public void testParamAnnotation(@TableMapping("arg") @Deprecated String arg, @TableMapping("height") int height) {

    }
}