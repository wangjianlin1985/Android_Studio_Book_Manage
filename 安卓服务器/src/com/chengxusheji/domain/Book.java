package com.chengxusheji.domain;

import java.sql.Timestamp;
public class Book {
    /*图书条形码*/
    private String barcode;
    public String getBarcode() {
        return barcode;
    }
    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    /*图书类别*/
    private BookType bookType;
    public BookType getBookType() {
        return bookType;
    }
    public void setBookType(BookType bookType) {
        this.bookType = bookType;
    }

    /*图书名称*/
    private String bookName;
    public String getBookName() {
        return bookName;
    }
    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    /*图书图片*/
    private String bookPhoto;
    public String getBookPhoto() {
        return bookPhoto;
    }
    public void setBookPhoto(String bookPhoto) {
        this.bookPhoto = bookPhoto;
    }

    /*图书价格*/
    private float price;
    public float getPrice() {
        return price;
    }
    public void setPrice(float price) {
        this.price = price;
    }

    /*库存*/
    private int count;
    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }

    /*出版日期*/
    private Timestamp publishDate;
    public Timestamp getPublishDate() {
        return publishDate;
    }
    public void setPublishDate(Timestamp publishDate) {
        this.publishDate = publishDate;
    }

    /*出版社*/
    private String publish;
    public String getPublish() {
        return publish;
    }
    public void setPublish(String publish) {
        this.publish = publish;
    }

    /*图书简介*/
    private String introduction;
    public String getIntroduction() {
        return introduction;
    }
    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

}