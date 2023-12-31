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
import com.chengxusheji.domain.Reader;

@Service @Transactional
public class ReaderDAO {

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
    public void AddReader(Reader reader) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(reader);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Reader> QueryReaderInfo(String readerNo,ReaderType readerType,String readerName,String birthday,String telephone,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Reader reader where 1=1";
    	if(!readerNo.equals("")) hql = hql + " and reader.readerNo like '%" + readerNo + "%'";
    	if(null != readerType && readerType.getReaderTypeId()!=0) hql += " and reader.readerType.readerTypeId=" + readerType.getReaderTypeId();
    	if(!readerName.equals("")) hql = hql + " and reader.readerName like '%" + readerName + "%'";
    	if(!birthday.equals("")) hql = hql + " and reader.birthday like '%" + birthday + "%'";
    	if(!telephone.equals("")) hql = hql + " and reader.telephone like '%" + telephone + "%'";
    	Query q = s.createQuery(hql);
    	/*计算当前显示页码的开始记录*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List readerList = q.list();
    	return (ArrayList<Reader>) readerList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Reader> QueryReaderInfo(String readerNo,ReaderType readerType,String readerName,String birthday,String telephone) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Reader reader where 1=1";
    	if(!readerNo.equals("")) hql = hql + " and reader.readerNo like '%" + readerNo + "%'";
    	if(null != readerType && readerType.getReaderTypeId()!=0) hql += " and reader.readerType.readerTypeId=" + readerType.getReaderTypeId();
    	if(!readerName.equals("")) hql = hql + " and reader.readerName like '%" + readerName + "%'";
    	if(!birthday.equals("")) hql = hql + " and reader.birthday like '%" + birthday + "%'";
    	if(!telephone.equals("")) hql = hql + " and reader.telephone like '%" + telephone + "%'";
    	Query q = s.createQuery(hql);
    	List readerList = q.list();
    	return (ArrayList<Reader>) readerList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Reader> QueryAllReaderInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From Reader";
        Query q = s.createQuery(hql);
        List readerList = q.list();
        return (ArrayList<Reader>) readerList;
    }

    /*计算总的页数和记录数*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(String readerNo,ReaderType readerType,String readerName,String birthday,String telephone) {
        Session s = factory.getCurrentSession();
        String hql = "From Reader reader where 1=1";
        if(!readerNo.equals("")) hql = hql + " and reader.readerNo like '%" + readerNo + "%'";
        if(null != readerType && readerType.getReaderTypeId()!=0) hql += " and reader.readerType.readerTypeId=" + readerType.getReaderTypeId();
        if(!readerName.equals("")) hql = hql + " and reader.readerName like '%" + readerName + "%'";
        if(!birthday.equals("")) hql = hql + " and reader.birthday like '%" + birthday + "%'";
        if(!telephone.equals("")) hql = hql + " and reader.telephone like '%" + telephone + "%'";
        Query q = s.createQuery(hql);
        List readerList = q.list();
        recordNumber = readerList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取对象*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public Reader GetReaderByReaderNo(String readerNo) {
        Session s = factory.getCurrentSession();
        Reader reader = (Reader)s.get(Reader.class, readerNo);
        return reader;
    }

    /*更新Reader信息*/
    public void UpdateReader(Reader reader) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(reader);
    }

    /*删除Reader信息*/
    public void DeleteReader (String readerNo) throws Exception {
        Session s = factory.getCurrentSession();
        Object reader = s.load(Reader.class, readerNo);
        s.delete(reader);
    }

}
