<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%-- <%@ page import="java.sql.Date" %>     --%>
<%@ include file="/common/setting.jsp" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!-- 반응형웹 -->
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>관리자 - 상품 상세페이지</title>

<link rel="stylesheet" href="${path}/resources/css/managerCss/header.css">
<link rel="stylesheet" href="${path}/resources/css/managerCss/footer.css">
<!-- 해당 css 추가 -->
<link rel="stylesheet" href="${path}/resources/css/managerCss/table2.css">

<!-- 1. kit.fontawesome.com에서 인증 2. 이미지 사용가능-->
<script src="https://kit.fontawesome.com/d46bcbd556.js" crossorigin="anonymous"></script>
<!-- 3-3. 자바스크립트 이벤트 추가 : 햄버거버튼을 클릭하면 아래쪽으로 메뉴가 나오도록 main.js에서 추가  -->
<script src="${path}/resources/js/customerJS/main.js"></script>
<script src="${path}/resources/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">

var sel_flie;

$(document).ready(function(){
	$("#pdImg").on("change",handleImgFileSelect);
});

function handleImgFileSelect(e){
	var files = e.target.files;
	var filesArr = Array.prototype.slice.call(files);
	
	filesArr.forEach(function(f){

	sel_file = f;
	
	var reader = new FileReader();
	reader.onload = function(e) {
		$("#imgsrc").attr("src",e.target.result);
		}
	reader.readAsDataURL(f);
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
					상품 상세페이지
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
						<input type="hidden" name="hiddenPdNo" value="${dto.pdNo}">
						<input type="hidden" name="hiddenPageNum" value="${pageNum}">
						<input type="hidden" name="hiddenPdImg" value="${dto.pdImg}">
						<table style="width:800px" align="center" border="1">
							<tr>
								<th style="width:150px">제품번호</th>
								 <td>${dto.pdNo}</td>
								
								<th>제품명</th>
								<td><input type="text" class="input" name="pdName" id="pdName" size="20" value="${dto.pdName}" placeholder="제품명 입력" required></td>
							</tr>
							
							<tr>	
								<th style="width:150px">브랜드</th>
								<td><input type="text" class="input" name="brand" id="brand" size="20" value="${dto.brand}" placeholder="브랜드명 입력" required></td>
								 	
								<th style="width:150px">상품이미지</th>
								<td>
									<input type="file" class="input" name="pdImg" id="pdImg" size="20" value="${dto.pdImg}" placeholder="이미지 입력"  required accept="image/*">
									<img src="${dto.pdImg}" width="80px" height="80px" id="imgsrc"> 
								</td>
							</tr>
							
							<tr>
								<th style="width:150px">상품카테고리</th>
								<td colspan="3">
									<div>
										<select id="category" name="category" required>
											<option>제품구분</option>
											<option <c:if test="${dto.category == '주방가전'}">selected</c:if> value ="주방가전"> 주방가전</option>
											<option <c:if test="${dto.category == '생활가전'}">selected</c:if> value ="생활가전"> 생활가전</option>
											<option <c:if test="${dto.category == '휴대폰'}">selected</c:if> value ="휴대폰"> 휴대폰</option>
										</select>
									</div>
								</td>
							</tr>	
									
							<tr>
								<th>제품설명 </th>
								<td colspan="3">
									<textarea rows="5" cols=50 id="content" name="content" placeholder="상세 설명 입력">${dto.content}</textarea> 
								</td>
							</tr>
							
							<tr>	
								<th>판매가격</th>
								<td>
									<input type="number" class="input" id="price" name="price" size="10" placeholder="상품명 입력" value="${dto.price}" required>
								</td>
								<th>등록수량</th>
								<td>
									<input type="number" class="input" id="quantity"  name="quantity" size="10" placeholder="상품 재고수량입력" value="${dto.quantity}" required>
								</td>
							</tr>
							<tr>
								<th>판매상태</th>
								<td colspan="3">
									<select id="status" name="status" required>
										<option>상품판매 상태</option>
										<option <c:if test="${dto.status == '판매중'}">selected</c:if> value ="판매중"> 판매중</option>
										<option <c:if test="${dto.status == '품절'}">selected</c:if> value ="품절"> 품절</option>
									</select>
								</td>
							</tr>
							<tr>
								<th>등록일</th>	
								<td colspan="3">
									<fmt:formatDate pattern="yyyy-MM-dd hh:mm" value="${dto.indate}"/>
								</td>
							</tr>	
							<tr>
								<th colspan="4">
									<input type="submit" class="inputButton" value="수정" id="btnEdit">
									<input type="reset" class="inputButton" value="취소" id="history.back();">
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