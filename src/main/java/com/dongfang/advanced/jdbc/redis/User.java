package com.dongfang.advanced.jdbc.redis;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class User implements Serializable {
    private static final long serialVersionUID = -11122L;
    private int id;
    private String userName;
    private String password;
    private int age;

    public static String getKeyName() {
        return "user:";
    }
}