package pj.mvc.jsp.pj.dto;

import java.sql.Date;
import java.sql.Timestamp;

public class CustomerDTO {
	private String id;
	private String password;
    private String name;
    private String passwordHint;
    private String address;	
    private String hp;
    private String email;
    private Timestamp regDate;
    private Date birth;
 
    // 디폴트 생성자
    public CustomerDTO() {
	}
    
    
	public Date getBirth() {
		return birth;
	}


	public void setBirth(Date birth) {
		this.birth = birth;
	}


	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPasswordHint() {
		return passwordHint;
	}
	public void setPasswordHint(String passwordHint) {
		this.passwordHint = passwordHint;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getHp() {
		return hp;
	}
	public void setHp(String hp) {
		this.hp = hp;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Timestamp getRegDate() {
		return regDate;
	}
	public void setRegDate(Timestamp regDate) {
		this.regDate = regDate;
	}

	@Override
	public String toString() {
		return "CustomerDTO [id=" + id + ", password=" + password + ", name=" + name + ", passwordHint=" + passwordHint
				+ ", address=" + address + ", hp=" + hp + ", email=" + email + ", regDate=" + regDate + "]";
	}
    
	
}
