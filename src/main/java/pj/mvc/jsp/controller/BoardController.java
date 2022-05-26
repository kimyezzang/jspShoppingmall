package pj.mvc.jsp.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pj.mvc.jsp.service.BoardServiceImpl;


@WebServlet("*.ad")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
   

	// 1단계. HTTP 요청 받음
	protected void doGet(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException {
		action(req,res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException {
		doGet(req, res);
	}
	public void action(HttpServletRequest req,HttpServletResponse res) 
			throws ServletException, IOException {
		// 한글 안깨지게 처리
		req.setCharacterEncoding("UTF-8");
		String viewPage = "";
		BoardServiceImpl service = new BoardServiceImpl();
		
		
		String uri = req.getRequestURI();
		String contextPath = req.getContextPath();
		String url = uri.substring(contextPath.length());
				
		// 2단계. 요청분석
		if(url.equals("/*.ad") || url.equals("/boardList.ad")) {	
			System.out.println("[url] ==> boardList.ad");
			service.boardList(req, res);
			viewPage = "/manager/csCenter/boardList.jsp";
			
			RequestDispatcher dispatcher = req.getRequestDispatcher(viewPage);
			dispatcher.forward(req, res);	
		
		// 글쓰기
		} else if(url.equals("/board_insertAction.ad")) {
			System.out.println("[url] ==> board_insertAction.ad");
						
			service.boardInsert(req, res);
						
			viewPage = req.getContextPath() + "/boardList.ad";		// jsp_pj_105
			res.sendRedirect(viewPage);		
		
		// 상세페이지
		} else if(url.equals("/board_detailAction.ad")){
			System.out.println("[url] ==> board_detailAction.ad");
			
			service.boardDetail(req, res);
			
			viewPage = "/manager/csCenter/board_detailAction.jsp";
			
			RequestDispatcher dispatcher = req.getRequestDispatcher(viewPage);
			dispatcher.forward(req, res);	
			
		// 수정 삭제시 비밀번호 인증
		} else if(url.equals("/password_chk.ad")){	
				System.out.println("[url] ==> password_chk.ad");
			
			service.password_chk(req, res);
			// 서비스에서 viewPage 페이지 핸들링
			 //viewPage = "/manager/csCenter/board_edit.jsp";
			
			//RequestDispatcher dispatcher = req.getRequestDispatcher(viewPage);
			//dispatcher.forward(req, res);	
		
		// 게시글 수정
		} else if(url.equals("/board_updateAction.ad")) {
			System.out.println("[url] ==> board_updateAction.ad");
			
			service.boardUpdate(req, res);
			
			viewPage = req.getContextPath() + "/boardList.ad";		// jsp_pj_105
			res.sendRedirect(viewPage);	
		
		// 게시글 삭제
		} else if(url.equals("/board_deleteAction.ad")) {
			System.out.println("[url] ==> board_deleteAction.ad");
			
			service.boardDelete(req, res);
			
			viewPage = req.getContextPath() + "/boardList.ad";		// jsp_pj_105
			res.sendRedirect(viewPage);		
			
		// -- 댓글 --
		// 댓글 작성
		} else if(url.equals("/commentAdd.ad")) { 	
			System.out.println("[url] ==> commentAdd.ad");
			
			service.commentAdd(req, res);
			
		// 댓글 조회	
		} else if(url.equals("/commentList.ad")) { 	
			System.out.println("[url] ==> commentList.ad");
			
			service.commentList(req, res);
			
			 viewPage = "/manager/csCenter/board_commentList.jsp";	
			 
			 RequestDispatcher dispatcher = req.getRequestDispatcher(viewPage);
			dispatcher.forward(req, res);		
		
		// 댓글 삭제
		} else if(url.equals("/deleteComment.ad")) { 	
			System.out.println("[url] ==> deleteComment.ad");
			
			service.deleteComment(req, res);
			 
			viewPage = "/manager/csCenter/board_detailAction.jsp";		
			RequestDispatcher dispatcher = req.getRequestDispatcher(viewPage);
			dispatcher.forward(req, res);		
			
		// 로그아웃	
		} else if(url.equals("/logout.ad")) { 	
			System.out.println("[url] ==> logout.ad");
			
			 viewPage = "/main.do";	
			 
			 RequestDispatcher dispatcher = req.getRequestDispatcher(viewPage);
			dispatcher.forward(req, res);		
		
		}
		
	}
}
