<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.Reader" %>
<%@ page import="com.chengxusheji.domain.ReaderType" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //获取所有的ReaderType信息
    List<ReaderType> readerTypeList = (List<ReaderType>)request.getAttribute("readerTypeList");
    Reader reader = (Reader)request.getAttribute("reader");

%>
<HTML><HEAD><TITLE>查看读者</TITLE>
<STYLE type=text/css>
body{margin:0px; font-size:12px; background-image:url(<%=basePath%>images/bg.jpg); background-position:bottom; background-repeat:repeat-x; background-color:#A2D5F0;}
.STYLE1 {color: #ECE9D8}
.label {font-style.:italic; }
.errorLabel {font-style.:italic;  color:red; }
.errorMessage {font-weight:bold; color:red; }
</STYLE>
 <script src="<%=basePath %>calendar.js"></script>
</HEAD>
<BODY><br/><br/>
<s:fielderror cssStyle="color:red" />
<TABLE align="center" height="100%" cellSpacing=0 cellPadding=0 width="80%" border=0>
  <TBODY>
  <TR>
    <TD align="left" vAlign=top ><s:form action="" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3'  class="tablewidth">
  <tr>
    <td width=30%>读者编号:</td>
    <td width=70%><%=reader.getReaderNo() %></td>
  </tr>

  <tr>
    <td width=30%>登录密码:</td>
    <td width=70%><%=reader.getPassword() %></td>
  </tr>

  <tr>
    <td width=30%>读者类型:</td>
    <td width=70%>
      <%=reader.getReaderType().getReaderTypeName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>姓名:</td>
    <td width=70%><%=reader.getReaderName() %></td>
  </tr>

  <tr>
    <td width=30%>性别:</td>
    <td width=70%><%=reader.getSex() %></td>
  </tr>

  <tr>
    <td width=30%>读者头像:</td>
    <td width=70%><img src="<%=basePath %><%=reader.getPhoto() %>" width="200px" border="0px"/></td>
  </tr>
  <tr>
    <td width=30%>读者生日:</td>
        <% java.text.DateFormat birthdaySDF = new java.text.SimpleDateFormat("yyyy-MM-dd");  %>
    <td width=70%><%=birthdaySDF.format(reader.getBirthday()) %></td>
  </tr>

  <tr>
    <td width=30%>联系电话:</td>
    <td width=70%><%=reader.getTelephone() %></td>
  </tr>

  <tr>
    <td width=30%>联系Email:</td>
    <td width=70%><%=reader.getEmail() %></td>
  </tr>

  <tr>
    <td width=30%>联系qq:</td>
    <td width=70%><%=reader.getQq() %></td>
  </tr>

  <tr>
    <td width=30%>读者地址:</td>
    <td width=70%><%=reader.getAddress() %></td>
  </tr>

  <tr>
      <td colspan="4" align="center">
        <input type="button" value="返回" onclick="history.back();"/>
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
