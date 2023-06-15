<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%>
<%@ page import="com.chengxusheji.domain.ReaderType" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //��ȡ���е�ReaderType��Ϣ
    List<ReaderType> readerTypeList = (List<ReaderType>)request.getAttribute("readerTypeList");
    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>��Ӷ���</TITLE> 
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
/*��֤��*/
function checkForm() {
    var readerNo = document.getElementById("reader.readerNo").value;
    if(readerNo=="") {
        alert('��������߱��!');
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
    <TD align="left" vAlign=top >
    <s:form action="Reader/Reader_AddReader.action" method="post" id="readerAddForm" onsubmit="return checkForm();"  enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">

  <tr>
    <td width=30%>���߱��:</td>
    <td width=70%><input id="reader.readerNo" name="reader.readerNo" type="text" /></td>
  </tr>

  <tr>
    <td width=30%>��¼����:</td>
    <td width=70%><input id="reader.password" name="reader.password" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>��������:</td>
    <td width=70%>
      <select name="reader.readerType.readerTypeId">
      <%
        for(ReaderType readerType:readerTypeList) {
      %>
          <option value='<%=readerType.getReaderTypeId() %>'><%=readerType.getReaderTypeName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>����:</td>
    <td width=70%><input id="reader.readerName" name="reader.readerName" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>�Ա�:</td>
    <td width=70%><input id="reader.sex" name="reader.sex" type="text" size="2" /></td>
  </tr>

  <tr>
    <td width=30%>����ͷ��:</td>
    <td width=70%><input id="photoFile" name="photoFile" type="file" size="50" /></td>
  </tr>

  <tr>
    <td width=30%>��������:</td>
    <td width=70%><input type="text" readonly id="reader.birthday"  name="reader.birthday" onclick="setDay(this);"/></td>
  </tr>

  <tr>
    <td width=30%>��ϵ�绰:</td>
    <td width=70%><input id="reader.telephone" name="reader.telephone" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>��ϵEmail:</td>
    <td width=70%><input id="reader.email" name="reader.email" type="text" size="50" /></td>
  </tr>

  <tr>
    <td width=30%>��ϵqq:</td>
    <td width=70%><input id="reader.qq" name="reader.qq" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>���ߵ�ַ:</td>
    <td width=70%><input id="reader.address" name="reader.address" type="text" size="80" /></td>
  </tr>

  <tr bgcolor='#FFFFFF'>
      <td colspan="4" align="center">
        <input type='submit' name='button' value='����' >
        &nbsp;&nbsp;
        <input type="reset" value='��д' />
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
