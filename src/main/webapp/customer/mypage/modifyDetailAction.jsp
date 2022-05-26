<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@ include file="/common/setting.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>modifyCustomer.jsp</title>
<link rel="stylesheet" href="${path}/resources/css/commonCss/header.css">
<link rel="stylesheet" href="${path}/resources/css/customerCss/mypage/modifyCustomerDetail.css">
<script src="${path}/resources/js/jquery-3.6.0.min.js"></script>
<script src="${path}/resources/js/jsTitle.js"></script>
<script src="https://kit.fontawesome.com/d46bcbd556.js" crossorigin="anonymous"></script>
<script src="${path}/resources/js/customerJS/ModifyMypage.js"></script>
<script src="${path}/resources/js/customerJS/errorMsg.js"></script>
</head>
<script type="text/javascript">

$(document).ready(function(){
	$("#email").on("change",function(){
		$("#hiddenEmail").val(0);
	});
});

/* var a = ${dto.getEmail()};
if (a == document.joinform.email.value){
	document.joinform.hiddenEmail.value = "1";
}; */
</script>
<body>
<div id="wrap">
<!--헤더 -->
<%@ include file="../../common/header.jsp" %>
<!--헤더 끝-->
   <!-- 메인 컨텐츠 시작 -->
<div id="container">
<c:if test="${selectCnt ==1 }">
	<div class="myPageLogo">	
		<h3>My page</h3>
	</div>
	
	<div class="leftMenu">
		<table>
			<tr>
				<td><a href="${path}/customerOrderList.do">주문 내역</a></td>
			</tr>
			<tr>
				<td>개인 정보 수정</td>
			</tr>
			<tr>
				<td><a href="${path}/customer/mypage/deleteCustomer.jsp">회원 탈퇴</a></td>
			</tr>
			
		</table>
	</div>
	<form action="${path}/modifyCustomerAction.do" name="joinform" method="post" onsubmit="return signInCheck()">
	<input type="hidden" id="hiddenEmail" name="hiddenEmail" value="1">
	<input type="hidden" id="hiddenEmailEx" name="hiddenEmailEx" value="${dto.getEmail()}">
   <fieldset>
   	<legend><b>개인정보 수정</b></legend>
   	<table class="join">
   		<tr>
   			<td colspan="3"><hr></td>
   		</tr>
   		<tr>
   			<td><label for="joinPW">비밀번호</label></td>
   			<td><input type="password" name="password" id="password" size="20" placeholder="비밀번호를 입력해주세요" value="${dto.getPassword()}"></td>
   		</tr>
   		<tr>
   			<td><label for="joinPWRe">비밀번호확인</label></td>
   			<td><input type="password" name="rePassword" id="rePassword" size="20" placeholder="비밀번호를 한번 더 입력해주세요" value="${dto.getPassword()}"></td>
   		</tr>
   		<tr>
   			<td><label for="joinPWRe">비밀번호힌트</label></td>
   			<td><input type="text" name="passwordHint" id="passwordHint" size="25" placeholder="출신 초등학교를 입력해주세요 ex) 관모초등학교"
   			 value="${dto.getPasswordHint()}"></td>
   		</tr>
   		<tr>
   			<td><label for="joinName">이름</label></td>
   			<td><input type="text" id="name" name="name" size="20" placeholder="이름을 입력해주세요" value="${dto.getName()}"></td>
   		</tr>
   		<tr>
   			<td><label for="joinEmail">이메일</label></td>
   			<td><input type="text" id="email" name="email" size="50" placeholder="이메일을 입력해주세요" value="${dto.getEmail()}"></td>
   			<td>
   				<input type="button" value="중복확인" id="emailChkButton" onclick="confirmEmail();">
   			</td>
   		</tr>
   		<tr>
   			<td><label for="joinHP">휴대폰</label></td>
   			<td><input type="text" id="hp" name="hp" size="13" placeholder="숫자만 입력해주세요" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" value="${dto.getHp()}"></td>
   		</tr>
   		<tr>
   			<td><label for="joinAddress">주소</label></td>
   			<td><input type="text" id="address" name="address" size="50" placeholder="주소를 입력해주세요" value="${dto.getEmail()}"></td>
   		</tr>
   		<tr>
   			<c:set var="br" value="${dto.getBirth()}"/>
   			<td><label for="joinBirth">생년월일</label></td>
   			<td><input type="text" id="birth" name="birth" size="10" placeholder="생년월일을 입력해주세요 ex) 19880212" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" 
   			value="${fn:substring(br,0,4)}${fn:substring(br,5,7)}${fn:substring(br,8,10)}"></td>
   		</tr>
   		<tr>
   			<td colspan="3"><hr></td>
   		</tr>
   		<tr><td colspan="2"><input type="submit" value="확인"></td></tr>
   	</table>
   </fieldset>
  </form>   
  </c:if>
</div>

	<c:if test="${selectCnt != 1 }">
		<script type="text/javascript">
			errorAlert(passwordError);
		</script>
	</c:if>		



<!-- 푸터시작 -->
<%@ include file="../../common/footer.jsp" %>
<!-- 푸터 끝 -->
</div>
</body>
</html>