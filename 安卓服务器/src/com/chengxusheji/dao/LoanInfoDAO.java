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
import com.chengxusheji.domain.Book;
import com.chengxusheji.domain.Reader;
import com.chengxusheji.domain.LoanInfo;

@Service @Transactional
public class LoanInfoDAO {

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
    public void AddLoanInfo(LoanInfo loanInfo) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(loanInfo);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<LoanInfo> QueryLoanInfoInfo(Book book,Reader reader,String borrowDate,String returnDate,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From LoanInfo loanInfo where 1=1";
    	if(null != book && !book.getBarcode().equals("")) hql += " and loanInfo.book.barcode='" + book.getBarcode() + "'";
    	if(null != reader && !reader.getReaderNo().equals("")) hql += " and loanInfo.reader.readerNo='" + reader.getReaderNo() + "'";
    	if(!borrowDate.equals("")) hql = hql + " and loanInfo.borrowDate like '%" + borrowDate + "%'";
    	if(!returnDate.equals("")) hql = hql + " and loanInfo.returnDate like '%" + returnDate + "%'";
    	Query q = s.createQuery(hql);
    	/*计算当前显示页码的开始记录*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List loanInfoList = q.list();
    	return (ArrayList<LoanInfo>) loanInfoList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<LoanInfo> QueryLoanInfoInfo(Book book,Reader reader,String borrowDate,String returnDate) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From LoanInfo loanInfo where 1=1";
    	if(null != book && !book.getBarcode().equals("")) hql += " and loanInfo.book.barcode='" + book.getBarcode() + "'";
    	if(null != reader && !reader.getReaderNo().equals("")) hql += " and loanInfo.reader.readerNo='" + reader.getReaderNo() + "'";
    	if(!borrowDate.equals("")) hql = hql + " and loanInfo.borrowDate like '%" + borrowDate + "%'";
    	if(!returnDate.equals("")) hql = hql + " and loanInfo.returnDate like '%" + returnDate + "%'";
    	Query q = s.createQuery(hql);
    	List loanInfoList = q.list();
    	return (ArrayList<LoanInfo>) loanInfoList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<LoanInfo> QueryAllLoanInfoInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From LoanInfo";
        Query q = s.createQuery(hql);
        List loanInfoList = q.list();
        return (ArrayList<LoanInfo>) loanInfoList;
    }

    /*计算总的页数和记录数*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(Book book,Reader reader,String borrowDate,String returnDate) {
        Session s = factory.getCurrentSession();
        String hql = "From LoanInfo loanInfo where 1=1";
        if(null != book && !book.getBarcode().equals("")) hql += " and loanInfo.book.barcode='" + book.getBarcode() + "'";
        if(null != reader && !reader.getReaderNo().equals("")) hql += " and loanInfo.reader.readerNo='" + reader.getReaderNo() + "'";
        if(!borrowDate.equals("")) hql = hql + " and loanInfo.borrowDate like '%" + borrowDate + "%'";
        if(!returnDate.equals("")) hql = hql + " and loanInfo.returnDate like '%" + returnDate + "%'";
        Query q = s.createQuery(hql);
        List loanInfoList = q.list();
        recordNumber = loanInfoList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取对象*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public LoanInfo GetLoanInfoByLoadId(int loadId) {
        Session s = factory.getCurrentSession();
        LoanInfo loanInfo = (LoanInfo)s.get(LoanInfo.class, loadId);
        return loanInfo;
    }

    /*更新LoanInfo信息*/
    public void UpdateLoanInfo(LoanInfo loanInfo) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(loanInfo);
    }

    /*删除LoanInfo信息*/
    public void DeleteLoanInfo (int loadId) throws Exception {
        Session s = factory.getCurrentSession();
        Object loanInfo = s.load(LoanInfo.class, loadId);
        s.delete(loanInfo);
    }

}
