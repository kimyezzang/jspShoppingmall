<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ include file="/common/setting.jsp" %> 
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상품 등록 처리 페이지</title>
<script src="${path}/resources/js/customerJS/main.js" charset="utf-8"></script>
</head>
<body>

<!-- insert 실패 -->
<c:if test="${insertCnt == 0}">
	<script type="text/javascript">
		alert("상품등록 실패");
	</script>
</c:if>


<!-- insert 성공 -->
<c:if test="${insertCnt != 0}">
	<script type="text/javascript">
		alert("상품등록 성공");
		window.location="${path}/productList.st"
	</script>
</c:if>
</body>
</html>