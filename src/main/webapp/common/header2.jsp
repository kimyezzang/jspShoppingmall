<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="setting.jsp" %>       
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>header.jsp</title>
<link rel="stylesheet" href="${path}/resources/css/commonCss/header.css">
<link rel="stylesheet" href="${path}/resources/css/customerCss/mainpage.css">
<script src="${path}/resources/js/jquery-3.6.0.min.js"></script>
<script src="${path}/resources/js/jsTitle.js"></script>
</head>
<body>
<header id="header">

<div class="header_wrap">
    
   <h1 class="logo"><a href="${path}/common/main.jsp"><img src="${path}/resources/images/logo.png"></a></h1>
   
   <div class="nav">
      <ul class="navbar">
         <li>
            <a href="#"><i class="fas fa-bars">&nbsp;&nbsp;전체 카테고리</i></a>
               <ul class="under_menu">
                  <li><a href="${path}/customer/customerProductList.html"> 상의 </a> </li>
                  <li><a href="#"> 하의 </a> </li>
                  <li><a href="#"> 신발 </a> </li>
               </ul>
         </li>
         <li><a href="${path}/customer/customerProductListNew.html">신상품</a></li>
         <li><a href="${path}/customer/customerMypageOrderList.html"><i class="far fa-user"></i></a></li>
         <li><a href="${path}/customer/customerBasket.html"><i class="fas fa-shopping-cart"></i></a></li>
         <li><input type="text" placeholder="검색어를 입력해주세요."></li>
      </ul>
   </div>
   
   <div>
      <ul class="util_menu">
         <li><a href="${path}/join.do">회원가입</a></li>
         	<li><a href="${path}/customer/login/login.jsp">로그아웃</a></li>
         <li><a href="${path}/customer/customerBoardList.html">게시판</a></li>
      </ul>
   </div>   
</div>
</header>  
</body>
</html>