// 에러메시지
var insertError = "회원가입에 실패하였습니다. \n확인 후 다시 시도하세요!";
var updateError = "회원정보 수정에 실패하였습니다. \n확인 후 다시 시도하세요!";
var deleteError = "회원삭제에 실패하였습니다. \n확인 후 다시 시도하세요!";
var passwordError = "비밀번호가 일치하지 않습니다. \n확인 후 다시 시도하세요!";

function errorAlert(errorMsg){
	alert(errorMsg);
	window.history.back();
}