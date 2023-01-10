<%@page import="java.util.Date"%>
<%@page import="org.apache.naming.java.javaURLContextFactory"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<style type="text/css">
	tr, th {
		margin : auto;
	    text-align: center;
	}
</style>
<meta charset="UTF-8">
<title>관리자 - 상영 영화관 관리</title>
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

	<header>
		<jsp:include page="../inc/top.jsp"></jsp:include>
	</header>
	<h1>관리자 - 상영 영화관 관리</h1><br>
	<input type="button" value="영화 등록" onclick="location.href='AdminTheaterInsertForm.ad'">
	<c:set var="now" value="<%=new java.util.Date() %>"/>
<%-- 	<c:if test="${theater.theater_date eq now }"> --%>
		<c:out value="${now}"/>
<%-- 	</c:if> --%>
	<table border="1">
		<tr>
			<th width="10">번호</th>
			<th width="150">상영 영화명</th>
			<th width="100">상영 날짜</th>
			<th width="150">상영 시작 시각</th>
			<th width="50">예매 현황</th>
			<th width="50">좌석 수</th>
			<th width="50">비고</th>
		</tr>
		<c:forEach var="theater" items="${theaterList }">
		<tr>
			<td>${theater.theater_idx }</td>
			<td>
				<a href="AdminTheaterDetail.ad?theater_idx=${theater.theater_idx }">${theater.theater_title }</a>
			</td>
			<td>${theater.theater_date }</td>
			<td>${theater.theater_time }</td>
			<td>${theater.theater_reserved }</td>
			<td>${theater.theater_seat }</td>
			<td></td>
		</tr>
		</c:forEach>
	</table>
		
</body>
</html>