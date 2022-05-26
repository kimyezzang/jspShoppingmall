<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  <%@ include file="/common/setting.jsp" %>        
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>deleteCustomerAction</title>
<script src="${path}/resources/js/customerJS/errorMsg.js"></script>
<script src="${path}/resources/js/jquery-3.6.0.min.js"></script>
</head>
<body>
<!--헤더 -->
<%@ include file="../../common/header.jsp" %>
<!--헤더 끝-->

<!-- // 아이디와 비밀번호 일치 : 1 -->
<c:if test="${selectCnt == 1}">
	<!-- 탈퇴실패 -->
	<c:if test="${deleteCnt == 0}">	
			<script type="text/javascript">
				errorAlert(deleteError);
			</script>
	</c:if>	
	<!-- 탈퇴 성공 - 세션 삭제, main.jsp로 이동 -->
	<c:if test="${deleteCnt != 0}">		
		<% 
			request.getSession().invalidate();	// 모든 섹션 삭제
		%>
		<script type="text/javascript">
				setTimeout(function(){
				alert("탈퇴처리되었습니다.");
				window.location = "${path}/main.do";
				}, 1000);
		</script>
	</c:if>	<!-- 탈퇴 성공 -->
</c:if>		<!-- 아이디와 비밀번호 일치 : 1 -->
			
<!-- // 아이디와 비밀번호 불일치 : 0 -->		
<c:if test="${selectCnt != 1}">			
		<script type="text/javascript">
			errorAlert(passwordError);
		</script>
</c:if>

<!-- 푸터시작 -->
<%@ include file="../../common/footer.jsp" %>
<!-- 푸터 끝 -->
</body>
</html>