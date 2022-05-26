package pj.mvc.jsp.pj.dao;

import java.util.List;

import pj.mvc.jsp.pj.dto.CustomerDTO;

public interface EtcDAO {

		// 회원정보 상세페이지
		public CustomerDTO getUserDetail(String id);
			
		// 회원정보 삭제 처리
		public int userDelete(String id);
			
		// 회원정보 갯수
		public int userCnt();
		
		// 회원정보 목록
		public List<CustomerDTO> userList(int start ,int end);
}
