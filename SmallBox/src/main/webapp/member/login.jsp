<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
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
<title>스몰박스 - 로그인</title>
<style type="text/css">
</style>
</head>
<body>
<!-- TOP -->
<header>
	<jsp:include page="/inc/top.jsp"></jsp:include>
</header>
<!-- TOP -->
	<hr>
    <!-- //header -->
<div id="container" class="f" align="center">
	<form action="MemberLoginPro.sm" method="post">
		<h2 align="center">스몰박스 로그인</h2>
			<table border="1" >
				<tr>
					<th>아이디</th>
					<td><input type="text" name="member_id" required="required" value="${sessionScope.sId }"></td>
				</tr>	
				<tr>
					<th>패스워드</th>
					<td><input type="password" name="member_passwd" required="required"></td>
				</tr>	
				<tr align="center">
					<td colspan="1" align="center"><input type="submit" value="로그인"></td>
				</tr>
		</table>
		<div align="center">
       		<button> <a href="FindIdForm.sm">아이디 찾기</a></button> | <button><a href="FindPasswdForm.sm">비밀번호 찾기</a></button> | <button><a href="MemberTermsForm.sm">회원가입</a></button>
       	</div>
	</form>
</div>

<!-- ---------------footer------------- -->
<footer id="footer">
	<jsp:include page="/inc/bottom.jsp"></jsp:include>
</footer>
<!-- ---------------footer------------- -->
</body>
</html>