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
	.progress-bar {
    width: 100%;
    height: 30px;
    background-color: #dedede;
    font-weight: 600;
    font-size: .8rem;
}

.progress-bar .progress {
   /*  width: 72%;   */
    height: 30px;
    padding: 0;
    text-align: center;
    background-color: #5F0080;
    color: #111;
}
	
</style>

<meta charset="UTF-8">
<!-- 반응형웹 -->
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>manager - 결산</title>
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
					결산
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
								<th style="width:100%" id="orNo">결산</th>
								
							</tr>
							<!-- 게시글이 있으면 -->
							<c:set var="dto" value="${dto}" />
							<c:if test="${dto.category1Money != 0}">
							<c:set var="category1" value="${ (dto.category1Money /dto.totalMoney) *100}"/>
								
							</c:if>
							<c:if test="${dto.category2Money != 0}">
								<c:set var="category2" value="${(dto.category2Money /dto.totalMoney) *100}"/>
							</c:if>
							<c:if test="${dto.category3Money != 0}">
								<c:set var="category3" value="${(dto.category3Money /dto.totalMoney)*100}"/>
							</c:if>
							<tr>
								<td>총 결산 : ${dto.totalMoney}</td>
							</tr>
							
							<tr>
								<td>상의 결산 : ${dto.category1Money}</td>
							</tr>
							<tr>
								<td>
									<div class="progress-bar">           
									   <div class="progress" style="width:${category1}%;"> <a style="font-size:20px; maxlength:2; size:2;" >${fn:substring(category1,0,4)}%</a></div>
									</div>
								</td>
							</tr>
							
							
							<tr>
								<td>하의 결산 : ${dto.category2Money}</td>
							</tr>
							<tr>
								<td>
									<div class="progress-bar">           
									   <div class="progress" style="width:${category2 }%;" > <a style="font-size:20px; maxlength:2; size:2;" >${fn:substring(category2,0,4)}%</a></div>
									</div>
								</td>
							</tr>
							
							<tr>
								<td>신발 결산 : ${dto.category3Money}</td>
							</tr>
							<tr>
								<td>
									<div class="progress-bar">           
									   <div class="progress" style="width:${category3 }%;" > <a style="font-size:20px;">${fn:substring(category3,0,4)}%</a></div>
									</div>
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