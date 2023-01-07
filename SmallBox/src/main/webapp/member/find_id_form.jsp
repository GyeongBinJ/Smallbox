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
<h1>아이디 찾기</h1>	
	<form action="" >
		<table>
			<tr>
				<th>이름</th>
				<td><input type="text" name="member_name" id="member_name" ></td>
			</tr>
			<tr>
				<th>핸드폰번호</th>
				<td><input type="text" name="member_phone" id="member_phone"></td>
			</tr>
			<tr>
				<th>생년월일</th>
				<td><input type="date" name="member_birth_date" id="member_birth_date"></td>
			</tr>
			<tr>
			<td align="right">
				<input type="button" id="findId" value="아이디 찾기">
			</td>	
			</tr>
		</table>
	</form>
	<hr>
	<div id="resultArea"></div>	
</body>
</html>