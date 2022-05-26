package pj.mvc.jsp.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pj.mvc.jsp.service.CustomerServiceImpl;
import pj.mvc.jsp.service.ProductServiceImpl;

@WebServlet("*.do")
public class CustomerController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	CustomerServiceImpl service = new CustomerServiceImpl();
	ProductServiceImpl productService = new ProductServiceImpl();
	protected void doGet(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException {
		action(req, res);
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
				
		String uri = req.getRequestURI();
		String contextPath = req.getContextPath();
		String url = uri.substring(contextPath.length());
	
		// 첫페이지
		if(url.equals("/*.do") || url.equals("/main.do")) {
			System.out.println("[url] ==> main.do");
					
			viewPage = "index.jsp";
			RequestDispatcher dispatcher = req.getRequestDispatcher(viewPage);
			dispatcher.forward(req, res);
			//---------------------[로그인]----------------
			// 로그인 화면
		} else if(url.equals("/login.do")) {
			System.out.println("[url ==> login.do]");
			
			 req.setAttribute("selectCnt", 2);	// 환영합니다!!
			 viewPage = "customer/login/login.jsp";
			 RequestDispatcher dispatcher = req.getRequestDispatcher(viewPage);
				dispatcher.forward(req, res);
				
		// 로그인 처리
		} else if(url.equals("/loginAction.do")) {
			System.out.println("[url ==> loginAction.do]");
			
			service.loginAction(req, res);
			
			viewPage ="customer/login/loginAction.jsp";
			RequestDispatcher dispatcher = req.getRequestDispatcher(viewPage);
			dispatcher.forward(req, res);
		
		// 로그아웃 페이지
		} else if(url.equals("/logout.do")) {
			System.out.println("[url ==> logout.do]");
			
			req.getSession().invalidate();		// 일괄 세션 삭제
			// req.removeAttribute("세션명");	// 해당 세션만 삭제
			// req.removeAttribute("customerID");	// 해당 세션만 삭제
			// req.getSession().setAttribute("세션명",null);	//해당 세션만 삭제
			req.setAttribute("selectCnt", 2);	// 로그인으로 이동시 환영합니다.
			
			viewPage ="common/main.jsp";
			RequestDispatcher dispatcher = req.getRequestDispatcher(viewPage);
			dispatcher.forward(req, res);
		//---------------------[회원가입]----------------
		// 회원가입 화면		
		} else if(url.equals("/join.do")) {
			System.out.println("[url ==> join.do]");
					
			viewPage = "customer/join/join.jsp";
			RequestDispatcher dispatcher = req.getRequestDispatcher(viewPage);
			dispatcher.forward(req, res);
		
		
		// ID 중복확인 처리
		} else if(url.equals("/confirmIdAction.do")) {
			System.out.println("[url ==> confirmIdAction.do]");
				
			service.confirmIdAction(req, res);
			viewPage = "customer/join/confirmIdAction.jsp";
			RequestDispatcher dispatcher = req.getRequestDispatcher(viewPage);
			dispatcher.forward(req, res);
			
		// email 중복확인 처리	
		} else if(url.equals("/confirmEmailAction.do")) {
			System.out.println("[url ==> confirmEmailAction.do]");
				
			service.confirmEmailAction(req, res);
			viewPage = "customer/join/confirmEmailAction.jsp";	
			RequestDispatcher dispatcher = req.getRequestDispatcher(viewPage);
			dispatcher.forward(req, res);
			
		// 회원가입 처리		
		} else if(url.equals("/joinAction.do")) {
			System.out.println("[url ==> joinAction.do]");
					
			service.signInAction(req, res);
						
			viewPage = "customer/join/joinAction.jsp";
			RequestDispatcher dispatcher = req.getRequestDispatcher(viewPage);
			dispatcher.forward(req, res);
							 
		// 회원가입 성공
		} else if(url.equals("/mainSuccess.do")) {
			System.out.println("[url ==> mainSuccess.do]");
						
			int cnt = Integer.parseInt(req.getParameter("insertCnt"));
			req.setAttribute("selectCnt", cnt);
			System.out.println("cnt : " + cnt);
						
			viewPage = "customer/login/login.jsp";		
			RequestDispatcher dispatcher = req.getRequestDispatcher(viewPage);
			dispatcher.forward(req, res);
					
		// ------------- [회원수정] ------------------
		// 회원수정 - 인증화면
		} else if(url.equals("/modifyCustomer.do")) {
			System.out.println("[url ==> modifyCustomer.do]");
		
			viewPage = "customer/mypage/modifyCustomer.jsp";
			RequestDispatcher dispatcher = req.getRequestDispatcher(viewPage);
			dispatcher.forward(req, res);
			
		// 회원수정 - 상세페이지	
		} else if(url.equals("/modifyDetailAction.do")) {
			System.out.println("[url ==> modifyDetailAction.do]");
			
			service.modifyDetailAction(req, res);
			viewPage = "customer/mypage/modifyDetailAction.jsp";
			RequestDispatcher dispatcher = req.getRequestDispatcher(viewPage);
			dispatcher.forward(req, res);
		// 회원수정 처리	
		} else if(url.equals("/modifyCustomerAction.do")) {
			System.out.println("[url ==> modifyCustomerAction.do]");
			
			service.modifyCustomerAction(req, res);
			
			viewPage = "customer/mypage/modifyCustomerAction.jsp";
			RequestDispatcher dispatcher = req.getRequestDispatcher(viewPage);
			dispatcher.forward(req, res);
		
		// 회원탈퇴 - 인증화면
		} else if(url.equals("/deleteCustomer.do")) {
			System.out.println("[url ==> deleteCustomer.do]");
			
			viewPage = "customer/mypage/deleteCustomer.jsp";
			RequestDispatcher dispatcher = req.getRequestDispatcher(viewPage);
			dispatcher.forward(req, res);
		
		// 회원탈퇴 처리
		} else if(url.equals("/deleteCustomerAction.do")) {
			System.out.println("[url ==> deleteCustomerAction.do]");
			
			service.deleteCustomerAction(req, res);
			System.out.println("삭제 서비스 후 ");
			viewPage = "customer/mypage/deleteCustomerAction.jsp";
			RequestDispatcher dispatcher = req.getRequestDispatcher(viewPage);
			dispatcher.forward(req, res);
		}
		
		// ------------------[ 게시판 ]-----------------------
		// 게시판 목록
		else if(url.equals("/BoardList.do")) {
			System.out.println("[url ==> BoardList.do]");
			service.boardList(req,res);
			
			viewPage = "customer/board/customerBoardList.jsp";
			RequestDispatcher dispatcher = req.getRequestDispatcher(viewPage);
			dispatcher.forward(req, res);
		}
		
		// 글쓰기
		else if(url.equals("/board_insertAction.do")) {
			System.out.println("[url] ==> board_insertAction.do");
						
			service.boardInsert(req, res);
			viewPage = req.getContextPath() + "/BoardList.do";		// jsp_pj_105
			res.sendRedirect(viewPage);	
		}	
		
		// 상세페이지
		else if(url.equals("/board_detailAction.do")){
			System.out.println("[url] ==> board_detailAction.do");
			
			service.boardDetail(req, res);
			
			viewPage = "customer/board/board_detailAction.jsp";
			RequestDispatcher dispatcher = req.getRequestDispatcher(viewPage);
			dispatcher.forward(req, res);
		}	
		// -- 댓글 --
		// 댓글 작성
		else if(url.equals("/commentAdd.do")) { 	
			System.out.println("[url] ==> commentAdd.do");
			
			service.commentAdd(req, res);
		}
		
		// 댓글 조회	
		else if(url.equals("/commentList.do")) { 	
			System.out.println("[url] ==> commentList.do");
			
			service.commentList(req, res);
			
			 viewPage = "customer/board/board_commentList.jsp";
			 RequestDispatcher dispatcher = req.getRequestDispatcher(viewPage);
				dispatcher.forward(req, res);
		}
		// ------------------[ 상품 ]-----------------------
		// 상의 리스트
		else if(url.equals("/productList1.do")) { 	
			System.out.println("[url] ==> productList1.do");
			
			productService.customerProductList1(req, res);
			
			 viewPage = "customer/product/productList1.jsp";
			 RequestDispatcher dispatcher = req.getRequestDispatcher(viewPage);
				dispatcher.forward(req, res);
		}
		// 하의 리스트
		else if(url.equals("/productList2.do")) { 	
			System.out.println("[url] ==> productList2.do");
			
			productService.customerProductList2(req, res);
			
			 viewPage = "customer/product/productList2.jsp";
			 RequestDispatcher dispatcher = req.getRequestDispatcher(viewPage);
				dispatcher.forward(req, res);
		}
		// 신발 리스트
		else if(url.equals("/productList3.do")) { 	
			System.out.println("[url] ==> productList3.do");
			
			productService.customerProductList3(req, res);
			
			 viewPage = "customer/product/productList3.jsp";
			 RequestDispatcher dispatcher = req.getRequestDispatcher(viewPage);
				dispatcher.forward(req, res);
		}
		// 신상품 리스트
		else if(url.equals("/newProductList.do")) { 	
			System.out.println("[url] ==> newProductList.do");
			
			productService.customerNewProductList(req, res);
			
			 viewPage = "customer/product/newProductList.jsp";
			 RequestDispatcher dispatcher = req.getRequestDispatcher(viewPage);
				dispatcher.forward(req, res);
		}
		// 상품 상세
		 else if(url.equals("/customerProductDetail.do")){	
			System.out.println("[url] ==> customerProductDetail.do");
			
			productService.productDetail(req, res);
			
			viewPage = "customer/product/productDetail.jsp";
			RequestDispatcher dispatcher = req.getRequestDispatcher(viewPage);
			dispatcher.forward(req, res);	
		
		 }
		// ------------------ 바로구매 ------------------------
		// 바로구매
		 else if(url.equals("/customerNowBuy.do")){	
			System.out.println("[url] ==> customerNowBuy.do");
			
			productService.nowBuy(req, res);
				
		}
		
		// ------------------[ 장바구니 ]-----------------------
		// 장바구니 담기
		 else if(url.equals("/customerBasket.do")){	
			System.out.println("[url] ==> customerBasket.do");
			
			productService.basketAddACtion(req, res);
			
		}
		
		// 장바구니 페이지
		 else if(url.equals("/customerBasketList.do")){	
			System.out.println("[url] ==> customerBasketList.do");
			
			productService.basketDetail(req, res);
			service.basketAddress(req, res);
			
			viewPage = "customer/basket/customerBasket.jsp";
			RequestDispatcher dispatcher = req.getRequestDispatcher(viewPage);
			dispatcher.forward(req, res);
		}
		
		// 장바구니 상품 삭제
		 else if(url.equals("/customerBasketDelete.do")){	
			System.out.println("[url] ==> customerBasketDelete.do");
			
			productService.basketDelete(req, res);
			
			viewPage = req.getContextPath() + "/customerBasketList.do";
			res.sendRedirect(viewPage);	
		}
		// 장바구니 회원 주소 불러오기
		 else if(url.equals("/customerBasketAddress.do")){	
			System.out.println("[url] ==> customerBasketAddress.do");
			
			service.basketAddress(req, res);
		}
		
		// 장바구니 구매하기
		 else if(url.equals("/customerBasketBuy.do")){	
			System.out.println("[url] ==> customerBasketBuy.do");
			
			productService.basketBuy(req, res);
			
			viewPage = req.getContextPath() + "/customerBasketList.do";	
			res.sendRedirect(viewPage);	
		}
		
		// ------------------[ 주문내역 ]-----------------------
		// 고객 주문리스트
		 else if(url.equals("/customerOrderList.do")){	
			System.out.println("[url] ==> customerOrderList.do");
			
			productService.customerOrderList(req, res);
			
			viewPage = "customer/mypage/customerOrderList.jsp";
			RequestDispatcher dispatcher = req.getRequestDispatcher(viewPage);
			dispatcher.forward(req, res);	
		}
		
		// 고객 배송준비중 > 취소하기 : 주문리스트에서 삭제
		 else if(url.equals("/customerPreparedOrederDelete.do")){	
			System.out.println("[url] ==> customerPreparedOrederDelete.do");
			
			productService.customerPreparedOrederDelete(req, res);
			
			viewPage = req.getContextPath() + "/customerOrderList.do";	
			res.sendRedirect(viewPage);		
		}
		
		// 고객 배송완료 > 환불하기 : 주문리스트 상태 환불요청으로 변경
		 else if(url.equals("/customerOrederRefund.do")){	
			System.out.println("[url] ==> customerOrederRefund.do");
			
			productService.customerOrederRefund(req, res);
			
			viewPage = req.getContextPath() + "/customerOrderList.do";	
			res.sendRedirect(viewPage);		
		}
		
		// ----------- [검색] ------------------
		 else if(url.equals("/search.do")){	
			System.out.println("[url] ==> search.do");
			
			service.search(req, res);
			
			 viewPage = "customer/product/searchProductList.jsp";
			 RequestDispatcher dispatcher = req.getRequestDispatcher(viewPage);
				dispatcher.forward(req, res);
		}
	
	}
}
