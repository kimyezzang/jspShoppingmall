<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  <%@ include file="/common/setting.jsp" %>     
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상품 리스트</title>
<link rel="stylesheet" href="${path}/resources/css/commonCss/header.css">
<link rel="stylesheet" href="${path}/resources/css/customerCss/product/customerProductList.css">
<script src="${path}/resources/js/jquery-3.6.0.min.js"></script>
<script src="${path}/resources/js/jsTitle.js"></script>
</head>
<body>
<!-- 1. kit.fontawesome.com에서 인증 2. 이미지 사용가능-->
<script src="https://kit.fontawesome.com/d46bcbd556.js" crossorigin="anonymous"></script>
<!-- https://fontawesome.com/ -->
<div id="wrap">
<!--헤더 -->
<%@ include file="../../common/header.jsp" %>
<!--헤더 끝-->
       
<!-- header class --> <!-- header끝 -->      
   <!-- 메인 컨텐츠 시작 -->
<div id="container" style="height:1200px;" >

   <div class="product_category"><h2 >상의</h2>
   </div>
   <!-- 게시글이 있으면 -->
	<c:forEach var="dto" items="${list}">
   <div class="product">
   	
   	<div class="product_img">
   		<a href="customerProductDetail.do?pdNo=${dto.pdNo}&pageNum=${paging.pageNum}">
   		<img src="${dto.pdImg}"></a>
   	</div>
   	<div class="product_brand">
   		<h3>${dto.brand}</h3>
   	</div>
   	<div class="product_name">
   		<h4>${dto.pdName}</h4>
   	</div>
   	<div class="product_price">
   		<h4>${dto.price}원</h4>
   	</div>
   </div>
   
   </c:forEach>
   
  
   <!-- 페이징 -->
   <table style="width:1000px; margin-top:50px; font-size:18px;" align="center" >
		<tr>
			<td colspan="11" align="center" id="testTest" style="margin-top:200px; padding-top:50px;">
				<!-- 페이징처리 -->
				<!-- 이전버튼 활성화 여부 -->
				<c:if test="${paging.startPage > 10}">
					<a href="${path}/productList1.do?pageNum=${paging.prev}">[이전]</a>
				</c:if>
				
				<!-- 페이지번호 처리 -->
				<c:forEach var="num" begin="${paging.startPage}" end="${paging.endPage}">
					<a href="${path}/productList1.do?pageNum=${num}">${num}</a>
				</c:forEach>
				
				<!-- 다음버튼 활성화 여부 -->
				<c:if test="${paging.endPage < paging.pageCount}" >
					<a href="${path}/productList1.do?pageNum=${paging.next}">[다음]</a>
				</c:if>
			
			</td>
		</tr>
   </table>
 </div>
   <!-- 메인 컨텐츠 끝 -->
   
<!-- 푸터시작 -->
<%@ include file="../../common/footer.jsp" %>
<!-- 푸터 끝 -->

</div>   
</body>
</html>