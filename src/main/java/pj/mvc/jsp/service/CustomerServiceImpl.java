package pj.mvc.jsp.service;

import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import page.Paging;
import page.ProductPaging;
import pj.mvc.jsp.pj.dao.BoardDAO;
import pj.mvc.jsp.pj.dao.BoardDAOImpl;
import pj.mvc.jsp.pj.dao.CustomerDAO;
import pj.mvc.jsp.pj.dao.CustomerDAOImpl;
import pj.mvc.jsp.pj.dto.BoardCommentDTO;
import pj.mvc.jsp.pj.dto.BoardDTO;
import pj.mvc.jsp.pj.dto.CustomerDTO;
import pj.mvc.jsp.pj.dto.ProductDTO;

public class CustomerServiceImpl implements CustomerService{

	// ID중복확인 처리
	@Override
	public void confirmIdAction(HttpServletRequest req, HttpServletResponse res) {
		System.out.println("Customer서비스 => ID중복확인 처리");
		
		// 3단계. 화면으로부터 입력받은 값을 받아서 dto에 담는다.
		String strId = req.getParameter("id");
		
		// 4단계. 싱글톤방식으로 dao 객체 생성, 당형성 적용
		CustomerDAO dao = CustomerDAOImpl.getInstance();
		// 5단계. 중복확인 처리
		int selectCnt = dao.idCheck(strId);
		// 6단계. jsp로 처리 결과 전달(request나 session으로 처리 결과를 저장 후 전달)
		req.setAttribute("id", strId);
		req.setAttribute("selectCnt", selectCnt);
		
	}

	// Email중복확인 처리
	@Override
	public void confirmEmailAction(HttpServletRequest req, HttpServletResponse res) {
		System.out.println("Customer서비스 => Email중복확인 처리");
		
		// 3단계. 화면으로부터 입력받은 값을 받아서 dto에 담는다.
		String strEmail = req.getParameter("email");
		
		// 4단계. 싱글톤방식으로 dao 객체 생성, 당형성 적용
		CustomerDAO dao = CustomerDAOImpl.getInstance();
		// 5단계. 중복확인 처리
		int selectCnt = dao.emailCheck(strEmail);
		// 6단계. jsp로 처리 결과 전달(request나 session으로 처리 결과를 저장 후 전달)
		req.setAttribute("email", strEmail);
		req.setAttribute("selectCnt", selectCnt);
		
	}

	// 회원가입 처리
	@Override
	public void signInAction(HttpServletRequest req, HttpServletResponse res) {
		System.out.println("Customer서비스 - 회원가입처리" );
		
		// 3단계. 화면으로부터 입력받은 값을 받아서 dto에 담는다.
		// dto 생성
		CustomerDTO dto = new CustomerDTO();
		
		dto.setId(req.getParameter("id"));
		dto.setPassword(req.getParameter("password"));
		dto.setPasswordHint(req.getParameter("passwordHint"));
		dto.setName(req.getParameter("name"));
		String strDate = req.getParameter("birth");
		String year = strDate.substring(0,4);
		String month = strDate.substring(4,6);
		String day = strDate.substring(6,8);
		strDate = year+"-"+month+"-"+day;
		System.out.println("strDate : " + strDate);
		Date date = Date.valueOf(strDate);
		dto.setBirth(date);
		dto.setAddress(req.getParameter("address"));
		dto.setEmail(req.getParameter("email"));
		dto.setHp(req.getParameter("hp"));
		
		// 4단계. 싱글톤방식으로 dao 객체 생성, 다형성 적용
		// 객체를 한번만 생성하기 위해 인스턴스 적용
		CustomerDAO dao = CustomerDAOImpl.getInstance();
		
		// 5단계. 회원가입처리
		int insertCnt = dao.insertCustomer(dto);
		System.out.println("서비스 insertCnt : " + insertCnt);
		
		// 6단계. jsp로 처리 결과 전달(request나 session으로 처리 결과를 저장 후 전달)
		req.setAttribute("insertCnt", insertCnt);
		
	}

