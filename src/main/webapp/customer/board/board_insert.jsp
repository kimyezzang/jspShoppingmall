<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ include file="/common/setting.jsp" %>       
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 글 추가</title>
<link rel="stylesheet" href="${path}/resources/css/commonCss/header.css">
<link rel="stylesheet" href="${path}/resources/css/customerCss/board/customerBoardAdd.css">
<script src="${path}/resources/js/jquery-3.6.0.min.js"></script>
<script src="${path}/resources/js/jsTitle.js"></script>
<script>
	$(function(){
		$("#btnSave").click(function(){	/* 등록 버튼 클릭시 작동, 주의사항 : button input type="button" */
			
			var title = $("#title").val();
			var content = $("content").val();
			
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
	<%@ include file="../../common/header.jsp" %>
	
<!-- header끝 -->

   <!-- 메인 컨텐츠 시작 -->
<div id="container">
	<h2>게시판 글</h2>
	<form action="${path}/board_insertAction.do" method="post">
	<table>
		<tr>
			<td>제목</td>
			<td><input type="text" placeholder="제목을 입력해 주세요" class="title" name="title"></td>
		</tr>
	</table>
	<div class="boardContent">
		<textarea rows="20" cols="100"  name="content" id="content" placeholder="내용을 입력해 주세요"></textarea>
	</div>
	<div class="boardContentCommentAdd">
		<div class="AddBtn">
			<input type="submit" value="등록" id="btnSave">
		</div>
		<div class="AddCancelBtn">
			<a href="${path}/BoardList.do"><input type="button" value="목록"></a>
		</div>
	</div>
	</form>	
</div> 
   <!-- 메인 컨텐츠 끝 -->

<!-- footer 시작 -->
	<%@ include file="../../common/footer.jsp" %>
<!-- footer 끝 -->
</div>
</body>
</html>