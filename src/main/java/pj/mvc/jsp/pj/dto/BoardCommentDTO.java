package pj.mvc.jsp.pj.dto;

import java.sql.Timestamp;

// 게시판 댓글 DTO
public class BoardCommentDTO {
	
	private int comment_num;	// PK, 댓글 일련번호
	private int board_num ;		// foreign key, 게시글 번호 
	private String writer;		// 작성자
	private String content;		// 글내용
	private Timestamp reg_date;		// 글내용
	
	
	public BoardCommentDTO() {}
	
	public BoardCommentDTO(int comment_num, int board_num, String writer, String content, Timestamp reg_date) {
		super();
		this.comment_num = comment_num;
		this.board_num = board_num;
		this.writer = writer;
		this.content = content;
		this.reg_date = reg_date;
	}
	
	public int getComment_num() {
		return comment_num;
	}
	public void setComment_num(int comment_num) {
		this.comment_num = comment_num;
	}
	public int getBoard_num() {
		return board_num;
	}
	public void setBoard_num(int board_num) {
		this.board_num = board_num;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Timestamp getReg_date() {
		return reg_date;
	}
	public void setReg_date(Timestamp reg_date) {
		this.reg_date = reg_date;
	}
	
	

	@Override
	public String toString() {
		return "BoardCommentDTO [comment_num=" + comment_num + ", board_num=" + board_num + ", writer=" + writer
				+ ", content=" + content + ", reg_date=" + reg_date + "]";
	}

	
}


/*
 * comment_num number(7) PRIMARY KEY, -- PK 댓글 일련번호
 *  board_num number(7) NOT NULL
 * references mvc_board_tbl(num), -- foreign key, 게시글 번호 
 * writer varchar2(50) NOT
 * NULL, -- 작성자 content clob not null, -- 글내용 
 * reg_date date default sysdate --작성일
 * comment_count
 */