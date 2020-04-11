package com.group1.book.controler.book;


import com.alibaba.fastjson.JSON;
import com.group1.book.dao.BookDao;
import com.group1.book.dao.BookDaoImpl;
import com.group1.book.model.Book;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/manager")
public class BookServlet extends HttpServlet {

    BookDao bookDao = new BookDaoImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        System.out.println(method);
        if("add".equals(method)){
            addBook(req, resp);
        }else if("update".equals(method)){
            updateById(req, resp);
        }else if("delete".equals(method)){
            deleteBook(req, resp);
        }else if("getById".equals(method)){
            getById(req, resp);
        }else {
            getAll(req, resp);
        }
    }

    private void updateById(HttpServletRequest req, HttpServletResponse resp) {
        Book book = new Book(Integer.parseInt(req.getParameter("bid")),req.getParameter("bname"),
                req.getParameter("author"), req.getParameter("category"),
                req.getParameter("desc"));
        bookDao.updateById(book);
        try {
            req.getRequestDispatcher("index.html").forward(req, resp); //转发到
        } catch (IOException | ServletException e) {
            e.printStackTrace();
        }
    }

    private void getById(HttpServletRequest req, HttpServletResponse resp) {
        PrintWriter writer = null;
        try {
            int bid = Integer.parseInt(req.getParameter("bid"));
            Book book = bookDao.selectById(bid);
            writer = resp.getWriter();
            writer.print(JSON.toJSON(book));
        }catch (NumberFormatException | IOException e){
            e.printStackTrace();
        }finally {
            if(writer != null) writer.close();
        }
    }

    private void deleteBook(HttpServletRequest req, HttpServletResponse resp) {
        try {
            int bid = Integer.parseInt(req.getParameter("bid"));
            bookDao.deleteById(bid);
            resp.sendRedirect("index.html");
        }catch (NumberFormatException | IOException e){
            e.printStackTrace();
        }
    }

    private void addBook(HttpServletRequest req, HttpServletResponse resp) {
        Book book = new Book(0,req.getParameter("name"),
                req.getParameter("author"), req.getParameter("category"),
                req.getParameter("desc"));

        bookDao.addBook(book);
        try {
            resp.sendRedirect("index.html"); //重定向到首页
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getAll(HttpServletRequest req, HttpServletResponse resp){
        List<Book> books = bookDao.selectAll();
        System.out.println(books);
        String books_json = JSON.toJSONString(books);
        PrintWriter writer = null;
        System.out.println(books_json);
        try {
            writer = resp.getWriter();
            writer.print(books_json);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(writer != null) writer.close();
        }
    }

}
