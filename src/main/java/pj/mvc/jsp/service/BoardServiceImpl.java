package pj.mvc.jsp.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import page.Paging;
import pj.mvc.jsp.pj.dao.BoardDAO;
import pj.mvc.jsp.pj.dao.BoardDAOImpl;
import pj.mvc.jsp.pj.dto.BoardCommentDTO;
import pj.mvc.jsp.pj.dto.BoardDTO;


public class BoardServiceImpl implements BoardService{

	// 싱글톤 방식 : 객체를 1번만 생성
		static BoardServiceImpl instance = new BoardServiceImpl();
		
		public static BoardServiceImpl getInstance() {
			if(instance == null) {
				instance = new BoardServiceImpl();
			}
			return instance;
		}	
	
	// 게시글 목록
	@Override
	public void boardList(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException{
			
			System.out.println("서비스 - boardList");
			
			// 3단계. 화면으로부터 입력받은 값을 받는다.
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
	public void boardInsert(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException{
		System.out.println("서비스 - boardInsert");
		
		// 3단계. 화면으로부터 입력받은 값을 받는다.	
		String writer = req.getParameter("writer");
		String password = req.getParameter("password");
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
	public void boardDetail(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException{
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

	// 비밀번호 인증
	@Override
	public void password_chk(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		// 3단계. 화면으로부터 입력받은 값(비밀번호), hidden값(num)을 받는다.
		int num = Integer.parseInt(req.getParameter("num"));
		String password = req.getParameter("password");
		
		System.out.println("글번호 : " + num + " 비밀번호 : " + password);
		// 4단계. 싱글톤방식으로 dao 객체 생성, 다형성 적용
		BoardDAO dao = BoardDAOImpl.getInstance();
		// 5-1단계. 비밀번호 인증
		String result = dao.password_chk(num, password);
		System.out.println("비밀번호 체크 결과 : " + result );
		
		String viewPage = "";
		// 비밀번호가 맞으면 수정화면으로 이동
		if(result != null) {
			viewPage = "/manager/csCenter/board_edit.jsp";
			BoardDTO dto = dao.getBoardDetail(num);
			req.setAttribute("dto", dto);
			
			RequestDispatcher dispatcher = req.getRequestDispatcher(viewPage);
			dispatcher.forward(req, res);	
		} else { // 비밀번호가 틀리면 되돌아감
			viewPage = req.getContextPath() + "/board_detailAction.ad?num=" + num + "&message=error";
			res.sendRedirect(viewPage);
		}
		
		// 6단계. jsp로 처리결과 전달(request나 session으로 결과 전달) 
		// req.setAttribute("result", result);
	}

	// 게시글 수정처리 페이지
	@Override
	public void boardUpdate(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException{
		// 3단계. 화면으로부터 입력받은 값(비밀번호), hidden값(num)을 받는다.
		BoardDTO dto = new BoardDTO();
		
		dto.setNum(Integer.parseInt(req.getParameter("num")));
		dto.setContent(req.getParameter("content"));
		dto.setPassword(req.getParameter("password"));
		dto.setTitle(req.getParameter("title"));
		dto.setWriter(req.getParameter("writer"));
		
		
		
		// 4단계. 싱글톤방식으로 dao 객체 생성, 다형성 적용
		BoardDAO dao = BoardDAOImpl.getInstance();
		// 5단계. update 후 list로 이동
		int updateCnt = dao.updateBoard(dto);
		
	}

	// 게시글 삭제처리 페이지
	@Override
	public void boardDelete(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException{
		
		// 3단계. 화면으로부터 입력받은 hidden값(num)를 받는다.
		int num = Integer.parseInt(req.getParameter("num"));
		System.out.println("삭제할 게시글 번호 : " + num);
		
		// 4단계. 싱글톤방식으로 dao 객체 생성, 다형성 적용
		BoardDAO dao = BoardDAOImpl.getInstance();
		
		// 5단계. delete
		int deleteCnt = dao.deleteBoard(num);
		
	}

	// 댓글 추가처리 페이지
	@Override
	public void commentAdd(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		System.out.println("서비스 : commentAdd");
		
		// 3단계. 화면으로부터 입력받은 hidden값(num)를 받아서 DTO에 담는다,
		BoardCommentDTO dto = new BoardCommentDTO();
		
		int board_num = Integer.parseInt(req.getParameter("board_num"));
		String writer = req.getParameter("writer");
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

	// 댓글 목록처리 페이지
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

	// 댓글 삭제처리 페이지
	@Override
	public void deleteComment(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		System.out.println("서비스 : deleteComment");
		// 3단계. 화면으로부터 입력받은 hidden값(num)를 받는다.
		int board_num = Integer.parseInt(req.getParameter("hiddenBoard_num"));
		int comment_num = Integer.parseInt(req.getParameter("hiddenComment_num"));
		
		System.out.println("삭제할 댓글 board_num : " + board_num);
		System.out.println("삭제할 댓글 comment_num : " + comment_num);
		
		// 4단계. 싱글톤방식으로 dao 객체 생성, 다형성 적용
		BoardDAO dao = BoardDAOImpl.getInstance();
		
		// 5단계. delete
		int deleteCnt = dao.deleteComment(board_num,comment_num);
		
		// 5-1단계. 조회수 
		dao.plusReadCnt(board_num);
			
		BoardDTO dto = dao.getBoardDetail(board_num);	
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
	
	
	

}