	// 로그인 처리
	@Override
	public void loginAction(HttpServletRequest req, HttpServletResponse res) {
		// 3단계. 화면으로부터 입력받은 값을 받는다.
		String strId = req.getParameter("id");
		String strPw = req.getParameter("password");
		
		// 4단계. 싱글톤방식으로 dao 객체 생성, 다형성 적용
		CustomerDAO dao = CustomerDAOImpl.getInstance();
		
		// 5단계. 로그인 처리
		int selectCnt = dao.idPasswordCheck(strId, strPw);
		
		if(selectCnt == 1) {
			// 로그인 성공시 세션 ID를 설정
			req.getSession().setAttribute("customerID", strId);
		}
		
		// 6단계. jsp로 처리 결과 전달(request나 session으로 처리 결과를 저장 후 전달)
		
		req.setAttribute("selectCnt", selectCnt);
		System.out.println("customerID : " + strId);
		
	}

	// 회원정보 인증 및 탈퇴 처리
	@Override
	public void deleteCustomerAction(HttpServletRequest req, HttpServletResponse res) {
		// 3단계. 화면으로부터 입력받은 값을 받는다.
		String strId = (String)req.getSession().getAttribute("customerID");
		String strPw = req.getParameter("password");
		int deleteCnt = 0;
		// 4단계. 싱글톤방식으로 dao 객체 생성, 다형성 적용
		CustomerDAO dao = CustomerDAOImpl.getInstance();
		
		// 5-1단계. 회원삭제를 위한 인증처리
		int selectCnt = dao.idPasswordCheck(strId, strPw);
		
		System.out.println("삭제 전 서비스 checkCnt : " + selectCnt);
		if(selectCnt == 1) {
			// 5-2단계. 인증성공시 삭제처리
			deleteCnt = dao.deleteCustomer(strId);
			System.out.println("삭제서비스 deleteCnt : " + deleteCnt);
		}
		
		// 6단계. jsp로 처리 결과 전달(request나 session으로 처리 결과를 저장 후 전달)
		req.setAttribute("deleteCnt", deleteCnt);
		req.setAttribute("selectCnt", selectCnt);
		
	}

	// 회원정보 인증 및 상세페이지
	@Override
	public void modifyDetailAction(HttpServletRequest req, HttpServletResponse res) {
		System.out.println("service - modifyDetailAction");
		// 3단계. 화면으로부터 입력받은 값을 받는다.
		String strId = (String)req.getSession().getAttribute("customerID");
		String strPw = req.getParameter("password");
		
		// 4단계. 싱글톤방식으로 dao 객체 생성, 다형성 적용
		CustomerDAO dao = CustomerDAOImpl.getInstance();
		
		// 5-1단계. 회원수정을 위한 인증처리
		int selectCnt = dao.idPasswordCheck(strId, strPw);
		// idPasswordChk
		System.out.println("수정서비스 selectCnt : " + selectCnt);
		CustomerDTO dto = null;
		
		// 5-2단계. 인증성공시 상세정보 조회
		if(selectCnt == 1) {
			
			 dto = dao.getCustomerDetail(strId);
			
			 // Date date = dto.getBirth();
			 // DateFormat format = new SimpleDateFormat("yyyyMMdd");
			 // String birth = format.format(date);
			 // System.out.println("birth");
			 // Date d = Date.valueOf(birth);
			 // java.util.Date date2 = format.parse(birth);
			 
			 // dto.setBirth(d);
			 System.out.println("selctCnt"+selectCnt);
		}
		
		// 6단계. jsp로 처리 결과 전달(request나 session으로 처리 결과를 저장 후 전달)
		req.setAttribute("selectCnt", selectCnt);
		req.setAttribute("dto", dto);
		// dto..?
		
	}

