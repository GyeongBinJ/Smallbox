<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<head>
    <meta charset="utf-8">
    <link href='https://fonts.googleapis.com/css?family=Nunito:400,300' rel='stylesheet' type='text/css'>
    <!-- ----------------------------------------들고다니세요-------------------------------------------------------------------------- -->
	<!-- css -->
	
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
	
	<!-- ----------------------------------------들고다니세요-------------------------------------------------------------------------- --> 
    <style type="text/css">
    *, *:before, *:after {
  -moz-box-sizing: border-box;
  -webkit-box-sizing: border-box;
  box-sizing: border-box;
}
.formDiv {
	padding-left: 250px;	
}

body {
  font-family: 'Nunito', sans-serif;
  color: #384047;
  
}

.joinForm {
	padding-top: 20px;	
	padding-left: 30px;	

}

form {
  max-width: 1000px;
  padding: 10px 20px;
  background: #f4f7f8;
  border-radius: 8px;
  margin-top: 10px;
}

h1 {
  margin: 0 0 10px 0;
}

.inputBox,
select {
  background: rgba(255,255,255,0.1);
  border: none;
  font-size: 12px;
  height: auto;
  margin: 0;
  outline: 0;
  padding: 5px;
  width: 30%;
  background-color: #e8eeef;
  color: #8a97a0;
  box-shadow: 0 1px 0 rgba(0,0,0,0.03) inset;
  margin-bottom: 30px;
}

input[type="radio"],
input[type="checkbox"] {
  margin: 0 4px 8px 0;
}

select {
  padding: 6px;
  height: 32px;
  border-radius: 2px;
}

button {
  background-color: #3B0B5F;
  border: none;
  color: white;
  padding: 20px 150px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 16px;
  margin: 4px 2px;
  cursor: pointer;
  margin-left: 150px;
}

fieldset {
  margin-bottom: 10px;
  border: none;
}

legend {
  font-size: 0.5em;
  margin-bottom: 100px;
}

label {
  display: block;
  margin-bottom: 8px;
  font-size: 15px;
}

label.light {
  font-weight: 100;
  display: inline;
}
.joinBtn{
	background-color: #3B0B5F;
	border-radius: 5px;
	color: white;
}

.number {
  background-color: #5fcf80;
  color: #fff;
  height: 30px;
  width: 30px;
  display: inline-block;
  font-size: 0.8em;
  margin-right: 4px;
  line-height: 30px;
  text-align: center;
  text-shadow: 0 1px 0 rgba(255,255,255,0.2);
  border-radius: 100%;
}

@media screen and (min-width: 480px) {

  form {
    max-width: 800px;
  }

}
    </style>
    <title>스몰박스 - 회원가입</title>
</head>
<body>
<!-- top -->
<header>
	<jsp:include page="/inc/top.jsp"></jsp:include>
</header>
<!-- top -->
<!-- ======= Breadcrumbs ======= -->
	<section class="breadcrumbs">
	  <div class="container">
	
	    <div class="d-flex justify-content-between align-items-center">
	      <h2>회원가입</h2>
	      <ol>
	        <li><a href="./">Home</a></li>
	        <li><a href="MyPageMain.my">MyPage</a></li>
	        <li>Join</li> 
	      </ol>
	    </div>
	  </div>
	</section>
<!-- End Breadcrumbs -->
<div class="formDiv">
  <form action="index.html" method="post" class="joinForm">
  
    <h1>회원정보 입력</h1>
    
    <fieldset>
      <label for="name">이름</label>
      <input type="text" id="member_name" name="member_name" class="inputBox">
      <span id="checkNameResult"></span><br>
      
      <label>아이디</label>
      <input type="text" name="member_id" id="member_id" class="inputBox">
   	  <input type="button" value="중복확인" id="duplcheck" class="joinBtn">
   	  <span id="checkIdResult"></span><br>
      
      <label>패스워드</label>
   	  <input type="password" name="member_passwd" id="member_passwd" required="required" class="inputBox">
   	  <span id="checkPasswdResult"></span><br>
	   
	  <label>패스워드확인</label>
	  <input type="password" id="member_passwd_check" name="member_passwd_check" required="required" class="inputBox">
	  <span id="checkPasswdConfirmResult"></span><br>
	  
	  <label>전화번호</label>
      <input type="text" name="member_phone" id="member_phone" required="required" class="inputBox">
      <span id="checkPhoneResult"></span><br>
      
       <label>이메일</label>
       <input type="text" name="member_email1" id="member_email1" required="required" class="inputBox">@
       <input type="text" name="member_email2" id="member_email2" required="required" class="inputBox">
       <select name="selectDomain" id="selectDomain">
			<option value="">직접입력</option>	
			<option value="naver.com">naver.com</option>
			<option value="nate.com">nate.com</option>
			<option value="daum.com">daum.com</option>
			<option value="google.gmail">google.gmail</option>
		</select><br>
	
	  <label>이메일 인증번호</label>
	  <input type="text" id="authInputBox" size="15" placeholder="인증코드입력란" required="required" class="inputBox">
	  <input type="button" id="authCheck" value="확인" class="joinBtn">
	  <span id= "authEmailCheck"></span><br>
	  
	  <label>생년월일</label>
  	  <input type="date" name="member_birth_date" required="required" class="inputBox"><br>
      
    </fieldset>
    
    
    </fieldset>
    <button type="submit">가입하기</button>
  </form>
