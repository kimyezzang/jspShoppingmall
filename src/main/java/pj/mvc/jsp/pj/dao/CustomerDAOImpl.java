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
import pj.mvc.jsp.pj.dto.CustomerDTO;
import pj.mvc.jsp.pj.dto.ProductDTO;

public class CustomerDAOImpl implements CustomerDAO{

	// 싱글톤 방식 : 객체를 1번만 생성
		static CustomerDAOImpl instance = new CustomerDAOImpl();
		
		public static CustomerDAOImpl getInstance() {
			if(instance == null) {
				instance = new CustomerDAOImpl();
			}
			return instance;
		}
		private CustomerDAOImpl() {
			/*
			 * 컨넥션풀 : 매번 connection을 만들지 말고 컨넥션풀 이용(context.xml 이용)
			 * DBCP(DataBase Connection Pool) 설정을 읽어서 컨넥션을 발급받겠다.
			 * 1. Context : Servers > context.xml 파일의 Resource 객체에 추가
			 * 2. lookup("java:comp/env/[컨넥션풀 name]");
			 *
			 *  String dbURL = "jdbc:oracle:thin:@localhost:1521:xe";  // @HOST:PORT:SID" 
			 *  String dbID = "scott";   // 계정
			 *   String dbPassword = "tiger";   // 비밀번호
			 * 주의사항 : 톰캣 재설치시 resource 정보가 날라가므로 재추가할 것
			 */
			
			// 컨넥션 풀 객체를 보관
			// DataSource dataSource;
			try {
				Context context = new InitialContext();
				dataSource = (DataSource)context.lookup("java:comp/env/jdbc/jsp_pj_marketyezzang");
			
			} catch(NamingException e) {
				e.printStackTrace();
			}
			
		};	
		DataSource dataSource;	
	// ID 중복확인 처리
	@Override
	public int idCheck(String strId) {
		System.out.println("dao - ID 중복확인 처리");
		int selectCnt = 0;
		Connection conn = null;	// 오라클 연결
		PreparedStatement pstmt = null;	// SQL 문장
		ResultSet rs = null;	// 결과
		
		try {
			conn = dataSource.getConnection();
			String sql = "SELECT * FROM mvc_customer_tbl WHERE id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, strId);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				selectCnt = 1;
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
		//System.out.println("selectCnt : " + selectCnt);
		return selectCnt;
	}

	// email 중복확인 처리
	@Override
	public int emailCheck(String strEmail) {
		System.out.println("dao - Email 중복확인 처리");
		int selectCnt = 0;
		Connection conn = null;	// 오라클 연결
		PreparedStatement pstmt = null;	// SQL 문장
		ResultSet rs = null;	// 결과
		
		try {
			conn = dataSource.getConnection();
			String sql = "SELECT * FROM mvc_customer_tbl WHERE email = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, strEmail);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				selectCnt = 1;
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
		//System.out.println("selectCnt : " + selectCnt);
		return selectCnt;
	}

	// 회원가입 처리
	@Override
	public int insertCustomer(CustomerDTO dto) {
		System.out.println("dao - 회원가입처리");
		
		int insertCnt = 0;
		Connection conn = null;	// 오라클 연결
		PreparedStatement pstmt = null;	// SQL 문장
		
		try {
			conn = dataSource.getConnection();
			String sql = "INSERT INTO mvc_customer_tbl(id, password, passwordHint, name, birthday, address, hp, email, regDate)"
					+" VALUES(?,?,?,?,?,?,?,?,sysdate)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getId());
			pstmt.setString(2, dto.getPassword());
			pstmt.setString(3, dto.getPasswordHint());
			pstmt.setString(4, dto.getName());
			pstmt.setDate(5, dto.getBirth());
			pstmt.setString(6, dto.getAddress());
			pstmt.setString(7, dto.getHp());
			pstmt.setString(8, dto.getEmail());
			// pstmt.setTimestamp(8, dto.getRegDate());
			// 이메일 인증키
			
			insertCnt = pstmt.executeUpdate();	// 입력성공:1 입력실패:0
			System.out.println("insertCnt : " + insertCnt);
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			// 사용한 자원 해제
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		return insertCnt;
	}

	// 로그인 처리
	@Override
	public int idPasswordCheck(String strId, String strPassword) {
		int selectCnt = 0;
		Connection conn = null;	// 오라클 연결
		PreparedStatement pstmt = null;	// SQL 문장
		ResultSet rs = null;	// 결과
		
		try {
			
			conn = dataSource.getConnection();
			String sql = "SELECT * FROM mvc_customer_tbl WHERE id = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, strId);
			rs = pstmt.executeQuery();
			System.out.println("dao - idPasswordCheck strId : " + strId);		
			System.out.println("dao - idPasswordCheck strPassword : " + strPassword);
			// 로그인한 id와 일치하고
			if(rs.next()) {
				
				// 패스워드가 일치하면 selectCnt = 1
				if(rs.getString("password").equals(strPassword)) {
				
					selectCnt = 1;}
				
				else {// 패스워드가 불일치하면 selectCnt = -1
				
					selectCnt = -1;}
			// id가 불일치하면 비가입 select = 0
			} else {
				selectCnt = 0;
			}
			System.out.println("selectCnt : " + selectCnt );
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			// 사용한 자원 해제
			try {
				if(rs!=null) rs.close();
				if(pstmt!=null) pstmt.close();
				if(conn!=null) conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return selectCnt;
	}

	// 회원정보 인증 및 탈퇴 처리
	@Override
	public int deleteCustomer(String strId) {
		System.out.println("dao - 회원정보 인증 및 탈퇴처리 CustomerDAOImpl");
		
		int deleteCnt = 0;
		Connection conn = null;	// 오라클 연결
		PreparedStatement pstmt = null;	// SQL 문장
		
		try {
			conn = dataSource.getConnection();
			String sql = "DELETE FROM mvc_customer_tbl WHERE id = ?";
					
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, strId);
			
			deleteCnt = pstmt.executeUpdate();	// 삭제성공:1 입력실패:0
			System.out.println("deleteCnt : " + deleteCnt);
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			// 사용한 자원 해제
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return deleteCnt;
	}

	// 회원정보 인증 및 상세페이지
	@Override
	public CustomerDTO getCustomerDetail(String strId) {
		System.out.println("dao - 회원정보 인증 및 상세페이지");
		
		Connection conn = null;	// 오라클 연결
		PreparedStatement pstmt = null;	// SQL 문장
		ResultSet rs = null;	// 결과
		
		// 1. 바구니 생성
		CustomerDTO dto = new CustomerDTO();
		
		try {
			
			conn = dataSource.getConnection();
			
			// 2.strId(로그인 화면에서 입력받은 세션Id)와 일치하는 데이터가 있는지 조회
			String sql = "SELECT * FROM mvc_customer_tbl WHERE id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, strId);
			
			rs = pstmt.executeQuery();
			
			// 3. ResultSet에 id와 일치하는 한사람의 회원정보가 존재하면 
			// ResultSet을 읽어서 DTO에 setter로 담는다. 
			if(rs.next()) {
				dto.setId(rs.getString("id"));
				dto.setPassword(rs.getString("password"));
				dto.setName(rs.getString("name"));
				dto.setBirth(rs.getDate("birthDay"));
				dto.setAddress(rs.getString("address"));
				dto.setHp(rs.getString("hp"));
				dto.setEmail(rs.getString("email"));
				dto.setRegDate(rs.getTimestamp("regDate"));
				
				dto.setPasswordHint(rs.getString("passwordHint"));
			}
			
		} catch(SQLException e) {
				e.printStackTrace();
		} finally {
				// 사용한 자원 해제
			try {
				if(rs!=null) rs.close();
				if(pstmt!=null) pstmt.close();
				if(conn!=null) conn.close();
			} catch(SQLException e) {
					e.printStackTrace();
			}
		}
		// 5. CustomerDTO를 리턴한다.
		return dto;
	}

	// 회원정보 수정 처리
	@Override
	public int updateCustomer(CustomerDTO dto) {
		System.out.println("dao - 회원수정처리 CustomerDAOImpl");
		
		int updateCnt = 0;
		Connection conn = null;	// 오라클 연결
		PreparedStatement pstmt = null;	// SQL 문장
		
		try {
			conn = dataSource.getConnection();
			String sql = "UPDATE mvc_customer_tbl SET password =? , name =?, birthday =? , address =?, hp =? ,email=?"
					+ ", passwordHint = ? WHERE id =?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getPassword());
			pstmt.setString(2, dto.getName());
			pstmt.setDate(3, dto.getBirth());
			pstmt.setString(4, dto.getAddress());
			pstmt.setString(5, dto.getHp());
			pstmt.setString(6, dto.getEmail());
			pstmt.setString(7, dto.getPasswordHint());
			pstmt.setString(8, dto.getId());
			
			// pstmt.setTimestamp(8, dto.getRegDate());
			// 이메일 인증키
			
			updateCnt = pstmt.executeUpdate();	// 입력성공:1 입력실패:0
			System.out.println("updateCnt : " + updateCnt);
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			// 사용한 자원 해제
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return updateCnt;
	}
	// ---- [게시판] ----
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
	
	// 게시판 갯수
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
	
	// 상세페이지
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
	// 댓글 추가
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
	
	// 댓글 목록
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
	
	// 장바구니 회원 주소
	@Override
	public String basketAddress(String id) {
		System.out.println("dao - basketAddress");
		
		Connection conn = null;	// 오라클 연결
		PreparedStatement pstmt = null;	// SQL 문장
		ResultSet rs = null;	// 결과
		
		// 1. 바구니 생성
		String address ="";
		
		try {
			
			conn = dataSource.getConnection();
			
			// 2.strId(로그인 화면에서 입력받은 세션Id)와 일치하는 데이터가 있는지 조회
			String sql = "SELECT * FROM mvc_customer_tbl WHERE id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			
			// 3. ResultSet에 id와 일치하는 한사람의 회원정보가 존재하면 
			// ResultSet을 읽어서 DTO에 setter로 담는다. 
			if(rs.next()) {
				
				address = rs.getString("address");
			}
			
		} catch(SQLException e) {
				e.printStackTrace();
		} finally {
				// 사용한 자원 해제
			try {
				if(rs!=null) rs.close();
				if(pstmt!=null) pstmt.close();
				if(conn!=null) conn.close();
			} catch(SQLException e) {
					e.printStackTrace();
			}
		}
		// 5. CustomerDTO를 리턴한다.
		return address;
	}
	
	// 검색
	@Override
	public List<ProductDTO> search(String content, int start, int end) {
		System.out.println("DAO - search");
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<ProductDTO> list = null;
		
		try {
			conn = dataSource.getConnection();
			String sql = "SELECT * "
					+ "FROM ( "
					+ 		  " SELECT A.* , rownum as rn "
					+ 		  " FROM ("
					+  		  " SELECT pdNo, pdName, pdImg, category, brand, content, price, quantity, status, indate "
					+ 		  " FROM mvc_product_tbl"
					+ 		  "	WHERE pdName LIKE ? "
					+ 		  " ORDER BY indate DESC"
					+ 		  " ) A "
					+ " ) "
					+ "WHERE rn BETWEEN ? AND ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%"+content+"%");
			pstmt.setInt(2, start);
			pstmt.setInt(3, end);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				// 1.list 생성
				list = new ArrayList<ProductDTO>();
				
				do {
					// 2.dto 생성
					ProductDTO dto = new ProductDTO();
					// 3.dto에 rs게시글정보를 담는다.
					dto.setPdNo(rs.getInt("pdNo"));
					dto.setPdName(rs.getString("pdName"));
					dto.setPdImg(rs.getString("pdImg"));
					dto.setCategory(rs.getString("category"));
					dto.setBrand(rs.getString("brand"));
					dto.setContent(rs.getString("content"));
					dto.setPrice(rs.getInt("price"));
					dto.setQuantity(rs.getInt("quantity"));
					dto.setStatus(rs.getString("status"));
					dto.setIndate(rs.getDate("indate"));
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
	
	// 검색 갯수
	@Override
	public int searchCnt(String content) {
		System.out.println("customerDAO - searchCnt");
	      
	      Connection conn = null;
	      PreparedStatement pstmt = null;
	      ResultSet rs = null;
	      int selectCnt = 0;
	      
	      try {
	         conn = dataSource.getConnection();
	         String sql =  "SELECT COUNT(*) as cnt FROM mvc_product_tbl WHERE pdName LIKE ? ";
	         pstmt = conn.prepareStatement(sql);
	         
	         pstmt.setString(1,"%"+content+"%");
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

}
