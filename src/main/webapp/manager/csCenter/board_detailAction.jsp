<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../common/setting.jsp" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!-- 반응형웹 -->
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>관리자 - 게시판 상세페이지</title>
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
	
	// 댓글쓰기 버튼
	$("#btnSave").click(function(){
		comment_add();
	});
	
	// 수정, 삭제화면으로 이동
	$("#btnEdit").click(function(){
		alert("상세페이지 ")
		document.form1.action="${path}/password_chk.ad";
		document.form1.submit();
	});
	// 목록버튼 클릭시 목록으로 이동
	$("#btnList").click(function(){
		location.href="${path}/boardList.ad";
	});
	
	// $(function(){ 랑 같음
	$(document).ready(function(){
		$.ajax({
			type: "post",
			url: "${path}/commentList.ad",
			data: "num=${dto.num}",
			success: function(result){
				$('#commentList').html(result);
			}
		});		
		
	});
});

// 댓글쓰기
function comment_add(){
	alert("댓글추가완료");
	// 게시글번호, 작성자, 글내용을 파라미터로 넘김
	var param = {
		"board_num" : ${dto.num},
		"writer" :  $('#writer').val(),
		"content" : $('#content').val(),
	}
	
	$.ajax({
		type: "post",
		url: "${path}/commentAdd.ad",
		data: param,	// commentAdd.ad?board_num=1&writer=홍길동&content=댓글테스트
		success: function() {	// 댓글쓰기가 완료되면 서버에서 콜백함수 호출
			$('#writer').val("");	// 입력한 내용을 지움
			$('#content').val("");
			comment_list();	// 댓글목록 새로고침
		
		}
	});

}

// 댓글목록
function comment_list() {
	
	$.ajax({
		type: "post",
		url: "${path}/commentList.ad",
		data: "num=${dto.num}",
		success: function(result){
			$('#commentList').html(result);
		}
	});		
}

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
					상세페이지
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
								
								<th style="width:150px">조회수</th>
								<td style="text-align:center">${dto.readCnt}</td>
							</tr>
							
							<tr>	
								<th style="width:150px">작성자</th>
								<td style="width:150px; text-align:center">${dto.writer} </td>  
								 	
								<th style="width:150px">비밀번호</th>
								<td>
									<input type="password" class="input" name="password" id="password" size="20" value="${dto.password}" placeholder="비밀번호 입력" required>
									<c:if test="${param.message == 'error'}">
										<span style="color:red"> 비밀번호가 불일치! </span>
									</c:if>
								</td>
							</tr>
							
							<tr>
								<th>글 제목</th>
								<td colspan="3" style="text-align:left">${dto.title }</td>
							</tr>			
							<tr>
								<th >글 내용</th>
								<td colspan="3" style="text-align:left">${dto.content }
									
								
								</td>
							</tr>	
									
							<tr>
								<th colspan="4">
									<input type="hidden" name="num" value="${dto.num}">
									<input type="button" class="inputButton" value="수정/삭제" id="btnEdit">
									<input type="button" class="inputButton" value="목록" id="btnList">
								</th>	
							</tr>
						
						</table>
						</form>
						
						<!-- 댓글 입력 코드 -->
                  <h3>댓글</h3>
                  
                  <table width="700px" border="1">
                     <tr>
                        <th style="width:100px">이름</th>
                        <td style="width:200px" colspan="2">
                           <input id="writer" placeholder="이름">
                        </td>   
                        <td style="width:30px" rowspan="2" align="right"><button id="btnSave" type="button">확인</button></td>                  
                     </tr>
                     <tr>
                        <th style="width:100px">내용</th>
                        <td  style="width:170px"><textarea rows="5" cols="60" id="content" placeholder="내용을 입력하세요."></textarea></td>
                     </tr>
                  </table>
                  <br><br>

						
						<!-- 댓글 목록을 출력할 영역 -->
						<div id="commentList">
						
						</div>
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