<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/setting.jsp" %>    
<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<c:forEach var="list" items="${list}">
	<div class="boardContentCommentList">
			<div>
				<div class="id" style="margin-left:0;">
					<p>${list.writer}</p>	
					<div class="delete">
					</div>		
				</div>
				<div class="content">
					<textarea rows="4" cols="70" width="640px">${list.content}</textarea>
				</div>
				<div class="date">
					<p><fmt:formatDate value="${list.reg_date}" pattern="yyyy-MM-dd HH:mm:ss"/></p>
				</div>
				<hr style="width:600px;">
			</div>
	</div>
</c:forEach>		
</body>
</html>