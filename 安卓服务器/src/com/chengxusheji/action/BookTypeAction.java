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
import com.chengxusheji.dao.BookTypeDAO;
import com.chengxusheji.domain.BookType;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class BookTypeAction extends BaseAction {

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

    private int bookTypeId;
    public void setBookTypeId(int bookTypeId) {
        this.bookTypeId = bookTypeId;
    }
    public int getBookTypeId() {
        return bookTypeId;
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

    /*待操作的BookType对象*/
    private BookType bookType;
    public void setBookType(BookType bookType) {
        this.bookType = bookType;
    }
    public BookType getBookType() {
        return this.bookType;
    }

    /*跳转到添加BookType视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*添加BookType信息*/
    @SuppressWarnings("deprecation")
    public String AddBookType() {
        ActionContext ctx = ActionContext.getContext();
        try {
            bookTypeDAO.AddBookType(bookType);
            ctx.put("message",  java.net.URLEncoder.encode("BookType添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("BookType添加失败!"));
            return "error";
        }
    }

    /*查询BookType信息*/
    public String QueryBookType() {
        if(currentPage == 0) currentPage = 1;
        List<BookType> bookTypeList = bookTypeDAO.QueryBookTypeInfo(currentPage);
        /*计算总的页数和总的记录数*/
        bookTypeDAO.CalculateTotalPageAndRecordNumber();
        /*获取到总的页码数目*/
        totalPage = bookTypeDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = bookTypeDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("bookTypeList",  bookTypeList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "query_view";
    }

    /*后台导出到excel*/
    public String QueryBookTypeOutputToExcel() { 
        List<BookType> bookTypeList = bookTypeDAO.QueryBookTypeInfo();
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "BookType信息记录"; 
        String[] headers = { "图书类别","类别名称","可借阅天数"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<bookTypeList.size();i++) {
        	BookType bookType = bookTypeList.get(i); 
        	dataset.add(new String[]{bookType.getBookTypeId() + "",bookType.getBookTypeName(),bookType.getDays() + ""});
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
			response.setHeader("Content-disposition","attachment; filename="+"BookType.xls");//filename是下载的xls的名，建议最好用英文 
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
    /*前台查询BookType信息*/
    public String FrontQueryBookType() {
        if(currentPage == 0) currentPage = 1;
        List<BookType> bookTypeList = bookTypeDAO.QueryBookTypeInfo(currentPage);
        /*计算总的页数和总的记录数*/
        bookTypeDAO.CalculateTotalPageAndRecordNumber();
        /*获取到总的页码数目*/
        totalPage = bookTypeDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = bookTypeDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("bookTypeList",  bookTypeList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "front_query_view";
    }

    /*查询要修改的BookType信息*/
    public String ModifyBookTypeQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键bookTypeId获取BookType对象*/
        BookType bookType = bookTypeDAO.GetBookTypeByBookTypeId(bookTypeId);

        ctx.put("bookType",  bookType);
        return "modify_view";
    }

    /*查询要修改的BookType信息*/
    public String FrontShowBookTypeQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键bookTypeId获取BookType对象*/
        BookType bookType = bookTypeDAO.GetBookTypeByBookTypeId(bookTypeId);

        ctx.put("bookType",  bookType);
        return "front_show_view";
    }

    /*更新修改BookType信息*/
    public String ModifyBookType() {
        ActionContext ctx = ActionContext.getContext();
        try {
            bookTypeDAO.UpdateBookType(bookType);
            ctx.put("message",  java.net.URLEncoder.encode("BookType信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("BookType信息更新失败!"));
            return "error";
       }
   }

    /*删除BookType信息*/
    public String DeleteBookType() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            bookTypeDAO.DeleteBookType(bookTypeId);
            ctx.put("message",  java.net.URLEncoder.encode("BookType删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("BookType删除失败!"));
            return "error";
        }
    }

}
