<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
	<header>
		<jsp:include page="../inc/top_admin.jsp"></jsp:include>
	</header>
	  <!-- ======= header ======= -->
	
	<main id="main">
	
	<!-- ======= Breadcrumbs ======= -->
   <section class="breadcrumbs_admin">
      <div class="container">

        <div class="d-flex justify-content-between align-items-center">
          <h2>공지사항 등록</h2>
          <ol>
            <li><a href="Admin.ad">관리자 페이지</a></li>
            <li><a href="Notice_list.ad">공지사항</a></li>
            <li>공지 등록</li>
          </ol>
        </div>

      </div>
    </section><!-- End Breadcrumbs -->
	<form action="Admin_notice_writePro.ad" name="fr" method="post" enctype="multipart/form-data" >
		<table border="1">
			<!-- 글쓴이는 관리자로 고정 -->
			<tr><td>제목</td><td><input type="text" name="notice_subject" required="required"></td></tr>
			<tr>
				<td>내용</td>
				<td><textarea name="notice_content" cols="50" rows="10"></textarea></td>
			</tr>
			<tr>
				<td>파일</td>
				<td><input type="file" name="notice_file" required="required"></td>
			</tr>
			<tr>
				<td colspan="2">
					<input type="submit" value="등록">
				</td>
			</tr>
		</table>
	</form>
	</main>
</body>
</html>