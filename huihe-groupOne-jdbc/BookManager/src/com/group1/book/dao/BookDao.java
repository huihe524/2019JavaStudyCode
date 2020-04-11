package com.group1.book.dao;

import com.group1.book.model.Book;

import java.util.List;

public interface BookDao {
    List<Book> selectAll(); //查询全部

    Book selectById(int id); //用编号查询

    int deleteById(int id); //通过id删除

    int addBook(Book book); //添加图书

    int updateById(Book book); //通过id更新
}
