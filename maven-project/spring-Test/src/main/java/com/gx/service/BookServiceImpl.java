package com.gx.service;

import com.gx.dao.BookDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookServiceImpl implements BookService {
    //删除new方式创建对象
    @Autowired
    private BookDao bookDao;

    public void save() {
        bookDao.save();
        System.out.println("book service save...");
    }

    public boolean openURL(String url, String pw) {
        return bookDao.readRes(url, pw);
    }


    //setter注入
//    public void setBookDao(BookDao bookDao) {
//        this.bookDao = bookDao;
//    }

    //构造器注入
//    public BookServiceImpl(BookDao bookDao) {
//        this.bookDao = bookDao;
//    }
}
