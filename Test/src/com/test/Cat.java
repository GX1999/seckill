package com.test;

import java.io.Serializable;

public class Cat implements Serializable {
    public int num;
    public String name;

    public Cat(int i, String n) {
        num = i;
        name = n;
    }

    public void speak() {
        System.out.println("cat is speaking");
    }
}
