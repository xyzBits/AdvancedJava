package com.dongfang.advanced.designpatterns.creational.factory.model;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Audi implements Car {

    @Override
    public void run() {
        System.out.println("Audi is running");
    }
}
