<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  <%@ include file="/common/setting.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
<link rel="stylesheet" href="${path}/resources/css/commonCss/header.css">
<link rel="stylesheet" href="${path}/resources/css/commonCss/join.css">
<script src="${path}/resources/js/jquery-3.6.0.min.js"></script>
<script src="${path}/resources/js/jsTitle.js"></script>
<script src="https://kit.fontawesome.com/d46bcbd556.js" crossorigin="anonymous"></script>
<script src="${path}/resources/js/customerJS/Join.js"></script>
<!-- https://fontawesome.com/ -->
<script type="text/javascript">
$(document).ready(function(){
	$("#id").on("change",function(){
		$("#hiddenId").val(0);
	});
});

$(document).ready(function(){
	$("#email").on("change",function(){
		$("#hiddenEmail").val(0);
	});
});
</script>
</head>
<body>
<div id="wrap">
<!--헤더 -->
<%@ include file="../../common/header.jsp" %>
<!--헤더 끝-->
<div id="container"> 
	
<form name="joinform" action="${path}/joinAction.do" method="post" onsubmit="return signInCheck()">
   <input type="hidden" id="hiddenId" name="hiddenId" value="0">
   <input type="hidden" id="hiddenEmail" name="hiddenEmail" value="0">
   <fieldset>
   	<legend><b>회원가입</b></legend>
   	<table id="join">
   		<tr>
   			<td colspan="3"><hr></td>
   		</tr>
   		<tr>
   			<td><label for="joinID">아이디</label></td>
   			<td><input type="text" name="id" id="id" size="20" placeholder="6자 이상 20자 이하의 영문 혹은 영문과 숫자를 조합" autofocus></td>
   			<td>
   				<input type="button" value="중복확인" id="idChkButton" onclick="confirmId();">
   			</td>
   		</tr>
   		<tr>
   			<td><label for="joinPW">비밀번호</label></td>
   			<td><input type="password" name="password" id="password" size="20" placeholder="비밀번호를 입력해주세요"></td>
   		</tr>
   		<tr>
   			<td><label for="joinPWRe">비밀번호확인</label></td>
   			<td><input type="password" name="rePassword" id="rePassword" size="20" placeholder="비밀번호를 한번 더 입력해주세요"></td>
   		</tr>
   		<tr>
   			<td><label for="joinPWHint">비밀번호힌트</label></td>
   			<td><input type="text" id="passwordHint" name="passwordHint" size="20" placeholder="출신 초등학교를 입력해주세요 ex) 관모초등학교"></td>
   		</tr>
   		<tr>
   			<td><label for="joinName">이름</label></td>
   			<td><input type="text" id="name" name="name" size="20" placeholder="이름을 입력해주세요"></td>
   		</tr>
   		<tr>
   			<td><label for="joinEmail">이메일</label></td>
   			<td><input type="text" id="email" name="email" size="50" placeholder="이메일을 입력해주세요" ></td>
   			<td>
   				<input type="button" value="중복확인" id="emailChkButton" onclick="confirmEmail();">
   			</td>	
   		</tr>
   		<tr>
   			<td><label for="joinHP">휴대폰</label></td>
   			<td><input type="text" id="hp" name="hp" size="13" placeholder="숫자만 입력해주세요" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');"></td>
   		</tr>
   		<tr>
   			<td><label for="joinAddress">주소</label></td>
   			<td><input type="text" id="address" name="address" size="50" placeholder="주소를 입력해주세요" ></td>
   		</tr>
   		<tr>
   			<td><label for="joinBirth">생년월일</label></td>
   			<td><input type="text" id="birth" name="birth" size="10" placeholder="생년월일을 입력해주세요 ex) 19880212" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');"></td>
   		</tr>
   		<tr>
   			<td colspan="3"><hr></td>
   		</tr>
   		<tr><td colspan="3"><input type="submit" value="가입하기"></td></tr>
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