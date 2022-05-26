<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@ include file="/common/setting.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원삭제 처리 페이지</title>
</head>
<body>
<!-- insert 실패 -->
<c:if test="${insertCnt == 0}">
	<script type="text/javascript">
		alert("회원삭제 실패");
	</script>
</c:if>

<!-- insert 성공 -->
<c:if test="${insertCnt != 0}">
	<script type="text/javascript">
		alert("회원삭제 성공");
		window.location="${path}/userList.etc?pageNum=${pageNum}"
	</script>
</c:if>
</body>
</html>