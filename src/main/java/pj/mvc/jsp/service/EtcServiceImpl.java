package pj.mvc.jsp.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import page.Paging;
import pj.mvc.jsp.pj.dao.EtcDAO;
import pj.mvc.jsp.pj.dao.EtcDAOImpl;
import pj.mvc.jsp.pj.dao.ProductDAO;
import pj.mvc.jsp.pj.dao.ProductDAOImpl;
import pj.mvc.jsp.pj.dto.CustomerDTO;
import pj.mvc.jsp.pj.dto.ProductDTO;

public class EtcServiceImpl implements EtcService {

	//-------[회원정보]
	// 회원정보 상세페이지
	@Override
	public void userDetail(HttpServletRequest req, HttpServletResponse res) {
		// 3단계. 화면으로부터 입력받은 값을 받는다.
		String id = req.getParameter("id");
		int pageNum = Integer.parseInt(req.getParameter("pageNum"));
		// 4단계. 싱글톤방식으로 dao 객체 생성, 다형성 적용
		EtcDAO dao = EtcDAOImpl.getInstance();
		
		// 5단계. 상품상세 정보를 가져와서 DTO로 리턴
		CustomerDTO dto = dao.getUserDetail(id);
		System.out.println("CustomerDTO : " + dto);
		// 6단계. jsp로 처리결과 전달(request나 session으로 결과 전달)
		req.setAttribute("pageNum", pageNum);
		req.setAttribute("dto", dto);

	}

	// 회원정보 삭제 처리
	@Override
	public void userDeleteACtion(HttpServletRequest req, HttpServletResponse res) {
		// 3단계. 화면으로부터 입력받은 값을 받는다.
		String id = req.getParameter("id");
		// 4단계. 싱글톤방식으로 dao 객체 생성, 다형성 적용
		EtcDAO dao = EtcDAOImpl.getInstance();
		// 5단계. 상품삭제
		int updateCnt = dao.userDelete(id);
		// 6단계. jsp로 처리결과 전달
		int pageNum = Integer.parseInt(req.getParameter("pageNum"));
		
		req.setAttribute("updateCnt", updateCnt);
		req.setAttribute("pageNum", pageNum);

	}

	// 회원정보 리스트
	@Override
	public void userList(HttpServletRequest req, HttpServletResponse res) {
		System.out.println("서비스 - userList");
		// 3단계. 화면으로부터 입력받은 값을받는다.
		String pageNum = req.getParameter("pageNum");
		// 4단계. 싱글톤방식으로 dao 객체 생성, 다형성 적용
		EtcDAO dao = EtcDAOImpl.getInstance();
		
		// 5-1단계.
		Paging paging = new Paging(pageNum);
		
		
		// product 카운트
		int total = dao.userCnt();
		paging.setTotalCount(total);
		
		System.out.println("total : " + total);
		
		int start = paging.getStartRow();	// 페이지별 시작번호
		int end = paging.getEndRow();		// 페이지별 끝번호
		
		// 5-2단계.
		List<CustomerDTO> list = dao.userList(start, end); 
		System.out.println("list : " + list);
		System.out.println("start : " + start);
		System.out.println("end : " + end);
		
		// 6단계. jsp로 처리결과 전달(request나 session으로 결과 전달)
		req.setAttribute("list", list);
		req.setAttribute("paging", paging);

	}

}
