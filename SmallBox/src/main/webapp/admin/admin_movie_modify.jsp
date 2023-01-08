<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<!DOCTYPE html>
<html lang="kr">

<head>
  <meta charset="utf-8">
  <meta content="width=device-width, initial-scale=1.0" name="viewport">

  <title>SMALLBOX</title>
  <meta content="" name="description">
  <meta content="" name="keywords">

  <!-- Favicons -->
  <link href="assets/img/favicon.png" rel="icon">
  <link href="assets/img/apple-touch-icon.png" rel="apple-touch-icon">

  <!-- Google Fonts -->
  <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i|Raleway:300,300i,400,400i,500,500i,600,600i,700,700i|Poppins:300,300i,400,400i,500,500i,600,600i,700,700i" rel="stylesheet">

  <!-- Vendor CSS Files -->
  <link href="assets/vendor/animate.css/animate.min.css" rel="stylesheet">
  <link href="assets/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
  <link href="assets/vendor/bootstrap-icons/bootstrap-icons.css" rel="stylesheet">
  <link href="assets/vendor/boxicons/css/boxicons.min.css" rel="stylesheet">
  <link href="assets/vendor/glightbox/css/glightbox.min.css" rel="stylesheet">
  <link href="assets/vendor/swiper/swiper-bundle.min.css" rel="stylesheet">

  <!-- Template Main CSS File -->
<link href="./assets/css/style_admin.css" rel="stylesheet">
 
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
		<jsp:include page="../inc/top_admin.jsp"></jsp:include>
	</header>
	  <!-- ======= header ======= -->
	  
	<main id="main">
	
	<!-- ======= Breadcrumbs ======= -->
	<div id="layoutSidenav_content">
	    <section class="breadcrumbs_admin">
	      <div class="container">
	
	        <div class="d-flex justify-content-between align-items-center">
	          <h2>영화 수정 </h2>
	          <ol>
	            <li><a href="Admin.ad">관리자 페이지</a></li>
	            <li><a href="AdminMovieList.ad">전체 영화</a></li>
	            <li>영화 수정</li>
	          </ol>
	        </div>
	      </div>
	    </section><!-- End Breadcrumbs -->
	    
	     <main>
	         <div class="container-fluid px-4">
	             <h1 class="mt-4">영화 수정</h1>
		            <div class="card mb-4" style="margin-bottom: 30px;">
		                 <div class="card-body">
		                     - 관리자로 등록된 회원만 조회할 수 있는 페이지입니다.
		                 </div>
		           	</div>
						<form action="MovieModifyPro.ad" name="fr" method="post" enctype="multipart/form-data">
						<input type="hidden" name="movie_idx" value="${movie.movie_idx }" >
						<input type="hidden" name="pageNum" value="${param.pageNum }" >
						<!-- 파일 수정 시 기존 파일 삭제를 위해 실제 파일명도 파라미터로 전달 필요 -->
						<input type="hidden" name="movie_real_picture" value="${movie.movie_real_picture }" >
						<!-- 서버에 실제 업로드 된 파일 = movie_real_picture -->
					
						<table style="border: 1;border-radius: 4px;">
							<th style="text-align: center;height: 40">영화 정보</th>
							<th style="text-align: center;height: 40">영화 정보</th>
							<tr><td>영화명</td>
								<td><label><input type="text" name="movie_title" value="${movie.movie_title }" required="required"></label>
								</td></tr>
							<tr>
								<td>등급</td>
								<td>
									<label><input type="radio" name="movie_grade" value="전체관람가" checked="checked">전체관람가</label>
									<label><input type="radio" name="movie_grade" value="12세이상관람가">12세이상관람가</label>
									<label><input type="radio" name="movie_grade" value="15세이상관람가">15세이상관람가</label>
									<label><input type="radio" name="movie_grade" value="청소년관람불가">청소년관람불가</label>
								</td>
							</tr>
							<tr>
								<td>장르</td>
								<td>
									<label><input type="checkbox" name="movie_genre" value="코미디">코미디</label>
									<label><input type="checkbox" name="movie_genre" value="로맨스">로맨스</label>
									<label><input type="checkbox" name="movie_genre" value="액션">액션</label>
									<label><input type="checkbox" name="movie_genre" value="드라마">드라마</label>
									<label><input type="checkbox" name="movie_genre" value="애니메이션">애니메이션</label>
									<label><input type="checkbox" name="movie_genre" value="공포">공포</label>
									<label><input type="checkbox" name="movie_genre" value="SF">SF</label>
								</td>
							</tr>
							<tr>
								<td>개봉일</td>
								<td>
									<label><input type="date" name="movie_open_date" value="${movie.movie_open_date }" required="required"></label> 
								</td>
							</tr>
							<tr>
								<td>상영시간</td>
								<td>
									<label><input type="number" name="movie_runtime" value="${movie.movie_runtime }" required="required"></label>
								</td>
							</tr>
							<tr>
								<td>줄거리</td>
								<td><textarea name="movie_intro" cols="50" rows="10">${movie.movie_intro }</textarea></td>
							</tr>
							<tr>
								<td>등장인물</td>
								<td><textarea name="movie_actors" cols="50" rows="3">${movie.movie_actors }</textarea></td>
							</tr>
							<tr>
								<td>영화 포스터</td>
								<td>
									<label><input type="file" name="movie_picture"></label>
								</td>
							</tr>
							<tr>
								<td>영화 티저</td>
								<td>
									<label>
										<span>https://www.youtube.com/embed/</span>
										<input type="text" name="movie_teaser" style="width:150px"/>
									</label>
								</td>
							</tr>
							<tr>
								<td>누적 관람객 수</td>
								<td><label><input type="number" name="movie_viewer" value="${movie.movie_viewer }"></label></td>
							</tr>
							<tr>
								<td colspan="2">
								<div style="text-align: center;">
									<input type="submit" class="pagebtn2" value="등록">
								</div>
								</td>
							</tr>
						</table>
					</form>
					</div>
				</main>
				</div>
	        </main>
</body>
</html>