package com.chengxusheji.action;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import com.opensymphony.xwork2.ActionContext;
import com.chengxusheji.dao.LoanInfoDAO;
import com.chengxusheji.domain.LoanInfo;
import com.chengxusheji.dao.BookDAO;
import com.chengxusheji.domain.Book;
import com.chengxusheji.dao.ReaderDAO;
import com.chengxusheji.domain.Reader;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class LoanInfoAction extends BaseAction {

    /*界面层需要查询的属性: 图书*/
    private Book book;
    public void setBook(Book book) {
        this.book = book;
    }
    public Book getBook() {
        return this.book;
    }

    /*界面层需要查询的属性: 读者*/
    private Reader reader;
    public void setReader(Reader reader) {
        this.reader = reader;
    }
    public Reader getReader() {
        return this.reader;
    }

    /*界面层需要查询的属性: 借阅日期*/
    private String borrowDate;
    public void setBorrowDate(String borrowDate) {
        this.borrowDate = borrowDate;
    }
    public String getBorrowDate() {
        return this.borrowDate;
    }

    /*界面层需要查询的属性: 归还日期*/
    private String returnDate;
    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }
    public String getReturnDate() {
        return this.returnDate;
    }

    /*当前第几页*/
    private int currentPage;
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public int getCurrentPage() {
        return currentPage;
    }

    /*一共多少页*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    private int loadId;
    public void setLoadId(int loadId) {
        this.loadId = loadId;
    }
    public int getLoadId() {
        return loadId;
    }

    /*当前查询的总记录数目*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*业务层对象*/
    @Resource BookDAO bookDAO;
    @Resource ReaderDAO readerDAO;
    @Resource LoanInfoDAO loanInfoDAO;

    /*待操作的LoanInfo对象*/
    private LoanInfo loanInfo;
    public void setLoanInfo(LoanInfo loanInfo) {
        this.loanInfo = loanInfo;
    }
    public LoanInfo getLoanInfo() {
        return this.loanInfo;
    }

    /*跳转到添加LoanInfo视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*查询所有的Book信息*/
        List<Book> bookList = bookDAO.QueryAllBookInfo();
        ctx.put("bookList", bookList);
        /*查询所有的Reader信息*/
        List<Reader> readerList = readerDAO.QueryAllReaderInfo();
        ctx.put("readerList", readerList);
        return "add_view";
    }

    /*添加LoanInfo信息*/
    @SuppressWarnings("deprecation")
    public String AddLoanInfo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Book book = bookDAO.GetBookByBarcode(loanInfo.getBook().getBarcode());
            loanInfo.setBook(book);
            Reader reader = readerDAO.GetReaderByReaderNo(loanInfo.getReader().getReaderNo());
            loanInfo.setReader(reader);
            loanInfoDAO.AddLoanInfo(loanInfo);
            ctx.put("message",  java.net.URLEncoder.encode("LoanInfo添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("LoanInfo添加失败!"));
            return "error";
        }
    }

    /*查询LoanInfo信息*/
    public String QueryLoanInfo() {
        if(currentPage == 0) currentPage = 1;
        if(borrowDate == null) borrowDate = "";
        if(returnDate == null) returnDate = "";
        List<LoanInfo> loanInfoList = loanInfoDAO.QueryLoanInfoInfo(book, reader, borrowDate, returnDate, currentPage);
        /*计算总的页数和总的记录数*/
        loanInfoDAO.CalculateTotalPageAndRecordNumber(book, reader, borrowDate, returnDate);
        /*获取到总的页码数目*/
        totalPage = loanInfoDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = loanInfoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("loanInfoList",  loanInfoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("book", book);
        List<Book> bookList = bookDAO.QueryAllBookInfo();
        ctx.put("bookList", bookList);
        ctx.put("reader", reader);
        List<Reader> readerList = readerDAO.QueryAllReaderInfo();
        ctx.put("readerList", readerList);
        ctx.put("borrowDate", borrowDate);
        ctx.put("returnDate", returnDate);
        return "query_view";
    }

    /*后台导出到excel*/
    public String QueryLoanInfoOutputToExcel() { 
        if(borrowDate == null) borrowDate = "";
        if(returnDate == null) returnDate = "";
        List<LoanInfo> loanInfoList = loanInfoDAO.QueryLoanInfoInfo(book,reader,borrowDate,returnDate);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "LoanInfo信息记录"; 
        String[] headers = { "借阅编号","图书","读者","借阅日期","归还日期"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<loanInfoList.size();i++) {
        	LoanInfo loanInfo = loanInfoList.get(i); 
        	dataset.add(new String[]{loanInfo.getLoadId() + "",loanInfo.getBook().getBookName(),
loanInfo.getReader().getReaderName(),
new SimpleDateFormat("yyyy-MM-dd").format(loanInfo.getBorrowDate()),new SimpleDateFormat("yyyy-MM-dd").format(loanInfo.getReturnDate())});
        }
        /*
        OutputStream out = null;
		try {
			out = new FileOutputStream("C://output.xls");
			ex.exportExcel(title,headers, dataset, out);
		    out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
		HttpServletResponse response = null;//创建一个HttpServletResponse对象 
		OutputStream out = null;//创建一个输出流对象 
		try { 
			response = ServletActionContext.getResponse();//初始化HttpServletResponse对象 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"LoanInfo.xls");//filename是下载的xls的名，建议最好用英文 
			response.setContentType("application/msexcel;charset=UTF-8");//设置类型 
			response.setHeader("Pragma","No-cache");//设置头 
			response.setHeader("Cache-Control","no-cache");//设置头 
			response.setDateHeader("Expires", 0);//设置日期头  
			String rootPath = ServletActionContext.getServletContext().getRealPath("/");
			ex.exportExcel(rootPath,title,headers, dataset, out);
			out.flush();
		} catch (IOException e) { 
			e.printStackTrace(); 
		}finally{
			try{
				if(out!=null){ 
					out.close(); 
				}
			}catch(IOException e){ 
				e.printStackTrace(); 
			} 
		}
		return null;
    }
    /*前台查询LoanInfo信息*/
    public String FrontQueryLoanInfo() {
        if(currentPage == 0) currentPage = 1;
        if(borrowDate == null) borrowDate = "";
        if(returnDate == null) returnDate = "";
        List<LoanInfo> loanInfoList = loanInfoDAO.QueryLoanInfoInfo(book, reader, borrowDate, returnDate, currentPage);
        /*计算总的页数和总的记录数*/
        loanInfoDAO.CalculateTotalPageAndRecordNumber(book, reader, borrowDate, returnDate);
        /*获取到总的页码数目*/
        totalPage = loanInfoDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = loanInfoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("loanInfoList",  loanInfoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("book", book);
        List<Book> bookList = bookDAO.QueryAllBookInfo();
        ctx.put("bookList", bookList);
        ctx.put("reader", reader);
        List<Reader> readerList = readerDAO.QueryAllReaderInfo();
        ctx.put("readerList", readerList);
        ctx.put("borrowDate", borrowDate);
        ctx.put("returnDate", returnDate);
        return "front_query_view";
    }

    /*查询要修改的LoanInfo信息*/
    public String ModifyLoanInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键loadId获取LoanInfo对象*/
        LoanInfo loanInfo = loanInfoDAO.GetLoanInfoByLoadId(loadId);

        List<Book> bookList = bookDAO.QueryAllBookInfo();
        ctx.put("bookList", bookList);
        List<Reader> readerList = readerDAO.QueryAllReaderInfo();
        ctx.put("readerList", readerList);
        ctx.put("loanInfo",  loanInfo);
        return "modify_view";
    }

    /*查询要修改的LoanInfo信息*/
    public String FrontShowLoanInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键loadId获取LoanInfo对象*/
        LoanInfo loanInfo = loanInfoDAO.GetLoanInfoByLoadId(loadId);

        List<Book> bookList = bookDAO.QueryAllBookInfo();
        ctx.put("bookList", bookList);
        List<Reader> readerList = readerDAO.QueryAllReaderInfo();
        ctx.put("readerList", readerList);
        ctx.put("loanInfo",  loanInfo);
        return "front_show_view";
    }

    /*更新修改LoanInfo信息*/
    public String ModifyLoanInfo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Book book = bookDAO.GetBookByBarcode(loanInfo.getBook().getBarcode());
            loanInfo.setBook(book);
            Reader reader = readerDAO.GetReaderByReaderNo(loanInfo.getReader().getReaderNo());
            loanInfo.setReader(reader);
            loanInfoDAO.UpdateLoanInfo(loanInfo);
            ctx.put("message",  java.net.URLEncoder.encode("LoanInfo信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("LoanInfo信息更新失败!"));
            return "error";
       }
   }

    /*删除LoanInfo信息*/
    public String DeleteLoanInfo() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            loanInfoDAO.DeleteLoanInfo(loadId);
            ctx.put("message",  java.net.URLEncoder.encode("LoanInfo删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("LoanInfo删除失败!"));
            return "error";
        }
    }

}
