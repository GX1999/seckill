package com.gx;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gx.dao.BookDao;
import com.gx.domain.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class MybatisplusApplicationTests {

    @Autowired
    private BookDao bookDao;

//    @Test
//    void addData(){
//        Book book = new Book(5,"JAVA从入门到入土","编程语言","JAVA学习教程");
//        bookDao.insert(book);
//    }
//
    @Test   //乐观锁测试
    void updateData(){
        Book book = bookDao.selectById(5);
        Book book1 = bookDao.selectById(5);

        book.setDescription("乐观锁修改后 aaa");
        bookDao.updateById(book);

        book1.setDescription("乐观锁修改后 bbb");
        bookDao.updateById(book1);
    }

//    @Test
//    void contextLoads() {
//        Book book = bookDao.selectById(1);
//        System.out.println(book);
//    }

    @Test
    void getAll() {
//        //按条件查询
//        QueryWrapper qw = new QueryWrapper();
//        qw.lt("id",3);  //less than
//        List<Book> bookList = bookDao.selectList(qw);
//        System.out.println(bookList);

        //lambda
        LambdaQueryWrapper<Book> lqw = new LambdaQueryWrapper<Book>();
        //lqw.lt(Book::getId,3).gt(Book::getId,1);  //less than 3 and greater than 1
        lqw.lt(Book::getId,3).or().gt(Book::getId,1);  //less than 3 or greater than 1
        List<Book> bookList = bookDao.selectList(lqw);
        System.out.println(bookList);
    }

}
