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

import pj.mvc.jsp.pj.dto.BasketDTO;
import pj.mvc.jsp.pj.dto.OrderDTO;
import pj.mvc.jsp.pj.dto.ProductDTO;
import pj.mvc.jsp.pj.dto.TotalMoneyDTO;

public class ProductDAOImpl implements ProductDAO{

static ProductDAOImpl instance = new ProductDAOImpl();
	
	public static ProductDAOImpl getInstance() {
		if(instance == null) {
			instance = new ProductDAOImpl();
		}
		return instance;
	}
		
	private ProductDAOImpl() {
		try {
			Context context = new InitialContext();
			dataSource = (DataSource)context.lookup("java:comp/env/jdbc/jsp_pj_marketyezzang");
		
		} catch(NamingException e) {
			e.printStackTrace();
		}
	};
	
	// 커넥션 풀 객체를 보관
	DataSource dataSource;
	
	// 상품추가 처리
	@Override
	public int productInsert(ProductDTO dto) {
		System.out.println("ProductDAOImpl - productInsert");
		
		int insertCnt = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			
			conn = dataSource.getConnection();
			String sql = "INSERT INTO mvc_product_tbl(pdNo,pdName,pdImg,category, brand,content,price,quantity, status,indate) "
					+ " VALUES ((SELECT NVL(MAX(pdNo) + 1, 1) FROM mvc_product_tbl), ?, ?, ?, ?, ?,?,?,?, sysdate) ";
					
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,dto.getPdName());
			pstmt.setString(2,dto.getPdImg());
			pstmt.setString(3,dto.getCategory());
			pstmt.setString(4,dto.getBrand());
			pstmt.setString(5,dto.getContent());
			pstmt.setInt(6,dto.getPrice());
			pstmt.setInt(7,dto.getQuantity());
			pstmt.setString(8,dto.getStatus());
			
			insertCnt = pstmt.executeUpdate();
			System.out.println("ProductDAOImpl insertCnt" + insertCnt);
			
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

	// 상품 상세페이지
	@Override
	public ProductDTO getProductDetail(int pdNo) {
		System.out.println("ProductDAOImpl - getProductDetail");
		
		// 2.dto 생성
		ProductDTO dto = new ProductDTO();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			
			conn = dataSource.getConnection();
			String sql = "SELECT * FROM mvc_product_tbl WHERE pdNo = ? ";
					
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,pdNo);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
					
					// 3.dto에 rs product정보를 담는다.
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
	
