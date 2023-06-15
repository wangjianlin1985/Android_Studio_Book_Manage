package com.chengxusheji.domain;

import java.sql.Timestamp;
public class ReaderType {
    /*读者类型编号*/
    private int readerTypeId;
    public int getReaderTypeId() {
        return readerTypeId;
    }
    public void setReaderTypeId(int readerTypeId) {
        this.readerTypeId = readerTypeId;
    }

    /*读者类型*/
    private String readerTypeName;
    public String getReaderTypeName() {
        return readerTypeName;
    }
    public void setReaderTypeName(String readerTypeName) {
        this.readerTypeName = readerTypeName;
    }

    /*可借阅数目*/
    private int number;
    public int getNumber() {
        return number;
    }
    public void setNumber(int number) {
        this.number = number;
    }

}