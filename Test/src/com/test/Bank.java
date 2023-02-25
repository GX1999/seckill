package com.test;

public class Bank {
    public int account = 100;

    public void getMoney(int amout) {
        account = account - amout;
        System.out.println("取走" + amout + "，剩余" + account);
    }
}
