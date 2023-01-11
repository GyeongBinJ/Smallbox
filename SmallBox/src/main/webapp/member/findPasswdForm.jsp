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
<link href="assets/vendor/bootstrap/css/bootstrap.min.css"
	rel="stylesheet">
<link href="assets/vendor/bootstrap-icons/bootstrap-icons.css"
	rel="stylesheet">
<link href="assets/vendor/boxicons/css/boxicons.min.css"
	rel="stylesheet">
<link href="assets/vendor/glightbox/css/glightbox.min.css"
	rel="stylesheet">
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

.btn-log {
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


<title>스몰박스 - 비밀번호 찾기</title>
</head>
<body>
	<!-- TOP -->
	<header>
		<jsp:include page="/inc/top.jsp"></jsp:include>
	</header>

	<!-- ======= Breadcrumbs ======= -->
	<section class="breadcrumbs">
		<div class="container">

			<div class="d-flex justify-content-between align-items-center">
				<h2>비밀번호 찾기</h2>
				<ol>
					<li><a href="./">Home</a></li>
					<li>비밀번호 찾기</li>
				</ol>
			</div>

		</div>
	</section>
	<!-- ======= Breadcrumbs ======= -->


	<form action="FindPasswdPro.sm" method="post" style="margin-top: 100px;">
		<div align="center">
			<div class="welcome">
				<img src="./assets/img/welcome.png" >
			</div>
			<table border="2" class="table">
				<tr>
					<th>아이디</th>
					<td><input type="text" value="아이디" name="member_id"
						id="member_id" required="required"></td>
				</tr>
				<tr>
					<th>이메일</th>
					<td><input type="text" value="이메일" name="member_email"
						id="member_email" required="required"></td>
				</tr>
			</table>
			<tr class="submit" style="text-align: center">
				<td class="btn_login">
				<input type="submit" id="findPasswd" value="비밀번호 찾기" class="btn-log" style="margin-bottom: 10px" onclick="alert('이메일이 전송되었습니다 메일함을 확인해주세요')">
					<br></td>
			</tr>
		</div>
	</form>
	<!--=========== findPasswd 본문 끝=================-->
	<!-- ---------------footer------------- -->
	<footer id="footer" style="margin-top: 120px">
		<jsp:include page="/inc/bottom.jsp"></jsp:include>
	</footer>
	<!-- ---------------footer------------- -->
</body>
</html>

