package pj.mvc.jsp.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import page.CustomerOrderPaging;
import page.Paging;
import page.ProductPaging;
import pj.mvc.jsp.pj.dao.CustomerDAO;
import pj.mvc.jsp.pj.dao.CustomerDAOImpl;
import pj.mvc.jsp.pj.dao.ProductDAO;
import pj.mvc.jsp.pj.dao.ProductDAOImpl;
import pj.mvc.jsp.pj.dto.BasketDTO;
import pj.mvc.jsp.pj.dto.OrderDTO;
import pj.mvc.jsp.pj.dto.ProductDTO;
import pj.mvc.jsp.pj.dto.TotalMoneyDTO;

public class ProductServiceImpl implements ProductService{

	// 상품 리스트
	@Override
	public void productList(HttpServletRequest req, HttpServletResponse res) {
		System.out.println("서비스 - productList");
		// 3단계. 화면으로부터 입력받은 값을받는다.
		String pageNum = req.getParameter("pageNum");
		// 4단계. 싱글톤방식으로 dao 객체 생성, 다형성 적용
		ProductDAO dao = ProductDAOImpl.getInstance();
		
		// 5-1단계.
		Paging paging = new Paging(pageNum);
		
		
		// product 카운트
		int total = dao.productCnt();
		paging.setTotalCount(total);
		
		System.out.println("total : " + total);
		
		int start = paging.getStartRow();	// 페이지별 시작번호
		int end = paging.getEndRow();		// 페이지별 끝번호
		
		// 5-2단계.
		List<ProductDTO> list = dao.productList(start, end); 
		System.out.println("list : " + list);
		System.out.println("start : " + start);
		System.out.println("end : " + end);
		
		// 6단계. jsp로 처리결과 전달(request나 session으로 결과 전달)
		req.setAttribute("list", list);
		req.setAttribute("paging", paging);
	}
		
	// 상품추가 처리
	@Override
	public void productAddACtion(HttpServletRequest req, HttpServletResponse res) {
		System.out.println("서비스 - productAddACtion");
		
		// 3단계. 화면으로부터 입력받은 값을 dto에 받는다.
		ProductDTO dto = new ProductDTO();
		dto.setBrand(req.getParameter("brand"));
		dto.setPdName(req.getParameter("pdName"));
		// 플젝명/upload 해당 경로
		//  ImageUploaderHandler 클래스에서 setAttribute()로 넘겼으므로
		String p_img1 = "/jsp_pj_marketyezzang/resources/upload/" + (String)req.getAttribute("fileName");	// 플젝명/경로
		
		
		System.out.println("p_img1 : " + p_img1);
		
		dto.setPdImg(p_img1);
		dto.setCategory(req.getParameter("category"));
		dto.setContent(req.getParameter("content"));
		dto.setPrice(Integer.parseInt(req.getParameter("price")));
		dto.setQuantity(Integer.parseInt(req.getParameter("quantity")));
		dto.setStatus(req.getParameter("status"));
		
		// 4단계. 싱글톤방식으로 dao 객체 생성, 다형성 적용
		ProductDAO dao = ProductDAOImpl.getInstance();
		// 5단계.상품정보 인서트
		int updateCnt = dao.productInsert(dto);
		System.out.println("[ProductServiceImpl 5-상품insert] updateCnt : " + updateCnt);	// 정상 : 1
		// upload폴더를 새로고침하면 등록한 이미지가 들어온다.
		
		// 6단계. jsp로 처리결과 전달(request나 session으로 결과 전달)
		req.setAttribute("insertCnt", updateCnt);
		
	}
	
	// 상세페이지
	@Override
	public void productDetail(HttpServletRequest req, HttpServletResponse res) {
		// 3단계. 화면으로부터 입력받은 값을 받는다.
		int pdNo = Integer.parseInt(req.getParameter("pdNo"));
		int pageNum = Integer.parseInt(req.getParameter("pageNum"));
		// 4단계. 싱글톤방식으로 dao 객체 생성, 다형성 적용
		ProductDAO dao = ProductDAOImpl.getInstance();
		
		// 5단계. 상품상세 정보를 가져와서 DTO로 리턴
		ProductDTO dto = dao.getProductDetail(pdNo);
		System.out.println("ProductDTO : " + dto);
		// 6단계. jsp로 처리결과 전달(request나 session으로 결과 전달)
		req.setAttribute("pageNum", pageNum);
		req.setAttribute("dto", dto);
	}

