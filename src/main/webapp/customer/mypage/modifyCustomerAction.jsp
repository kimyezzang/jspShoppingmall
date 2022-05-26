<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ include file="/common/setting.jsp" %>     
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원정보 수정 처리</title>
<link rel="stylesheet" href="${path}/resources/css/commonCss/header.css">
<link rel="stylesheet" href="${path}/resources/css/commonCss/footer.css">
<link rel="stylesheet" href="${path}/resources/css/commonCss/join.css">
<script src="${path}/resources/js/customerJS/errorMsg.js">

</script>
</head>
<body>
<!-- header시작 -->
<%@include file="../../common/header.jsp" %>
<!-- header시작 -->
<br>
<br>
	
	<c:if test="${updateCnt == 0 }">
		<!-- out.print("updateCnt : " + updateCnt); -->
		<script type="text/javascript">
			/* alert("회원정보 수정 실패"); */
			errorAlert(updateError);
		</script>
	</c:if>
	<c:if test="${updateCnt != 0 }">
		<% 			
				// 모든 세션 삭제 => 비밀번호가 변경되었으므로 다시 로그인 하도록 함(세션이 남아있으면 로그인시 변경전 비밀번호로 무조건 인증되므로)
				request.getSession().invalidate();	// 세션 삭제
		%>
			<script type="text/javascript">
				setTimeout(function(){
					alert("회원정보가 수정되었습니다.");
					window.location = "${path}/main.do";
				}, 1000);
			</script>
	</c:if>
	
<br><br><br><br><br><br>
<!-- footer 시작 -->
	<%@ include file="../../common/footer.jsp" %>
<!-- footer 끝 -->
</body>
</html>