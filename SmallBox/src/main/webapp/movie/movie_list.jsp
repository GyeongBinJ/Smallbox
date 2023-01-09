<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
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
  
<link href="assets/css/style.css" rel="stylesheet">
 
 <!-- jquery -->
<script src="js/jquery-3.6.3.js"></script>
<script type="text/javascript">
	// 비회원이 찜버튼 누르면 작동
	function login_need() {
		alert("로그인 후 이용 가능 합니다.");
	}
	
	// 회원이 찜 버튼 또는 찜 해제 버튼 클릭시 
	function changeLike(movie_idx) {
		
		let member_id = "${sessionScope.sId}";
		
			$.ajax({
			      type: "POST",
			      url: "MovieLikePro.mv", 
			      data: { 
			    	  member_id : member_id,
			    	  movie_idx : movie_idx 
			      }, 
			      success: function(result) {
			    	  
			    	 let isLike = "${sessionScope.isLike}";
			    	  
			    	 if(isLike) { // 찜작업이 성공하면
			    		 $("#btn_like").html('찜해제');
//	 			    	  location.reload(); // 전체페이지 새로고침
				    	  $(".py-5").load(location.href+' .py-5'); // 특정페이지 새로고침
			    		 isLike = false;
			    	 } else {
			    		 $("#btn_like").html('찜');
//	 			    	  location.reload(); // 전체페이지 새로고침
				    	  $(".py-5").load(location.href+' .py-5'); // 특정페이지 새로고침
			    		 isLike = true;
			    	 }
			      }
		      });

			}

</script>
<!-- jquery -->

</head>
<body>
	<header>
		<jsp:include page="../inc/top.jsp"></jsp:include>
	</header>
	  <!-- ======= header ======= -->
	
	<main id="main">
	
	<!-- ======= Breadcrumbs ======= -->
    <section class="breadcrumbs">
      <div class="container">

        <div class="d-flex justify-content-between align-items-center">
          <h2>전체 영화 </h2>
          <ol>
            <li><a href="index.html">Home</a></li>
            <li>전체 영화</li>
          </ol>
        </div>

      </div>
    </section><!-- End Breadcrumbs -->
	
	 <!-- ======= 찐 본문영역 ======= -->
	<section class="inner-page" style="display: inline;">
	
	  	<!-- 만약, pageNum 파라미터가 비어있을 경우 pageNum 변수 선언 및 기본값 1로 설정 -->
		<c:choose>
			<c:when test="${empty param.pageNum }">
				<c:set var="pageNum" value="1" />
			</c:when>
			<c:otherwise>
				<c:set var="pageNum" value="${param.pageNum }" />
			</c:otherwise>
		</c:choose>
    	 
		
      <div class="container">
	  <div style="text-align: center;">	
		<button class="Listbtn" onclick="location.href='MovieList.mv'">박스오피스</button>
		<button class="Listbtn" onclick="location.href='ComingMovieList.mv'">상영 예정작</button>
	  	<!-- movie_open_date를 어케 넘길지 -->
	  </div>	
        <p>
       	  <section class="py-5">
       	  <form action="MovieList.mv">
       	  	<div style="margin-bottom: 10px;">
				<input type="text" name="keyword" class="input_box">
				<input type="submit" value="Search" class="btn">
			</div>
		  </form>
        	<div class="wrap">
       		<div class="row row-cols-1 row-cols-md-4 g-4">
        	<!-- MovieLikeListProAction으로 부터 전달받은 request 객체의 likeList(영화 정보)를 꺼내서 출력 -->
			<c:forEach var="movie" items="${movieList }">
        		<div class="col">
            	<div class="card" style="object-fit:cover">
               		 <a href="MovieDetail.mv?movie_idx=${movie.movie_idx}&pageNum=${pageNum }"><img src="<%=request.getContextPath() %>/upload/${movie.movie_real_picture}"  width="300" height="350"
                     class="card-img-top" alt="..." ></a> <!-- 포스터 클릭하면 상세페이지로 이동 -->
                <div class="card-body">
                <h5 class="card-title" style="text-align: center;">${movie.movie_title }</h5>
                <h5 class="card-date" style="text-align: center;font-size: 16px">개봉일 ${movie.movie_open_date }</h5>
                <P class="card-star" style="text-align: center;">⭐⭐⭐</P> <!-- 평균낸 별점과 자바스크립트 들어가면 될듯 -->
                <div class="text-center"> 
                
<!--                 movie.movie_idx가 like.movie_idx를 포함하면 찜해제 아니면 찜 -->
<!-- 				{fn:contains(String, Substr)} -->
		    	     	<!-- c:when 로그인이 되어있을 경우 -->
		    	     	<!-- 영화목록 페이지 로딩시 찜 여부 판별 -->
		    	     	<!-- c:otherwise 로그인이 안되어있을 경우 찜버튼 클릭시 알림창 -->
		    	     	<c:choose>
		    	     	  <c:when test="${sessionScope.sId ne null}">
<%-- 	                     		<c:when test="${empty LikeList }"> --%>
<%-- 	                     		</c:when> --%>
					               			<c:choose>
						               			<c:when test="${likeList.contains(movie.movie_idx)}">
						               				<button class="btn btn-outline-dark mt-auto" id="btn_like" onclick="changeLike('${movie.movie_idx}')">찜해제</button>
						               			</c:when>
						               			<c:otherwise>
						               				<button class="btn btn-outline-dark mt-auto" id="btn_like" onclick="changeLike('${movie.movie_idx}')">찜</button>
						               			</c:otherwise>
		              			 			</c:choose>
	                   		 </c:when>
		    	     	  
		    	     	  <c:otherwise>
		    	     	      <button class="btn btn-outline-dark mt-auto" id="btn_like" onclick='javascript: login_need();'>찜</button>
		    	     	  </c:otherwise>
		    	     	
		    	     	</c:choose>
                  			
                	<a class="btn btn-outline-dark mt-auto" href="Reserve.mv">예매하기</a></div>
                   
                </div>
           		</div>
       	        </div>
       		</c:forEach>
   			</div>
    		</div>
    	</section>
       </p>
    </div>
   </section>
   
   <section id="pageList" style="text-align: center;"> <!-- 페이징 처리 영역 -->
		
		<!-- 
		현재 페이지 번호(pageNum)가 1보다 클 경우에만 [이전] 링크 동작
		=> 클릭 시 BoardList.bo 서블릿 주소 요청하면서 
		   현재 페이지 번호(pageNum) - 1 값을 page 파라미터로 전달
		-->
		<c:choose>
			<c:when test="${pageNum > 1}">
				<input type="button" class="pagebtn" value="이전" onclick="location.href='MovieList.mv?pageNum=${pageNum - 1}'">
			</c:when>
			<c:otherwise>
				<input type="button" class="pagebtn" value="이전">
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
					<a href="MovieList.mv?pageNum=${i }">${i }</a>
				</c:otherwise>
			</c:choose>
		</c:forEach>

		<!-- 현재 페이지 번호(pageNum)가 총 페이지 수보다 작을 때만 [다음] 링크 동작 -->
		<c:choose>
			<c:when test="${pageNum < pageInfo.maxPage}">
				<input type="button" class="pagebtn" value="다음" onclick="location.href='MovieList.mv?pageNum=${pageNum + 1}'">
			</c:when>
			<c:otherwise>
				<input type="button" class="pagebtn" value="다음">
			</c:otherwise>
		</c:choose>
	</section>
		
	</main>
		  <!-- ======= 본문영역 ======= -->
		
		  <!-- ======= Footer ======= -->
		<jsp:include page="../inc/bottom.jsp" />
</body>
</html>