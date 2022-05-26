package pj.mvc.jsp.pj.dto;

import java.sql.Date;

public class OrderDTO {

	private int orNo;
	private int pdNo;
	private String pdName;
	private String pdImg;
	private String id;
	private String brand;
	private int price;
	private int quantity;
	private String orState;
	private Date orDate;
	public int getOrNo() {
		return orNo;
	}
	public void setOrNo(int orNo) {
		this.orNo = orNo;
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
	public String getOrState() {
		return orState;
	}
	public void setOrState(String orState) {
		this.orState = orState;
	}
	public Date getOrDate() {
		return orDate;
	}
	public void setOrDate(Date orDate) {
		this.orDate = orDate;
	}
	@Override
	public String toString() {
		return "OrderDTO [orNo=" + orNo + ", pdNo=" + pdNo + ", pdName=" + pdName + ", pdImg=" + pdImg + ", id=" + id
				+ ", brand=" + brand + ", price=" + price + ", quantity=" + quantity + ", orState=" + orState
				+ ", orDate=" + orDate + "]";
	}
	
	
	
	
	
	
	
}
