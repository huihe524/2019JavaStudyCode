package com.group1.book.service.impl;

import com.group1.book.dao.BookDao;
import com.group1.book.dao.BookDaoImpl;
import com.group1.book.model.Book;
import com.group1.book.service.BookService;

import java.util.List;

public class BookServiceImpl implements BookService {

    BookDao bookDao = new BookDaoImpl();

    @Override
    public List<Book> selectAll() {
        return bookDao.selectAll();
    }

    @Override
    public Book selectById(int id) {
        return bookDao.selectById(id);
    }

    @Override
    public int deleteById(int id) {
        return bookDao.deleteById(id);
    }

    @Override
    public int addBook(Book book) {
        return bookDao.addBook(book);
    }

    public int updateById(Book book) {
        return bookDao.updateById(book);
    }


}
