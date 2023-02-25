package com.test;

import java.util.Objects;

public class Dog {
    public String name;

    public Dog(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        Dog temp = (Dog) o;
        return this.name.equals(temp.name) ? true : false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name);
    }

    public void bark() {
        System.out.println("This is dog barking");
    }
}
