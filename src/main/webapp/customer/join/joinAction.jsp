<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
      <%@ include file="/common/setting.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JoinAction</title>
</head>
<body>
<div class="wrap">
	<!-- header시작 -->
		<%@ include file="../../common/header.jsp" %>
	<!-- header끝 -->

	<!-- // insert 실패 -->
<c:if test="${insertCnt == 0}">		
	<script type="text/javascript">
		alert("insert 실패");
	</script>		
</c:if>		

	<!-- // insert 성공 -->
<c:if test="${insertCnt != 0}">	
		<!-- response.sendRedirect("mainSuccess.do?insertCnt=" + insertCnt); -->
		<script type="text/javascript">
			alert("회원가입 성공");
		</script>
		<c:redirect url="mainSuccess.do?insertCnt=${insertCnt}"/>
</c:if>

	<!-- footer 시작 -->
	<%@ include file="../../common/footer.jsp" %>
	<!-- footer 끝 -->
</div>	
</body>
</html>