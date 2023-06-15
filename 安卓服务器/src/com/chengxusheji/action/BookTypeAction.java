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

    /*��ǰ�ڼ�ҳ*/
    private int currentPage;
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public int getCurrentPage() {
        return currentPage;
    }

    /*һ������ҳ*/
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

    /*��ǰ��ѯ���ܼ�¼��Ŀ*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*ҵ������*/
    @Resource BookTypeDAO bookTypeDAO;

    /*��������BookType����*/
    private BookType bookType;
    public void setBookType(BookType bookType) {
        this.bookType = bookType;
    }
    public BookType getBookType() {
        return this.bookType;
    }

    /*��ת�����BookType��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*���BookType��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddBookType() {
        ActionContext ctx = ActionContext.getContext();
        try {
            bookTypeDAO.AddBookType(bookType);
            ctx.put("message",  java.net.URLEncoder.encode("BookType��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("BookType���ʧ��!"));
            return "error";
        }
    }

    /*��ѯBookType��Ϣ*/
    public String QueryBookType() {
        if(currentPage == 0) currentPage = 1;
        List<BookType> bookTypeList = bookTypeDAO.QueryBookTypeInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        bookTypeDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = bookTypeDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = bookTypeDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("bookTypeList",  bookTypeList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "query_view";
    }

    /*��̨������excel*/
    public String QueryBookTypeOutputToExcel() { 
        List<BookType> bookTypeList = bookTypeDAO.QueryBookTypeInfo();
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "BookType��Ϣ��¼"; 
        String[] headers = { "ͼ�����","�������","�ɽ�������"};
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
		HttpServletResponse response = null;//����һ��HttpServletResponse���� 
		OutputStream out = null;//����һ����������� 
		try { 
			response = ServletActionContext.getResponse();//��ʼ��HttpServletResponse���� 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"BookType.xls");//filename�����ص�xls���������������Ӣ�� 
			response.setContentType("application/msexcel;charset=UTF-8");//�������� 
			response.setHeader("Pragma","No-cache");//����ͷ 
			response.setHeader("Cache-Control","no-cache");//����ͷ 
			response.setDateHeader("Expires", 0);//��������ͷ  
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
    /*ǰ̨��ѯBookType��Ϣ*/
    public String FrontQueryBookType() {
        if(currentPage == 0) currentPage = 1;
        List<BookType> bookTypeList = bookTypeDAO.QueryBookTypeInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        bookTypeDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = bookTypeDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = bookTypeDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("bookTypeList",  bookTypeList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�BookType��Ϣ*/
    public String ModifyBookTypeQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������bookTypeId��ȡBookType����*/
        BookType bookType = bookTypeDAO.GetBookTypeByBookTypeId(bookTypeId);

        ctx.put("bookType",  bookType);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�BookType��Ϣ*/
    public String FrontShowBookTypeQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������bookTypeId��ȡBookType����*/
        BookType bookType = bookTypeDAO.GetBookTypeByBookTypeId(bookTypeId);

        ctx.put("bookType",  bookType);
        return "front_show_view";
    }

    /*�����޸�BookType��Ϣ*/
    public String ModifyBookType() {
        ActionContext ctx = ActionContext.getContext();
        try {
            bookTypeDAO.UpdateBookType(bookType);
            ctx.put("message",  java.net.URLEncoder.encode("BookType��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("BookType��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��BookType��Ϣ*/
    public String DeleteBookType() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            bookTypeDAO.DeleteBookType(bookTypeId);
            ctx.put("message",  java.net.URLEncoder.encode("BookTypeɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("BookTypeɾ��ʧ��!"));
            return "error";
        }
    }

}
