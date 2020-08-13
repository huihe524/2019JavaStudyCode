package com.group1.book.dao;

import com.group1.book.model.Book;
import com.group1.book.util.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BookDaoImpl implements BookDao {
    Connection conn; //维护一个数据库连接
    PreparedStatement pstm; //预处理sql对象
    ResultSet rs; //结果集
    @Override
    public List<Book> selectAll() {
        List<Book> books = new ArrayList<>();
        String sql = "select * from book";
        try {
            conn = JDBCUtils.getConnection();
            pstm = conn.prepareStatement(sql);
            rs = JDBCUtils.executeQuery(pstm, null);
            while(rs.next()){
                Book book = new Book(rs.getInt(1),rs.getString(2),rs.getString(3)
                ,rs.getString(4),rs.getString(5));//构建对象
                books.add(book); //添加
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.closeResource(rs, pstm, conn); //释放资源
        }
        return books;
    }

    @Override
    public Book selectById(int id) {
        Book book = null;
        String sql = "select * from book where bid=?";
        Object[] objects = new Object[]{id};
        try {
            conn = JDBCUtils.getConnection();
            pstm = conn.prepareStatement(sql);
            rs = JDBCUtils.executeQuery(pstm, objects);
            while(rs.next()){
                book = new Book(rs.getInt(1),rs.getString(2),rs.getString(3)
                        ,rs.getString(4),rs.getString(5));//构建对象

            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.closeResource(rs, pstm, conn); //释放资源
        }
        return book;
    }

    @Override
    public int deleteById(int id) {
        String sql = "delete from book where bid=?";
        int count = 0;
        Object[] objects = new Object[]{id};
        try {
            conn = JDBCUtils.getConnection();
            pstm = conn.prepareStatement(sql);
            count = JDBCUtils.executeUpdate(pstm, objects);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.closeResource(rs, pstm, conn); //释放资源
        }
        return count;
    }

    @Override
    public int addBook(Book book) {
        String sql = "insert into book(bname, author, category, description)values(?,?,?,?)";
        int count = 0;
        Object[] objects = new Object[]{book.getBname(), book.getAuthor(), book.getCategory(), book.getDescription()};
        try {
            conn = JDBCUtils.getConnection();
            pstm = conn.prepareStatement(sql);
            count = JDBCUtils.executeUpdate(pstm, objects);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.closeResource(rs, pstm, conn); //释放资源
        }
        return count;
    }

    @Override
    public int updateById(Book book) {
        String sql = "update book set bname=?, author=?, category=?, description=? where bid=?";
        int count = 0;
        Object[] objects = new Object[]{book.getBname(), book.getAuthor(), book.getCategory(), book.getDescription(), book.getBid()};
        try {
            conn = JDBCUtils.getConnection();
            pstm = conn.prepareStatement(sql);
            count = JDBCUtils.executeUpdate(pstm, objects);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.closeResource(rs, pstm, conn); //释放资源
        }
        return count;
    }
}
