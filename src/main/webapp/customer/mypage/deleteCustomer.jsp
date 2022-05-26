<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  <%@ include file="/common/setting.jsp" %>     
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>deleteCustomer.jsp</title>
<link rel="stylesheet" href="${path}/resources/css/commonCss/header.css">
<link rel="stylesheet" href="${path}/resources/css/customerCss/mypage/deleteCustomer.css">
<script src="${path}/resources/js/jquery-3.6.0.min.js"></script>
<script src="${path}/resources/js/jsTitle.js"></script>
<script src="https://kit.fontawesome.com/d46bcbd556.js" crossorigin="anonymous"></script>
</head>
<body>
<div id="wrap">
<!--헤더 -->
<%@ include file="../../common/header.jsp" %>
<!--헤더 끝-->

<div id="container">
	<div class="myPageLogo">	
		<h3>My page</h3>
	</div>
	
	
	<div class="leftMenu">
		<table>
			<tr>
				<td><a href="${path}/customerOrderList.do">주문 내역</a></td>
				
			</tr>
			<tr>
				<td><a href="${path}/customer/mypage/modifyCustomer.jsp">개인 정보 수정</a></td>
			</tr>
			<tr>
				<td>회원 탈퇴</td>
			</tr>
			
		</table>
	</div>
	<form action="${path}/deleteCustomerAction.do" method="post">
   <fieldset>
   	<legend><b>회원 탈퇴</b></legend>
   	<table class="join">
   		<tr>
   			<td colspan="2"><hr></td>
   		</tr>
   		<tr>
   			<td><label for="joinPW">비밀번호</label></td>
   			<td><input type="password" id="password" name="password" size="25" placeholder="비밀번호를 입력해주세요"></td>
   		</tr>
   		<tr>
   			<td colspan="2"><hr></td>
   		</tr>
   		<tr><td colspan="2"><input type="submit" value="확인"></td></tr>
   	</table>
   </fieldset>
  </form>   
</div>


<!-- 푸터시작 -->
<%@ include file="../../common/footer.jsp" %>
<!-- 푸터 끝 -->
</div>
</body>
</html>