	// 회원정보 수정 처리
	@Override
	public void modifyCustomerAction(HttpServletRequest req, HttpServletResponse res) {
		// 3-1단계. dto 생성 후 화면으로부터 입력받은 값을 dto에 담는다.
				CustomerDTO dto = new CustomerDTO();
				
				// 3-2단계. 화면으로부터 입력받은 값을 dto에 담는다.
				dto.setId((String)req.getSession().getAttribute("customerID"));
				dto.setPassword(req.getParameter("password"));
				dto.setName(req.getParameter("name"));
						
				String strDate = req.getParameter("birth");
				String year = strDate.substring(0,4);
				String month = strDate.substring(4,6);
				String day = strDate.substring(6,8);
				strDate = year+"-"+month+"-"+day;
				System.out.println("strDate : " + strDate);
				Date date = Date.valueOf(strDate);
				dto.setBirth(date);
				
				dto.setAddress(req.getParameter("address"));
				dto.setHp(req.getParameter("hp"));
				dto.setPasswordHint(req.getParameter("passwordHint"));
				
				dto.setEmail(req.getParameter("email"));
				
				// regDate는 입력값이 없으면 default가 sysdate로 설정했음, 아래행은 직접 Timestamp로 설정  
				// dto.setRegDate(new Timestamp(System.currentTimeMillis()));		// import필요
				// 4단계. 싱글톤방식으로 dao 객체 생성, 다형성 적용
				CustomerDAO dao = CustomerDAOImpl.getInstance();
				// 5단계. 회원수정처리
				int updateCnt = dao.updateCustomer(dto);
						
				System.out.println("updateCnt" + updateCnt);
				// 6단계. jsp로 처리 결과 전달(request나 session으로 처리 결과를 저장 후 전달)
				req.setAttribute("updateCnt", updateCnt);
		
	}

	// 게시판 목록
	@Override
	public void boardList(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		System.out.println("서비스 - boardList");
		
		// 페이지번호 클릭시
		String pageNum = req.getParameter("pageNum");
		// int amount = 10;
		
		// 4단계. 싱글톤방식으로 dao 객체 생성, 다형성 적용
		BoardDAO dao = BoardDAOImpl.getInstance();
		
		// 5-1 단계. 
		Paging paging = new Paging(pageNum);
		
		// 전체 게시글 갯수 카운트
		int total = dao.boardCnt();
		paging.setTotalCount(total);
		
		System.out.println("total ==> " + total);
		int start = paging.getStartRow();
		int end = paging.getEndRow();
				
		// 5-2 단계. 게시글 목록
		List<BoardDTO> list = dao.boadrList(start, end);
		
		// 6단계. jsp로 처리결과 전달(request나 session으로 결과 전달)
		req.setAttribute("list", list);
		req.setAttribute("paging", paging);
		
	}
	
	// 게시글 작성처리 페이지
	@Override
	public void boardInsert(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		System.out.println("서비스 - boardInsert");
		
		// 3단계. 화면으로부터 입력받은 값을 받는다.	
		// String writer = req.getParameter("writer");
		String writer = (String) req.getSession().getAttribute("customerID");
		String password = "1234";
		String title = req.getParameter("title");
		String content = req.getParameter("content");
		
		BoardDTO dto = new BoardDTO();
		
		dto.setWriter(writer);
		dto.setPassword(password);
		dto.setTitle(title);
		dto.setContent(content);
		
		// 4단계. 싱글톤방식으로 dao 객체 생성, 다형성 적용
		BoardDAO dao = BoardDAOImpl.getInstance();
		
		// 5단계. 게시글 저장
		int insertCnt = dao.boardInsert(dto);
		System.out.println("insertCnt : " + insertCnt);
		
		// 6단계. jsp로 처리결과 전달(request나 session으로 결과 전달)
		req.setAttribute("insertCnt", insertCnt);
		
	}
	
