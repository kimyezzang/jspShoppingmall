<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ include file="/common/setting.jsp" %>          
<!DOCTYPE html>
<html>
<head>
<style>
	#testTest a{
	color: black;
	}
	img {
		width:70px; height:60px;
	}
</style>
<meta charset="UTF-8">
<!-- 반응형웹 -->
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>manager - 환불요청리스트</title>
<link rel="stylesheet" href="${path}/resources/css/managerCss/header.css">
<link rel="stylesheet" href="${path}/resources/css/managerCss/footer.css">
<link rel="stylesheet" href="${path}/resources/css/managerCss/table.css">
<script src="${path}/resources/js/customerJS/main.js"></script>
<%
	request.setCharacterEncoding("UTF-8");
%>
</head>
<body>
<div class="wrap">
	<!-- header시작 -->
	<%@ include file="/manager/common/header.jsp" %>
	<!-- header끝 -->
	
	<!--  컨텐츠 시작 -->
	<div id="container">
		<div id="contents">
			<!-- section1 시작 -->
			<div id="section1">
				<h1 align="center">
					환불 요청 목록
					<hr>
				</h1>
			</div>
			
			<!-- section2 시작 -->
			<div id="section2">
				<!-- 좌측 메뉴바 시작 -->
				<div id="left">
					<div class="left_inside">
						<!-- 좌측 메뉴바 -->
						<%@ include file="/manager/common/leftMenu.jsp" %>
						<!-- 좌측메뉴바 -->
					
					</div>
				</div>	
				<!-- 우측 내용 시작 -->
				<div id="right">
					<div class="table_div">
						<table style="width:1000px" align="center">
							<tr>
								<th style="width:5%" id="orNo">번호</th>
								<th style="width:8%" id="pdNo">pd번호</th>
								<th style="width:12%" id="pdName">제품명</th>
								<th style="width:10%" id="pdImg">제품이미지</th>
								<th style="width:10%" id="id">ID</th>
								<th style="width:9%" id="brand">브랜드</th>
								<th style="width:5%" id="quantity">수량</th>
								<th style="width:9%" id="price">총가격</th>
								<th style="width:9%" id="status">상태</th>
								<th style="width:13%"  id="indate">주문일</th>
								<th style="width:5%" id="pdUpdate">승인</th>
								<th style="width:5%" id="pdDelete">취소</th>
							</tr>
							<!-- 게시글이 있으면 -->
							<c:forEach var="dto" items="${list}">
							<tr>
								<td>${dto.orNo}</td>
								<td>${dto.pdNo}</td>
								<td>${dto.pdName}</td>
								<td><img src="${dto.pdImg}"></td>
								<td>${dto.id}</td>
								<td>${dto.brand}</td>
								<td>${dto.quantity}</td>
								<td>${dto.price}</td>
								<td>${dto.orState}</td>
								<td>${dto.orDate}</td>
								<td>
									<input type="button" value="승인" onclick="window.location='refundApproval.st?orNo=${dto.orNo}&pageNum=${paging.pageNum}'">
								</td>
								<td>
									<input type="button" value="취소" onclick="window.location='refundCancel.st?orNo=${dto.orNo}&pageNum=${paging.pageNum}'">
								</td>
							</tr>
							</c:forEach>
							<!-- 페이징 -->
							<tr>
								<td colspan="12" align="center" id="testTest">
									<!-- 페이징처리 -->
									<!-- 이전버튼 활성화 여부 -->
									<c:if test="${paging.startPage > 10}">
										<a href="${path}/refundRequestList.st?pageNum=${paging.prev}">[이전]</a>
									</c:if>
									
									<!-- 페이지번호 처리 -->
									<c:forEach var="num" begin="${paging.startPage}" end="${paging.endPage}">
										<a href="${path}/refundRequestList.st?pageNum=${num}">${num}</a>
									</c:forEach>
									
									<!-- 다음버튼 활성화 여부 -->
									<c:if test="${paging.endPage < paging.pageCount}" >
										<a href="${path}/refundRequestList.st?pageNum=${paging.next}">[다음]</a>
									</c:if>
								
								</td>
							</tr>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 컨텐츠 끝 -->
	

	<!-- footer 시작 -->
	<%@ include file="/manager/common/footer.jsp" %>
	
	<!-- footer 끝 -->

</div>	
</body>
</html>