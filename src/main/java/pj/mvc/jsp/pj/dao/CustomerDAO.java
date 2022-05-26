package pj.mvc.jsp.pj.dao;

import java.util.List;

import pj.mvc.jsp.pj.dto.BoardCommentDTO;
import pj.mvc.jsp.pj.dto.BoardDTO;
import pj.mvc.jsp.pj.dto.CustomerDTO;
import pj.mvc.jsp.pj.dto.ProductDTO;

public interface CustomerDAO {
		// ID 중복확인 처리
		public int idCheck(String strId);
		
		// email 중복확인 처리
		public int emailCheck(String strEmail);
				
		// 회원가입 처리
		public int insertCustomer(CustomerDTO dto);
		
		// 로그인 처리
		public int idPasswordCheck(String strId, String strPassword);
		
		// 회원정보 인증 및 탈퇴 처리
		public int deleteCustomer(String strId);
		
		// 회원정보 인증 및 상세페이지
		public CustomerDTO getCustomerDetail(String strId);
		
		// 회원정보 수정 처리
		public int updateCustomer(CustomerDTO dto);
		
		// 검색
		public List<ProductDTO> search(String content, int start, int end);
		
		// 검색 갯수
		public int searchCnt(String content);
				
		
		// ---- [게시판] ----
		// 게시글 목록
		public List<BoardDTO> boadrList(int start, int end);
		
		// 게시판 갯수
		public int boardCnt();
		
		// 게시글 작성처리
		public int boardInsert(BoardDTO dto);
		
		// 조회수 증가
		public void plusReadCnt(int num);
		
		// 게시글 상세페이지
		public BoardDTO getBoardDetail(int num);
		
		// ---- [댓글] ----
		// 댓글 추가처리
		public void getCommentInsert(BoardCommentDTO dto);
		
		// 댓글 목록
		public List<BoardCommentDTO> getCommentList(int board_num);
		
		// ----- [장바구니] -----
		// 장바구니 회원 주소 정보
		public String basketAddress(String id);
		
}
