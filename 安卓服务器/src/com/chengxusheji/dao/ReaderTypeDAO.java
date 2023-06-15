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
    public void AddReaderType(ReaderType readerType) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(readerType);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<ReaderType> QueryReaderTypeInfo(int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From ReaderType readerType where 1=1";
    	Query q = s.createQuery(hql);
    	/*计算当前显示页码的开始记录*/
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

    /*计算总的页数和记录数*/
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

    /*根据主键获取对象*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ReaderType GetReaderTypeByReaderTypeId(int readerTypeId) {
        Session s = factory.getCurrentSession();
        ReaderType readerType = (ReaderType)s.get(ReaderType.class, readerTypeId);
        return readerType;
    }

    /*更新ReaderType信息*/
    public void UpdateReaderType(ReaderType readerType) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(readerType);
    }

    /*删除ReaderType信息*/
    public void DeleteReaderType (int readerTypeId) throws Exception {
        Session s = factory.getCurrentSession();
        Object readerType = s.load(ReaderType.class, readerTypeId);
        s.delete(readerType);
    }

}
