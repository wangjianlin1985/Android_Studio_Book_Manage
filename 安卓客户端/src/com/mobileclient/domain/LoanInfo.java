package com.mobileclient.domain;

import java.io.Serializable;

public class LoanInfo implements Serializable {
    /*���ı��*/
    private int loadId;
    public int getLoadId() {
        return loadId;
    }
    public void setLoadId(int loadId) {
        this.loadId = loadId;
    }

    /*ͼ��*/
    private String book;
    public String getBook() {
        return book;
    }
    public void setBook(String book) {
        this.book = book;
    }

    /*����*/
    private String reader;
    public String getReader() {
        return reader;
    }
    public void setReader(String reader) {
        this.reader = reader;
    }

    /*��������*/
    private java.sql.Timestamp borrowDate;
    public java.sql.Timestamp getBorrowDate() {
        return borrowDate;
    }
    public void setBorrowDate(java.sql.Timestamp borrowDate) {
        this.borrowDate = borrowDate;
    }

    /*�黹����*/
    private java.sql.Timestamp returnDate;
    public java.sql.Timestamp getReturnDate() {
        return returnDate;
    }
    public void setReturnDate(java.sql.Timestamp returnDate) {
        this.returnDate = returnDate;
    }

    /*������Ϣ*/
    private String memo;
    public String getMemo() {
        return memo;
    }
    public void setMemo(String memo) {
        this.memo = memo;
    }

}