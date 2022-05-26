<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ include file="/common/setting.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상품 삭제 처리 페이지</title>
</head>
<body>
<!-- insert 실패 -->
<c:if test="${insertCnt == 0}">
	<script type="text/javascript">
		alert("상품삭제 실패");
	</script>
</c:if>


<!-- insert 성공 -->
<c:if test="${insertCnt != 0}">
	<script type="text/javascript">
		alert("상품삭제 성공");
		window.location="${path}/productList.st?pageNum=${pageNum}"
	</script>
</c:if>
</body>
</html>