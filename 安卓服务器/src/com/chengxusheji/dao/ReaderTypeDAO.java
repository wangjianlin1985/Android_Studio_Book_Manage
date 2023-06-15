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
import com.chengxusheji.domain.ReaderType;

@Service @Transactional
public class ReaderTypeDAO {

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
    public void AddReaderType(ReaderType readerType) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(readerType);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<ReaderType> QueryReaderTypeInfo(int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From ReaderType readerType where 1=1";
    	Query q = s.createQuery(hql);
    	/*���㵱ǰ��ʾҳ��Ŀ�ʼ��¼*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List readerTypeList = q.list();
    	return (ArrayList<ReaderType>) readerTypeList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<ReaderType> QueryReaderTypeInfo() { 
    	Session s = factory.getCurrentSession();
    	String hql = "From ReaderType readerType where 1=1";
    	Query q = s.createQuery(hql);
    	List readerTypeList = q.list();
    	return (ArrayList<ReaderType>) readerTypeList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<ReaderType> QueryAllReaderTypeInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From ReaderType";
        Query q = s.createQuery(hql);
        List readerTypeList = q.list();
        return (ArrayList<ReaderType>) readerTypeList;
    }

    /*�����ܵ�ҳ���ͼ�¼��*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber() {
        Session s = factory.getCurrentSession();
        String hql = "From ReaderType readerType where 1=1";
        Query q = s.createQuery(hql);
        List readerTypeList = q.list();
        recordNumber = readerTypeList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*����������ȡ����*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ReaderType GetReaderTypeByReaderTypeId(int readerTypeId) {
        Session s = factory.getCurrentSession();
        ReaderType readerType = (ReaderType)s.get(ReaderType.class, readerTypeId);
        return readerType;
    }

    /*����ReaderType��Ϣ*/
    public void UpdateReaderType(ReaderType readerType) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(readerType);
    }

    /*ɾ��ReaderType��Ϣ*/
    public void DeleteReaderType (int readerTypeId) throws Exception {
        Session s = factory.getCurrentSession();
        Object readerType = s.load(ReaderType.class, readerTypeId);
        s.delete(readerType);
    }

}
