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
import com.chengxusheji.dao.BookDAO;
import com.chengxusheji.domain.Book;
import com.chengxusheji.dao.BookTypeDAO;
import com.chengxusheji.domain.BookType;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class BookAction extends BaseAction {

	/*图片或文件字段bookPhoto参数接收*/
	private File bookPhotoFile;
	private String bookPhotoFileFileName;
	private String bookPhotoFileContentType;
	public File getBookPhotoFile() {
		return bookPhotoFile;
	}
	public void setBookPhotoFile(File bookPhotoFile) {
		this.bookPhotoFile = bookPhotoFile;
	}
	public String getBookPhotoFileFileName() {
		return bookPhotoFileFileName;
	}
	public void setBookPhotoFileFileName(String bookPhotoFileFileName) {
		this.bookPhotoFileFileName = bookPhotoFileFileName;
	}
	public String getBookPhotoFileContentType() {
		return bookPhotoFileContentType;
	}
	public void setBookPhotoFileContentType(String bookPhotoFileContentType) {
		this.bookPhotoFileContentType = bookPhotoFileContentType;
	}
    /*界面层需要查询的属性: 图书条形码*/
    private String barcode;
    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }
    public String getBarcode() {
        return this.barcode;
    }

    /*界面层需要查询的属性: 图书类别*/
    private BookType bookType;
    public void setBookType(BookType bookType) {
        this.bookType = bookType;
    }
    public BookType getBookType() {
        return this.bookType;
    }

    /*界面层需要查询的属性: 图书名称*/
    private String bookName;
    public void setBookName(String bookName) {
        this.bookName = bookName;
    }
    public String getBookName() {
        return this.bookName;
    }

    /*界面层需要查询的属性: 出版日期*/
    private String publishDate;
    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }
    public String getPublishDate() {
        return this.publishDate;
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

    /*当前查询的总记录数目*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*业务层对象*/
    @Resource BookTypeDAO bookTypeDAO;
    @Resource BookDAO bookDAO;

    /*待操作的Book对象*/
    private Book book;
    public void setBook(Book book) {
        this.book = book;
    }
    public Book getBook() {
        return this.book;
    }

    /*跳转到添加Book视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*查询所有的BookType信息*/
        List<BookType> bookTypeList = bookTypeDAO.QueryAllBookTypeInfo();
        ctx.put("bookTypeList", bookTypeList);
        return "add_view";
    }

    /*添加Book信息*/
    @SuppressWarnings("deprecation")
    public String AddBook() {
        ActionContext ctx = ActionContext.getContext();
        /*验证图书条形码是否已经存在*/
        String barcode = book.getBarcode();
        Book db_book = bookDAO.GetBookByBarcode(barcode);
        if(null != db_book) {
            ctx.put("error",  java.net.URLEncoder.encode("该图书条形码已经存在!"));
            return "error";
        }
        try {
            BookType bookType = bookTypeDAO.GetBookTypeByBookTypeId(book.getBookType().getBookTypeId());
            book.setBookType(bookType);
            /*处理图书图片上传*/
            String bookPhotoPath = "upload/noimage.jpg"; 
       	 	if(bookPhotoFile != null)
       	 		bookPhotoPath = photoUpload(bookPhotoFile,bookPhotoFileContentType);
       	 	book.setBookPhoto(bookPhotoPath);
            bookDAO.AddBook(book);
            ctx.put("message",  java.net.URLEncoder.encode("Book添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Book添加失败!"));
            return "error";
        }
    }

    /*查询Book信息*/
    public String QueryBook() {
        if(currentPage == 0) currentPage = 1;
        if(barcode == null) barcode = "";
        if(bookName == null) bookName = "";
        if(publishDate == null) publishDate = "";
        List<Book> bookList = bookDAO.QueryBookInfo(barcode, bookType, bookName, publishDate, currentPage);
        /*计算总的页数和总的记录数*/
        bookDAO.CalculateTotalPageAndRecordNumber(barcode, bookType, bookName, publishDate);
        /*获取到总的页码数目*/
        totalPage = bookDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = bookDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("bookList",  bookList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("barcode", barcode);
        ctx.put("bookType", bookType);
        List<BookType> bookTypeList = bookTypeDAO.QueryAllBookTypeInfo();
        ctx.put("bookTypeList", bookTypeList);
        ctx.put("bookName", bookName);
        ctx.put("publishDate", publishDate);
        return "query_view";
    }

    /*后台导出到excel*/
    public String QueryBookOutputToExcel() { 
        if(barcode == null) barcode = "";
        if(bookName == null) bookName = "";
        if(publishDate == null) publishDate = "";
        List<Book> bookList = bookDAO.QueryBookInfo(barcode,bookType,bookName,publishDate);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Book信息记录"; 
        String[] headers = { "图书条形码","图书类别","图书名称","图书图片","图书价格","库存","出版日期","出版社"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<bookList.size();i++) {
        	Book book = bookList.get(i); 
        	dataset.add(new String[]{book.getBarcode(),book.getBookType().getBookTypeName(),
book.getBookName(),book.getBookPhoto(),book.getPrice() + "",book.getCount() + "",new SimpleDateFormat("yyyy-MM-dd").format(book.getPublishDate()),book.getPublish()});
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
			response.setHeader("Content-disposition","attachment; filename="+"Book.xls");//filename是下载的xls的名，建议最好用英文 
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
    /*前台查询Book信息*/
    public String FrontQueryBook() {
        if(currentPage == 0) currentPage = 1;
        if(barcode == null) barcode = "";
        if(bookName == null) bookName = "";
        if(publishDate == null) publishDate = "";
        List<Book> bookList = bookDAO.QueryBookInfo(barcode, bookType, bookName, publishDate, currentPage);
        /*计算总的页数和总的记录数*/
        bookDAO.CalculateTotalPageAndRecordNumber(barcode, bookType, bookName, publishDate);
        /*获取到总的页码数目*/
        totalPage = bookDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = bookDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("bookList",  bookList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("barcode", barcode);
        ctx.put("bookType", bookType);
        List<BookType> bookTypeList = bookTypeDAO.QueryAllBookTypeInfo();
        ctx.put("bookTypeList", bookTypeList);
        ctx.put("bookName", bookName);
        ctx.put("publishDate", publishDate);
        return "front_query_view";
    }

    /*查询要修改的Book信息*/
    public String ModifyBookQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键barcode获取Book对象*/
        Book book = bookDAO.GetBookByBarcode(barcode);

        List<BookType> bookTypeList = bookTypeDAO.QueryAllBookTypeInfo();
        ctx.put("bookTypeList", bookTypeList);
        ctx.put("book",  book);
        return "modify_view";
    }

    /*查询要修改的Book信息*/
    public String FrontShowBookQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键barcode获取Book对象*/
        Book book = bookDAO.GetBookByBarcode(barcode);

        List<BookType> bookTypeList = bookTypeDAO.QueryAllBookTypeInfo();
        ctx.put("bookTypeList", bookTypeList);
        ctx.put("book",  book);
        return "front_show_view";
    }

    /*更新修改Book信息*/
    public String ModifyBook() {
        ActionContext ctx = ActionContext.getContext();
        try {
            BookType bookType = bookTypeDAO.GetBookTypeByBookTypeId(book.getBookType().getBookTypeId());
            book.setBookType(bookType);
            /*处理图书图片上传*/
            if(bookPhotoFile != null) {
            	String bookPhotoPath = photoUpload(bookPhotoFile,bookPhotoFileContentType);
            	book.setBookPhoto(bookPhotoPath);
            }
            bookDAO.UpdateBook(book);
            ctx.put("message",  java.net.URLEncoder.encode("Book信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Book信息更新失败!"));
            return "error";
       }
   }

    /*删除Book信息*/
    public String DeleteBook() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            bookDAO.DeleteBook(barcode);
            ctx.put("message",  java.net.URLEncoder.encode("Book删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Book删除失败!"));
            return "error";
        }
    }

}