	// 상품 수정 처리
	@Override
	public void productUpdateACtion(HttpServletRequest req, HttpServletResponse res) {
		// 3단계. 화면으로부터 입력받은 값(비밀번호), hidden값(num)을 받는다.
		//hiddenPdNo,hiddenPageNum,hiddenPdImg
		String hiddenPdImg = req.getParameter("hiddenPdImg");
		int hiddenPdNo = Integer.parseInt(req.getParameter("hiddenPdNo"));
		int hiddenPageNum = Integer.parseInt(req.getParameter("hiddenPageNum"));
		String uploadPdImg = (String)req.getAttribute("fileName");
		
		// 화면값 받아오기
		String pdName = req.getParameter("pdName");
		String brand = req.getParameter("brand");
		// String pdImg = req.getParameter("pdImg");
		String category = req.getParameter("category");
		String content = req.getParameter("content");
		int price = Integer.parseInt(req.getParameter("price"));
		int quantity = Integer.parseInt(req.getParameter("quantity"));
		String status = req.getParameter("status");
		// if문 변수 사용
		String strPdImg;
		
		// 이미지를 수정하지 않았을 때
		if(uploadPdImg == null ) {
			// 기존 이미지 활용
			strPdImg = hiddenPdImg;
			// 이미지를 수정했을 때
		} else {
			// 수정된 이미지 경로 사용
			strPdImg = "/jsp_pj_marketyezzang/resources/upload/" +uploadPdImg;
		}
		System.out.println("이미지(strPdImg) : " + strPdImg);	
		ProductDTO dto = new ProductDTO();
		dto.setPdName(pdName);
		dto.setBrand(brand);
		dto.setPdImg(strPdImg);
		dto.setCategory(category);
		dto.setContent(content);
		dto.setPrice(price);
		dto.setQuantity(quantity);
		dto.setStatus(status);
		dto.setPdNo(hiddenPdNo);
		// 4단계. 싱글톤방식으로 dao 객체 생성, 다형성 적용
		ProductDAO dao = ProductDAOImpl.getInstance();
		// 5단계. 상품정보 수정
		
		int updateCnt = dao.productUpdate(dto);
		System.out.println("productUpdateACtion - updateCnt : " + updateCnt);
		
		// 6단계. jsp로 처리결과 전달
		req.setAttribute("pageNum", hiddenPageNum);
		req.setAttribute("updateCnt", updateCnt);
	}

	// 상품 삭제 처리
	@Override
	public void productDeleteACtion(HttpServletRequest req, HttpServletResponse res) {
		// 3단계. 화면으로부터 입력받은 값을 받는다.
		int pdNo = Integer.parseInt(req.getParameter("pdNo"));
		// 4단계. 싱글톤방식으로 dao 객체 생성, 다형성 적용
		ProductDAO dao = ProductDAOImpl.getInstance();
		// 5단계. 상품삭제
		int updateCnt = dao.productDelete(pdNo);
		// 6단계. jsp로 처리결과 전달
		int pageNum = Integer.parseInt(req.getParameter("pageNum"));
		
		req.setAttribute("updateCnt", updateCnt);
		req.setAttribute("pageNum", pageNum);
	}

	

	//-------------------[고객 상품]-------------------------
	// 상품 리스트
	// 상의 리스트
	@Override
	public void customerProductList1(HttpServletRequest req, HttpServletResponse res) {
		System.out.println("서비스 - customerProductList1");
		// 3단계. 화면으로부터 입력받은 값을받는다.
		String pageNum = req.getParameter("pageNum");
		// 4단계. 싱글톤방식으로 dao 객체 생성, 다형성 적용
		ProductDAO dao = ProductDAOImpl.getInstance();
		
		// 5-1단계.
		ProductPaging paging = new ProductPaging(pageNum);
		
		
		// product 카운트
		int total = dao.customerProductCnt1();
		paging.setTotalCount(total);
		
		System.out.println("total : " + total);
		
		int start = paging.getStartRow();	// 페이지별 시작번호
		int end = paging.getEndRow();		// 페이지별 끝번호
		
		// 5-2단계.
		List<ProductDTO> list = dao.customerProductList1(start, end);
		System.out.println("list : " + list);
		System.out.println("start : " + start);
		System.out.println("end : " + end);
		
		// 6단계. jsp로 처리결과 전달(request나 session으로 결과 전달)
		req.setAttribute("list", list);
		req.setAttribute("paging", paging);
		
	}

