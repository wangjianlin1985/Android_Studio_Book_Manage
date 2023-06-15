package com.chengxusheji.dao;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service; 
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.chengxusheji.domain.BookType;
import com.chengxusheji.domain.Book;

@Service @Transactional
public class BookDAO {

	@Resource SessionFactory factory;
    /*每页显示记录数目*/
    private final int PAGE_SIZE = 10;

    /*保存查询后总的页数*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*保存查询到的总记录数*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*添加图书信息*/
    public void AddBook(Book book) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(book);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Book> QueryBookInfo(String barcode,BookType bookType,String bookName,String publishDate,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Book book where 1=1";
    	if(!barcode.equals("")) hql = hql + " and book.barcode like '%" + barcode + "%'";
    	if(null != bookType && bookType.getBookTypeId()!=0) hql += " and book.bookType.bookTypeId=" + bookType.getBookTypeId();
    	if(!bookName.equals("")) hql = hql + " and book.bookName like '%" + bookName + "%'";
    	if(!publishDate.equals("")) hql = hql + " and book.publishDate like '%" + publishDate + "%'";
    	Query q = s.createQuery(hql);
    	/*计算当前显示页码的开始记录*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List bookList = q.list();
    	return (ArrayList<Book>) bookList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Book> QueryBookInfo(String barcode,BookType bookType,String bookName,String publishDate) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Book book where 1=1";
    	if(!barcode.equals("")) hql = hql + " and book.barcode like '%" + barcode + "%'";
    	if(null != bookType && bookType.getBookTypeId()!=0) hql += " and book.bookType.bookTypeId=" + bookType.getBookTypeId();
    	if(!bookName.equals("")) hql = hql + " and book.bookName like '%" + bookName + "%'";
    	if(!publishDate.equals("")) hql = hql + " and book.publishDate like '%" + publishDate + "%'";
    	Query q = s.createQuery(hql);
    	List bookList = q.list();
    	return (ArrayList<Book>) bookList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Book> QueryAllBookInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From Book";
        Query q = s.createQuery(hql);
        List bookList = q.list();
        return (ArrayList<Book>) bookList;
    }

    /*计算总的页数和记录数*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(String barcode,BookType bookType,String bookName,String publishDate) {
        Session s = factory.getCurrentSession();
        String hql = "From Book book where 1=1";
        if(!barcode.equals("")) hql = hql + " and book.barcode like '%" + barcode + "%'";
        if(null != bookType && bookType.getBookTypeId()!=0) hql += " and book.bookType.bookTypeId=" + bookType.getBookTypeId();
        if(!bookName.equals("")) hql = hql + " and book.bookName like '%" + bookName + "%'";
        if(!publishDate.equals("")) hql = hql + " and book.publishDate like '%" + publishDate + "%'";
        Query q = s.createQuery(hql);
        List bookList = q.list();
        recordNumber = bookList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取对象*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public Book GetBookByBarcode(String barcode) {
        Session s = factory.getCurrentSession();
        Book book = (Book)s.get(Book.class, barcode);
        return book;
    }

    /*更新Book信息*/
    public void UpdateBook(Book book) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(book);
    }

    /*删除Book信息*/
    public void DeleteBook (String barcode) throws Exception {
        Session s = factory.getCurrentSession();
        Object book = s.load(Book.class, barcode);
        s.delete(book);
    }

}
