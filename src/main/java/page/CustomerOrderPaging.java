package page;

public class CustomerOrderPaging {
	
	private int pageSize = 4;	// 화면에 보여질 게시글의 갯수를 지정
	private int count = 0;		// 전체 글의 갯수를 저장하는 변수
	private int number = 0;		// 페이지 번호
	private String pageNum;
	
	private int startRow;		// 페이지당 시작페이지 번호
	private int endRow;			// 페이지당 끝 번호

	private int currentPage;
	private int pageCount;
	private int startPage;
	private int pageBlock;
	private int endPage;

	private int prev;	// 이전
	private int next;	// 다음
	
	public CustomerOrderPaging() {}
	public CustomerOrderPaging(String pageNum) {
		// 맨처음 boardList.jsp를 클릭하거나, 수정 삭제등 다른 게시글에서 페이지로 넘어올 때 pageNum이 없는 경우 null 처리
		if(pageNum == null) {
			pageNum = "1";
		}
		this.pageNum = pageNum;
		
		currentPage = Integer.parseInt(pageNum);
		System.out.println("pageNum : " + pageNum);
		System.out.println("currentPage : " + currentPage);
	}
	
	public void setTotalCount(int count) {
		this.count = count;
		
		startRow = (currentPage - 1) * pageSize + 1;
		endRow = currentPage * pageSize;
		
		System.out.println("startRow : " + startRow);
		System.out.println("endRow : " + endRow);
		
		this.number = count - (currentPage - 1) * pageSize;
		
		// 페이지 계산
		pageCalculator();
		
	}
	
	public void pageCalculator() {
		if(count > 0) {
			pageCount = count / pageSize + (count % pageSize == 0 ? 0 : 1);
			System.out.println("pageCount : " + pageCount);
			
			startPage = 1;
		
			if(currentPage % 10 != 0) {
				startPage = (int) (currentPage/10) * 10 + 1;
			} else {
				startPage = ((int) (currentPage/10) -1) * 10 + 1;
			}
			
			System.out.println("startPage : " + startPage);
			
			pageBlock = 10;
			endPage = startPage + pageBlock - 1;	//
			if(endPage > pageCount) endPage = pageCount;
			
			System.out.println("endPage : " + endPage);
			// 이전
			if(startPage > pageSize) prev = startPage - 10;
			// 다음
			if(endPage < pageCount) next = startPage + 10;
			
			System.out.println("prev : "  + prev);
			System.out.println("next : "  + next);
			
			
		}
	}
	
	
	// setter getter
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String getPageNum() {
		return pageNum;
	}
	public void setPageNum(String pageNum) {
		this.pageNum = pageNum;
	}
	public int getStartRow() {
		return startRow;
	}
	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}
	public int getEndRow() {
		return endRow;
	}
	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getPageCount() {
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	public int getStartPage() {
		return startPage;
	}
	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}
	public int getPageBlock() {
		return pageBlock;
	}
	public void setPageBlock(int pageBlock) {
		this.pageBlock = pageBlock;
	}
	public int getEndPage() {
		return endPage;
	}
	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}
	public int getPrev() {
		return prev;
	}
	public void setPrev(int prev) {
		this.prev = prev;
	}
	public int getNext() {
		return next;
	}
	public void setNext(int next) {
		this.next = next;
	}
	
	
	
	
}

/*
  		1 2 3 4 5 6 7 8 9 10	[다음]
  [이전] 11 12 13 14 15 16 17 18 19 20 [다음]
  1 : 1~10
  2 : 11~20



*/

