package pj.mvc.jsp.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

public class ImageUploaderHandler {

	private String uploadPath;
	
	public void setUploadPath(String url) {	// 이미지 경로
		this.uploadPath = url;
	}
	
	public void imageUpload(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException{
		
		req.setCharacterEncoding("UTF-8");
		res.setCharacterEncoding("UTF-8");
		File uploadDir = new File(uploadPath);	// IO 유틸 
		
		if(!uploadDir.exists()) { // 업로드할 경로에 폴더가 없는 경우 폴더 생성 (uploadPath 경로에)
			uploadDir.mkdir();	// make directory 일거 같은 느낌..
		}
		
		try {
			uploadDir.setWritable(true);	// 파일 쓰기 가능으로 설정	.. 아마 서버쪽에 권한 설정으로 보인다..
			uploadDir.setReadable(true);	// 파일 읽기 가능으로 설정
			uploadDir.setExecutable(true);	// 실행 권한 설정
			
			String fileName = "";
			for(Part part : req.getParts()) {	
				System.out.println(part.getHeader("content-disposition"));
				fileName = getFileName(part);	// 무슨 뜻일라나
				if(fileName != null && !"".equals(fileName)) {
					part.write(uploadPath + File.separator + fileName);	// 파일명을 가지고 와라
					System.out.println("fileName : " + fileName);
					req.setAttribute("fileName", fileName);
				}
			}
				
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		res.setContentType("text/html");	// ;charset=utf-8 이어 붙여서 언어 설정가능 
		res.getWriter().println("=== 업로드 완료 ===");
	}
	
	private String getFileName(Part part) {
		for(String content : part.getHeader("content-disposition").split(";")) {
			if(content.trim().startsWith("filename")) {// startsWith : ~으로 시작하는
				System.out.println("content : " + content);
				
				/* 
				 * form-data; name="pdImg"; filename="제트청소기.jpg"
				 * content
				 *  
				 *  */
				return content.substring(content.indexOf("=") + 2, content.length() - 1);
			}
			
		}
		return null;	// filename이 없는 경우(폼필드가 데이터인 경우);
	}
	
}