	// 하의 리스트
	@Override
	public void customerProductList2(HttpServletRequest req, HttpServletResponse res) {
		System.out.println("서비스 - customerProductList2");
		// 3단계. 화면으로부터 입력받은 값을받는다.
		String pageNum = req.getParameter("pageNum");
		// 4단계. 싱글톤방식으로 dao 객체 생성, 다형성 적용
		ProductDAO dao = ProductDAOImpl.getInstance();
		
		// 5-1단계.
		ProductPaging paging = new ProductPaging(pageNum);
		
		// product 카운트
		int total = dao.customerProductCnt2();
		paging.setTotalCount(total);
		
		System.out.println("total : " + total);
		
		int start = paging.getStartRow();	// 페이지별 시작번호
		int end = paging.getEndRow();		// 페이지별 끝번호
		
		// 5-2단계.
		List<ProductDTO> list = dao.customerProductList2(start, end);
		System.out.println("list : " + list);
		System.out.println("start : " + start);
		System.out.println("end : " + end);
		
		// 6단계. jsp로 처리결과 전달(request나 session으로 결과 전달)
		req.setAttribute("list", list);
		req.setAttribute("paging", paging);
		
	}

	// 신발 리스트
	@Override
	public void customerProductList3(HttpServletRequest req, HttpServletResponse res) {
		System.out.println("서비스 - customerProductList3");
		// 3단계. 화면으로부터 입력받은 값을받는다.
		String pageNum = req.getParameter("pageNum");
		// 4단계. 싱글톤방식으로 dao 객체 생성, 다형성 적용
		ProductDAO dao = ProductDAOImpl.getInstance();
		
		// 5-1단계.
		ProductPaging paging = new ProductPaging(pageNum);
		
		
		// product 카운트
		int total = dao.customerProductCnt3();
		paging.setTotalCount(total);
		
		System.out.println("total : " + total);
		
		int start = paging.getStartRow();	// 페이지별 시작번호
		int end = paging.getEndRow();		// 페이지별 끝번호
		
		// 5-2단계.
		List<ProductDTO> list = dao.customerProductList3(start, end);
		System.out.println("list : " + list);
		System.out.println("start : " + start);
		System.out.println("end : " + end);
		
		// 6단계. jsp로 처리결과 전달(request나 session으로 결과 전달)
		req.setAttribute("list", list);
		req.setAttribute("paging", paging);
		
	}

	// 신상품 리스트
	@Override
	public void customerNewProductList(HttpServletRequest req, HttpServletResponse res) {
		System.out.println("서비스 - customerNewProductList");
		// 3단계. 화면으로부터 입력받은 값을받는다.
		String pageNum = req.getParameter("pageNum");
		// 4단계. 싱글톤방식으로 dao 객체 생성, 다형성 적용
		ProductDAO dao = ProductDAOImpl.getInstance();
		
		// 5-1단계.
		ProductPaging paging = new ProductPaging(pageNum);
		
		
		// product 카운트
		int total = dao.customerNewProductCnt();
		paging.setTotalCount(total);
		
		System.out.println("total : " + total);
		
		int start = paging.getStartRow();	// 페이지별 시작번호
		int end = paging.getEndRow();		// 페이지별 끝번호
		
		// 5-2단계.
		List<ProductDTO> list = dao.customerNewProductList(start, end);
		System.out.println("list : " + list);
		System.out.println("start : " + start);
		System.out.println("end : " + end);
		
		// 6단계. jsp로 처리결과 전달(request나 session으로 결과 전달)
		req.setAttribute("list", list);
		req.setAttribute("paging", paging);
		
	}
	// ---------------- [바로구매] ---------------
	@Override
	public void nowBuy(HttpServletRequest req, HttpServletResponse res) {
		System.out.println("서비스 - nowBuy");
		
		// 3단계. 화면으로부터 입력받은 값을 dto에 받는다.
		BasketDTO dto = new BasketDTO();
		dto.setPdNo(Integer.parseInt(req.getParameter("hiddenPdNo")));
		dto.setPdName(req.getParameter("hiddenPdName"));
		dto.setPdImg(req.getParameter("hiddenPdImg"));
		dto.setBrand(req.getParameter("hiddenPdBrand"));
		dto.setPrice(Integer.parseInt(req.getParameter("totalPrice")));
		dto.setQuantity(Integer.parseInt(req.getParameter("buttonCount")));
		dto.setId((String) req.getSession().getAttribute("customerID"));
		
		System.out.println("dto : "+ dto);
		
		// 4단계. 싱글톤방식으로 dao 객체 생성, 다형성 적용
		ProductDAO dao = ProductDAOImpl.getInstance();
		// 5단계.상품정보 인서트
		int updateCnt = dao.nowBuy(dto);
		System.out.println("updateCnt : " + updateCnt);	// 정상 : 1
		// upload폴더를 새로고침하면 등록한 이미지가 들어온다.
		
		// 6단계. jsp로 처리결과 전달(request나 session으로 결과 전달)
		req.setAttribute("insertCnt", updateCnt);
		
	}
	
