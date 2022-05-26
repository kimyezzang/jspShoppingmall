<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../common/setting.jsp" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!-- 반응형웹 -->
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>관리자 - 글작성</title>

<link rel="stylesheet" href="${path}/resources/css/managerCss/header.css">
<link rel="stylesheet" href="${path}/resources/css/managerCss/footer.css">
<!-- 해당 css 추가 -->
<link rel="stylesheet" href="${path}/resources/css/managerCss/table2.css">
<script src="${path}/resources/js/jquery-3.6.0.min.js"></script>
<!-- 1. kit.fontawesome.com에서 인증 2. 이미지 사용가능-->
<script src="https://kit.fontawesome.com/d46bcbd556.js" crossorigin="anonymous"></script>
<!-- 3-3. 자바스크립트 이벤트 추가 : 햄버거버튼을 클릭하면 아래쪽으로 메뉴가 나오도록 main.js에서 추가  -->
<script>
	$(function(){
		$("#btnSave").click(function(){	/* 등록 버튼 클릭시 작동, 주의사항 : button input type="button" */
			
		
			var writer = $("#writer").val();
			var password = $("#password").val();
			var title = $("#title").val();
			var content = $("content").val();
			
			if(writer == ""){
				alert("비밀번호를 입력하세요!!")
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
			document.boardInsert.submit();	/* action애서 지정한 페이지로 이동 */
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
					글쓰기
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
						<form action="${path}/board_insertAction.ad" method="post" name="boardInsert">
						<table style="width:800px" align="center">
							<tr>
								<th>작성자</th>
								<td><input type="text" class="input" name="writer" size="20" placeholder="작성자 입력" required autofocus></td>
							</tr>
							<tr>
								<th>비밀번호</th>
								<td><input type="password" class="input" name="password" size="20" placeholder="비밀번호 입력"></td>
							</tr>
							
							<tr>
							<th>글 제목</th>
								<td>
									<input type="text" class="input" name="title" size="50" placeholder="글 제목 입력"></td>
							</tr>			
							<tr>
								<th>글 내용</th>
								<td>
									<textarea rows="5" cols="60"  name="content" id="content"></textarea></td>
							</tr>	
									
							<tr>
								<th colspan="2">
									<input type="button" class="inputButton" value="등록" id="btnSave">
									<input type="reset" class="inputButton" value="초기화">
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