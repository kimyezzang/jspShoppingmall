<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<!-- 좌측 메뉴바 -->
	<ul class="menubarL">
		<li><h3>관리자 메뉴</h3></li>
		<hr>
		<li>
			<h4>재고관리</h4>
			<ul class="order_menu">
				<li><a href="${path}/productList.st">상품목록</a></li>
				<li><a href="${path}/productAdd.st">상품등록</a></li>
			</ul>							
		</li>
		
		<li>
			<h4>주문관리</h4>
			<ul class="order_menu">
				<li><a href="${path}/productOrderList.st">주문목록</a></li>
				<li><a href="${path}/productOrderApprovalList.st">주문승인</a></li>
				<li><a href="${path}/refundRequestList.st">환불요청</a></li>
				<li><a href="${path}/refundApprovalList.st">환불완료</a></li>
				<li><a href="${path}/refundCancelList.st">환불취소</a></li>
			</ul>							
		</li>
								
		<li><h4><a href="${path}/userList.etc">회원정보</a></h4></li>
		<li><h4><a href="${path}/boardList.ad">게시판</a></h4></li>
		<li><h4><a href="${path}/totalMoney.st">결산</a></h4></li>
		<li><h4><a href="${path}/logout.do">로그아웃</a></h4></li>
	</ul>
<!-- 좌측메뉴바 -->
</body>
</html>