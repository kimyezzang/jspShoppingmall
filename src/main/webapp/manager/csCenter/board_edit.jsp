<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- <%@ page import="java.sql.Date" %>     --%>
<%@ include file="../../common/setting.jsp" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!-- 반응형웹 -->
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>관리자 - 게시판 수정/삭제</title>
<script src="${path}/resources/js/jquery-3.6.0.min.js"></script>
<link rel="stylesheet" href="${path}/resources/css/managerCss/header.css">
<link rel="stylesheet" href="${path}/resources/css/managerCss/footer.css">
<!-- 해당 css 추가 -->
<link rel="stylesheet" href="${path}/resources/css/managerCss/table2.css">

<!-- 1. kit.fontawesome.com에서 인증 2. 이미지 사용가능-->
<script src="https://kit.fontawesome.com/d46bcbd556.js" crossorigin="anonymous"></script>
<!-- 3-3. 자바스크립트 이벤트 추가 : 햄버거버튼을 클릭하면 아래쪽으로 메뉴가 나오도록 main.js에서 추가  -->
<script type="text/javascript">
$(function(){
	// 목록으로 이동
	$("#btnList").click(function(){
		location.href="${path}/boardList.ad";
	});
	
	// 수정 버튼 클릭
	$("#btnUpdate").click(function(){
		
		var writer = $("#writer").val();
		var password = $("#password").val();
		var title = $("#title").val();
		var content = $("content").val();
		
		if(writer == ""){
			alert("작성자를 입력하세요!!")
			$("#writer").focus();
			return false;
		}
		if(password == ""){
			alert("비밀번호를 입력하세요!!")
			$("#password").focus();
			return false;
		}
		if(title == ""){
			alert("제목을 입력하세요!!")
			$("#title").focus();
			return false;
		}
		if(content == ""){
			alert("내용을 입력하세요!!")
			$("#content").focus();
			return false;
		}
		document.form1.action="${path}/board_updateAction.ad";
		document.form1.submit();
	});
	
	
	// 삭제버튼 클릭
	$("#btnDelete").click(function(){
		var password = $("#password").val();
		
		if(confirm("삭제하시겠습니까?")){
			
			if(password == ""){
				alert("비밀번호를 입력하세요!!")
				$("#password").focus();
				return false;
			}
			
			document.form1.action="${path}/board_deleteAction.ad";
			document.form1.submit();
		}
	});
	
});

</script>
</head>
<body>
<div class="wrap">
	<!-- header시작 -->
	<%@ include file="/manager/common/header.jsp" %>
	<!-- header끝 -->
	
	<!-- header끝 -->
	
	<!--  컨텐츠 시작 -->
	<div id="container">
		<div id="contents">
			<!-- section1 시작 -->
			<div id="section1">
				<h1 align="center">
					수정 / 삭제
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
						<form method="post" name="form1">
						<table style="width:800px" align="center">
							<tr>
								<th style="width:150px">작성일</th>
								 <td style="width:150px; text-align:center">
									<fmt:formatDate pattern="yyyy-MM-dd hh:mm" value="${dto.regDate}"/>
								</td> 
								
								<th>조회수</th>
								<td style="text-align:center; width:150px;" >${dto.readCnt}</td>
							</tr>
							
							<tr>	
								<th style="width:150px">작성자</th>
								<td style="width:150px; text-align:center">
									<input type="text" name="writer" id="writer" size="20" value="${dto.writer}">
								</td>  
								 	
								<th style="width:150px">비밀번호</th>
								<td><input type="password" class="input" name="password" id="password" size="20" placeholder="비밀번호 입력">
								</td>
							</tr>
							
							<tr>
								<th>글 제목</th>
								<td colspan="3" style="text-align:left">
									<input type="text" name="title" id="title" size="50" value="${dto.title }">
								</td>
							</tr>			
							<tr>
								<th >글 내용</th>
								<td colspan="3" style="text-align:left">
									<textarea rosw="5" cols="60" name="content" id="content">${dto.content }</textarea>
								</td>
							</tr>	
									
							<tr>
								<th colspan="4">
									<input type="hidden" name="num" value="${dto.num}">	<!-- 수정, 삭제시 WHERE절에서 key 비교시 사용 -->
									<input type="button" class="inputButton" value="수정" id="btnUpdate">
									<input type="button" class="inputButton" value="삭제" id="btnDelete">
									<input type="button" class="inputButton" value="목록" id="btnList">
								</th>	
							</tr>
						
						</table>
						</form>
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