package com.mobileclient.domain;

import java.io.Serializable;

public class LoanInfo implements Serializable {
    /*借阅编号*/
    private int loadId;
    public int getLoadId() {
        return loadId;
    }
    public void setLoadId(int loadId) {
        this.loadId = loadId;
    }

    /*图书*/
    private String book;
    public String getBook() {
        return book;
    }
    public void setBook(String book) {
        this.book = book;
    }

    /*读者*/
    private String reader;
    public String getReader() {
        return reader;
    }
    public void setReader(String reader) {
        this.reader = reader;
    }

    /*借阅日期*/
    private java.sql.Timestamp borrowDate;
    public java.sql.Timestamp getBorrowDate() {
        return borrowDate;
    }
    public void setBorrowDate(java.sql.Timestamp borrowDate) {
        this.borrowDate = borrowDate;
    }

    /*归还日期*/
    private java.sql.Timestamp returnDate;
    public java.sql.Timestamp getReturnDate() {
        return returnDate;
    }
    public void setReturnDate(java.sql.Timestamp returnDate) {
        this.returnDate = returnDate;
    }

    /*附加信息*/
    private String memo;
    public String getMemo() {
        return memo;
    }
    public void setMemo(String memo) {
        this.memo = memo;
    }

}