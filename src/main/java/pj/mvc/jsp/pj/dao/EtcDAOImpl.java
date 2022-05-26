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

import pj.mvc.jsp.pj.dto.CustomerDTO;
import pj.mvc.jsp.pj.dto.ProductDTO;

public class EtcDAOImpl implements EtcDAO {

	static EtcDAOImpl instance = new EtcDAOImpl();
	
	public static EtcDAOImpl getInstance() {
		if(instance == null) {
			instance = new EtcDAOImpl();
		}
		return instance;
	}
		
	private EtcDAOImpl() {
		try {
			Context context = new InitialContext();
			dataSource = (DataSource)context.lookup("java:comp/env/jdbc/jsp_pj_marketyezzang");
		
		} catch(NamingException e) {
			e.printStackTrace();
		}
	};
	
	// 커넥션 풀 객체를 보관
	DataSource dataSource;
	
	// 회원정보 상세페이지
	@Override
	public CustomerDTO getUserDetail(String id) {
		System.out.println("EtcDAOImpl - getUserDetail");
		
		// 2.dto 생성
		CustomerDTO dto = new CustomerDTO();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			
			conn = dataSource.getConnection();
			String sql = "SELECT * FROM mvc_customer_tbl WHERE id = ? ";
					
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,id);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
					
					// 3.dto에 rs product정보를 담는다.
				dto.setId(rs.getString("id"));
				dto.setPassword(rs.getString("password"));
				dto.setPasswordHint(rs.getString("passwordHint"));
				dto.setName(rs.getString("name"));
				dto.setBirth(rs.getDate("birthday"));
				dto.setEmail(rs.getString("email"));
				dto.setRegDate(rs.getTimestamp("regDate"));
				dto.setAddress(rs.getString("address"));
				dto.setHp(rs.getString("hp"));
			}
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
		return dto;
	}

	// 회원정보 삭제 처리
	@Override
	public int userDelete(String id) {
		System.out.println("DAO - userDelete");
		
		int updateCnt = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			
			conn = dataSource.getConnection();
			String sql = "DELETE FROM mvc_customer_tbl where id = ?";
					
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,id);
			
			updateCnt = pstmt.executeUpdate();
			System.out.println("DAO - userDelete - updateCnt" + updateCnt);
			
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

	// 회원정보 갯수
	@Override
	public int userCnt() {
		System.out.println("EtcDAO - userCnt");
	      
	      Connection conn = null;
	      PreparedStatement pstmt = null;
	      ResultSet rs = null;
	      int selectCnt = 0;
	      
	      try {
	         conn = dataSource.getConnection();
	         String sql =  "SELECT COUNT(*) as cnt FROM mvc_customer_tbl";
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

	// 회원정보 목록
	@Override
	public List<CustomerDTO> userList(int start, int end) {
		System.out.println("DAO - userList");
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<CustomerDTO> list = null;
		
		try {
			conn = dataSource.getConnection();
			String sql = "SELECT * "
					+ "FROM ( "
					+ 		  " SELECT A.* , rownum as rn "
					+ 		  " FROM ("
					+  		  " SELECT id, password, passwordHint, name, birthday, address, email, regDate,hp "
					+ 		  " FROM mvc_customer_tbl "
					+ 		  " ) A "
					+ " ) "
					+ "WHERE rn BETWEEN ? AND ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				// 1.list 생성
				list = new ArrayList<CustomerDTO>();
				
				do {
					// 2.dto 생성
					CustomerDTO dto = new CustomerDTO();
					// 3.dto에 rs게시글정보를 담는다.
					dto.setId(rs.getString("id"));
					dto.setPassword(rs.getString("password"));
					dto.setPasswordHint(rs.getString("passwordHint"));
					dto.setName(rs.getString("name"));
					dto.setBirth(rs.getDate("birthday"));
					dto.setEmail(rs.getString("email"));
					dto.setRegDate(rs.getTimestamp("regDate"));
					dto.setAddress(rs.getString("address"));
					dto.setHp(rs.getString("hp"));
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

}
