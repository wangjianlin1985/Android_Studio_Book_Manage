package com.mobileclient.domain;

import java.io.Serializable;

public class BookType implements Serializable {
    /*ͼ�����*/
    private int bookTypeId;
    public int getBookTypeId() {
        return bookTypeId;
    }
    public void setBookTypeId(int bookTypeId) {
        this.bookTypeId = bookTypeId;
    }

    /*�������*/
    private String bookTypeName;
    public String getBookTypeName() {
        return bookTypeName;
    }
    public void setBookTypeName(String bookTypeName) {
        this.bookTypeName = bookTypeName;
    }

    /*�ɽ�������*/
    private int days;
    public int getDays() {
        return days;
    }
    public void setDays(int days) {
        this.days = days;
    }

}