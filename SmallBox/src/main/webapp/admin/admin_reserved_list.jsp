<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>     
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>관리자 - 매출</title>
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
	
	<h1>관리자 - 매출</h1>
		<table border="1">
			<tr>
				<td>상영 영화명</td>
				<td>${theater.theater_title }</td>
			</tr>
			<tr>
				<td>상영 날짜</td>
				<td>${theater.theater_date }</td>
			</tr>
			<tr>
				<td>영화 상영시각</td>
				<td>${theater.theater_time }</td>
			</tr>
			<tr>
				<td>예매 현황</td>
				<td>${theater.theater_reserved }</td>
			</tr>
			<tr>
				<td>좌석 수</td>
				<td>${theater.theater_seat }</td>
			</tr>
		</table>
			<input type="button" class="pagebtn" value="이전" onclick="#'">
</body>
</html>