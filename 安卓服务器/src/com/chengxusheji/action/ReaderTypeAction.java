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
import com.chengxusheji.dao.ReaderTypeDAO;
import com.chengxusheji.domain.ReaderType;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class ReaderTypeAction extends BaseAction {

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

    private int readerTypeId;
    public void setReaderTypeId(int readerTypeId) {
        this.readerTypeId = readerTypeId;
    }
    public int getReaderTypeId() {
        return readerTypeId;
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
    @Resource ReaderTypeDAO readerTypeDAO;

    /*��������ReaderType����*/
    private ReaderType readerType;
    public void setReaderType(ReaderType readerType) {
        this.readerType = readerType;
    }
    public ReaderType getReaderType() {
        return this.readerType;
    }

    /*��ת�����ReaderType��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*���ReaderType��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddReaderType() {
        ActionContext ctx = ActionContext.getContext();
        try {
            readerTypeDAO.AddReaderType(readerType);
            ctx.put("message",  java.net.URLEncoder.encode("ReaderType��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("ReaderType���ʧ��!"));
            return "error";
        }
    }

    /*��ѯReaderType��Ϣ*/
    public String QueryReaderType() {
        if(currentPage == 0) currentPage = 1;
        List<ReaderType> readerTypeList = readerTypeDAO.QueryReaderTypeInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        readerTypeDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = readerTypeDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = readerTypeDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("readerTypeList",  readerTypeList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "query_view";
    }

    /*��̨������excel*/
    public String QueryReaderTypeOutputToExcel() { 
        List<ReaderType> readerTypeList = readerTypeDAO.QueryReaderTypeInfo();
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "ReaderType��Ϣ��¼"; 
        String[] headers = { "�������ͱ��","��������","�ɽ�����Ŀ"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<readerTypeList.size();i++) {
        	ReaderType readerType = readerTypeList.get(i); 
        	dataset.add(new String[]{readerType.getReaderTypeId() + "",readerType.getReaderTypeName(),readerType.getNumber() + ""});
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
			response.setHeader("Content-disposition","attachment; filename="+"ReaderType.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯReaderType��Ϣ*/
    public String FrontQueryReaderType() {
        if(currentPage == 0) currentPage = 1;
        List<ReaderType> readerTypeList = readerTypeDAO.QueryReaderTypeInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        readerTypeDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = readerTypeDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = readerTypeDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("readerTypeList",  readerTypeList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�ReaderType��Ϣ*/
    public String ModifyReaderTypeQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������readerTypeId��ȡReaderType����*/
        ReaderType readerType = readerTypeDAO.GetReaderTypeByReaderTypeId(readerTypeId);

        ctx.put("readerType",  readerType);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�ReaderType��Ϣ*/
    public String FrontShowReaderTypeQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������readerTypeId��ȡReaderType����*/
        ReaderType readerType = readerTypeDAO.GetReaderTypeByReaderTypeId(readerTypeId);

        ctx.put("readerType",  readerType);
        return "front_show_view";
    }

    /*�����޸�ReaderType��Ϣ*/
    public String ModifyReaderType() {
        ActionContext ctx = ActionContext.getContext();
        try {
            readerTypeDAO.UpdateReaderType(readerType);
            ctx.put("message",  java.net.URLEncoder.encode("ReaderType��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("ReaderType��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��ReaderType��Ϣ*/
    public String DeleteReaderType() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            readerTypeDAO.DeleteReaderType(readerTypeId);
            ctx.put("message",  java.net.URLEncoder.encode("ReaderTypeɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("ReaderTypeɾ��ʧ��!"));
            return "error";
        }
    }

}
