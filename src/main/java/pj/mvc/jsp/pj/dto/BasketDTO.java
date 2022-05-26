package pj.mvc.jsp.pj.dto;

public class BasketDTO {

	private int bkNo;
	private int pdNo;
	private String pdName;
	private String pdImg;
	private String id;
	private String brand;
	private int price;
	private int quantity;
	
	public BasketDTO() {
	}

	public int getBkNo() {
		return bkNo;
	}

	public void setBkNo(int bkNo) {
		this.bkNo = bkNo;
	}

	public int getPdNo() {
		return pdNo;
	}

	public void setPdNo(int pdNo) {
		this.pdNo = pdNo;
	}

	public String getPdName() {
		return pdName;
	}

	public void setPdName(String pdName) {
		this.pdName = pdName;
	}

	public String getPdImg() {
		return pdImg;
	}

	public void setPdImg(String pdImg) {
		this.pdImg = pdImg;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "BasketDTO [bkNo=" + bkNo + ", pdNo=" + pdNo + ", pdName=" + pdName + ", pdImg=" + pdImg + ", id=" + id
				+ ", brand=" + brand + ", price=" + price + ", quantity=" + quantity + "]";
	}
	
}
