package com.test;

public class Family implements Runnable {
    Bank b = new Bank();

    public void run() {
        for (int i = 0; i < 10; i++) {
            makeBuy();
        }
    }

    public synchronized void makeBuy() {
        if (b.account >= 10) {
            System.out.println(Thread.currentThread().getName() + "is taking money, the rest is " + b.account);
            try {
                Thread.sleep(500);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            b.account = b.account - 10;
        } else {
            System.out.println("没那么多钱");
        }
    }
}
