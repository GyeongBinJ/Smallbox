<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>     
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>쿠폰 목록</title>
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
	<!-- 관리자 아니면 접근 불가 -->

	<c:if test='${empty sessionScope.sId or sessionScope.sId ne "admin"}'>
		<script type="text/javascript">
		 	alert("접근 불가합니다.");
		 	history.back();
		</script>
	</c:if>
	<!-- 관리자 아니면 접근 불가 -->
	
<!-- TOP -->
<header>
	<jsp:include page="/inc/top.jsp"></jsp:include>
</header>
<!-- TOP -->
	<h1>쿠폰 목록</h1>
<form>	
	<table border="1">
		<tr>
			<th width="150">회원 아이디</th>
<!-- 			<th width="150">쿠폰 번호</th> -->
			<th width="150">쿠폰 유형</th>
			<th width="100">할인율</th>
			<th width="150">쿠폰 발행 날짜</th>
			<th width="150">쿠폰 만료 날짜</th>
			<th width="100"></th>
		</tr>
		
		<c:forEach var="coupon" items="${couponList }">
		<tr>
			<td>${coupon.member_id } </td>
<%-- 			<td>${coupon.coupon_idx } </td> --%>
			<td>${coupon.coupon_type } </td>
			<td>${coupon.coupon_rate } </td>
			<td>${coupon.coupon_date } </td>
			<td>${coupon.coupon_end_date } </td>
			<td>
				<input type="button" class="pagebtn" value="수정" onclick="location.href='CouponModify.ad?member_id=${coupon.member_id }&coupon_idx=${coupon.coupon_idx }'"">
				<input type="button" class="pagebtn" value="삭제" onclick="location.href='CouponDelete.ad?member_id=${coupon.member_id }&coupon_end_date=${coupon.coupon_end_date }'">
			</td>
		</tr>
	</c:forEach>
	</table>
</form>	
</body>
</html>