</div>
<!-- ---------------footer------------- -->
<footer id="header">
	<jsp:include page="/inc/bottom.jsp"></jsp:include>
</footer>
<!-- ---------------footer------------- -->
</body>
<script src="http://code.jquery.com/jquery-3.6.3.min.js"></script>
<script type="text/javascript">
$(function() {

	//아이디 유효성검사
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
                      $("#checkIdResult").html("이미 존재하는 ID").css("color","red");
                  } else {
                 	 $("#checkIdResult").html("사용 가능한 ID").css("color", "green");
                 }
                }
         }); // ajax
      } // if
   });
	
	// 이름 유효성 검사
	$("#member_name").on("keyup", function() {
		let name = $("#member_name").val();
		let regex = /^[가-힣]{2,10}$/;
		
		if(!regex.exec(name)) {
			$("#checkNameResult").html("정확한 이름을 입력하세요.").css("color", "red");
		} else {
			$("#checkNameResult").html("반갑습니다.").css("color", "green");
		}
	});
	
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
	
	// 휴대폰 유효성 검사
	$("#member_phone").on("change", function() {
		let phone = $("#member_phone").val();
		let regex = /^01\d\d{4}\d{4}$/;
		
		if(!regex.exec(phone)) {
			$("#checkPhoneResult").html("올바른 휴대폰 번호가 아닙니다.").css("color", "red");
		} else {
			$("#checkPhoneResult").html("");
		}
		
	});

});
// 비밀번호 정규표현식
$(function() {
	
	// 비밀번호 유효성 검사
	$("#member_passwd").on("change", function() {
		let passwd = $("#member_passwd").val();
		let lengthRegex = /^[A-Za-z0-9!@#$%]{8,16}$/;
		
		let engUpperRegex = /[A-Z]/; 
		let engLowerRegex = /[a-z]/; 
		let numRegex = /[0-9]/; 
		let specRegex = /[!@#$%]/; 
		
		if(!lengthRegex.exec(passwd)) {
			$("#checkPasswdResult").html("8~16자 영문 대 소문자, 숫자, 특수문자(!@#$%) 필수").css("color", "red");
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
	
	// 비밀번호 재확인
	$("#member_passwd_check").on("change", function() {
		let passwd_check = $("#member_passwd_check").val();
		let passwd = member_passwd;
		
		if(!passwd.exec(passwd_check)) {
			$("#checkPasswdConfirmResult").html("불일치").css("color", "red");
		} else {
			$("#checkPasswdConfirmResult").html("일치").css("color", "green");
		}
	});	
	
});	

// 이메일 인증코드
$(function() {
	// 이메일 인증 코드 전송
	// 이메일 인증 하이퍼링크 클릭시 이벤트 처리
	$("#authCheck").on("click", function() {
		// AJAX 를 활용하여 MemberAuth.me 서블릿 요청
		$.ajax({
			type: "get",
			url: "MemberAuth.sm",
			data: {
				id : $("#member_id").val(),
				authCode: $("#authInputBox").val(),
				email1 : $("#member_email1").val(),
				email2 : $("#member_email2").val()
			},
			success: function(result) {
				// 리턴받은 판별 결과("true", "false") 판별
				if(result == "true"){
					$("#authInputBox").attr("disabled",true);
					$("#authEmailCheck").html("이메일 인증 코드 발송!").css("color", "blue");
				}
			}
		});
	});
	
	// 이메일 인증 코드 비교
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
					$("#authEmailCheck").html("이메일 인증 완료!").css("color", "green");
			}
			
		});
	});
});

$(function() {
	
	//회원가입 버튼 눌렀을 때, 빈칸 있으면 다시 유효성 검사진행    
    $("#signupbtn").on("click",function(){
 	   var name = $("#member_name").val();
 	   var id = $("#member_id").val();
 	   var pw = $("#member_passwd").val();
 	   var phone = $("#member_phone").val();
 	   
 	   var nameregex = /[가-힣]{2,}/;
 	   var idregex = /^[a-z][a-z\d]{4,11}$/;
 	   var pwregex = /^[A-Za-z\d]{8,12}$/;
 	   var phoneregex = /^01\d\d{4}\d{4}$/;
 	   
 	   var nameregex = nameregex.exec(name);
 	   if(nameregex == null){
 		   alert("이름양식을 다시 확인해주세요");
 		   retrun;
 	   }
 	   var idregex = idregex.exec(id);
 	   if(idregex == null){
 		   alert("아이디양식을 다시 확인해주세요");
 		   return;
 	   }
 	   var pwregex = pwregex.exec(pw);
 	   if(pwregex == null){
 		   alert("비밀번호양식을 다시 확인해주세요");
 		   retrun;
 	   }
 	   var phoneregex = phoneregex.exec(phone);
 	   if(phoneregex == null){
 		   alert("핸드폰번호양식을 다시 확인해주세요");
 		   retrun;
 	   }
 	   
      //빈칸 없을 때 제출.
 	   $("#joinForm").submit();
    
    });
	
});
</script>
</html>