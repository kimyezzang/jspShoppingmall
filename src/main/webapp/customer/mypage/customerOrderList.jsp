<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  <%@ include file="/common/setting.jsp" %>     
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>customerMypageOrderList.jsp</title>
<link rel="stylesheet" href="${path}/resources/css/commonCss/header.css">
<link rel="stylesheet" href="${path}/resources/css/customerCss/mypage/customerMypageOrderList.css">
<script src="${path}/resources/js/jquery-3.6.0.min.js"></script>
<script src="${path}/resources/js/jsTitle.js"></script>
</head>
<body>
<div id="wrap">
<!--헤더 -->
<%@ include file="../../common/header.jsp" %>
<!--헤더 끝-->
<c:if test="${sessionScope.customerID eq null}">
	<script type="text/javascript">
	alert("로그인 후 마이페이지 이동해주세요");
		history.go(-1);
	</script>
</c:if>

<div id="container">
	<div class="myPageLogo">	
		<h3>My page</h3>
	</div>
	<div class="myPageOrderListTitle">	
		<h3>주문 내역</h3>
	</div>
	
	<div class="leftMenu">
		
		<table>
			<tr>
				<td>주문 내역</td>
			</tr>
			<tr>
				<td><a href="${path}/customer/mypage/modifyCustomer.jsp">개인 정보 수정</a></td>
			</tr>
			<tr>
				<td><a href="${path}/customer/mypage/deleteCustomer.jsp">회원 탈퇴</a></td>
			</tr>
		</table>
		
	</div>
	<div class="orderListContent">
		<table>
		<c:forEach var="dto" items="${list}">
			<tr>
				<td class="orderListImg"><img src="${dto.pdImg}"></td>
				<td>${dto.brand}</td>
				<td>${dto.pdName}</td>
				<td>${dto.quantity}개</td>
				<td>${dto.price}원</td>
				<td>${dto.orState}</td>
			
				<c:set var="state" value="${dto.orState}"/>
				<c:if test="${state == '배송준비중' }">
				<td><a href="${path}/customerPreparedOrederDelete.do?orNo=${dto.orNo}"><input type="button" value="취소하기"></a></td>
				</c:if>
				<c:if test="${state == '배송완료' }">
				<td><a href="${path}/customerOrederRefund.do?orNo=${dto.orNo}"><input type="button" value="환불하기"></a></td>
				</c:if>
				<c:if test="${state == '주문취소' }">
				<td><input type="button" value="구매불가" style="background:black;"></td>
				</c:if>
				<c:if test="${state == '환불요청' }">
				<td><input type="button" value="환불진행중" style="background:black;"></td>
				</c:if>
				<c:if test="${state == '환불완료' }">
				<td><input type="button" value="환불완료" style="background:black; color:yellow;"></td>
				</c:if>
				<c:if test="${state == '환불취소' }">
				<td><input type="button" value="환불불가" style="background:red;"></td>
				</c:if>
			</tr>
			<tr>
				<td colspan="7"><hr></td>
			</tr>
			</c:forEach>
		</table>
	</div>
	
	<div style="width:300px; height:50px; margin:620px auto;">
	<table>
	<tr>
		<td id="testTest" style=" width:300px; margin:50px auto; text-align:center; align:center;" >
			<!-- 페이징처리 -->
			<!-- 이전버튼 활성화 여부 -->
			<c:if test="${paging.startPage > 10}">
				<a href="${path}/customerOrderList.do?pageNum=${paging.prev}">[이전]</a>
			</c:if>
			
			<!-- 페이지번호 처리 -->
			<c:forEach var="num" begin="${paging.startPage}" end="${paging.endPage}">
				<a href="${path}/customerOrderList.do?pageNum=${num}">${num}</a>
			</c:forEach>
			
			<!-- 다음버튼 활성화 여부 -->
			<c:if test="${paging.endPage < paging.pageCount}" >
				<a href="${path}/customerOrderList.do?pageNum=${paging.next}">[다음]</a>
			</c:if>
		
		</td>
	</tr>
	</table>
	</div>
</div> 



<!-- 푸터시작 -->
<%@ include file="../../common/footer.jsp" %>
<!-- 푸터 끝 -->
</div>
</body>
</html>