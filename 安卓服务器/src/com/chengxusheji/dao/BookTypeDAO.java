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

@Service @Transactional
public class BookTypeDAO {

	@Resource SessionFactory factory;
    /*ÿҳ��ʾ��¼��Ŀ*/
    private final int PAGE_SIZE = 10;

    /*�����ѯ���ܵ�ҳ��*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*�����ѯ�����ܼ�¼��*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*���ͼ����Ϣ*/
    public void AddBookType(BookType bookType) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(bookType);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<BookType> QueryBookTypeInfo(int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From BookType bookType where 1=1";
    	Query q = s.createQuery(hql);
    	/*���㵱ǰ��ʾҳ��Ŀ�ʼ��¼*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List bookTypeList = q.list();
    	return (ArrayList<BookType>) bookTypeList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<BookType> QueryBookTypeInfo() { 
    	Session s = factory.getCurrentSession();
    	String hql = "From BookType bookType where 1=1";
    	Query q = s.createQuery(hql);
    	List bookTypeList = q.list();
    	return (ArrayList<BookType>) bookTypeList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<BookType> QueryAllBookTypeInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From BookType";
        Query q = s.createQuery(hql);
        List bookTypeList = q.list();
        return (ArrayList<BookType>) bookTypeList;
    }

    /*�����ܵ�ҳ���ͼ�¼��*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber() {
        Session s = factory.getCurrentSession();
        String hql = "From BookType bookType where 1=1";
        Query q = s.createQuery(hql);
        List bookTypeList = q.list();
        recordNumber = bookTypeList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*����������ȡ����*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public BookType GetBookTypeByBookTypeId(int bookTypeId) {
        Session s = factory.getCurrentSession();
        BookType bookType = (BookType)s.get(BookType.class, bookTypeId);
        return bookType;
    }

    /*����BookType��Ϣ*/
    public void UpdateBookType(BookType bookType) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(bookType);
    }

    /*ɾ��BookType��Ϣ*/
    public void DeleteBookType (int bookTypeId) throws Exception {
        Session s = factory.getCurrentSession();
        Object bookType = s.load(BookType.class, bookTypeId);
        s.delete(bookType);
    }

}
