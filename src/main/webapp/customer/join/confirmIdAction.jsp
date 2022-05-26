<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ include file="/common/setting.jsp" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ID중복확인</title>
<script src="${path}/resources/js/customerJS/Join.js"></script>
<link rel="stylesheet" href="${path}/resources/css/customerCss/confirmIdAction.css">
</head>
<body onload="confirmIdFocus();">
	<h2> 중복확인 페이지 </h2>
 	<!-- id 중복 -->

<c:if test="${selectCnt == 1 }">
	<table>
		<tr>
			<th colspan="2">
				<span>${id}</span><a id="text">는 사용할 수 없습니다.</a>
			</th>
		</tr>
		
		<tr>
			<th>
				<input class="input" type="Button" value="확인" onclick="self.close();">
			</th>
		</tr>
	</table>
</c:if>	
 	
 	<!-- id 중복 아닐 때 -->
<c:if test="${selectCnt != 1 }">
	<table>
		<tr>
			<td align="center">
				<span>${id}</span><a id="text">는 사용할 수 있습니다.</a>
			</td>
		</tr>
		
		<tr>
			<th>
				<input class="input" type="Button" value="확인" onclick="setId('${id}');">
			</th>
		</tr>
	</table>
</c:if>		
	
 </form>
</body>
</html>