	// ---------------- [장바구니] ----------------
	// 장바구니 담기
	@Override
	public void basketAddACtion(HttpServletRequest req, HttpServletResponse res) {
		System.out.println("서비스 - basketAddACtion");
		
		// 3단계. 화면으로부터 입력받은 값을 dto에 받는다.
		BasketDTO dto = new BasketDTO();
		dto.setPdNo(Integer.parseInt(req.getParameter("hiddenPdNo")));
		dto.setPdName(req.getParameter("hiddenPdName"));
		dto.setPdImg(req.getParameter("hiddenPdImg"));
		dto.setBrand(req.getParameter("hiddenPdBrand"));
		dto.setPrice(Integer.parseInt(req.getParameter("totalPrice")));
		dto.setQuantity(Integer.parseInt(req.getParameter("buttonCount")));
		dto.setId((String) req.getSession().getAttribute("customerID"));
		
		System.out.println("dto : "+ dto);
		
		// 4단계. 싱글톤방식으로 dao 객체 생성, 다형성 적용
		ProductDAO dao = ProductDAOImpl.getInstance();
		// 5단계.상품정보 인서트
		int updateCnt = dao.basketInsert(dto);
		System.out.println("updateCnt : " + updateCnt);	// 정상 : 1
		// upload폴더를 새로고침하면 등록한 이미지가 들어온다.
		
		// 6단계. jsp로 처리결과 전달(request나 session으로 결과 전달)
		req.setAttribute("insertCnt", updateCnt);
		
		
	}

	// 장바구니..
	@Override
	public void basketDetail(HttpServletRequest req, HttpServletResponse res) {
		System.out.println("서비스 - basketDetail");
		String id = ((String) req.getSession().getAttribute("customerID"));
		
		// 4단계. 싱글톤방식으로 dao 객체 생성, 다형성 적용
		ProductDAO dao = ProductDAOImpl.getInstance();
		// 5단계.상품정보 인서트
		List <BasketDTO> list = dao.basketList(id);
		System.out.println("list : " + list);	// 정상 : 1
		// upload폴더를 새로고침하면 등록한 이미지가 들어온다.
		CustomerDAO dao2 = CustomerDAOImpl.getInstance();
		String address = dao2.basketAddress(id);
		
		// 6단계. jsp로 처리결과 전달(request나 session으로 결과 전달)
		req.setAttribute("list", list);
		req.setAttribute("address", address);
		
		System.out.println("장바구니 address " + address);
	}

	// 장바구니 상품 삭제
	@Override
	public void basketDelete(HttpServletRequest req, HttpServletResponse res) {
		System.out.println("서비스 - basketDelete");
		
		int bkNo = Integer.parseInt(req.getParameter("bkNo"));
		
		ProductDAO dao = ProductDAOImpl.getInstance();
		int updateCnt = dao.baksetDelete(bkNo);
		System.out.println("updateCnt : " + updateCnt);	// 정상 : 1
		
		req.setAttribute("insertCnt", updateCnt);
	}

	// 장바구니에서 > 주문목록 , 장바구니 삭제 완료
	@Override
	public void basketBuy(HttpServletRequest req, HttpServletResponse res) {
		System.out.println("서비스 - basketBuy");
		
		String list = req.getParameter("list");
		List<String> bkNoList = new ArrayList<String>();
		String[] arrList = list.split(",");
		
		for(int i=0; i<arrList.length; i++ ) {
			bkNoList.add(arrList[i]);
		}
		/* System.out.println("list : " + list); */
		System.out.println("bkNoList : " + bkNoList);
		
		ProductDAO dao = ProductDAOImpl.getInstance();
		// 장바구니 체크 목록 가져오기 
		int cnt = dao.baksetGet(bkNoList);
		System.out.println("장바구니 구매 cnt : " + cnt);
		
		
	}
	
