package com.gx;

import com.gx.config.SpringConfig;
import com.gx.service.BookService;
import com.gx.service.BookServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AppForAnnotation {
    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringConfig.class);

        BookService bookService = ctx.getBean(BookServiceImpl.class);
        boolean ret = bookService.openURL("www", "root ");
        System.out.println(ret);
    }
}
