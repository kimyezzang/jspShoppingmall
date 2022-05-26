package pj.mvc.jsp.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface CustomerService {
		// ID중복확인 처리
		public void confirmIdAction(HttpServletRequest req, HttpServletResponse res);
		
		// Email중복확인 처리
		public void confirmEmailAction(HttpServletRequest req, HttpServletResponse res);
				
		// 회원가입 처리
		public void signInAction(HttpServletRequest req, HttpServletResponse res);
		
		// 로그인 처리
		public void loginAction(HttpServletRequest req, HttpServletResponse res);
		
		// 회원정보 인증 및 탈퇴 처리
		public void deleteCustomerAction(HttpServletRequest req, HttpServletResponse res);
		
		// 회원정보 인증 및 상세페이지
		public void modifyDetailAction(HttpServletRequest req, HttpServletResponse res);
		
		// 회원정보 수정 처리
		public void modifyCustomerAction(HttpServletRequest req, HttpServletResponse res);
		
		// 이메일 인증
		public void emailChkAction(HttpServletRequest req, HttpServletResponse res);
		
		// 검색 처리
		public void search(HttpServletRequest req, HttpServletResponse res);
		
		
		//-----------------------------------[게시판]---------------------------
		// 게시글 목록
		public void boardList(HttpServletRequest req, HttpServletResponse res)
				throws ServletException, IOException;
		
		// 게시글 작성처리 페이지
		public void boardInsert(HttpServletRequest req, HttpServletResponse res)
				throws ServletException, IOException;
		
		// 게시글 상세페이지
		public void boardDetail(HttpServletRequest req, HttpServletResponse res)
				throws ServletException, IOException;
		
		//-----------------------------------[댓글]---------------------------
		// 댓글 추가처리 페이지
		public void commentAdd(HttpServletRequest req, HttpServletResponse res)
				throws ServletException, IOException; 
			
		// 댓글 목록처리 페이지
		public void commentList(HttpServletRequest req, HttpServletResponse res)
				throws ServletException, IOException;
		
		// -- [장바구니]--
		public void basketAddress(HttpServletRequest req, HttpServletResponse res)
				throws ServletException, IOException;	

}
