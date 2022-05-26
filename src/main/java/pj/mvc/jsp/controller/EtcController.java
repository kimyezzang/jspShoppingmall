package pj.mvc.jsp.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pj.mvc.jsp.service.EtcServiceImpl;
import pj.mvc.jsp.service.ProductServiceImpl;


@WebServlet("*.etc")
public class EtcController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
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
		EtcServiceImpl service = new EtcServiceImpl();
		
		
		String uri = req.getRequestURI();
		String contextPath = req.getContextPath();
		String url = uri.substring(contextPath.length());
		
		// ------- [회원정보] ------
		if(url.equals("/*.etc") || url.equals("/userList.etc")) {	
			System.out.println("[url] ==> userList.etc");
			
			service.userList(req, res);
			viewPage = "/manager/etc/userList.jsp";
			
			RequestDispatcher dispatcher = req.getRequestDispatcher(viewPage);
			dispatcher.forward(req, res);	
		
		}
		// 회원정보 상세
		else if(url.equals("/userDetail.etc")){	
			System.out.println("[url] ==> userDetail.etc");
			
			service.userDetail(req, res);
			
			viewPage = "/manager/etc/userDetail.jsp";
			RequestDispatcher dispatcher = req.getRequestDispatcher(viewPage);
			dispatcher.forward(req, res);	
			
		// 회원 정보삭제
		} else if(url.equals("/userDeleteAction.etc")) {
			System.out.println("[url] ==> userDeleteAction.etc");
			
			service.userDeleteACtion(req, res);
			viewPage = "/manager/etc/userDeleteAction.jsp";
			
			RequestDispatcher dispatcher = req.getRequestDispatcher(viewPage);
			dispatcher.forward(req, res);
		}
}
	
}
