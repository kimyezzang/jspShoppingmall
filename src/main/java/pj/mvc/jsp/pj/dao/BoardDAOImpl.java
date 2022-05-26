package pj.mvc.jsp.pj.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import pj.mvc.jsp.pj.dto.BoardCommentDTO;
import pj.mvc.jsp.pj.dto.BoardDTO;

public class BoardDAOImpl implements BoardDAO{

	static BoardDAOImpl instance = new BoardDAOImpl();
	
	public static BoardDAOImpl getInstance() {
		if(instance == null) {
			instance = new BoardDAOImpl();
		}
		return instance;
	}
		
	private BoardDAOImpl() {
		try {
			Context context = new InitialContext();
			dataSource = (DataSource)context.lookup("java:comp/env/jdbc/jsp_pj_marketyezzang");
		
		} catch(NamingException e) {
			e.printStackTrace();
		}
	};
	
	// 커넥션 풀 객체를 보관
	DataSource dataSource;
	
	// 게시글 목록
	@Override
	public List<BoardDTO> boadrList(int start, int end) {
		System.out.println("DAO - boardList");
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<BoardDTO> list = null;
		
		try {
			conn = dataSource.getConnection();
			String sql = "SELECT * "
					+ "FROM ( "
					+ 		  " SELECT A.* , rownum as rn "
					+ 		  " FROM ("
					+  		  " SELECT num, title, content, writer, password, readCnt, regDate, "
					+ 		  " (SELECT COUNT(*) FROM mvc_board_comment_tbl "
					+ 		  "		WHERE board_num = b.num) comment_count "
					+ 		  " FROM mvc_board_tbl b"
					+ 		  " WHERE show in 'y' "	// 게시글 삭제시 show='n'
					+ 		  " ORDER BY num DESC"
					+ 		  " ) A "
					+ 		" ) "
					+ "WHERE rn BETWEEN ? AND ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				// 1.list 생성
				list = new ArrayList<BoardDTO>();
				
				do {
					// 2.dto 생성
					BoardDTO dto = new BoardDTO();
					// 3.dto에 rs게시글정보를 담는다.
					dto.setNum(rs.getInt("num"));
					dto.setTitle(rs.getString("title"));
					dto.setContent(rs.getString("content"));
					dto.setWriter(rs.getString("writer"));
					dto.setPassword(rs.getString("password"));
					dto.setReadCnt(rs.getInt("readCnt"));
					dto.setRegDate(rs.getDate("regDate"));
					dto.setComment_count(rs.getInt("comment_count"));
					// 4.list에 dto를 추가한다.
					list.add(dto);
					
				} while(rs.next());
				
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			// 사용한 자원 해제
			try {
				if(rs!=null)rs.close();
				if(pstmt!=null)pstmt.close();
				if(conn!=null)conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		// 5. list 리턴
		return list;
	}

	// 게시글 작성처리
	@Override
	public int boardInsert(BoardDTO dto) {
		System.out.println("DAO - boardInsert");
		
		int insertCnt = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			
			conn = dataSource.getConnection();
			String sql = "INSERT INTO mvc_board_tbl(num,title,content,writer,password,readCnt,regDate) "
					+ " VALUES ((SELECT NVL(MAX(num) + 1, 1) FROM mvc_board_tbl), ?, ?, ?, ?, 0, sysdate) ";
					
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,dto.getTitle());
			pstmt.setString(2,dto.getContent());
			pstmt.setString(3,dto.getWriter());
			pstmt.setString(4,dto.getPassword());
			
			insertCnt = pstmt.executeUpdate();
			System.out.println("insertCnt" + insertCnt);
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			// 사용한 자원 해제
			try {
				if(pstmt!=null)pstmt.close();
				if(conn!=null)conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return insertCnt;
	}

	// 조회수 증가
	@Override
	public void plusReadCnt(int num) {
		System.out.println("DAO - plusReadCnt");
		
		int updateCnt = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			
			conn = dataSource.getConnection();
			String sql = "UPDATE mvc_board_tbl SET readCnt = (readCnt + 1) "
					+ " WHERE num = ? ";
					
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,num);
			
			updateCnt = pstmt.executeUpdate();
			System.out.println("updateCnt" + updateCnt);
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			// 사용한 자원 해제
			try {
				if(pstmt!=null)pstmt.close();
				if(conn!=null)conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
	}

	// 게시글 상세페이지
	@Override
	public BoardDTO getBoardDetail(int num) {
		System.out.println("DAO - getBoardDetail");
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		BoardDTO dto = new BoardDTO();
		
		try {
			
			conn = dataSource.getConnection();
			String sql = "SELECT * FROM mvc_board_tbl WHERE num IN ?";
					
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,num);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
			// 3.dto에 rs게시글정보를 담는다.
			dto.setNum(rs.getInt("num"));
			dto.setTitle(rs.getString("title"));
			dto.setContent(rs.getString("content"));
			// 줄바꿈처리
//			if(newline == true) {
//				String content = dto.getContent();
//				content = content.replace("\n", "<br>");
//				dto.setContent(content);
//			}
			dto.setWriter(rs.getString("writer"));
			dto.setPassword(rs.getString("password"));
			dto.setReadCnt(rs.getInt("readCnt"));
			dto.setRegDate(rs.getDate("regDate"));
			
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			// 사용한 자원 해제
			try {
				if(rs!=null)rs.close();
				if(pstmt!=null)pstmt.close();
				if(conn!=null)conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		return dto;
	}

	// 비밀번호 인증 - 비밀번호 리턴
	@Override
	public String password_chk(int num, String password) {
		System.out.println("DAO - password_chk");
		
		String result = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			conn = dataSource.getConnection();
			String sql = "SELECT * FROM mvc_board_tbl WHERE num = ? "
					+ " AND password = ?";
					
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,num);
			pstmt.setString(2, password);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
			// 3.dto에 rs게시글정보를 담는다.
			result = rs.getString("password");
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			// 사용한 자원 해제
			try {
				if(pstmt!=null)pstmt.close();
				if(conn!=null)conn.close();
				if(rs!=null)rs.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		System.out.println("result값 : " + result);
		return result;
	}

	// 게시글 수정처리
	@Override
	public int updateBoard(BoardDTO dto) {
		System.out.println("DAO - updateBoard");
		
		int updateCnt = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			
			conn = dataSource.getConnection();
			String sql = "UPDATE mvc_board_tbl "
					+ " SET writer =?, title=?, content = ? , password = ? "
					+ "WHERE num in ? ";
					
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,dto.getWriter());
			pstmt.setString(2,dto.getTitle());
			pstmt.setString(3,dto.getContent());
			pstmt.setString(4,dto.getPassword());
			pstmt.setInt(5,dto.getNum());
			
			updateCnt = pstmt.executeUpdate();
			System.out.println("updateCnt" + updateCnt);
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			// 사용한 자원 해제
			try {
				if(pstmt!=null)pstmt.close();
				if(conn!=null)conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		return updateCnt;
	}

	// 게시글 삭제처리
	@Override
	public int deleteBoard(int num) {
		System.out.println("DAO - deleteBoard");
		
		int deleteCnt = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			
			conn = dataSource.getConnection();
			String sql = "UPDATE mvc_board_tbl "
					+ " SET show = 'n' "
					+ " WHERE num = ? ";
			// list where절에 show='y' 추가
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,num);
			
			deleteCnt = pstmt.executeUpdate();
			System.out.println("(DAO deleteBoard)deleteCnt : " + deleteCnt);
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			// 사용한 자원 해제
			try {
				if(pstmt!=null)pstmt.close();
				if(conn!=null)conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		return deleteCnt;
	}

	@Override
	public void getCommentInsert(BoardCommentDTO dto) {
		System.out.println("DAO - getCommentInsert");
		
		int insertCnt = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			
			conn = dataSource.getConnection();
			String sql = "INSERT INTO mvc_board_comment_tbl "
					+ "(comment_num, board_num, writer, content, reg_date ) "
					+ "VALUES((SELECT NVL(MAX(comment_num) +1, 1) FROM mvc_board_comment_tbl),?,?,?,sysdate)";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1,dto.getBoard_num());
			pstmt.setString(2, dto.getWriter());
			pstmt.setString(3,dto.getContent());
			
			insertCnt = pstmt.executeUpdate();
			System.out.println("DAO getCommentInsert : " + insertCnt);
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			// 사용한 자원 해제
			try {
				if(pstmt!=null)pstmt.close();
				if(conn!=null)conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public List<BoardCommentDTO> getCommentList(int board_num) {
		System.out.println("DAO - getCommentList");
		
		List<BoardCommentDTO> list = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = dataSource.getConnection();
			String sql = "SELECT * FROM mvc_board_comment_tbl WHERE board_num = ? ORDER BY comment_num";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, board_num);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				// 1.list 생성
				list = new ArrayList<BoardCommentDTO>();
				
				do {
					
					// 2.dto 생성
					BoardCommentDTO dto = new BoardCommentDTO();
					// 3.dto에 rs게시글정보를 담는다.
					dto.setBoard_num(rs.getInt("board_num"));
					dto.setComment_num(rs.getInt("comment_num"));
					dto.setContent(rs.getString("content"));
					dto.setReg_date(rs.getTimestamp("reg_date"));
					dto.setWriter(rs.getString("writer"));
					
					// 4.list에 dto를 추가한다.
					list.add(dto);
					
					
				} while(rs.next());
				
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			// 사용한 자원 해제
			try {
				if(rs!=null)rs.close();
				if(pstmt!=null)pstmt.close();
				if(conn!=null)conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		// 5. list 리턴
		return list;
		
	}

	// 게시글 갯수 구하기
	@Override
	 public int boardCnt() {
	      System.out.println("DAO - boardCnt");
	      
	      Connection conn = null;
	      PreparedStatement pstmt = null;
	      ResultSet rs = null;
	      int selectCnt = 0;
	      
	      try {
	         conn = dataSource.getConnection();
	         String sql =  "SELECT COUNT(*) as cnt FROM mvc_board_tbl";
	         pstmt = conn.prepareStatement(sql);
	         
	         rs = pstmt.executeQuery();
	         
	         if(rs.next()) {
	            selectCnt = rs.getInt("cnt");
	         }
	         
	      } catch(SQLException e) {
	         e.printStackTrace();
	      } finally {
	         // 사용한 자원해제
	         try {
	            if(rs != null) rs.close();
	            if(pstmt != null) pstmt.close();
	            if(conn != null) conn.close();
	         } catch(SQLException e) {
	            e.printStackTrace();
	         }
	      }
	      
	      return selectCnt;
	   }

	// 댓글삭제
	@Override
	public int deleteComment(int board_num, int comment_num) {
		System.out.println("DAO - deleteComment");
		
		int deleteCnt = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			
			conn = dataSource.getConnection();
			String sql = "DELETE FROM mvc_board_comment_tbl "
					+ " WHERE board_num = ? AND comment_num = ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,board_num);
			pstmt.setInt(2,comment_num);
			
			deleteCnt = pstmt.executeUpdate();
			System.out.println("(DAO deleteComment)deleteCnt : " + deleteCnt);
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			// 사용한 자원 해제
			try {
				if(pstmt!=null)pstmt.close();
				if(conn!=null)conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		return deleteCnt;
		
	}
	
}