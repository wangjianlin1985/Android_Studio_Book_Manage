<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%>
<%@ page import="com.chengxusheji.domain.BookType" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //获取所有的BookType信息
    List<BookType> bookTypeList = (List<BookType>)request.getAttribute("bookTypeList");
    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>添加图书</TITLE> 
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
    var barcode = document.getElementById("book.barcode").value;
    if(barcode=="") {
        alert('请输入图书条形码!');
        return false;
    }
    var bookName = document.getElementById("book.bookName").value;
    if(bookName=="") {
        alert('请输入图书名称!');
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
    <s:form action="Book/Book_AddBook.action" method="post" id="bookAddForm" onsubmit="return checkForm();"  enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">

  <tr>
    <td width=30%>图书条形码:</td>
    <td width=70%><input id="book.barcode" name="book.barcode" type="text" /></td>
  </tr>

  <tr>
    <td width=30%>图书类别:</td>
    <td width=70%>
      <select name="book.bookType.bookTypeId">
      <%
        for(BookType bookType:bookTypeList) {
      %>
          <option value='<%=bookType.getBookTypeId() %>'><%=bookType.getBookTypeName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>图书名称:</td>
    <td width=70%><input id="book.bookName" name="book.bookName" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>图书图片:</td>
    <td width=70%><input id="bookPhotoFile" name="bookPhotoFile" type="file" size="50" /></td>
  </tr>

  <tr>
    <td width=30%>图书价格:</td>
    <td width=70%><input id="book.price" name="book.price" type="text" size="8" /></td>
  </tr>

  <tr>
    <td width=30%>库存:</td>
    <td width=70%><input id="book.count" name="book.count" type="text" size="8" /></td>
  </tr>

  <tr>
    <td width=30%>出版日期:</td>
    <td width=70%><input type="text" readonly id="book.publishDate"  name="book.publishDate" onclick="setDay(this);"/></td>
  </tr>

  <tr>
    <td width=30%>出版社:</td>
    <td width=70%><input id="book.publish" name="book.publish" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>图书简介:</td>
    <td width=70%><textarea id="book.introduction" name="book.introduction" rows="5" cols="50"></textarea></td>
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
