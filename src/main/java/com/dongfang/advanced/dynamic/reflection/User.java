package com.dongfang.advanced.dynamic.reflection;

import java.util.Date;

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

    public User() {
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
}