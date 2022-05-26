package pj.mvc.jsp.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pj.mvc.jsp.service.ProductServiceImpl;
import pj.mvc.jsp.util.ImageUploaderHandler;

/*
 * location => 파일저장 경로
 * 	 파일은 Part의 write 메서드가 호출될 때 저장
 *   기본값은 해당 자바가 실행되는 temp 폴더 
 *   
 * fileSizeThreshold
 * => 입력하지 않을 경우 기본값은 0
 * 	  업로드되는 이미지가 여기서 설정한 용량을 초과할 경우 디렉토리에 임시로 파일을 복사
 * 
 * maxFileSize
 * => 파일 1개당 최대 파일 크기, 기본값은 -1(크기 제한 없음)
 * 
 * maxRequestSize
 * => HTTP 요청의 최대 크기 값, 기본값은 -1(크기 제한 없음)
 * 
 * */
@WebServlet("*.st")
@MultipartConfig(location="D:\\Dev105\\workspace\\jsp_pj_marketyezzang\\src\\main\\webapp\\resources\\upload",
fileSizeThreshold=1024*1024, maxFileSize=1024*1024*5, maxRequestSize=1024*1024*5*5)
public class StockController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String IMG_UPLOAD_DIR="D:\\Dev105\\workspace\\jsp_pj_marketyezzang\\src\\main\\webapp\\resources\\upload";
	// 파일 저장될 경로 설정
	private ImageUploaderHandler uploader;   
   

	
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
		ProductServiceImpl service = new ProductServiceImpl();
		
		
		String uri = req.getRequestURI();
		String contextPath = req.getContextPath();
		String url = uri.substring(contextPath.length());
		
		// 2단계. 요청분석
		// 상품목록
		if(url.equals("/*.st") || url.equals("/productList.st")) {	
			System.out.println("[url] ==> productList.st");
			
			service.productList(req, res);
			viewPage = "/manager/stock/productList.jsp";
			
			RequestDispatcher dispatcher = req.getRequestDispatcher(viewPage);
			dispatcher.forward(req, res);	
		
		// -------------------- product -----------------------
		// 상품등록
		} else if(url.equals("/productAdd.st")){
			System.out.println("[url] ==> productAdd.st");
			
			viewPage = "/manager/stock/productAdd.jsp";
			
			RequestDispatcher dispatcher = req.getRequestDispatcher(viewPage);
			dispatcher.forward(req, res);	
		// 상품등록 처리
		} else if(url.equals("/productAddAction.st")){	
			System.out.println("[url] ==> productAddAction.st");
			String contentType = req.getContentType();
			System.out.println("contenttype : " + contentType);
			//클라이언트가 요청 정보를 전송할 때 사용한 컨텐트의 타입을 구한다. 
			// toLowerCase : 대상 문자열을 모두 소문자로 변환한다.
			// startWith : ~로 시작하는
			if(contentType != null && contentType.toLowerCase().startsWith("multipart/")) {
				uploader = new ImageUploaderHandler();	// 핸들러 객체 생성 (호출)
				uploader.setUploadPath(IMG_UPLOAD_DIR);	// img 경로 uploader 멤버변수에 insert
				uploader.imageUpload(req, res);
				
			}
			service.productAddACtion(req, res);
			
			viewPage = "/manager/stock/productAddAction.jsp";
			RequestDispatcher dispatcher = req.getRequestDispatcher(viewPage);
			dispatcher.forward(req, res);	
		
		// 상품 상세
		} else if(url.equals("/productDetail.st")){	
			System.out.println("[url] ==> productDetail.st");
			
			service.productDetail(req, res);
			
			viewPage = "/manager/stock/productDetail.jsp";
			RequestDispatcher dispatcher = req.getRequestDispatcher(viewPage);
			dispatcher.forward(req, res);	
		// 상품 수정	
		} else if(url.equals("/productUpdateAction.st")){	
			System.out.println("[url] ==> productUpdateAction.st");
			String contentType = req.getContentType();
			System.out.println("contenttype : " + contentType);
			//클라이언트가 요청 정보를 전송할 때 사용한 컨텐트의 타입을 구한다. 
			// toLowerCase : 대상 문자열을 모두 소문자로 변환한다.
			// startWith : ~로 시작하는
			if(contentType != null && contentType.toLowerCase().startsWith("multipart/")) {
				uploader = new ImageUploaderHandler();	// 핸들러 객체 생성 (호출)
				uploader.setUploadPath(IMG_UPLOAD_DIR);	// img 경로 uploader 멤버변수에 insert
				uploader.imageUpload(req, res);
				
			}
			service.productUpdateACtion(req, res);
			
			viewPage = "/manager/stock/productUpdateAction.jsp";
			
			RequestDispatcher dispatcher = req.getRequestDispatcher(viewPage);
			dispatcher.forward(req, res);	
			
		// 제품삭제
		} else if(url.equals("/productDeleteAction.st")) {
			System.out.println("[url] ==> productDeleteAction.st");
			
			service.productDeleteACtion(req, res);
			viewPage = "/manager/stock/productDeleteAction.jsp";
			
			RequestDispatcher dispatcher = req.getRequestDispatcher(viewPage);
			dispatcher.forward(req, res);
		}
		// -------------------- [주문관리] -----------------------			
		// 주문목록
		else if(url.equals("/productOrderList.st")) {
			System.out.println("[url] ==> productOrderList.st");
			
			service.managerOrderList(req, res);
			
			viewPage = "/manager/order/productOrderList.jsp";
			
			RequestDispatcher dispatcher = req.getRequestDispatcher(viewPage);
			dispatcher.forward(req, res);
		}
		// 주문 취소 버튼
		else if(url.equals("/productOrderCancel.st")) {
			System.out.println("[url] ==> productOrderCancel.st");
			
			service.managerOrderCancel(req, res);
			
			viewPage = req.getContextPath() + "/productOrderList.st";	
			res.sendRedirect(viewPage);		
		}
		
		// 주문 승인 버튼
		else if(url.equals("/productOrderApproval.st")) {
			System.out.println("[url] ==> productOrderApproval.st");
			
			service.managerOrderApproval(req, res);
			
			viewPage = req.getContextPath() + "/productOrderList.st";	
			res.sendRedirect(viewPage);		
		}
		
		// 주문 승인 목록
		else if(url.equals("/productOrderApprovalList.st")) {
			System.out.println("[url] ==> productOrderApprovalList.st");
			
			service.managerOrderApprovalList(req, res);
			
			viewPage = "/manager/order/productOrderApproval.jsp";
			
			RequestDispatcher dispatcher = req.getRequestDispatcher(viewPage);
			dispatcher.forward(req, res);
		}
		// ---------- [환불] --------- 
		// 환불 요청 목록
		else if(url.equals("/refundRequestList.st")) {
			System.out.println("[url] ==> refundRequestList.st");
			
			service.refundRequsetList(req, res);
			
			viewPage = "/manager/refund/refundRequestList.jsp";
			
			RequestDispatcher dispatcher = req.getRequestDispatcher(viewPage);
			dispatcher.forward(req, res);
		}
		
		// 환불 승인 목록
		else if(url.equals("/refundApprovalList.st")) {
			System.out.println("[url] ==> refundApprovalList.st");
			
			service.refundApprovalList(req, res);
			
			viewPage = "/manager/refund/refundApprovalList.jsp";
			
			RequestDispatcher dispatcher = req.getRequestDispatcher(viewPage);
			dispatcher.forward(req, res);
		}
		
		// 환불 취소 목록
		else if(url.equals("/refundCancelList.st")) {
			System.out.println("[url] ==> refundCancelList.st");
			
			service.refundCancelList(req, res);
			
			viewPage = "/manager/refund/refundCancelList.jsp";
			
			RequestDispatcher dispatcher = req.getRequestDispatcher(viewPage);
			dispatcher.forward(req, res);
		}
		// 환불 취소 버튼
		else if(url.equals("/refundCancel.st")) {
			System.out.println("[url] ==> refundCancel.st");
			
			service.refundCancel(req, res);
			
			viewPage = req.getContextPath() + "/refundRequestList.st";	
			res.sendRedirect(viewPage);		
		}
		
		// 환불 승인 버튼
		else if(url.equals("/refundApproval.st")) {
			System.out.println("[url] ==> refundApproval.st");
			
			service.refundApproval(req, res);
			
			viewPage = req.getContextPath() + "/refundRequestList.st";	
			res.sendRedirect(viewPage);		
		}
		// 결산
		else if(url.equals("/totalMoney.st")) {
			System.out.println("[url] ==> totalMoney.st");
			
			service.totalMoney(req, res);
			
			viewPage = "/manager/totalMoney/totalMoney.jsp";
			
			RequestDispatcher dispatcher = req.getRequestDispatcher(viewPage);
			dispatcher.forward(req, res);
		}
	}
}