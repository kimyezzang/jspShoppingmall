/**
 * 회원가입 validation Check
if (!/^[a-z0-9]{4,20}$/.test($("#userId").val())) {
  alert("아이디는 영 소문자, 숫자 4~20자리로 입력해주세요.");
  return;
}
 */
var idchk = new RegExp("^(?=.*[a-z])(?=.*[0-9]).{6,20}$");
var passwordchk = new RegExp("^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\(\\)\-_=+]).{8,16}$");
var emailchk = new RegExp("^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$");
// 필수항목 체크
function signInCheck(){
			
		
		// password
	 if(!document.joinform.password.value){
		alert("비밀번호를 입력하세요.");
		document.joinform.password.focus();
		return false;
		
	} else if(passwordchk.test(document.joinform.password.value) != 1){
		alert("8 자의 하나 이상의 문자, 하나의 숫자 및 하나의 특수 문자비밀번호를 입력하세요.");
		document.joinform.password.focus();
		return false;
		
		// repassword
	} else if(!document.joinform.rePassword.value){
		alert("비밀번호확인을 입력하세요.");
		document.joinform.rePassword.focus();
		return false;
		
		// passwordHint
	} else if(!document.joinform.passwordHint.value){
		alert("비밀번호힌트를 입력하세요.");
		document.joinform.passwordHint.focus();
		return false;
			
		// password 일치
	} else if(document.joinform.rePassword.value != document.joinform.password.value){
		alert("비밀번호가 불일치합니다.");
		document.joinform.rePassword.value = ""; 
		document.joinform.rePassword.focus();
		return false;
		
		// name
	}  else if(!document.joinform.name.value){
		alert("이름을 입력하세요.");
		document.joinform.name.focus();
		return false;
		
		// birthday
	} else if(!document.joinform.birth.value){
		alert("생년월일을 입력하세요.");
		document.joinform.birth.focus();
		return false;
	
		// birthday
	} else if(document.joinform.birth.value.length >=9 || document.joinform.birth.value.length<=7){
		alert("숫자 8자리 생년월일을 입력해주세요 ex) 19990606");
		document.joinform.birth.focus();
		return false;
		// address
	} else if(!document.joinform.address.value){
		alert("주소를 입력하세요.");
		document.joinform.address.focus();
		return false;
		
		// email1
	} else if(!document.joinform.email.value){
		alert("이메일을 입력하세요.");
		document.joinform.email.focus();
		return false;

	// eamil 정규식
	} else if(emailchk.test(document.joinform.email.value)!=1){
		alert("이메일형식을 확인하세요.");
		document.joinform.email.focus();
		return false;

		// hp		
	} else if(!document.joinform.hp.value){
		alert("휴대폰을 입력하세요.");
		document.joinform.hp.focus();
		return false;
		
		// hp
	} else if(!(document.joinform.hp.value.length == 10 || document.joinform.hp.value.length == 11)){
		alert("휴대폰번호 길이를 확인하세요.");
		document.joinform.hp.focus();
		return false;	
		
	}	
	// 2-1 .join.jsp 폼바로 아래 추가
	/*<input type="hidden" name="hiddenId" value="0">*/
	// hiddenId : 중복확인 버튼 클릭여부 체크(0:클릭안함, 1:클릭함)
	
	// 2-2. 중복확인 버튼 클릭하지 않는 경우 "중복확인 해주세요."
	else if (document.joinform.hiddenEmailEx.value == document.joinform.email.value){
		document.joinform.hiddenEmail.value = "1";
	}
	else if(document.joinform.hiddenEmail.value == "0"){
		alert("email 중복확인 해주세요.");
		return false; 
	}	
}
// 아이디 중복확인 페이지 
// 1. 중복확인 페이지 open

// 이메일 중복확인 페이지 
// 1. 중복확인 페이지 open
function confirmEmail(){
	if(!document.joinform.email.value){
		alert("email을 입력하세요.");
		document.joinform.email.focus();
		return false;
	// eamil 정규식
	} else if(emailchk.test(document.joinform.email.value)!=1){
		alert("이메일형식을 확인하세요.");
		document.joinform.email.focus();
		return false;	
	}
	
	// 중복확인 페이지 open
	var url = "/jsp_pj_marketyezzang/confirmEmailAction.do?email=" + document.joinform.email.value;
	window.open(url, "confirm", "member=no, width=305, height=180");	// window.open(url, "별칭", size);
}


// 4. 자식창에서 부모창으로 id값을 전달
/*
opener : window 객체의 open() 메서드로 열릴 새창(=중복확인창)에서 부모창에 접근할 때 사용
join.jsp - hiddenId : 중복확인 버튼 클릭여부 체크(0:클릭안함, 1:클릭함)
self.close(); 내창 닫기
*/

function setEmail(email){
	opener.document.joinform.email.value = email;
	opener.document.joinform.hiddenEmail.value = "1";
	self.close();
	
}	  
	
	