	// 주문내역
	@Override
	public void customerOrderList(HttpServletRequest req, HttpServletResponse res) {
		System.out.println("서비스 - customerOrderList");
		
		String id = (String) req.getSession().getAttribute("customerID");
		
		String pageNum = req.getParameter("pageNum");
		// 4단계. 싱글톤방식으로 dao 객체 생성, 다형성 적용
		ProductDAO dao = ProductDAOImpl.getInstance();
		
		// 5-1단계.
		CustomerOrderPaging paging = new CustomerOrderPaging(pageNum);
		
		// OrderList 카운트
		int total = dao.customerOrderCnt(id);
		paging.setTotalCount(total);
		
		System.out.println("total : " + total);
		
		int start = paging.getStartRow();	// 페이지별 시작번호
		int end = paging.getEndRow();		// 페이지별 끝번호
		
		// 5-2단계.
		List<OrderDTO> list = dao.customerOrderList(start, end, id);
		System.out.println("list : " + list);
		System.out.println("start : " + start);
		System.out.println("end : " + end);
		
		// 6단계. jsp로 처리결과 전달(request나 session으로 결과 전달)
		req.setAttribute("list", list);
		req.setAttribute("paging", paging);
		
	}

	// 고객 주문 배송준비중 취소하기 > 주문목록에서 삭제
	@Override
	public void customerPreparedOrederDelete(HttpServletRequest req, HttpServletResponse res) {
		String orNo = req.getParameter("orNo");
		ProductDAO dao = ProductDAOImpl.getInstance();
		
		int deleteCnt = dao.customerPreparedOrederDelete(orNo);
		
		System.out.println("주문목록 배송준비중 취소하기 : " + deleteCnt);
		
	}
	
	// 고객 배송완료 > 환불하기
	@Override
	public void customerOrederRefund(HttpServletRequest req, HttpServletResponse res) {
		String orNo = req.getParameter("orNo");
		ProductDAO dao = ProductDAOImpl.getInstance();
		
		int updateCnt = dao.customerOrederRefund(orNo);
		
		System.out.println("주문목록 배송완료 환불요청 : " + updateCnt);
		}

	// -------------------- [관리자 주문관리] -----------------------------
	// 주문목록
	@Override
	public void managerOrderList(HttpServletRequest req, HttpServletResponse res) {
		
		ProductDAO dao = ProductDAOImpl.getInstance();
		
		String pageNum = req.getParameter("pageNum");
		Paging paging = new Paging(pageNum);
		
		// OrderList 카운트
		int total = dao.managerOrderCnt();
		paging.setTotalCount(total);
		
		int start = paging.getStartRow();	// 페이지별 시작번호
		int end = paging.getEndRow();		// 페이지별 끝번호
		
		// 5-2단계.
		List<OrderDTO> list = dao.managerOrderList(start, end);
		System.out.println("list : " + list);
		System.out.println("start : " + start);
		System.out.println("end : " + end);
		
		req.setAttribute("list", list);
		req.setAttribute("paging", paging);
		
	}
	
	// 주문취소
	@Override
	public void managerOrderCancel(HttpServletRequest req, HttpServletResponse res) {
		String orNo = req.getParameter("orNo");
		ProductDAO dao = ProductDAOImpl.getInstance();
		
		int deleteCnt = dao.managerOrderCancel(orNo);
		
		System.out.println("주문 승인취소 : " + deleteCnt);
		
	}

	// 주문 승인
	@Override
	public void managerOrderApproval(HttpServletRequest req, HttpServletResponse res) {
		String orNo = req.getParameter("orNo");
		ProductDAO dao = ProductDAOImpl.getInstance();
		
		int updateCnt = dao.managerOrederApproval(orNo);
		
		System.out.println("주문 승인 : " + updateCnt);
		
		
	}

