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
import com.chengxusheji.dao.ReaderDAO;
import com.chengxusheji.domain.Reader;
import com.chengxusheji.dao.ReaderTypeDAO;
import com.chengxusheji.domain.ReaderType;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class ReaderAction extends BaseAction {

	/*图片或文件字段photo参数接收*/
	private File photoFile;
	private String photoFileFileName;
	private String photoFileContentType;
	public File getPhotoFile() {
		return photoFile;
	}
	public void setPhotoFile(File photoFile) {
		this.photoFile = photoFile;
	}
	public String getPhotoFileFileName() {
		return photoFileFileName;
	}
	public void setPhotoFileFileName(String photoFileFileName) {
		this.photoFileFileName = photoFileFileName;
	}
	public String getPhotoFileContentType() {
		return photoFileContentType;
	}
	public void setPhotoFileContentType(String photoFileContentType) {
		this.photoFileContentType = photoFileContentType;
	}
    /*界面层需要查询的属性: 读者编号*/
    private String readerNo;
    public void setReaderNo(String readerNo) {
        this.readerNo = readerNo;
    }
    public String getReaderNo() {
        return this.readerNo;
    }

    /*界面层需要查询的属性: 读者类型*/
    private ReaderType readerType;
    public void setReaderType(ReaderType readerType) {
        this.readerType = readerType;
    }
    public ReaderType getReaderType() {
        return this.readerType;
    }

    /*界面层需要查询的属性: 姓名*/
    private String readerName;
    public void setReaderName(String readerName) {
        this.readerName = readerName;
    }
    public String getReaderName() {
        return this.readerName;
    }

    /*界面层需要查询的属性: 读者生日*/
    private String birthday;
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
    public String getBirthday() {
        return this.birthday;
    }

    /*界面层需要查询的属性: 联系电话*/
    private String telephone;
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    public String getTelephone() {
        return this.telephone;
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
    @Resource ReaderTypeDAO readerTypeDAO;
    @Resource ReaderDAO readerDAO;

    /*待操作的Reader对象*/
    private Reader reader;
    public void setReader(Reader reader) {
        this.reader = reader;
    }
    public Reader getReader() {
        return this.reader;
    }

    /*跳转到添加Reader视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*查询所有的ReaderType信息*/
        List<ReaderType> readerTypeList = readerTypeDAO.QueryAllReaderTypeInfo();
        ctx.put("readerTypeList", readerTypeList);
        return "add_view";
    }

    /*添加Reader信息*/
    @SuppressWarnings("deprecation")
    public String AddReader() {
        ActionContext ctx = ActionContext.getContext();
        /*验证读者编号是否已经存在*/
        String readerNo = reader.getReaderNo();
        Reader db_reader = readerDAO.GetReaderByReaderNo(readerNo);
        if(null != db_reader) {
            ctx.put("error",  java.net.URLEncoder.encode("该读者编号已经存在!"));
            return "error";
        }
        try {
            ReaderType readerType = readerTypeDAO.GetReaderTypeByReaderTypeId(reader.getReaderType().getReaderTypeId());
            reader.setReaderType(readerType);
            /*处理读者头像上传*/
            String photoPath = "upload/noimage.jpg"; 
       	 	if(photoFile != null)
       	 		photoPath = photoUpload(photoFile,photoFileContentType);
       	 	reader.setPhoto(photoPath);
            readerDAO.AddReader(reader);
            ctx.put("message",  java.net.URLEncoder.encode("Reader添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Reader添加失败!"));
            return "error";
        }
    }

    /*查询Reader信息*/
    public String QueryReader() {
        if(currentPage == 0) currentPage = 1;
        if(readerNo == null) readerNo = "";
        if(readerName == null) readerName = "";
        if(birthday == null) birthday = "";
        if(telephone == null) telephone = "";
        List<Reader> readerList = readerDAO.QueryReaderInfo(readerNo, readerType, readerName, birthday, telephone, currentPage);
        /*计算总的页数和总的记录数*/
        readerDAO.CalculateTotalPageAndRecordNumber(readerNo, readerType, readerName, birthday, telephone);
        /*获取到总的页码数目*/
        totalPage = readerDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = readerDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("readerList",  readerList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("readerNo", readerNo);
        ctx.put("readerType", readerType);
        List<ReaderType> readerTypeList = readerTypeDAO.QueryAllReaderTypeInfo();
        ctx.put("readerTypeList", readerTypeList);
        ctx.put("readerName", readerName);
        ctx.put("birthday", birthday);
        ctx.put("telephone", telephone);
        return "query_view";
    }

    /*后台导出到excel*/
    public String QueryReaderOutputToExcel() { 
        if(readerNo == null) readerNo = "";
        if(readerName == null) readerName = "";
        if(birthday == null) birthday = "";
        if(telephone == null) telephone = "";
        List<Reader> readerList = readerDAO.QueryReaderInfo(readerNo,readerType,readerName,birthday,telephone);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Reader信息记录"; 
        String[] headers = { "读者编号","读者类型","姓名","性别","读者头像","读者生日","联系电话","联系qq","读者地址"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<readerList.size();i++) {
        	Reader reader = readerList.get(i); 
        	dataset.add(new String[]{reader.getReaderNo(),reader.getReaderType().getReaderTypeName(),
reader.getReaderName(),reader.getSex(),reader.getPhoto(),new SimpleDateFormat("yyyy-MM-dd").format(reader.getBirthday()),reader.getTelephone(),reader.getQq(),reader.getAddress()});
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
			response.setHeader("Content-disposition","attachment; filename="+"Reader.xls");//filename是下载的xls的名，建议最好用英文 
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
    /*前台查询Reader信息*/
    public String FrontQueryReader() {
        if(currentPage == 0) currentPage = 1;
        if(readerNo == null) readerNo = "";
        if(readerName == null) readerName = "";
        if(birthday == null) birthday = "";
        if(telephone == null) telephone = "";
        List<Reader> readerList = readerDAO.QueryReaderInfo(readerNo, readerType, readerName, birthday, telephone, currentPage);
        /*计算总的页数和总的记录数*/
        readerDAO.CalculateTotalPageAndRecordNumber(readerNo, readerType, readerName, birthday, telephone);
        /*获取到总的页码数目*/
        totalPage = readerDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = readerDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("readerList",  readerList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("readerNo", readerNo);
        ctx.put("readerType", readerType);
        List<ReaderType> readerTypeList = readerTypeDAO.QueryAllReaderTypeInfo();
        ctx.put("readerTypeList", readerTypeList);
        ctx.put("readerName", readerName);
        ctx.put("birthday", birthday);
        ctx.put("telephone", telephone);
        return "front_query_view";
    }

    /*查询要修改的Reader信息*/
    public String ModifyReaderQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键readerNo获取Reader对象*/
        Reader reader = readerDAO.GetReaderByReaderNo(readerNo);

        List<ReaderType> readerTypeList = readerTypeDAO.QueryAllReaderTypeInfo();
        ctx.put("readerTypeList", readerTypeList);
        ctx.put("reader",  reader);
        return "modify_view";
    }

    /*查询要修改的Reader信息*/
    public String FrontShowReaderQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键readerNo获取Reader对象*/
        Reader reader = readerDAO.GetReaderByReaderNo(readerNo);

        List<ReaderType> readerTypeList = readerTypeDAO.QueryAllReaderTypeInfo();
        ctx.put("readerTypeList", readerTypeList);
        ctx.put("reader",  reader);
        return "front_show_view";
    }

    /*更新修改Reader信息*/
    public String ModifyReader() {
        ActionContext ctx = ActionContext.getContext();
        try {
            ReaderType readerType = readerTypeDAO.GetReaderTypeByReaderTypeId(reader.getReaderType().getReaderTypeId());
            reader.setReaderType(readerType);
            /*处理读者头像上传*/
            if(photoFile != null) {
            	String photoPath = photoUpload(photoFile,photoFileContentType);
            	reader.setPhoto(photoPath);
            }
            readerDAO.UpdateReader(reader);
            ctx.put("message",  java.net.URLEncoder.encode("Reader信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Reader信息更新失败!"));
            return "error";
       }
   }

    /*删除Reader信息*/
    public String DeleteReader() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            readerDAO.DeleteReader(readerNo);
            ctx.put("message",  java.net.URLEncoder.encode("Reader删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Reader删除失败!"));
            return "error";
        }
    }

}
