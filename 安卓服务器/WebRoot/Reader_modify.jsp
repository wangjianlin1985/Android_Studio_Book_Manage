<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.Reader" %>
<%@ page import="com.chengxusheji.domain.ReaderType" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //获取所有的ReaderType信息
    List<ReaderType> readerTypeList = (List<ReaderType>)request.getAttribute("readerTypeList");
    Reader reader = (Reader)request.getAttribute("reader");

    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>修改读者</TITLE>
<STYLE type=text/css>
BODY {
	MARGIN-LEFT: 0px; BACKGROUND-COLOR: #ffffff
}
.STYLE1 {color: #ECE9D8}
.label {font-style.:italic; }
.errorLabel {font-style.:italic;  color:red; }
.errorMessage {font-weight:bold; color:red; }
</STYLE>
 <script src="<%=basePath %>calendar.js"></script>
<script language="javascript">
/*验证表单*/
function checkForm() {
    var readerNo = document.getElementById("reader.readerNo").value;
    if(readerNo=="") {
        alert('请输入读者编号!');
        return false;
    }
    return true; 
}
 </script>
</HEAD>
<BODY background="<%=basePath %>images/adminBg.jpg">
<s:fielderror cssStyle="color:red" />
<TABLE align="center" height="100%" cellSpacing=0 cellPadding=0 width="80%" border=0>
  <TBODY>
  <TR>
    <TD align="left" vAlign=top ><s:form action="Reader/Reader_ModifyReader.action" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">
  <tr>
    <td width=30%>读者编号:</td>
    <td width=70%><input id="reader.readerNo" name="reader.readerNo" type="text" value="<%=reader.getReaderNo() %>" readOnly /></td>
  </tr>

  <tr>
    <td width=30%>登录密码:</td>
    <td width=70%><input id="reader.password" name="reader.password" type="text" size="20" value='<%=reader.getPassword() %>'/></td>
  </tr>

  <tr>
    <td width=30%>读者类型:</td>
    <td width=70%>
      <select name="reader.readerType.readerTypeId">
      <%
        for(ReaderType readerType:readerTypeList) {
          String selected = "";
          if(readerType.getReaderTypeId() == reader.getReaderType().getReaderTypeId())
            selected = "selected";
      %>
          <option value='<%=readerType.getReaderTypeId() %>' <%=selected %>><%=readerType.getReaderTypeName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>姓名:</td>
    <td width=70%><input id="reader.readerName" name="reader.readerName" type="text" size="20" value='<%=reader.getReaderName() %>'/></td>
  </tr>

  <tr>
    <td width=30%>性别:</td>
    <td width=70%><input id="reader.sex" name="reader.sex" type="text" size="2" value='<%=reader.getSex() %>'/></td>
  </tr>

  <tr>
    <td width=30%>读者头像:</td>
    <td width=70%><img src="<%=basePath %><%=reader.getPhoto() %>" width="200px" border="0px"/><br/>
    <input type=hidden name="reader.photo" value="<%=reader.getPhoto() %>" />
    <input id="photoFile" name="photoFile" type="file" size="50" /></td>
  </tr>
  <tr>
    <td width=30%>读者生日:</td>
    <% DateFormat birthdaySDF = new SimpleDateFormat("yyyy-MM-dd");  %>
    <td width=70%><input type="text" readonly  id="reader.birthday"  name="reader.birthday" onclick="setDay(this);" value='<%=birthdaySDF.format(reader.getBirthday()) %>'/></td>
  </tr>

  <tr>
    <td width=30%>联系电话:</td>
    <td width=70%><input id="reader.telephone" name="reader.telephone" type="text" size="20" value='<%=reader.getTelephone() %>'/></td>
  </tr>

  <tr>
    <td width=30%>联系Email:</td>
    <td width=70%><input id="reader.email" name="reader.email" type="text" size="50" value='<%=reader.getEmail() %>'/></td>
  </tr>

  <tr>
    <td width=30%>联系qq:</td>
    <td width=70%><input id="reader.qq" name="reader.qq" type="text" size="20" value='<%=reader.getQq() %>'/></td>
  </tr>

  <tr>
    <td width=30%>读者地址:</td>
    <td width=70%><input id="reader.address" name="reader.address" type="text" size="80" value='<%=reader.getAddress() %>'/></td>
  </tr>

  <tr bgcolor='#FFFFFF'>
      <td colspan="4" align="center">
        <input type='submit' name='button' value='保存' >
        &nbsp;&nbsp;
        <input type="reset" value='重写' />
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
