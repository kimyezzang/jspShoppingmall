<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ include file="../../common/setting.jsp" %> 
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="${path}/resources/js/jquery-3.6.0.min.js"></script>
</head>

<body>
<c:forEach var="list" items="${list}">
<form action="${path}/deleteComment.ad"  method="get">
<input type="hidden"  name ="hiddenComment_num" id ="hiddenComment_num" value="${list.comment_num}">
<input type="hidden" name ="hiddenBoard_num" id ="hiddenBoard_num" value="${list.board_num}">

<table border="1" width="700px">
	<tr style="text-align:center">
		<td width="15%" style="text-align:center">작성자</td>
		<td>${list.writer}</td>
		<td width="15%" style="text-align:center">작성시간</td>
		<td width="35%"><fmt:formatDate value="${list.reg_date}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	</tr>
	
	<tr>
		<td style="text-align:center">내용</td>
		<td colspan="3" ><textarea rows="4" cols="70" width="640px">${list.content}</textarea>
			<input type="submit" value="삭제" style="text-align:center; width:60px; height:50px;
			 display:inline; margin-top:-50px;">
		</td>
	</tr>

	
</table>
</form>
</c:forEach>
</body>
</html>