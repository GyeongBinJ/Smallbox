<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 가입</title>
<!-- ----------------------------------------들고다니세요-------------------------------------------------------------------------- -->
<!-- css -->
<link rel="stylesheet" href="assets/css/reset.css">
<link rel="stylesheet" href="assets/css/style.css">
<link rel="stylesheet" href="assets/css/swiper.css">
<link rel="stylesheet" href="assets/css/style.css">

<!-- Favicons -->
<link href="assets/img/favicon.png" rel="icon">
<link href="assets/img/apple-touch-icon.png" rel="apple-touch-icon">

<!-- Vendor CSS Files -->
<link href="assets/vendor/animate.css/animate.min.css" rel="stylesheet">
<link href="assets/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link href="assets/vendor/bootstrap-icons/bootstrap-icons.css" rel="stylesheet">
<link href="assets/vendor/boxicons/css/boxicons.min.css" rel="stylesheet">
<link href="assets/vendor/glightbox/css/glightbox.min.css" rel="stylesheet">
<link href="assets/vendor/swiper/swiper-bundle.min.css" rel="stylesheet">

<link href="assets/css/style.css" rel="stylesheet">
<!-- ----------------------------------------들고다니세요-------------------------------------------------------------------------- --> 

<link href="css/default.css" rel="stylesheet" type="text/css">

<script src="js/jquery-3.6.3.js"></script>
<script type="text/javascript">
//아이디 관련
$(function() {
   $("#member_id").on("change", function() {
      
      var id = $("#member_id").val();
      var regex = /^[A-Za-z0-9]{4,16}$/;
      var result2 = regex.exec(id);
      
      if(!result2) {
         $("#checkIdResult").html("4~16자의 영문 소문자, 숫자만 사용 가능합니다.").css("color", "red");
      } else { // 정규표현식 성립 한다면 디비 조회
         $.ajax({
            url:"MemberCheckId.sm",
            data: {
               id
            },
            success: function(result){
                  if(result == "true"){
                      $("#checkIdResult").html("이미존재하는아이디").css("color","red");
                  } else {
                 	 $("#checkIdResult").html("사용 가능한 아이디입니다.").css("color", "green");
                 }
                }
         }); // ajax
      } // if
   });
});
  
// -------------------------------------------------------------------------------------------------------------------------
	// 이름 정상여부 판별
$(function() {
	
	$("#member_name").on("keyup", function() {
		let name = $("#member_name").val();
		let regex = /^[가-힣]{2,10}$/;
		
		if(!regex.exec(name)) {
			$("#checkNameResult").html("정확한 이름을 입력하세요.").css("color", "red");
		} else {
			$("#checkNameResult").html("반갑습니다.").css("color", "green");
		}
	});	

// -------------------------------------------------------------------------------------------------------------------------

	// 이메일 도메인 선택
	$("#selectDomain").on("change",function(){
	      let domain = $("#selectDomain").val();
	      $("#member_email2").val(domain);
	      
	      //단 , 선택된 도메인이 "직접입력" 이 아닐경우 email2 입력창 잠금 
	      //주의 ! 자바스크립트상의 readonly 속성 제어 시 명칭이 readOnly
	      if(domain == ""){
	         $("#member_email2").prop("readonly", false);
	         $("#member_email2").css("background", "white");
	         $("#member_email2").focus();
	      }else{
	         $("#member_email2").prop("readonly",true);
	         $("#member_email2").css("background", "lightgray");
	      }
	});
	   
});
	   