	// 상품 수정 처리
		@Override
		public int productUpdate(ProductDTO dto) {
			System.out.println("DAO - productUpdate");
			
			int updateCnt = 0;
			Connection conn = null;
			PreparedStatement pstmt = null;
			
			try {
				
				conn = dataSource.getConnection();
				String sql = "UPDATE mvc_product_tbl "
						+ " SET pdName = ?, pdImg = ?, category = ?, brand = ?, content = ?, price = ?, quantity = ?, status = ? "
						+ "WHERE pdNo = ? ";
						
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1,dto.getPdName());
				pstmt.setString(2,dto.getPdImg());
				pstmt.setString(3,dto.getCategory());
				pstmt.setString(4,dto.getBrand());
				pstmt.setString(5,dto.getContent());
				pstmt.setInt(6,dto.getPrice());
				pstmt.setInt(7,dto.getQuantity());
				pstmt.setString(8,dto.getStatus());
				pstmt.setInt(9,dto.getPdNo());
				
				updateCnt = pstmt.executeUpdate();
				System.out.println("DAO - productUpdate - updateCnt" + updateCnt);
				
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
		
	// 상품 삭제 처리
	@Override
	public int productDelete(int pdNo) {
		System.out.println("DAO - productDelete");
		
		int updateCnt = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			
			conn = dataSource.getConnection();
			String sql = "DELETE FROM mvc_product_tbl where pdNo = ?";
					
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,pdNo);
			
			updateCnt = pstmt.executeUpdate();
			System.out.println("DAO - productDelete - updateCnt" + updateCnt);
			
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

	// 상품갯수
	@Override
	public int productCnt() {
		System.out.println("ProductDAO - productCnt");
	      
	      Connection conn = null;
	      PreparedStatement pstmt = null;
	      ResultSet rs = null;
	      int selectCnt = 0;
	      
	      try {
	         conn = dataSource.getConnection();
	         String sql =  "SELECT COUNT(*) as cnt FROM mvc_product_tbl";
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
		
	// 상품목록
	@Override
	public List<ProductDTO> productList(int start, int end) {
		System.out.println("DAO - productList");
		
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
					+ 		  " FROM mvc_product_tbl "
					+ 		  " ORDER BY pdNo DESC"
					+ 		  " ) A "
					+ " ) "
					+ "WHERE rn BETWEEN ? AND ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			
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

	// ------------[고객 상품]---------------------
	// 상의 리스트
	@Override
	public List<ProductDTO> customerProductList1(int start, int end) {
		System.out.println("DAO - customerProductList1");
		
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
					+ 		  " FROM mvc_product_tbl "
					+ 		  " WHERE category = '상의' "
					+ 		  " ORDER BY pdNo DESC"
					+ 		  " ) A "
					+ " ) "
					+ "WHERE rn BETWEEN ? AND ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			
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
	
	// 하의 리스트
	@Override
	public List<ProductDTO> customerProductList2(int start, int end) {
		System.out.println("DAO - customerProductList2");
		
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
					+ 		  " FROM mvc_product_tbl "
					+ 		  " WHERE category = '하의' "
					+ 		  " ORDER BY pdNo DESC"
					+ 		  " ) A "
					+ " ) "
					+ "WHERE rn BETWEEN ? AND ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			
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
	
	// 신발 리스트
	@Override
	public List<ProductDTO> customerProductList3(int start, int end) {
		System.out.println("DAO - customerProductList3");
		
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
					+ 		  " FROM mvc_product_tbl "
					+ 		  " WHERE category = '신발' "
					+ 		  " ORDER BY pdNo DESC"
					+ 		  " ) A "
					+ " ) "
					+ "WHERE rn BETWEEN ? AND ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			
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

	@Override
	public List<ProductDTO> customerNewProductList(int start, int end) {
		System.out.println("DAO - customerNewProductList");
		
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
					+ 		  " FROM mvc_product_tbl "
					+ 		  " ORDER BY indate DESC"
					+ 		  " ) A "
					+ " ) "
					+ "WHERE rn BETWEEN ? AND ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			
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
	
	
	// 상의 상품 갯수
	@Override
	public int customerProductCnt1() {
		System.out.println("ProductDAO - customerProductCnt1");
	      
	      Connection conn = null;
	      PreparedStatement pstmt = null;
	      ResultSet rs = null;
	      int selectCnt = 0;
	      
	      try {
	         conn = dataSource.getConnection();
	         String sql =  "SELECT COUNT(*) as cnt FROM mvc_product_tbl WHERE category = '상의' ";
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

	// 하의 상품 갯수
	@Override
	public int customerProductCnt2() {
		System.out.println("ProductDAO - customerProductCnt2");
	      
	      Connection conn = null;
	      PreparedStatement pstmt = null;
	      ResultSet rs = null;
	      int selectCnt = 0;
	      
	      try {
	         conn = dataSource.getConnection();
	         String sql =  "SELECT COUNT(*) as cnt FROM mvc_product_tbl WHERE category = '하의' ";
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

	// 신발 상품 갯수
	@Override
	public int customerProductCnt3() {
		System.out.println("ProductDAO - customerProductCnt3");
	      
	      Connection conn = null;
	      PreparedStatement pstmt = null;
	      ResultSet rs = null;
	      int selectCnt = 0;
	      
	      try {
	         conn = dataSource.getConnection();
	         String sql =  "SELECT COUNT(*) as cnt FROM mvc_product_tbl WHERE category = '신발' ";
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

	// 신상품 상품 갯수
	@Override
	public int customerNewProductCnt() {
		System.out.println("ProductDAO - customerNewProductCnt");
	      
	      Connection conn = null;
	      PreparedStatement pstmt = null;
	      ResultSet rs = null;
	      int selectCnt = 0;
	      
	      try {
	         conn = dataSource.getConnection();
	         String sql =  "SELECT COUNT(*) as cnt FROM mvc_product_tbl ";
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

	// -------------- [장바구니] -------------------
	// 장바구니 추가 처리
	@Override
	public int basketInsert(BasketDTO dto) {
		System.out.println("ProductDAOImpl - basketInsert");
		
		int insertCnt = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			
			conn = dataSource.getConnection();
			String sql = "INSERT INTO mvc_basket_tbl(bkNo,pdNo,pdName,pdImg, id,brand,price,quantity) "
					+ " VALUES ((SELECT NVL(MAX(bkNo) + 1, 1) FROM mvc_basket_tbl), ?, ?, ?, ?, ?,?,?) ";
					
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,dto.getPdNo());
			pstmt.setString(2,dto.getPdName());
			pstmt.setString(3,dto.getPdImg());
			pstmt.setString(4,dto.getId());
			pstmt.setString(5,dto.getBrand());
			pstmt.setInt(6,dto.getPrice());
			pstmt.setInt(7,dto.getQuantity());
			
			insertCnt = pstmt.executeUpdate();
			System.out.println("ProductDAOImpl insertCnt" + insertCnt);
			
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

	// 장바구니 목록
	@Override
	public List<BasketDTO> basketList(String id) {
		System.out.println("DAO - basketList");
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<BasketDTO> list = null;
		
		try {
			conn = dataSource.getConnection();
			String sql = "SELECT "
					+  		  "  bkNo, pdNo, pdName, pdImg, id, brand, price, quantity"
					+ 		  " FROM mvc_basket_tbl "
					+ 		  " WHERE id = ? "
					+ 		  " ORDER BY bkNo DESC";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				// 1.list 생성
				list = new ArrayList<BasketDTO>();
				
				do {
					// 2.dto 생성
					BasketDTO dto = new BasketDTO();
					// 3.dto에 rs게시글정보를 담는다.
					dto.setBkNo(rs.getInt("bkNo"));
					dto.setPdNo(rs.getInt("pdNo"));
					dto.setPdName(rs.getString("pdName"));
					dto.setPdImg(rs.getString("pdImg"));
					dto.setId(rs.getString("id"));
					dto.setBrand(rs.getString("brand"));
					dto.setPrice(rs.getInt("price"));
					dto.setQuantity(rs.getInt("quantity"));
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

	// 장바구니 상품 삭제
	@Override
	public int baksetDelete(int bkNo) {
		System.out.println("ProductDAOImpl - baksetDelete");
		
		int insertCnt = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			
			conn = dataSource.getConnection();
			String sql = "DELETE FROM mvc_basket_tbl WHERE bkNo = ?";
					
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bkNo);
			
			insertCnt = pstmt.executeUpdate();
			System.out.println("ProductDAOImpl insertCnt" + insertCnt);
			
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

	// 장바구니 구매 처리1 - 장바구니 체크 목록 가져오기
	@Override
	public int baksetGet(List<String> list) {
		System.out.println("ProductDAOImpl - baksetGet");
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int insertCnt=0;
		int deleteCnt = 0;
		/* List<String> arr = new ArrayList<String>(); */
		/* arr = list; */
		List<OrderDTO> dtoList = null;
			
		for(int i=0; i<list.size(); i++) {		// for문 시작
		
			OrderDTO dto = new OrderDTO();
			
		try {
			
			conn = dataSource.getConnection();
			String sql = "SELECT "
					+  		  "  bkNo, pdNo, pdName, pdImg, id, brand, price, quantity"
					+ 		  " FROM mvc_basket_tbl "
					+ 		  " WHERE bkNo = ? "
					+ 		  " ORDER BY bkNo DESC";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, list.get(i));
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				// 1.list 생성
				dtoList = new ArrayList<OrderDTO>();
				
				do {
					// 2.dto 생성
					
					// 3.dto에 rs게시글정보를 담는다.
					dto.setOrNo(rs.getInt("bkNo"));
					dto.setPdNo(rs.getInt("pdNo"));
					dto.setPdName(rs.getString("pdName"));
					dto.setPdImg(rs.getString("pdImg"));
					dto.setId(rs.getString("id"));
					dto.setBrand(rs.getString("brand"));
					dto.setPrice(rs.getInt("price"));
					dto.setQuantity(rs.getInt("quantity"));
					// 4.list에 dto를 추가한다.
					dtoList.add(dto);
					
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
		
		
		try {
			
			conn = dataSource.getConnection();
			String sql = "INSERT INTO mvc_order_tbl(orNo,pdNo,pdName,pdImg, id,brand,price,quantity) "
					+ " VALUES ((SELECT NVL(MAX(orNo) + 1, 1) FROM mvc_order_tbl), ?, ?, ?, ?, ?,?,?) ";
					
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,dto.getPdNo());
			pstmt.setString(2,dto.getPdName());
			pstmt.setString(3,dto.getPdImg());
			pstmt.setString(4,dto.getId());
			pstmt.setString(5,dto.getBrand());
			pstmt.setInt(6,dto.getPrice());
			pstmt.setInt(7,dto.getQuantity());
			
			insertCnt = pstmt.executeUpdate();
			System.out.println("ProductDAOImpl 주문목록 추가작업 : " + insertCnt);
			
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
		}	// 주문목록 추가 작업 끝
		
		
		try {	// 장바구니 삭제 시작
			
			conn = dataSource.getConnection();
			String sql = "DELETE FROM mvc_basket_tbl WHERE bkNo = ?";
					
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, dto.getOrNo());
			
			deleteCnt = pstmt.executeUpdate();
			System.out.println("ProductDAOImpl 장바구니 삭제 : " + insertCnt);
			
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
		} 	// 장바구니 삭제 끝
		
		
		}	// for문 끝
		
		// 5. list 리턴
		return deleteCnt;
	}

	// 고객 주문내역 갯수
	@Override
	public int customerOrderCnt(String id) {
		System.out.println("ProductDAO - customerOrderCnt");
	      
	      Connection conn = null;
	      PreparedStatement pstmt = null;
	      ResultSet rs = null;
	      int selectCnt = 0;
	      
	      try {
	         conn = dataSource.getConnection();
	         String sql =  "SELECT COUNT(*) as cnt FROM mvc_order_tbl WHERE id = ?";
	         pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, id);
				
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
	
	
	// 고객 주문 내역
	@Override
	public List<OrderDTO> customerOrderList(int start, int end, String id) {
		System.out.println("DAO - customerOrderList");
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<OrderDTO> list = null;
		
		try {
			conn = dataSource.getConnection();
			String sql = "SELECT *"
					+ "FROM ( "
					+ "			SELECT A.* , rownum as rn"
					+ "			FROM ("
					+  		  " SELECT orNo, pdNo, pdName, pdImg, id, brand, price, quantity, orState, orDate"
					+ 		  " FROM mvc_order_tbl "
					+ 		  " WHERE id = ? "
					+ 		  " ORDER BY orNo DESC"
					+ "			) A "
					+ " ) "
					+ " WHERE rn BETWEEN ? AND ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setInt(2, start);
			pstmt.setInt(3, end);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				// 1.list 생성
				list = new ArrayList<OrderDTO>();
				
				do {
					// 2.dto 생성
					OrderDTO dto = new OrderDTO();
					// 3.dto에 rs게시글정보를 담는다.
					dto.setOrNo(rs.getInt("orNo"));
					dto.setPdNo(rs.getInt("pdNo"));
					dto.setPdName(rs.getString("pdName"));
					dto.setPdImg(rs.getString("pdImg"));
					dto.setId(rs.getString("id"));
					dto.setBrand(rs.getString("brand"));
					dto.setPrice(rs.getInt("price"));
					dto.setQuantity(rs.getInt("quantity"));
					dto.setOrState(rs.getString("orState"));
					dto.setOrDate(rs.getDate("orDate"));
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

	// 주문목록 배송준비중 취소하기..
	@Override
	public int customerPreparedOrederDelete(String orNo) {
		System.out.println("ProductDAOImpl - customerPreparedOrederDelete");
		
		int insertCnt = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			
			conn = dataSource.getConnection();
			String sql = "DELETE FROM mvc_order_tbl WHERE orNo = ?";
					
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, orNo);
			
			insertCnt = pstmt.executeUpdate();
			System.out.println("ProductDAOImpl insertCnt" + insertCnt);
			
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

	// 고객 배송완료 > 환불하기
	@Override
	public int customerOrederRefund(String orNo) {
		System.out.println("ProductDAOImpl - customerOrederRefund");
		
		int insertCnt = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			
			conn = dataSource.getConnection();
			String sql = "UPDATE mvc_order_tbl SET orState = '환불요청' WHERE orNo = ? ";
					
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, orNo);
			
			insertCnt = pstmt.executeUpdate();
			System.out.println("ProductDAOImpl insertCnt" + insertCnt);
			
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
	// ---------------- [관리자 주문관리] -----------------
	// 주문 목록
	@Override
	public List<OrderDTO> managerOrderList(int start, int end) {
		System.out.println("DAO - managerOrderList");
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<OrderDTO> list = null;
		
		try {
			conn = dataSource.getConnection();
			String sql = "SELECT *"
					+ "FROM ( "
					+ "			SELECT A.* , rownum as rn"
					+ "			FROM ("
					+  		  " SELECT orNo, pdNo, pdName, pdImg, id, brand, price, quantity, orState, orDate"
					+ 		  " FROM mvc_order_tbl "
					+ 		  "	WHERE orState = '배송준비중' "
					+ 		  " ORDER BY orNo DESC"
					+ "			) A "
					+ " ) "
					+ " WHERE rn BETWEEN ? AND ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				// 1.list 생성
				list = new ArrayList<OrderDTO>();
				
				do {
					// 2.dto 생성
					OrderDTO dto = new OrderDTO();
					// 3.dto에 rs게시글정보를 담는다.
					dto.setOrNo(rs.getInt("orNo"));
					dto.setPdNo(rs.getInt("pdNo"));
					dto.setPdName(rs.getString("pdName"));
					dto.setPdImg(rs.getString("pdImg"));
					dto.setId(rs.getString("id"));
					dto.setBrand(rs.getString("brand"));
					dto.setPrice(rs.getInt("price"));
					dto.setQuantity(rs.getInt("quantity"));
					dto.setOrState(rs.getString("orState"));
					dto.setOrDate(rs.getDate("orDate"));
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

	// 주문목록 갯수
	@Override
	public int managerOrderCnt() {
		System.out.println("ProductDAO - managerOrderCnt");
	      
	      Connection conn = null;
	      PreparedStatement pstmt = null;
	      ResultSet rs = null;
	      int selectCnt = 0;
	      
	      try {
	         conn = dataSource.getConnection();
	         String sql =  "SELECT COUNT(*) as cnt FROM mvc_order_tbl WHERE orState = '배송준비중' ";
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

	// 관리자 주문 취소
	@Override
	public int managerOrderCancel(String orNo) {
		System.out.println("ProductDAOImpl - managerOrderCancel");
		
		int insertCnt = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			
			conn = dataSource.getConnection();
			String sql = "UPDATE mvc_order_tbl SET orState = '주문취소' WHERE orNo = ?";
					
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, orNo);
			
			insertCnt = pstmt.executeUpdate();
			System.out.println("ProductDAOImpl insertCnt" + insertCnt);
			
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

	// 관리자 주문 승인
	@Override
	public int managerOrederApproval(String orNo) {
		System.out.println("ProductDAOImpl - managerOrederApproval");
		
		int insertCnt = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		int updateCnt = 0;
		
		try {
			
			conn = dataSource.getConnection();
			String sql = "UPDATE mvc_order_tbl SET orState = '배송완료' WHERE orNo = ?";
					
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, orNo);
			
			insertCnt = pstmt.executeUpdate();
			System.out.println("ProductDAOImpl insertCnt" + insertCnt);
			
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
		
		
		try {
			
			conn = dataSource.getConnection();
			String sql = "UPDATE mvc_product_tbl SET quantity = quantity - (SELECT quantity"
					+ "														FROM mvc_order_tbl"
					+ "														 WHERE orNo = ?  ) "
					+ "	WHERE pdNo = (SELECT pdNo "
					+ " FROM mvc_order_tbl "
					+ " WHERE orNo = ?  ) ";
					
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, orNo);
			pstmt.setString(2, orNo);
			
			updateCnt = pstmt.executeUpdate();
			
			System.out.println("ProductDAOImpl updateCnt" + updateCnt);
			
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

	// 주문 승인 목록
	@Override
	public List<OrderDTO> managerOrderApprovalList(int start, int end) {
		System.out.println("DAO - managerOrderApprovalList");
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<OrderDTO> list = null;
		
		try {
			conn = dataSource.getConnection();
			String sql = "SELECT *"
					+ "FROM ( "
					+ "			SELECT A.* , rownum as rn"
					+ "			FROM ("
					+  		  " SELECT orNo, pdNo, pdName, pdImg, id, brand, price, quantity, orState, orDate"
					+ 		  " FROM mvc_order_tbl "
					+ 		  "	WHERE orState = '배송완료' "
					+ 		  " ORDER BY orNo DESC"
					+ "			) A "
					+ " ) "
					+ " WHERE rn BETWEEN ? AND ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				// 1.list 생성
				list = new ArrayList<OrderDTO>();
				
				do {
					// 2.dto 생성
					OrderDTO dto = new OrderDTO();
					// 3.dto에 rs게시글정보를 담는다.
					dto.setOrNo(rs.getInt("orNo"));
					dto.setPdNo(rs.getInt("pdNo"));
					dto.setPdName(rs.getString("pdName"));
					dto.setPdImg(rs.getString("pdImg"));
					dto.setId(rs.getString("id"));
					dto.setBrand(rs.getString("brand"));
					dto.setPrice(rs.getInt("price"));
					dto.setQuantity(rs.getInt("quantity"));
					dto.setOrState(rs.getString("orState"));
					dto.setOrDate(rs.getDate("orDate"));
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
	
	// 주문 승인목록 갯수
	@Override
	public int managerOrderApprovalCnt() {
		System.out.println("ProductDAO - managerOrderApprovalCnt");
	      
	      Connection conn = null;
	      PreparedStatement pstmt = null;
	      ResultSet rs = null;
	      int selectCnt = 0;
	      
	      try {
	         conn = dataSource.getConnection();
	         String sql =  "SELECT COUNT(*) as cnt FROM mvc_order_tbl WHERE orState = '배송완료' ";
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

	// --- 환불 ---
	// 환불요청 목록
	@Override
	public List<OrderDTO> refundRequestList(int start, int end) {
		System.out.println("DAO - refundRequestList");
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<OrderDTO> list = null;
		
		try {
			conn = dataSource.getConnection();
			String sql = "SELECT *"
					+ "FROM ( "
					+ "			SELECT A.* , rownum as rn"
					+ "			FROM ("
					+  		  " SELECT orNo, pdNo, pdName, pdImg, id, brand, price, quantity, orState, orDate"
					+ 		  " FROM mvc_order_tbl "
					+ 		  "	WHERE orState = '환불요청' "
					+ 		  " ORDER BY orNo DESC"
					+ "			) A "
					+ " ) "
					+ " WHERE rn BETWEEN ? AND ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				// 1.list 생성
				list = new ArrayList<OrderDTO>();
				
				do {
					// 2.dto 생성
					OrderDTO dto = new OrderDTO();
					// 3.dto에 rs게시글정보를 담는다.
					dto.setOrNo(rs.getInt("orNo"));
					dto.setPdNo(rs.getInt("pdNo"));
					dto.setPdName(rs.getString("pdName"));
					dto.setPdImg(rs.getString("pdImg"));
					dto.setId(rs.getString("id"));
					dto.setBrand(rs.getString("brand"));
					dto.setPrice(rs.getInt("price"));
					dto.setQuantity(rs.getInt("quantity"));
					dto.setOrState(rs.getString("orState"));
					dto.setOrDate(rs.getDate("orDate"));
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

	// 환불요청 갯수
	@Override
	public int refundRequestCnt() {
		System.out.println("ProductDAO - refundRequestCnt");
	      
	      Connection conn = null;
	      PreparedStatement pstmt = null;
	      ResultSet rs = null;
	      int selectCnt = 0;
	      
	      try {
	         conn = dataSource.getConnection();
	         String sql =  "SELECT COUNT(*) as cnt FROM mvc_order_tbl WHERE orState = '환불요청' ";
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

	// 환불 취소
	@Override
	public int refundCancel(String orNo) {
		System.out.println("ProductDAOImpl - refundCancel");
		
		int insertCnt = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			
			conn = dataSource.getConnection();
			String sql = "UPDATE mvc_order_tbl SET orState = '환불취소' WHERE orNo = ?";
					
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, orNo);
			
			insertCnt = pstmt.executeUpdate();
			System.out.println("ProductDAOImpl insertCnt" + insertCnt);
			
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

	// 환불 승인
	@Override
	public int refundApproval(String orNo) {
		System.out.println("ProductDAOImpl - refundApproval");
		
		int insertCnt = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		int updateCnt = 0;
		try {
			
			conn = dataSource.getConnection();
			String sql = "UPDATE mvc_order_tbl SET orState = '환불완료' WHERE orNo = ?";
					
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, orNo);
			
			insertCnt = pstmt.executeUpdate();
			System.out.println("ProductDAOImpl insertCnt" + insertCnt);
			
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
		
		try {
			
			conn = dataSource.getConnection();
			String sql = "UPDATE mvc_product_tbl SET quantity = quantity + (SELECT quantity"
					+ "														FROM mvc_order_tbl"
					+ "														 WHERE orNo = ?  ) "
					+ "	WHERE pdNo = (SELECT pdNo "
					+ " FROM mvc_order_tbl "
					+ " WHERE orNo = ?  ) ";
					
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, orNo);
			pstmt.setString(2, orNo);
			
			updateCnt = pstmt.executeUpdate();
			
			System.out.println("ProductDAOImpl updateCnt" + updateCnt);
			
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

	// 환불승인 목록
	@Override
	public List<OrderDTO> refundApprovalList(int start, int end) {
		System.out.println("DAO - refundApprovalList");
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<OrderDTO> list = null;
		
		try {
			conn = dataSource.getConnection();
			String sql = "SELECT *"
					+ "FROM ( "
					+ "			SELECT A.* , rownum as rn"
					+ "			FROM ("
					+  		  " SELECT orNo, pdNo, pdName, pdImg, id, brand, price, quantity, orState, orDate"
					+ 		  " FROM mvc_order_tbl "
					+ 		  "	WHERE orState = '환불완료' "
					+ 		  " ORDER BY orNo DESC"
					+ "			) A "
					+ " ) "
					+ " WHERE rn BETWEEN ? AND ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				// 1.list 생성
				list = new ArrayList<OrderDTO>();
				
				do {
					// 2.dto 생성
					OrderDTO dto = new OrderDTO();
					// 3.dto에 rs게시글정보를 담는다.
					dto.setOrNo(rs.getInt("orNo"));
					dto.setPdNo(rs.getInt("pdNo"));
					dto.setPdName(rs.getString("pdName"));
					dto.setPdImg(rs.getString("pdImg"));
					dto.setId(rs.getString("id"));
					dto.setBrand(rs.getString("brand"));
					dto.setPrice(rs.getInt("price"));
					dto.setQuantity(rs.getInt("quantity"));
					dto.setOrState(rs.getString("orState"));
					dto.setOrDate(rs.getDate("orDate"));
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

	// 환불 승인 목록 갯수
	@Override
	public int refundApprovalCnt() {
		System.out.println("ProductDAO - refundApprovalCnt");
	      
	      Connection conn = null;
	      PreparedStatement pstmt = null;
	      ResultSet rs = null;
	      int selectCnt = 0;
	      
	      try {
	         conn = dataSource.getConnection();
	         String sql =  "SELECT COUNT(*) as cnt FROM mvc_order_tbl WHERE orState = '환불완료' ";
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

	// 환불취소 목록
	@Override
	public List<OrderDTO> refundCancelList(int start, int end) {
		System.out.println("DAO - refundCancelList");
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<OrderDTO> list = null;
		
		try {
			conn = dataSource.getConnection();
			String sql = "SELECT *"
					+ "FROM ( "
					+ "			SELECT A.* , rownum as rn"
					+ "			FROM ("
					+  		  " SELECT orNo, pdNo, pdName, pdImg, id, brand, price, quantity, orState, orDate"
					+ 		  " FROM mvc_order_tbl "
					+ 		  "	WHERE orState = '환불취소' "
					+ 		  " ORDER BY orNo DESC"
					+ "			) A "
					+ " ) "
					+ " WHERE rn BETWEEN ? AND ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				// 1.list 생성
				list = new ArrayList<OrderDTO>();
				
				do {
					// 2.dto 생성
					OrderDTO dto = new OrderDTO();
					// 3.dto에 rs게시글정보를 담는다.
					dto.setOrNo(rs.getInt("orNo"));
					dto.setPdNo(rs.getInt("pdNo"));
					dto.setPdName(rs.getString("pdName"));
					dto.setPdImg(rs.getString("pdImg"));
					dto.setId(rs.getString("id"));
					dto.setBrand(rs.getString("brand"));
					dto.setPrice(rs.getInt("price"));
					dto.setQuantity(rs.getInt("quantity"));
					dto.setOrState(rs.getString("orState"));
					dto.setOrDate(rs.getDate("orDate"));
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

	// 환불 취소 목록 갯수
	@Override
	public int refundCancelCnt() {
		System.out.println("ProductDAO - refundCancelCnt");
	      
	      Connection conn = null;
	      PreparedStatement pstmt = null;
	      ResultSet rs = null;
	      int selectCnt = 0;
	      
	      try {
	         conn = dataSource.getConnection();
	         String sql =  "SELECT COUNT(*) as cnt FROM mvc_order_tbl WHERE orState = '환불취소' ";
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

	// 결산
	@Override
	public TotalMoneyDTO totalMoney() {
		System.out.println("DAO - TotalMoneyDTO");
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		TotalMoneyDTO dto = new TotalMoneyDTO();
		
		try {
			conn = dataSource.getConnection();
			String sql = "SELECT SUM(o.price) AS price FROM mvc_order_tbl o "
					+ "               JOIN mvc_product_tbl p "
					+ "                 ON o.pdNo = p.pdNo "
					+ "               WHERE o.orState IN ('배송완료', '환불요청', '환불취소')";
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
					dto.setTotalMoney(rs.getInt("price"));
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
		// 상의 결산
		try {
			conn = dataSource.getConnection();
			String sql = "SELECT SUM(o.price) AS price FROM mvc_order_tbl o "
					+ "               JOIN mvc_product_tbl p "
					+ "                 ON o.pdNo = p.pdNo "
					+ "               WHERE o.orState IN ('배송완료', '환불요청', '환불취소')"
					+ "					 AND p.category = '상의'";
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
					dto.setCategory1Money(rs.getInt("price"));
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
		
		// 하의 결산
		try {
			conn = dataSource.getConnection();
			String sql = "SELECT SUM(o.price) AS price FROM mvc_order_tbl o "
					+ "               JOIN mvc_product_tbl p "
					+ "                 ON o.pdNo = p.pdNo "
					+ "               WHERE o.orState IN ('배송완료', '환불요청', '환불취소')"
					+ "					 AND p.category = '하의'";
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
					dto.setCategory2Money(rs.getInt("price"));
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
		
		// 신발 결산
		try {
			conn = dataSource.getConnection();
			String sql = "SELECT SUM(o.price) AS price FROM mvc_order_tbl o"
					+ "               JOIN mvc_product_tbl p "
					+ "                 ON o.pdNo = p.pdNo "
					+ "               WHERE o.orState IN ('배송완료', '환불요청', '환불취소')"
					+ "					 AND p.category = '신발'";
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
					dto.setCategory3Money(rs.getInt("price"));
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

	// 고객 바로구매
	@Override
	public int nowBuy(BasketDTO dto) {
		System.out.println("ProductDAOImpl - nowBuy");
		
		int insertCnt = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			
			conn = dataSource.getConnection();
			String sql = "INSERT INTO mvc_order_tbl(orNo,pdNo,pdName,pdImg, id,brand,price,quantity) "
					+ " VALUES ((SELECT NVL(MAX(orNo) + 1, 1) FROM mvc_order_tbl), ?, ?, ?, ?, ?,?,?) ";
					
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,dto.getPdNo());
			pstmt.setString(2,dto.getPdName());
			pstmt.setString(3,dto.getPdImg());
			pstmt.setString(4,dto.getId());
			pstmt.setString(5,dto.getBrand());
			pstmt.setInt(6,dto.getPrice());
			pstmt.setInt(7,dto.getQuantity());
			
			insertCnt = pstmt.executeUpdate();
			System.out.println("ProductDAOImpl insertCnt" + insertCnt);
			
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

	
	
}
