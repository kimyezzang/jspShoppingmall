<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  <%@ include file="/common/setting.jsp" %>        
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="${path}/resources/css/commonCss/header.css">
<script src="${path}/resources/js/jquery-3.6.0.min.js"></script>
<script src="${path}/resources/js/jsTitle.js"></script>
<link rel="stylesheet" href="${path}/resources/css/customerCss/customerBasket.css">
<!-- 1. kit.fontawesome.com에서 인증 2. 이미지 사용가능-->
<script src="https://kit.fontawesome.com/d46bcbd556.js" crossorigin="anonymous"></script>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript">

// 주소값 받아오기
$(function(){
	$('#address').val("${address}");
	
	// 전체 체크박스 체크
	 $('#allCheckBox').click(function(){
		if($("#allCheckBox").is(":checked")) $("input[name=chkbox]").prop("checked", true);
		else $("input[name=chkbox]").prop("checked", false);
		
		totalPrice();
	}); 
	 
});		

// 상품 총액 계산
   function totalPrice(){
      // 
      var sum = 0;
      var count = $('input[name=chkbox]').length;
      for(var i=0; i<count; i++){
    	  if($('input[name=chkbox]')[i].checked == true ){	
    		  sum += parseInt($('input[name=chkbox]')[i].value);
    	  }
      }
      $('input[name=total_sum]').val(sum);
     
   } 
	
   var list = [];
   var listTest = [];
   var newTest = "5";
  
  function buyChk(){
	   if(!($('input[name=chkbox]').is(":checked"))){
		   alert("주문할 상품을 선택해주세요.");
		   return false;
	   }
	   
	   var bkNo = document.getElementsByName("chkbox").length;
	   for(var i=0; i< bkNo; i++){
		   if(document.getElementsByName("chkbox")[i].checked == true){
			   list = document.getElementsByName("chkbox")[i].getAttribute('class');
			   /* alert(document.getElementsByName("chkbox")[i].getAttribute('class')+"(체크)"); */
			   /* alert(list); */
			   listTest.push(document.getElementsByName("chkbox")[i].getAttribute('class'));
			   /* alert(document.getElementsByName("chkbox")[i].value+"(체크)"); */
			   
			   /* location.href="${path}/customerBasketBuy.do?list="+list;  */
			  
			  /* document.form.list.value = list.join(","); */
			  /* alert(listTest); */
			  /*  $('input[name=total_sum]').val('list');
			   document.form.list.value = listTest; */
			   
			    
		   }
		   
	   }
	   /* alert(listTest);
	   alert(list); */
	 /*  document.test.action="${path}/customerBasketBuy.do?list="+listTest;
	   document.form.tt.value = newTest;
	  document.test.submit();  */
	  
	  $('input[name=list]').val(listTest);
	  alert("상품이 주문 되었습니다.");
   }
 function get_action(){
	 return listTest;
	 
 }
</script>
</head>
<body>
<div id="wrap">
<!--헤더 -->
<%@ include file="../../common/header.jsp" %>
<!--헤더 끝-->
<c:if test="${sessionScope.customerID eq null}">
	<script type="text/javascript">
	alert("로그인 후 장바구니 이동해주세요");
		history.go(-1);
	</script>
</c:if>
   <!-- 메인 컨텐츠 시작 -->
<div id="container">
	<div class="basket">
		<div class="basketTitle">
			<h2>장바구니</h2>
		</div>
		 
		<form action="${path}/customerBasketBuy.do" onsubmit="return buyChk();" name="test">
		<input type="hidden" name="list" >
 		<div class = "basketSelectAll">
 			<div><p><input type="checkbox" name="allChk" onClick="allCheck();" id="allCheckBox"> </p></div>
 			 <div><p>전체 선택</p></div>
 		</div>
		<div class="basketSelectedProdcut">
			<table class="basketSelectTable">
				<c:forEach var="list" items="${list}">
				<tr class="basketSelectedTable">
					<td><p><input type="checkbox" name="chkbox" class="${list.bkNo}" value="${list.price}" onClick="totalPrice();"></p></td>
					<td><img src="${list.pdImg}"></td>
					<td><p><b>${list.brand}&nbsp;&nbsp; ${list.pdName}</b></p></td>
					<td><p><b> &nbsp;&nbsp;&nbsp;${list.quantity}개</b></p></td>
					<td><p>${list.price}원</p></td>
					<td><a href="${path}/customerBasketDelete.do?bkNo=${list.bkNo}"><p><i class="far fa-times-circle"></i></p></a></td>
				</tr>
				<tr><td colspan="6"><hr></td></tr>
			</c:forEach>
				
				
				
			</table>
		</div>
 		<div class="basketOrder">
 			<div class="basketOrder_1">
 				<p><i class="fas fa-map-marker-alt"></i> 배송지</p>
 				<p> &nbsp;</p>
 				<p><textarea id="address" rows="6" cols="25"  style="font-size:17px;" readonly></textarea></p>
 				<p> &nbsp;</p>
 			</div>
 			<div class="basketOrder_2">
 				<p><hr></p>
 				<p> &nbsp;</p>
 				<p> &nbsp;</p>
 				<p>결제예정금액 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 				<input type="text" name="total_sum" readonly > 원</p>
 				<p> &nbsp;</p>
 				<p> &nbsp;</p>
 			</div>
 			
 			
 				<input type="submit" value="주문하기" id="btn">
 			</form>
 			</div>
 		</div>
 	</div>
</div>
   <!-- 메인 컨텐츠 끝 -->

<!-- 푸터시작 -->
<%@ include file="../../common/footer.jsp" %>
<!-- 푸터 끝 -->
</div>
</body>
</html>