// 비밀번호 관련
$(function() {
	$("#member_passwd_check").on("keyup", function() {
		let passwd_check = $("#member_passwd_check").val();
		let passwd = member_passwd;
		
		if(!passwd.exec(passwd_check)) {
			$("#checkPasswdConfirmResult").html("비밀번호를 다시 확인하여주세요.").css("color", "red");
		} else {
			$("#checkPasswdConfirmResult").html("비밀번호가 일치합니다.").css("color", "green");
		}
	});	
	
	
	$("#member_passwd").on("change", function() {
		let passwd = $("#member_passwd").val();
		let lengthRegex = /^[A-Za-z0-9!@#$%]{8,16}$/;
		
		let engUpperRegex = /[A-Z]/; 
		let engLowerRegex = /[a-z]/; 
		let numRegex = /[0-9]/; 
		let specRegex = /[!@#$%]/; 
		
		if(!lengthRegex.exec(passwd)) {
			$("#checkPasswdResult").html("8~16자 영문 대 소문자, 숫자, 특수문자(!@#$%) 사용하여야 안전합니다").css("color", "red");
		} else {
			let count = 0;
			
			if(engUpperRegex.exec(passwd)) { count++ };
			if(engLowerRegex.exec(passwd)) { count++ };
			if(numRegex.exec(passwd)) { count++ };
			if(specRegex.exec(passwd)) { count++ };
			
			switch(count) {
			case 4 : $("#checkPasswdResult").html("안전").css("color", "green"); break;
			case 3 : $("#checkPasswdResult").html("보통").css("color", "yellow"); break;
			case 2 : $("#checkPasswdResult").html("위험").css("color", "orange"); break;
			case 1 : $("#checkPasswdResult").html("사용불가").css("color", "red");
			}
		}
	});
	
	$("#member_passwd_confirm")
	
});

function passConfirm() {
	/* 비밀번호, 비밀번호 확인 입력창에 입력된 값을 비교해서 같다면 비밀번호 일치, 그렇지 않으면 불일치 라는 텍스트 출력.*/
	/* document : 현재 문서를 의미함. 작성되고 있는 문서를 뜻함. */
	/* getElementByID('아이디') : 아이디에 적힌 값을 가진 id의 value를 get을 해서 password 변수 넣기 */
		var member_passwd = document.getElementById('member_passwd');					//비밀번호 
		var member_passwd_check = document.getElementById('member_passwd_check');	//비밀번호 확인 값
		var checkPasswdConfirmResult = document.getElementById('checkPasswdConfirmResult');				//확인 메세지
		
		if(member_passwd.value == member_passwd_check.value){ //password 변수의 값과 passwordConfirm 변수의 값과 동일하다.
			checkPasswdConfirmResult.style.color = "GREEN"; /* span 태그의 ID사용  */
				checkPasswdConfirmResult.innerHTML ="비밀번호 일치";/* innerHTML : HTML 내부에 추가적인 내용을 넣을 때 사용하는 것. */
		}else{
			checkPasswdConfirmResult.style.color = "RED";
			checkPasswdConfirmResult.innerHTML ="비밀번호 불일치";
		}
	}
	
// 이메일 인증 코드 전송
$(function() {
	// 이메일 인증 하이퍼링크 클릭시 이벤트 처리
	$("#authCheck").on("click", function() {
		// AJAX 를 활용하여 MemberAuth.me 서블릿 요청
		$.ajax({
				type: "get",
				url: "MemberAuth.sm",
				data: {
					id : $("#member_id").val(),
					email1 : $("#member_email1").val(),
					email2 : $("#member_email2").val()
				}
			});
	});
});
	
// 이메일 인증 코드 비교
$(function() {
	$("#authInputBox").on("change", function() {
		var checkInput = $("#authInputBox")
		$.ajax({
				type:"get",
				url: "MemberAuthCheck.sm",
				data : {
					id : $("#member_id").val(),
					authCode: $("#authInputBox").val()
				},
				success: function(result) {
					if(result == $("#authInputBox").val()) { // 입력한 인증코드가 디비에 있는 인증코드와 같다면
						$("#authEmailCheck").html("이메일 인증 완료!").css("color", "green");
					} else {
						$("#authEmailCheck").html("인증 실패").css("color", "red");
					}	
				}
			});
			
		});
	});
	
	
</script>
</head>
<body>
<!-- top -->
<header>
	<jsp:include page="../inc/top.jsp"></jsp:include>
</header>
<!-- top -->
<h1>회원 가입</h1>
<form action="MemberJoinPro.sm" method="post" name="joinForm">
	<table border="1">
		<tr>
			<td>이름</td>
			<td><input type="text" name="member_name" id="member_name" required="required" size="20">
			<span id="checkNameResult"></span></td>
			
		</tr>
		<tr>
		<tr>
			<td>아이디</td>
			<td>
				<input type="text" name="member_id" id="member_id" required="required" size="20" placeholder="4-16자리 영문자,숫자 조합">
				<span id="checkIdResult"><!-- 자바스크립트에 의해 메세지가 표시될 공간 --></span>
			</td>
		<tr>
		<tr>
			<td>패스워드</td>
			<td>
				<input type="password" name="member_passwd" id="member_passwd" required="required" size="20" placeholder="8-20자리 영문자,숫자,특수문자 조합">
				<span id="checkPasswdResult"><!-- 자바스크립트에 의해 메세지가 표시될 공간 --></span>
			</td>
		</tr>
		<tr>
			<td>패스워드 확인</td>
			<td><input type="password" name="member_passwd_check"
				id="member_passwd_check" onchange="passConfirm()"
				required="required" size="20" placeholder="비밀번호를 다시 입력하여주세요.">
				<span id="checkPasswdConfirmResult">
					<!-- 자바스크립트에 의해 메세지가 표시될 공간 -->
			</span></td>
		</tr>
		<tr>
			<td> E-Mail</td>
				<td>
					<input type="text" name="member_email1" id="member_email1" required="required" size="10">@
					<input type="text" name="member_email2" id="member_email2" required="required" size="10">
					<select name="selectDomain" id="selectDomain">
						<option value="">직접입력</option>	
						<option value="naver.com">naver.com</option>
						<option value="nate.com">nate.com</option>
						<option value="daum.com">daum.com</option>
						<option value="google.gmail">google.gmail</option>
					</select>
			</td>
		</tr>
		<tr>
			<td>이메일 인증번호</td>
			<td> 
				<input type="text" id="authInputBox" size="15" placeholder="인증코드입력란">
<!-- 					<span id = "authEmailCheck"><a id ="authCheck" href="MemberAuth.sm"> 인증하려면 확인하세요!</a></span> -->
				<input type="button" id="authCheck" value="이얍!">
				<span id = "authEmailCheck"></span>
			</td>
		</tr>
			<tr>
			<td>연락처</td>
			<td>
				<input type="text" name="member_phone" id="member_phone" required="required" size="20">
			</td>
		</tr>
		<tr>
			<td>생년월일</td>
			<td>
				<input type="date" name="member_birth_date" required="required"> 
			</td>
		</tr>
		<tr>
			<td colspan="2" align="center">
				<input type="submit" value="회원가입">
				<input type="button" value="취소" onclick="history.back()">
			</td>
		</tr>
	</table>
</form>
</body>
</html>