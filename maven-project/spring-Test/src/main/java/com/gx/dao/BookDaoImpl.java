package com.gx.dao;

import org.springframework.stereotype.Component;

@Component

public class BookDaoImpl implements BookDao {

    public void save() {
        Long startTime = System.currentTimeMillis();
//        for (int i = 0; i < 10000; i++) {
//            System.out.println("data save");
//        }
//        Long endTime = System.currentTimeMillis();
        System.out.println("开始时间：" + (startTime) + "ms");
        System.out.println("data save");
    }

    public boolean readRes(String url, String pw) {
        return pw.equals("root");
    }

}