	// 주문 승인 목록
	@Override
	public void managerOrderApprovalList(HttpServletRequest req, HttpServletResponse res) {
		System.out.println("service - 주문승인 목록");
		ProductDAO dao = ProductDAOImpl.getInstance();
		
		String pageNum = req.getParameter("pageNum");
		Paging paging = new Paging(pageNum);
		
		// OrderList 카운트
		int total = dao.managerOrderApprovalCnt();
		paging.setTotalCount(total);
		
		int start = paging.getStartRow();	// 페이지별 시작번호
		int end = paging.getEndRow();		// 페이지별 끝번호
		
		// 5-2단계.
		List<OrderDTO> list = dao.managerOrderApprovalList(start, end);
		System.out.println("list : " + list);
		System.out.println("start : " + start);
		System.out.println("end : " + end);
		
		req.setAttribute("list", list);
		req.setAttribute("paging", paging);
		
		
	}

	// 환불 요청 목록
	@Override
	public void refundRequsetList(HttpServletRequest req, HttpServletResponse res) {
		System.out.println("service - 환불 요청 목록");
		ProductDAO dao = ProductDAOImpl.getInstance();
		
		String pageNum = req.getParameter("pageNum");
		Paging paging = new Paging(pageNum);
		
		// OrderList 카운트
		int total = dao.refundRequestCnt();
		paging.setTotalCount(total);
		
		int start = paging.getStartRow();	// 페이지별 시작번호
		int end = paging.getEndRow();		// 페이지별 끝번호
		
		// 5-2단계.
		List<OrderDTO> list = dao.refundRequestList(start, end);
		System.out.println("list : " + list);
		System.out.println("start : " + start);
		System.out.println("end : " + end);
		
		req.setAttribute("list", list);
		req.setAttribute("paging", paging);
		
	}

	// 환불 승인
	@Override
	public void refundApproval(HttpServletRequest req, HttpServletResponse res) {
		String orNo = req.getParameter("orNo");
		ProductDAO dao = ProductDAOImpl.getInstance();
		
		int updateCnt = dao.refundApproval(orNo);
		
		System.out.println("주문 승인 : " + updateCnt);
		
	}

	// 환불 승인 목록
	@Override
	public void refundApprovalList(HttpServletRequest req, HttpServletResponse res) {
		System.out.println("service - 환불 승인 목록");
		ProductDAO dao = ProductDAOImpl.getInstance();
		
		String pageNum = req.getParameter("pageNum");
		Paging paging = new Paging(pageNum);
		
		// OrderList 카운트
		int total = dao.refundApprovalCnt();
		paging.setTotalCount(total);
		
		int start = paging.getStartRow();	// 페이지별 시작번호
		int end = paging.getEndRow();		// 페이지별 끝번호
		
		// 5-2단계.
		List<OrderDTO> list = dao.refundApprovalList(start, end);
		System.out.println("list : " + list);
		System.out.println("start : " + start);
		System.out.println("end : " + end);
		
		req.setAttribute("list", list);
		req.setAttribute("paging", paging);
		
	}

	// 환불 취소
	@Override
	public void refundCancel(HttpServletRequest req, HttpServletResponse res) {
		String orNo = req.getParameter("orNo");
		ProductDAO dao = ProductDAOImpl.getInstance();
		
		int updateCnt = dao.refundCancel(orNo);
		
		System.out.println("주문 승인 : " + updateCnt);
		
	}

	// 환불 취소 목록
	@Override
	public void refundCancelList(HttpServletRequest req, HttpServletResponse res) {
		System.out.println("service - 환불 취소 목록");
		ProductDAO dao = ProductDAOImpl.getInstance();
		
		String pageNum = req.getParameter("pageNum");
		Paging paging = new Paging(pageNum);
		
		// OrderList 카운트
		int total = dao.refundCancelCnt();
		paging.setTotalCount(total);
		
		int start = paging.getStartRow();	// 페이지별 시작번호
		int end = paging.getEndRow();		// 페이지별 끝번호
		
		// 5-2단계.
		List<OrderDTO> list = dao.refundCancelList(start, end);
		System.out.println("list : " + list);
		System.out.println("start : " + start);
		System.out.println("end : " + end);
		
		req.setAttribute("list", list);
		req.setAttribute("paging", paging);
		
	}

	// 결산
	@Override
	public void totalMoney(HttpServletRequest req, HttpServletResponse res) {
		System.out.println("service - 결산");
		ProductDAO dao = ProductDAOImpl.getInstance();
		TotalMoneyDTO dto = dao.totalMoney();
	
		req.setAttribute("dto", dto);
	}

	
	
	
	
}
