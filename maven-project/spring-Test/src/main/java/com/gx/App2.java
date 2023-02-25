package com.gx;

import com.gx.dao.BookDao;
import com.gx.service.BookService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App2 {
    public static void main(String[] args) {
        //通过加载配置文件，获取IoC容器
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        //获取Bean
        BookDao bookDao = (BookDao) ctx.getBean("bookDao");
        //BookService bookService = ctx.getBean("bookDao",BookService.class);
        bookDao.save();
        System.out.println(bookDao);

        BookService bookService = (BookService) ctx.getBean(BookService.class);
    }
}
