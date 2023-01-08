<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<% String member_id = request.getParameter("member_id"); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>쿠폰 지급</title>
</head>
<body>
	<!-- 관리자 아니면 접근 불가 -->
	<c:if test="${empty sessionScope.sId or sessionScope.sId ne 'admin'}">
		<script type="text/javascript">
		 	alert("접근 불가합니다.");
		 	history.back();
		</script>
	</c:if>
	<!-- 관리자 아니면 접근 불가 -->
	
	<h1>쿠폰 지급</h1>
	<form action="CouponInsertPro.ad" name="fr" method="post">
	<input type="hidden" name="member_id" value="${param.member_id }">
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
					<input class="pagebtn" type="submit" value="등록">
				</td>
			</tr>
		</table>
	</form>
</body>
</html>