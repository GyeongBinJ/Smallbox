<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>     
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<!-- --------------------- 들고다니세요 ------------------------------------ -->
<!-- css -->
<link rel="stylesheet" href="./assets/css/reset.css">
<link rel="stylesheet" href="./assets/css/style.css">
<link rel="stylesheet" href="./assets/css/swiper.css">
<link rel="stylesheet" href="./assets/css/style.css">

<!-- Favicons -->
<link href="./assets/img/favicon.png" rel="icon">
<link href="./assets/img/apple-touch-icon.png" rel="apple-touch-icon">

<!-- Vendor CSS Files -->
<link href="./assets/vendor/animate.css/animate.min.css" rel="stylesheet">
<link href="./assets/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link href="./assets/vendor/bootstrap-icons/bootstrap-icons.css" rel="stylesheet">
<link href="./assets/vendor/boxicons/css/boxicons.min.css" rel="stylesheet">
<link href="./assets/vendor/glightbox/css/glightbox.min.css" rel="stylesheet">
<link href="./assets/vendor/swiper/swiper-bundle.min.css" rel="stylesheet">

<link href="./assets/css/style_admin.css" rel="stylesheet">
<!-- --------------------- 들고다니세요 ------------------------------------ -->
<title>SMALL BOX - 관리자</title>
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

	<header id="header">
    	<jsp:include page="../inc/top_admin.jsp"></jsp:include>
    </header>
    
<!-- --------------------- <관리자 페이지> 들고다니세요 ------------------------------------ -->
    <section class="breadcrumbs_admin">
      <div class="container">
        <div class="d-flex justify-content-between align-items-center">
          <h2>관리자 페이지</h2>
          <ol>
            <li><a href="Admin.ad">관리자 페이지</a></li>
            <!-- 페이지 주소, 이름 넣는곳 -->
<!--             <li><a href="#"></a>##</li> -->
          </ol>
        </div>
      </div>
    </section>
<!-- --------------------- <관리자 페이지> 들고다니세요 ------------------------------------ -->
            <!-- 본문 영역 시작 -->
           
            <center>
            <h1>관리자 메인페이지입니다요 ~</h1>
            <img src="./assets/img/roopy.jpeg">
            </center>
           

      <footer id="footer">
      		<jsp:include page="../inc/bottom.jsp"></jsp:include>
      </footer>
    </body>
</html>
