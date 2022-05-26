package pj.mvc.jsp.pj.dao;

import java.util.List;

import pj.mvc.jsp.pj.dto.BoardCommentDTO;
import pj.mvc.jsp.pj.dto.BoardDTO;

public interface BoardDAO {
	
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
	
	// 비밀번호 인증
	public String password_chk(int num, String password);
	
	// 게시글 수정처리
	public int updateBoard(BoardDTO dto);
	
	// 게시글 삭제처리
	public int deleteBoard(int num);

	// 댓글 추가처리
	public void getCommentInsert(BoardCommentDTO dto);
	
	// 댓글 목록
	public List<BoardCommentDTO> getCommentList(int board_num);
	
	// 댓글 삭제
	public int deleteComment(int board_num, int comment_num);
	
}
