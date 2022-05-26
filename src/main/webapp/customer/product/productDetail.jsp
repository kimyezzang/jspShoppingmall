<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/setting.jsp" %>     
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상품 상세 페이지</title>
<link rel="stylesheet" href="${path}/resources/css/commonCss/header.css">
<link rel="stylesheet" href="${path}/resources/css/customerCss/product/customerProductInfo.css">
<script src="${path}/resources/js/jquery-3.6.0.min.js"></script>
<script src="${path}/resources/js/jsTitle.js"></script>
<script type="text/javascript">
$(function(){
	$('#buttonCount').on('change',function(){
		$('#totalPrice').val(Number($('#hiddenPrice').val())* Number($('#buttonCount').val()))
	});


	// 장바구니 버튼
	$("#basketAdd").click(function(){
		basket_add();
	});

	// 장바구니 추가
	function basket_add(){
		
		// 
		var param = {
			"writer" :  $('#writer').val(),
			"content" : $('#content').val(),
			"hiddenPdNo" : $('#hiddenPdNo').val(),
			"hiddenPdName" : $('#hiddenPdName').val(),
			"hiddenPdImg" :  $('#hiddenPdImg').val(),
			"hiddenPdBrand" : $('#hiddenPdBrand').val(),
			"totalPrice" : $('#totalPrice').val(),
			"buttonCount" : $('#buttonCount').val()
			
		}
		
		$.ajax({
			type: "post",
			url: "${path}/customerBasket.do",
			data: param,	// 
			success: function() {	// 
				$('#buttonCount').val("");
				$('#totalPrice').val("");
			
			}
		});
		alert("장바구니 추가완료");
	};
	
	// 바로구매 버튼
	$("#basketBuy").click(function(){
		nowBuy();
	});
	
	// 바로구매
	function nowBuy(){
		
		var param = {
			"writer" :  $('#writer').val(),
			"content" : $('#content').val(),
			"hiddenPdNo" : $('#hiddenPdNo').val(),
			"hiddenPdName" : $('#hiddenPdName').val(),
			"hiddenPdImg" :  $('#hiddenPdImg').val(),
			"hiddenPdBrand" : $('#hiddenPdBrand').val(),
			"totalPrice" : $('#totalPrice').val(),
			"buttonCount" : $('#buttonCount').val()
			
		}
		
		$.ajax({
			type: "post",
			url: "${path}/customerNowBuy.do",
			data: param,	// 
			success: function() {	// 
				$('#buttonCount').val("");
				$('#totalPrice').val("");
			
			}
		});
		alert("바로구매 완료");
	}
});


</script>

</head>
<body>
<div id="wrap">
<!--헤더 -->
<%@ include file="../../common/header.jsp" %>
<!--헤더 끝-->

   <!-- 메인 컨텐츠 시작 -->
<div id="container">
	<div class="container_inner">
	<form name="form" action="">
	<input type="hidden" name="hiddenPdNo" value="${dto.pdNo}" id="hiddenPdNo">
	<input type="hidden" name="hiddenPageNum" value="${pageNum}" id="hiddenPageNum">
	<input type="hidden" name="hiddenPrice" id="hiddenPrice" value="${dto.price}">
	<input type="hidden" name="hiddenPdName" value="${dto.pdName}" id="hiddenPdName">
	<input type="hidden" name="hiddenPdBrand" value="${dto.brand}" id="hiddenPdBrand">
	<input type="hidden" name="hiddenPdImg" value="${dto.pdImg}" id="hiddenPdImg">
		<div class="productInfoImage"><img src="${dto.pdImg}"></div>
		<div class="productInfoFirst">
			<div class="productInfoBrand"><p>${dto.brand}</p></div>
			<div class="productInfoName"><p>${dto.pdName}</p></div>
		</div>
		<div class="productInfoSecond">
			<p class="ProductInfoPrice">${dto.price}원</p>
			<p class="ProductInfoKind">종류</p>
			<p class="ProductInfoKind2">${dto.category}</p>
			<p class="ProductInfoMaterial">상태</p>
			<p class="ProductInfoMaterial2">${dto.status}</p>
			<hr>
			
			<p class="ProductInfoShip">재고수량</p>
			<p class="ProductInfoShip2">${dto.quantity}</p>
			<hr>
			<p class="productInfoContent"><textarea rows="6" cols=90 id="content" name="content" readonly>${dto.content}</textarea> 
			</p>
			<hr>
			<p class="ProductInfoCountProduct">상품수량
				<input type="number" name = "buttonCount" id="buttonCount" min="1" max="${dto.quantity}">
				</p>
			<div class="ProductInfoTotalPrice">
				<p class="ProductInfoTotalPrice2">총상품금액 : </p>
				<p class="ProductInfoTotalPrice3"><input type="text" id ="totalPrice" name="totalPrice"/>원</p>
			</div>
			<c:if test="${sessionScope.customerID != null}">
		<div class="ProductInfoToBasketBtn" style="left:100px;">
				<input type="button" value="장바구니 담기" id="basketAdd">
				<a><input type="button" value="바로 구매" id="basketBuy"></a>
		</div>	
				</c:if>		
		</div>
		
		
	</form>
	</div>
	
</div> 
   <!-- 메인 컨텐츠 끝 -->
   



<!-- 푸터시작 -->
<%@ include file="../../common/footer.jsp" %>
<!-- 푸터 끝 -->
</div>
</body>
</html>