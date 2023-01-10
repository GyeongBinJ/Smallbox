<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
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
<style type="text/css">
.btn_area {
   margin-top: 30px;
}
.table {
   width: 350px;
   height: 100px;
}
body table tr td {
   font-size: 20px;
   text-align: center;
}
th td {
   margin-top: 20px;
}

th {
   font-size: 20px;
   text-align: center;
   padding-left: 20px;
}

.welcome {
   width: 300px;
   height: 80px;
}
.btn-log{
   width: 350px;
   height: 50px;
   background: #3B0B5F;
   color: #fff;
}
input {
   width: 200px;
   height: 30px;
}
</style>  


<title>스몰박스 - 아이디 찾기</title>
<script src="js/jquery-3.6.3.js"></script>
<script type="text/javascript">
	$(function() {
		// 아이디 찾기 버튼 클릭시 이벤트 처리
		$("#findId").on("click", function() {
			// AJAX 를 활용하여 FindIdPro.sm 서블릿 요청을 통해
			// 이름, 핸드폰번호, 생년월일이 같은 아이디 출력
			$.ajax({
					type: "get",
					url: "FindIdPro.sm",
					data: {
						name: $("#member_name").val(),
						phone: $("#member_phone").val(),
						birth_date: $("#member_birth_date").val()
					},
					success: function(result) {
						// 리턴받은 판별 결과("true", "false") 판별
						$("#resultArea").html(result);
						
						}
				});
		});
	});
	
</script>
</head>
<body>
<!-- TOP -->
<header>
	<jsp:include page="/inc/top.jsp"></jsp:include>
</header>
<!-- TOP -->

<!-- ======= Breadcrumbs ======= -->
   <section class="breadcrumbs">
     <div class="container">
   
       <div class="d-flex justify-content-between align-items-center">
         <h2>아이디찾기</h2>
         <ol>
           <li><a href="./">Home</a></li>
           <li>아이디찾기</li> 
         </ol>
       </div>
   
     </div>
   </section>
<!-- ======= Breadcrumbs ======= -->

<!--=========== Login 본문 =================-->

<form action="MemberLoginPro.sm" method="post">   
   <div align="center">
      <div class="welcome">
         <img src="./assets/img/welcome.png">
      </div>
      <table border="2"  class="table">
         <tr>
            <th>이름</th>
            <td><input type="text" name="member_name" id="member_name" required="required"></td>
         </tr>   
         <tr>
            <th>핸드폰번호</th>
            <td><input type="text" name="member_phone" id="member_phone" required="required"></td>
         </tr>   
         <tr>
			<th>생년월일</th>
			<td><input type="date" name="member_birth_date" id="member_birth_date"></td>
		</tr>
      </table>
         <tr class="submit" style="text-align: center">
            <td class="btn_login">
				<input type="button" id="findId" value="아이디 찾기" class="btn-log" style="margin-bottom: 10px"> <br>
            </td>
         </tr>
		회원님의 아이디는 <div class="btn_area" id="resultArea"></div> 입니다.
          </div>
</form>
<!--=========== Login 본문 끝=================-->
	<!-- ---------------footer------------- -->
	<footer id="footer" style="margin-top: 40px">
		<jsp:include page="/inc/bottom.jsp"></jsp:include>
	</footer>
	<!-- ---------------footer------------- -->
</body>
</html>