package com.gx.service;

import com.gx.domain.Book;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//业务层，处理数据（在其实现类BookServiceImpl中调用数据层接口的方法）
@Transactional
public interface BookService {

    public boolean save(Book book);

    public boolean update(Book book);

    public boolean delete(Integer id);

    public Book getById(Integer id);

    public List<Book> getAll();

}
