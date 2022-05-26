<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ include file="/common/setting.jsp" %>      
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>고객 게시판 </title>
<link rel="stylesheet" href="${path}/resources/css/commonCss/header.css">
<link rel="stylesheet" href="${path}/resources/css/customerCss/board/customerBoardList.css">
<script src="${path}/resources/js/jquery-3.6.0.min.js"></script>
<script src="${path}/resources/js/jsTitle.js"></script>
<script>
$(function(){	// 페이지가 로딩되면
	$("#btnInsert").click(function(){
		alert("insert");
		location.href="${path}/customer/board/board_insert.jsp"
	});
});
</script>
</head>
<body>
<!-- 1. kit.fontawesome.com에서 인증 2. 이미지 사용가능-->
<script src="https://kit.fontawesome.com/d46bcbd556.js" crossorigin="anonymous"></script>
<div class="wrap">
	<!-- header시작 -->
	<%@ include file="../../common/header.jsp" %>
	
	<!-- header끝 -->
	
	<!--  컨텐츠 시작 -->
	<div id="container">
		<div id="contents">
			<!-- section1 시작 -->
			<div id="section1">
				<h1 align="center">
					게시판
					<hr>
				</h1>
			</div>
			
			<!-- section2 시작 -->
			<div id="section2">
				
				<!-- 우측 내용 시작 -->
				<div id="right">
					<div class="table_div">
						<table style="width:1000px" align="center">
							<tr>
								<th style="width:5%">번호</th>
								<th style="width:10%">작성자</th>
								<th style="width:15%">제목</th>
								<th style="width:10%">날짜</th>
								<th style="width:10%">조회수</th>
							</tr>
							<!-- 게시글이 있으면 -->
							<!-- dto.num 도가능 -->
							<c:forEach var="dto" items="${list}">	
								<tr>
									<td>${dto.getNum()}</td>			
									<td>${dto.getWriter()}</td>
									<td>
										<a href="${path}/board_detailAction.do?num=${dto.num}" style="color:black;">${dto.getTitle()}</a>
										<c:if test="${dto.comment_count > 0}">
										 [${dto.comment_count}]
										</c:if>
									</td>
									<td>${dto.getRegDate()}</td>
									<td>${dto.getReadCnt()}</td>
								</tr>
							</c:forEach>
							<tr>
								<td colspan="5" align="center" id="testTest">
									<!-- 페이징처리 -->
									<!-- 이전버튼 활성화 여부 -->
									<c:if test="${paging.startPage > 10}">
										<a href="${path}/BoardList.do?pageNum=${paging.prev}">[이전]</a>
									</c:if>
									
									<!-- 페이지번호 처리 -->
									<c:forEach var="num" begin="${paging.startPage}" end="${paging.endPage}">
										<a href="${path}/BoardList.do?pageNum=${num}">${num}</a>
									</c:forEach>
									
									<!-- 다음버튼 활성화 여부 -->
									<c:if test="${paging.endPage < paging.pageCount}" >
										<a href="${path}/BoardList.do?pageNum=${paging.next}">[다음]</a>
									</c:if>
								
								</td>
							</tr>
							<c:if test="${sessionScope.customerID != null}">
							<tr>
								<td colspan="5" style="text-align: right; border-bottom: none">
								<input type="button" id="btnInsert" value="글쓰기">
								</td>
							</tr>		
							</c:if>
						</table>
					</div>
				</div>
				
			</div>
		</div>
	</div>
	
	<!-- 컨텐츠 끝 -->
	
	<!-- footer 시작 -->
	<%@ include file="../../common/footer.jsp" %>
	<!-- footer 끝 -->

</div><!-- wrap  -->	
</body>
</html>