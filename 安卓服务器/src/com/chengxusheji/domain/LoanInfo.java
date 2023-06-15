package com.chengxusheji.domain;

import java.sql.Timestamp;
public class LoanInfo {
    /*借阅编号*/
    private int loadId;
    public int getLoadId() {
        return loadId;
    }
    public void setLoadId(int loadId) {
        this.loadId = loadId;
    }

    /*图书*/
    private Book book;
    public Book getBook() {
        return book;
    }
    public void setBook(Book book) {
        this.book = book;
    }

    /*读者*/
    private Reader reader;
    public Reader getReader() {
        return reader;
    }
    public void setReader(Reader reader) {
        this.reader = reader;
    }

    /*借阅日期*/
    private Timestamp borrowDate;
    public Timestamp getBorrowDate() {
        return borrowDate;
    }
    public void setBorrowDate(Timestamp borrowDate) {
        this.borrowDate = borrowDate;
    }

    /*归还日期*/
    private Timestamp returnDate;
    public Timestamp getReturnDate() {
        return returnDate;
    }
    public void setReturnDate(Timestamp returnDate) {
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