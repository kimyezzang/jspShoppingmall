<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/setting.jsp" %>     
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원정보 - 상세 페이지</title>
<link rel="stylesheet" href="${path}/resources/css/managerCss/header.css">
<link rel="stylesheet" href="${path}/resources/css/managerCss/footer.css">
<!-- 해당 css 추가 -->
<link rel="stylesheet" href="${path}/resources/css/managerCss/table2.css">
</head>
<body>
<!-- header시작 -->
	<%@ include file="/manager/common/header.jsp" %>
<!-- header끝 -->

<!--  컨텐츠 시작 -->
	<div id="container">
		<div id="contents">
			<!-- section1 시작 -->
			<div id="section1">
				<h1 align="center">
					회원정보 상세페이지
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
						
						<form action="${path}/productUpdateAction.st" method="post" name="form1" enctype="multipart/form-data">
						<input type="hidden" name="hiddenId" value="${dto.id}">
						<input type="hidden" name="hiddenPageNum" value="${pageNum}">
						<table style="width:800px" align="center" border="1">
							<tr>
								<th style="width:150px">ID</th>
								 <td>${dto.id}</td>
								
								<th>이름</th>
								<td>${dto.name}</td>
							</tr>
							
							<tr>	
								<th style="width:150px">비밀번호</th>
								<td>${dto.password}</td>
								 	
								<th style="width:150px">비밀번호힌트</th>
								<td>${dto.passwordHint}</td>
							</tr>
							
							<tr>
								<th style="width:150px">주소</th>
								<td colspan="3">
									${dto.address}
								</td>
							</tr>	
									
							<tr>
								<th>email </th>
								<td colspan="3">
									${dto.email}
								</td>
							</tr>
							
							<tr>	
								<th>생년월일</th>
								<td colspan="3">
									${dto.birth}
								</td>
							</tr>
							<tr>
								<th>핸드폰</th>
								<td colspan="3">
									${dto.hp}
								</td>
							</tr>
							<tr>
								<th>가입일</th>	
								<td colspan="3">
									${dto.regDate}
								</td>
							</tr>	
							<tr>
								<th colspan="4">
									<a href="${path}/userList.etc"><input type="button" class="inputButton" value="목록"></a>	
								</th>	
							</tr>
						
						</table>
						</form>
						
					</div>
				</div>
			</div>
	
		</div>
	</div>
<!-- footer 시작 -->
<%@ include file="/manager/common/footer.jsp" %>
<!-- footer 끝 -->
</body>
</html>