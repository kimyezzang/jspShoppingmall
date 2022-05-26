<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../common/setting.jsp" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!-- 반응형웹 -->
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>관리자 - 상품등록</title>

<link rel="stylesheet" href="${path}/resources/css/managerCss/header.css">
<link rel="stylesheet" href="${path}/resources/css/managerCss/footer.css">
<!-- 해당 css 추가 -->
<link rel="stylesheet" href="${path}/resources/css/managerCss/table2.css">

<!-- 1. kit.fontawesome.com에서 인증 2. 이미지 사용가능-->
<script src="https://kit.fontawesome.com/d46bcbd556.js" crossorigin="anonymous"></script>
<!-- 3-3. 자바스크립트 이벤트 추가 : 햄버거버튼을 클릭하면 아래쪽으로 메뉴가 나오도록 main.js에서 추가  -->
<script src="${path}/resources/js/customerJS/main.js"></script>
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
					상품등록
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
						<form action="${path}/productAddAction.st" method="post" name="productAdd" enctype="multipart/form-data">
						<table style="width:800px" align="center">
							<tr>
								<th>브랜드 *</th>
								<td><input type="text" class="input" id="brand" name="brand" size="60" placeholder="브랜드명 입력"></td>
							</tr>
							<tr>
								<th>제품명 *</th>
								<td><input type="text" class="input" id="pdName" name="pdName" size="60" placeholder="상품명 입력"></td>
							</tr>
							<tr>
								<th>제품이미지 *</th>
								<td><input type="file" class="inputOpt" id="pdImg" name="pdImg" accept="image/*"></td>
							</tr>
							<tr>
								<th>상품카테고리 *</th>
								<td>
									<select id="category" class="input" name="category" required>
										<option>상품카테고리</option>
										<option value="상의">상의 </option>
										<option value="하의">하의 </option>
										<option value="신발">신발 </option>
									</select>
								</td>
							</tr>
							
							<tr>
								<th>제품설명 *</th>
								<td>
									<textarea rows="5" cols=50 id="content" name="content" placeholder="상세 설명 입력"></textarea>
								</td>
							</tr>
							
							<tr>
							<th>판매가</th>
								<td>
									<input type="number" class="input" id="price" name="price" size="10" placeholder="상품명 입력" required></td>
							</tr>			
								
							<tr>
							<th>등록수량</th>
								<td>
									<input type="number" class="input" id="quantity"  name="quantity" size="10" placeholder="상품 재고수량입력" required></td>
							</tr>			
							
							<tr>
								<th>판매상태</th>
								<td>
									<select class="input" id="status" name="status" required>
										<option>상품판매상태</option>
										<option value="판매중" selected>판매중 </option>
										<option value="품절">품절 </option>
									</select>
								</td>
							</tr>
							
							<tr>
								<th colspan="2">
									<input type="submit" class="inputButton" value="등록">
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