	// 게시글 상세페이지
	@Override
	public void boardDetail(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		System.out.println("서비스 - boardDetail");
		
		// 3단계. 화면으로부터 입력받은 값을 받는다.
		int num = Integer.parseInt(req.getParameter("num"));
		System.out.println("게시글번호 : " + num);
		
		// 4단계. 싱글톤방식으로 dao 객체 생성, 다형성 적용
		BoardDAO dao = BoardDAOImpl.getInstance();
	
		// 5-1단계. 조회수 
		dao.plusReadCnt(num);
			
		BoardDTO dto = dao.getBoardDetail(num);	
		// 글 내용 줄바꿈 처리
		
		  String content= dto.getContent(); 
		  
		  if(content != null) {
			  content = content.replace("\n","<br>");
		  }
		  dto.setContent(content);
		
		System.out.println("상세페이지 dto : " + dto);
		// 6단계. jsp로 처리결과 전달(request나 session으로 결과 전달)
		
		req.setAttribute("dto", dto);
		
	}
	
	// 댓글 추가
	@Override
	public void commentAdd(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException {
		System.out.println("서비스 : commentAdd");
		
		// 3단계. 화면으로부터 입력받은 hidden값(num)를 받아서 DTO에 담는다,
		BoardCommentDTO dto = new BoardCommentDTO();
		
		int board_num = Integer.parseInt(req.getParameter("board_num"));
		String writer = (String) req.getSession().getAttribute("customerID");
		String content = req.getParameter("content");
		
		dto.setBoard_num(board_num);
		dto.setWriter(writer);
		dto.setContent(content);
		
		// 4단계. 싱글톤방식으로 dao 객체 생성, 다형성 적용
		BoardDAO dao = BoardDAOImpl.getInstance();
		
		// 5단계. 댓글 insert
		dao.getCommentInsert(dto);
		
		// 실행이 끝나면 board_detail.jsp의 comment_add()콜백함수(success)로 넘어감
		
	}

	// 댓글 목록
	@Override
	public void commentList(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException {
		System.out.println("서비스 : commentList");
		int board_num = Integer.parseInt(req.getParameter("num"));
		
		System.out.println("board_num : " + board_num);
		// 4단계. 싱글톤방식으로 dao 객체 생성, 다형성 적용
		BoardDAO dao = BoardDAOImpl.getInstance();
				
		// 5단계. 댓글 list 조회
		List<BoardCommentDTO> list = dao.getCommentList(board_num);
		
		req.setAttribute("list", list);
	}

	// 장바구니 회원 주소 가져오기
	@Override
	public void basketAddress(HttpServletRequest req, HttpServletResponse res) {
		System.out.println("service - basketAddress");
		// 3단계. 화면으로부터 입력받은 값을 받는다.
		String strId = (String)req.getSession().getAttribute("customerID");
		
		// 4단계. 싱글톤방식으로 dao 객체 생성, 다형성 적용
		CustomerDAO dao = CustomerDAOImpl.getInstance();
		
		
		String address = dao.basketAddress(strId);
		
		// 6단계. jsp로 처리 결과 전달(request나 session으로 처리 결과를 저장 후 전달)
		req.setAttribute("address", address);
	}
		
		// 검색처리
		@Override
		public void search(HttpServletRequest req, HttpServletResponse res) {
			System.out.println("service - search");
			CustomerDAO dao = CustomerDAOImpl.getInstance();
			
			String content = req.getParameter("searchContent");
			String pageNum = req.getParameter("pageNum");
			ProductPaging paging = new ProductPaging(pageNum);
			
			int total = dao.searchCnt(content);
			paging.setTotalCount(total);
			
			int start = paging.getStartRow();	// 페이지별 시작번호
			int end = paging.getEndRow();		// 페이지별 끝번호
			
			List<ProductDTO> list = dao.search(content, start, end);
			
			req.setAttribute("list", list);
			req.setAttribute("paging", paging);
		}

	

		
		// 이메일 인증
		@Override
		public void emailChkAction(HttpServletRequest req, HttpServletResponse res) {
			// TODO Auto-generated method stub
			
		}	

}
