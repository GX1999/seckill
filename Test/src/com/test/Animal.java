package com.test;

public class Animal extends Dog implements Pet {
    public int index;

    public Animal(String name, int index) {
        super(name);
        this.index = index;
    }

    @Override
    public int comparePet() {
        return 1;
    }
}
