<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  <%@include file="setting.jsp" %>        
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메인화면</title>
<link rel="stylesheet" href="${path}/resources/css/commonCss/header.css">
<link rel="stylesheet" href="${path}/resources/css/customerCss/mainpage.css">
<script src="${path}/resources/js/jquery-3.6.0.min.js"></script>
<script src="${path}/resources/js/jsTitle.js"></script>
<script src="https://kit.fontawesome.com/d46bcbd556.js" crossorigin="anonymous"></script>
<!-- https://fontawesome.com/ -->
</head>
<body>
<div id="wrap">
<!--헤더 -->

<%@ include file="header.jsp" %>

 
<!--헤더 끝-->
<!-- main 시작 -->
<div id="container"> 
	<div class="main1">
	<p>Brand</p>
   		<p> 브랜드에는 결국 그것을 만드는 <br>
   			브랜더의 유전자가 들어가게 마련이다.</p>
	</div>
 
	<div class="main2">
   		<p><img src="${path}/resources/images/main1.jpg"></p>
	</div>

	<div class="main3">
   		<p><img src="${path}/resources/images/main2.jpg"></p>
    </div> 

	<div class="main4">
	<p>Identity</p>
   		<p> 창업은 Item이 아니라  <br>
   			자신의 Identity를 찾는 것에서 시작한다.</p>
	</div>
<!-- main 끝 -->
</div>  
<!-- 푸터시작 -->

<%@ include file="footer.jsp" %>
<!-- 푸터 끝 -->
</div>
</body>
</html>