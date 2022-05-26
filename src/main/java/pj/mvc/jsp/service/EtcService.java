package pj.mvc.jsp.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface EtcService {

	//-------[회원정보]
	// 회원정보 상세페이지
	public void userDetail(HttpServletRequest req, HttpServletResponse res);
	
	// 회원정보 삭제 처리
	public void userDeleteACtion(HttpServletRequest req, HttpServletResponse res);
	
	// 회원정보 리스트
	public void userList(HttpServletRequest req, HttpServletResponse res);
	
}
