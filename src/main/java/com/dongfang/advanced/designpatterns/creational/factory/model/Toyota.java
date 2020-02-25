package com.dongfang.advanced.designpatterns.creational.factory.model;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Toyota implements Car {

    @Override
    public void run() {
        System.out.println("Toyota is running");
    }
}
