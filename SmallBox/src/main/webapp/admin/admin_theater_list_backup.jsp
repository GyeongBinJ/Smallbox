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
	<c:if test="${empty sessionScope.sId or sessionScope.sId ne 'admin'}">
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
	<section id="buttonArea">
		<form action="AdminTheaterList.ad">
			<input type="text" name="keyword">
			<input type="submit" value="검색"><br>
			<input type="button" value="상영영화관 등록" onclick="location.href='AdminTheaterInsertForm.ad'">
		</form>
	</section>
	<table border="1">
		<tr>
			<th width="10">번호</th>
			<th width="150">상영 영화명</th>
			<th width="100">상영 날짜</th>
			<th width="150">영화 상영시각</th>
			<th width="50">예매 현황</th>
			<th width="50">남은 좌석 수</th>
			<th width="50">비고</th>
		</tr>
		
		<!-- 상영 영화관 목록 -->
		<c:forEach var="theater" items="${theaterList }">
		<tr>
			<td>${theater.theater_idx }</td>
			
			<!-- pageNum값 null 방지를 위한 코드 -->
			<c:choose>
					<c:when test="${empty param.pageNum }">
						<c:set var="pageNum" value="1"/>
					</c:when>
					<c:otherwise>
						<c:set var="pageNum" value="${param.pageNum }"/>
					</c:otherwise>
				</c:choose>
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
	
	<!-- 페이징 처리를 위한 코드 -->
	<section id="pageList">
		<c:choose>
			<c:when test="${pageNum > 1}">
				<input type="button" value="이전" onclick="location.href='AdminTheaterList.ad?pageNum=${pageNum - 1}'">
			</c:when>
			<c:otherwise>
				<input type="button" value="이전">
			</c:otherwise>
		</c:choose>
			
		<!-- 페이지 번호 목록은 시작 페이지(startPage)부터 끝 페이지(endPage) 까지 표시 -->
		<c:forEach var="i" begin="${pageInfo.startPage }" end="${pageInfo.endPage }">
			<!-- 단, 현재 페이지 번호는 링크 없이 표시 -->
			<c:choose>
				<c:when test="${pageNum eq i}">
					${i }
				</c:when>
				<c:otherwise>
					<a href="AdminTheaterList.ad?pageNum=${i }">${i }</a>
				</c:otherwise>
			</c:choose>
		</c:forEach>

		<!-- 현재 페이지 번호(pageNum)가 총 페이지 수보다 작을 때만 [다음] 링크 동작 -->
		<c:choose>
			<c:when test="${pageNum < pageInfo.maxPage}">
				<input type="button" value="다음" onclick="location.href='AdminTheaterList.ad?pageNum=${pageNum + 1}'">
			</c:when>
			<c:otherwise>
				<input type="button" value="다음">
			</c:otherwise>
		</c:choose>
	</section>
</body>
</html>