<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
</head>
<!-- ----------------------------------------들고다니세요-------------------------------------------------------------------------- -->
<!-- css -->
<link rel="stylesheet" href="assets/css/reset.css">
<link rel="stylesheet" href="/assets/css/top.css">
<link rel="stylesheet" href="/assets/css/style.css">
<link rel="stylesheet" href="/assets/css/swiper.css">

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
.resultForm {
  max-width: 1000px;
/*   padding: 100px 200px; */
  background: #f4f7f8;
  border-radius: 8px;
  margin-top: 10px;
}

.h1 {
  margin: 0 0 10px 0;
  text-align: center;
}


.resultBtn {
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
</style>
<title>스몰박스 - 회원가입 완료</title>
</head>
<body class="result">
<!-- TOP -->
<header>
	<jsp:include page="/inc/top.jsp"></jsp:include>
</header>
<!-- TOP -->
<!-- ======= Breadcrumbs ======= -->
	<section class="breadcrumbs">
	  <div class="container">
	
	    <div class="d-flex justify-content-between align-items-center">
	      <h2>회원가입완료</h2>
	      <ol>
	        <li><a href="./">Home</a></li>
	        <li>JoinResult</li> 
	      </ol>
	    </div>
	  </div>
	</section>
<!-- End Breadcrumbs -->
    <div align="center">
		<form action="" class="resultForm">
		    <!-- //header -->
			<h1 class="h1">축하합니다!</h1>
			<h3>회원가입이 완료되었습니다.</h3>
			<div style="padding-top: 20px; margin-right: 150px;">
				<input type="button" value="홈으로" onclick="location.href='./'" class="resultBtn">
				<input type="button" value="로그인" onclick="location.href='MemberLoginForm.sm'" class="resultBtn">
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
