<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>관리자페이지</title>
<link href="../css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
	<header>
		<jsp:include page="../inc/top.jsp"></jsp:include>
	</header>
	
	<h1>관리자페이지</h1>
		<h3><a href="MemberList.ad">회원관리</a></h3>
		<h3><a href="MovieInsertForm.ad">영화등록</a></h3>
		<h3><a href="AdminTheaterList.ad">상영일정관리</a></h3>
		<h3><a href="#">매출</a></h3>
		<h3><a href="NoticeInsert.ad">공지등록</a></h3>
</body>
</html>