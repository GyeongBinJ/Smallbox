<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% String member_id = request.getParameter("member_id"); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>쿠폰 지급</title>
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

</head>
<body>
<!-- TOP -->
<header>
	<jsp:include page="/inc/top.jsp"></jsp:include>
</header>
<!-- TOP -->
	<h1>쿠폰 수정</h1>
	<form action="CouponModifyPro.ad" name="fr" method="post">
	<input type="hidden" name="member_id" value="${param.member_id }">
	<input type="hidden" name="coupon_idx" value="${param.coupon_idx }">
		<table border="1">
			<tr>
				<td>쿠폰 유형</td>
				<td>
					<input type="text" name="coupon_type" required="required">
				</td>
			</tr>
			<tr>
				<td>할인율</td>
				<td>
					<input type="number" name="coupon_rate" required="required"> 
				</td>
			</tr>
			<tr>
				<td>쿠폰 만료 날짜</td>
				<td><input type="date" name="coupon_end_date"></td>
			</tr>
			<tr>
				<td colspan="2">
					<input class="pagebtn" type="submit" value="수정">
				</td>
			</tr>
		</table>
	</form>
</body>
</html>