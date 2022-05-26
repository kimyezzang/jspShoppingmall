<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/setting.jsp" %>      
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 상세 내용</title>
<link rel="stylesheet" href="${path}/resources/css/commonCss/header.css">
<link rel="stylesheet" href="${path}/resources/css/customerCss/board/customerBoardInfo.css">
<script src="${path}/resources/js/jsTitle.js"></script>
<script src="${path}/resources/js/jquery-3.6.0.min.js"></script>

<script type="text/javascript">

$(function(){
   
   // 댓글쓰기 버튼
   $("#btnSave").click(function(){
      comment_add();
   });
   
   // 목록버튼 클릭시 목록으로 이동
   $("#btnList").click(function(){
      location.href="${path}/BoardList.do";
   });
   
   // $(function(){ 랑 같음
   $(document).ready(function(){
      $.ajax({
         type: "post",
         url: "${path}/commentList.do",
         data: "num=${dto.num}",
         success: function(result){
            $('#commentList').html(result);
         }
      });      
      
   });
});

// 댓글쓰기
function comment_add(){
   alert("comment_add");
   // 게시글번호, 작성자, 글내용을 파라미터로 넘김
   var param = {
      "board_num" : ${dto.num},
      "writer" :  $('#writer').val(),
      "content" : $('#content').val(),
   }
   
   $.ajax({
      type: "post",
      url: "${path}/commentAdd.do",
      data: param   ,
      success: function() {   
         $('#writer').val("");   
         $('#content').val("");
         comment_list();   
      
      }
   });
}

// 댓글목록
function comment_list() {
   alert("comment_list");
   $.ajax({
      type: "post",
      url: "${path}/commentList.do",
      data: "num=${dto.num}",
      success: function(result){
         $('#commentList').html(result);
      }
   });      
}

</script>
</head>
<body>
   <div id="wrap">
   <!-- header시작 -->
      <%@ include file="../../common/header.jsp" %>
   </div>
   <!-- header끝 -->      
   <!-- header class --> <!-- header끝 -->      
      <!-- 메인 컨텐츠 시작 -->
   <div id="container">
      <div id="containerWrap">
         <h2>상세페이지</h2>
         
         <table>
            <tr>
               <td>제목</td>
               <td>${dto.title }</td>
            </tr>
            <tr>
               <td>작성자</td>
               <td>${dto.writer}</td>
            </tr>
            <tr>
               <td>작성일</td>
               <td><fmt:formatDate pattern="yyyy-MM-dd" value="${dto.regDate}"/></td>
            </tr>
         </table>
         
         <div class="boardContent">
            <p>${dto.content }</p>
         </div>
         
           <c:if test="${sessionScope.customerID != null}">
         <div class="boardContentCommentAdd">
            <h5>댓글</h5>
            <div>
               <form>
                  <input type="hidden" name="num" value="${dto.num}">
                  <input type="text" placeholder="댓글을 입력해 주세요." id="content">
                  <input type="button" value="등록" id="btnSave">
                 
                  <input type="button" value="목록" id="btnList">
               </form>
            </div>
         </div> 
          </c:if>
         
      </div>   
      
   </div> 
   <c:if test="${sessionScope.customerID != null}">
   <div class="boardContentCommentList" id="commentList" style="margin-left:400px;">
         </div>
         </c:if>
   <!-- 메인 컨텐츠 끝 -->
  
   <!-- footer 시작 -->
   <%@ include file="../../common/footer.jsp" %>
   <!-- footer 끝 -->

</body>
</html>