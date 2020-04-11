package com.group1.book.model;

public class Book {
    Integer bid;
    String bname;
    String author;
    String category;
    String description;

    public Book() {
    }

    public Book(Integer bid, String bname, String author, String category, String description) {
        this.bid = bid;
        this.bname = bname;
        this.author = author;
        this.category = category;
        this.description = description;
    }

    public Integer getBid() {
        return bid;
    }

    public void setBid(Integer bid) {
        this.bid = bid;
    }

    public String getBname() {
        return bname;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
