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
<title>userList</title>

<link rel="stylesheet" href="${path}/resources/css/managerCss/header.css">
<link rel="stylesheet" href="${path}/resources/css/managerCss/footer.css">
<!-- 해당 css 추가 -->
<link rel="stylesheet" href="${path}/resources/css/managerCss/table.css">

<!-- 1. kit.fontawesome.com에서 인증 2. 이미지 사용가능-->
<script src="https://kit.fontawesome.com/d46bcbd556.js" crossorigin="anonymous"></script>
<!-- 3-3. 자바스크립트 이벤트 추가 : 햄버거버튼을 클릭하면 아래쪽으로 메뉴가 나오도록 main.js에서 추가  -->
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
					회원정보 목록
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
								<th style="width:15%" id="id">id</th>
								<th style="width:10%" id="name">이름</th>
								<th style="width:20%" id="birth">생년월일</th>
								<th style="width:15%" id="address">주소</th>
								<th style="width:20%" id="regDate">가입일</th>
								<th style="width:10%" id="pdUpdate">상세</th>
								<th style="width:10%" id="pdDelete">삭제</th>
							</tr>
							<!-- 게시글이 있으면 -->
							<c:forEach var="dto" items="${list}">
							<tr>
								<td>${dto.id}</td>
								<td>${dto.name}</td>
								<td>${dto.birth}</td>
								<td>${dto.address}</td>
								<td><fmt:formatDate value="${dto.regDate}" pattern="yyyy-MM-dd"/></td>
								<td>
									<input type="button" value="상세" onclick="window.location='userDetail.etc?id=${dto.id}&pageNum=${paging.pageNum}'">
								</td>
								<td>
									<input type="button" value="삭제" onclick="window.location='userDeleteAction.etc?id=${dto.id}&pageNum=${paging.pageNum}'">
								</td>
							</tr>
							</c:forEach>
							<!-- 페이징 -->
							<tr>
								<td colspan="11" align="center" id="testTest">
									<!-- 페이징처리 -->
									<!-- 이전버튼 활성화 여부 -->
									<c:if test="${paging.startPage > 10}">
										<a href="${path}/userList.etc?pageNum=${paging.prev}">[이전]</a>
									</c:if>
									
									<!-- 페이지번호 처리 -->
									<c:forEach var="num" begin="${paging.startPage}" end="${paging.endPage}">
										<a href="${path}/userList.etc?pageNum=${num}">${num}</a>
									</c:forEach>
									
									<!-- 다음버튼 활성화 여부 -->
									<c:if test="${paging.endPage < paging.pageCount}" >
										<a href="${path}/userList.etc?pageNum=${paging.next}">[다음]</a>
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