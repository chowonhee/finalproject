<%@ page contentType = "text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="./view/color.jspf"%>

<html>
<head>
<title>게시판</title>

</head>
<script>alert("aaaaa");</script>
<body> 
<b>글내용 보기</b>
<br>
<form>
<table width="500" border="1" cellspacing="0" cellpadding="0" align="center"> 
  <tr height="30">
    <td align="center" width="125" >글번호</td>
    <td align="center" width="125" align="center">${article.num}</td>
    <td align="center" width="125" >조회수</td>
    <td align="center" width="125" align="center">${article.readcount}</td>
  </tr>
  <tr height="30">
    <td align="center" width="125" >작성자</td>
    <td align="center" width="125" align="center">${article.writer}</td>
    <td align="center" width="125"  >작성일</td>
    <td align="center" width="125" align="center">${article.reg_date}</td>
  </tr>
  <tr height="30">
    <td align="center" width="125" >글제목</td>
    <td align="center" width="375" align="center" colspan="3">${article.subject}</td>
  </tr>
  <tr>
    <td align="center" width="125" >글내용</td>
    <td align="left" width="375" colspan="3"><pre>${article.content}</pre></td>
  </tr>
  <tr height="30">     
    <td colspan="4" bgcolor="${value_c}" align="right" >
  <input type="button" value="글수정"
       onclick="document.location.href='updateForm.do?num=${article.num}&pageNum=${pageNum}'">
   &nbsp;&nbsp;&nbsp;&nbsp;
  <input type="button" value="글삭제"
       onclick="document.location.href='deleteForm.do?num=${article.num}&pageNum=${pageNum}'">
   &nbsp;&nbsp;&nbsp;&nbsp;
      <input type="button" value="답글쓰기"
       onclick="document.location.href='writeForm.do?num=${article.num}&ref=${article.ref}&re_step=${article.re_step}&re_level=${article.re_level}'">
   &nbsp;&nbsp;&nbsp;&nbsp;
       <input type="button" value="글목록"
       onclick="document.location.href='list.do?pageNum=${pageNum}'">
    </td>
  </tr>
</table>   
</form>     
</body>
</html>    