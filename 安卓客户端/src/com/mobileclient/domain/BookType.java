package com.mobileclient.domain;

import java.io.Serializable;

public class BookType implements Serializable {
    /*图书类别*/
    private int bookTypeId;
    public int getBookTypeId() {
        return bookTypeId;
    }
    public void setBookTypeId(int bookTypeId) {
        this.bookTypeId = bookTypeId;
    }

    /*类别名称*/
    private String bookTypeName;
    public String getBookTypeName() {
        return bookTypeName;
    }
    public void setBookTypeName(String bookTypeName) {
        this.bookTypeName = bookTypeName;
    }

    /*可借阅天数*/
    private int days;
    public int getDays() {
        return days;
    }
    public void setDays(int days) {
        this.days = days;
    }

}