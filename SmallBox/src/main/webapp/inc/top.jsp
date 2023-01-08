<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<link rel="stylesheet" href="https://cdn.jsdelivr.net/gh/hung1001/font-awesome-pro@4cac1a6/css/all.css" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script type="text/javascript">
function confirm_logout() {
	let result = confirm("로그아웃 하시겠습니까?");
	
	if(result) {
		location.href="./MemberLogout.sm";
	}
}
</script>
</head>
<body>
<c:choose>
	<c:when test="${empty sessionScope.sId}">
	  <header id="header" class="d-flex align-items-center">
	    <div class="container d-flex align-items-center">
	    	<nav id="navbar" class="navbar">
	    	<li class="dropdown">
              	<div style="margin-right: 100px;"><i class="fas fa-bars fa-2x"></i></div> 
              		<ul>
	          		<li class=""><h4>전체 메뉴</h4></li>
          			<li class="dropdown"><a href="MovieList.mv"><span>영화</span> <i class="bi bi-chevron-right"></i></a>
						<ul>
							<li><a href="MovieList.mv">박스오피스</a></li>
							<li><a href="ComingMovieList.mv">상영 예정작</a></li>
						</ul>
					</li>
	          		<li class="dropdown"><a href="#">예매<i class="bi bi-chevron-right"></i></a>
	          			<ul>
							<li><a href="#">박스오피스</a></li>
							<li><a href="#">현재 상영작</a></li>
							<li><a href="#">개봉 예정작</a></li>
						</ul>
	          		</li>
	          		<li class="dropdown"><a href="#">극장<i class="bi bi-chevron-right"></i></a>
	          			<ul>
							<li><a href="#">박스오피스</a></li>
							<li><a href="#">현재 상영작</a></li>
						</ul>
	          		</li>
	          		<li class="dropdown"><a href="#">이벤트<i class="bi bi-chevron-right"></i></a>
	          			<ul>
							<li><a href="#">박스오피스</a></li>
							<li><a href="#">현재 상영작</a></li>
						</ul>
	          		</li>
	          		<li class="dropdown"><a href="#">혜택<i class="bi bi-chevron-right"></i></a>
	          			<ul>
							<li><a href="#">박스오피스</a></li>
							<li><a href="#">현재 상영작</a></li>
						</ul>
	          		</li>
                    <li class="dropdown"><a href="#">고객센터<i class="bi bi-chevron-right"></i></a>
                    	<ul>
							<li><a href="#">박스오피스</a></li>
							<li><a href="#">현재 상영작</a></li>
							<li><a href="#">개봉 예정작</a></li>
						</ul>
                    </li>
                    <li class="dropdown"><a href="#">조원소개<i class="bi bi-chevron-right"></i></a>
                    	<ul>
							<li><a href="#">박스오피스</a></li>
							<li><a href="#">현재 상영작</a></li>
							<li><a href="#">개봉 예정작</a></li>
						</ul>
                    </li>
                    <li class="dropdown"><a href="#">이용정책<i class="bi bi-chevron-right"></i></a>
                    	<ul>
							<li><a href="#">박스오피스</a></li>
							<li><a href="#">현재 상영작</a></li>
							<li><a href="#">개봉 예정작</a></li>
						</ul>
                    </li>
	          	</ul>
              </li>
	    	 </nav><!-- .navbar -->
	    	  <!-- 로고 -->
		      <h1 class="logo me-auto" style="margin: 0 auto;">
			      <a href="./">
			        <img src="assets/img/logo.png">
			      </a>
		      </h1>
		        <!-- 로고 끝 -->
		      
	
	      <nav id="navbar" class="navbar">
	        <ul>
	          <li class="dropdown"><a class="nav-link scrollto active" href="MovieList.mv">영화<i class="bi bi-chevron-down"></i></a>
	          	<ul>
	          		<li><a href="MovieList.mv">박스오피스</a></li>
                    <li><a href="ComingMovieList.mv">상영 예정작</a></li>
	          	</ul>
	          </li>
	          <li><a class="nav-link scrollto" href="#services">극장</a></li>
	          <li><a class="nav-link scrollto" href="Notice_list.ad">공지</a></li>
              <li><a class="nav-link scrollto" href="MemberLoginForm.sm">로그인</a></li>
	          <li><button class="getstarted scrollto" href="Reserve.mv">빠른예매</button></li>
	        </ul>
	        <i class="bi bi-list mobile-nav-toggle"></i>
	      </nav><!-- .navbar -->
	
	    </div>
	  </header><!-- End Header -->
	</c:when>
	<c:otherwise>
		<header id="header" class="d-flex align-items-center">
	    <div class="container d-flex align-items-center">
			<nav id="navbar" class="navbar">
			
	    	<li class="dropdown">
              	<div style="margin-right: 100px;"><i class="fas fa-bars fa-2x"></i></div> 
              		<ul>
	          		<li class=""><h4>전체 메뉴</h4></li>
          			<li class="dropdown"><a href="MovieList.mv"><span>영화</span> <i class="bi bi-chevron-right"></i></a>
						<ul>
							<li><a href="MovieList.mv">박스오피스</a></li>
							<li><a href="ComingMovieList.mv">상영 예정작</a></li>
						</ul>
					</li>
	          		<li class="dropdown"><a href="Reserve.mv">예매<i class="bi bi-chevron-right"></i></a>
	          			<ul>
							<li><a href="#">박스오피스</a></li>
							<li><a href="#">현재 상영작</a></li>
							<li><a href="#">개봉 예정작</a></li>
						</ul>
	          		</li>
	          		<li class="dropdown"><a href="MyPageMain.my">마이페이지<i class="bi bi-chevron-right"></i></a>
	          			<ul>
							<li><a href="#">회원정보수정</a></li>
							<li><a href="#">예매내역</a></li>
							<li><a href="CouponList.my">쿠폰함</a></li>
							<li><a href="MovieLikeList.my">찜목록</a></li>
							<li><a href="ReviewList.my">리뷰내역</a></li>
							<li><a href="#">문의내역</a></li>
							<li><a href="#">회원탈퇴</a></li>
						</ul>
	          		</li>
	          		<li class="dropdown"><a href="#">이벤트<i class="bi bi-chevron-right"></i></a>
	          			<ul>
							<li><a href="#">박스오피스</a></li>
							<li><a href="#">현재 상영작</a></li>
							<li><a href="#">개봉 예정작</a></li>
						</ul>
	          		</li>
                    <li class="dropdown"><a href="#">고객센터<i class="bi bi-chevron-right"></i></a>
                    	<ul>
							<li><a href="#">박스오피스</a></li>
							<li><a href="#">현재 상영작</a></li>
							<li><a href="#">개봉 예정작</a></li>
						</ul>
                    </li>
                    <li class="dropdown"><a href="#">조원소개<i class="bi bi-chevron-right"></i></a>
                    	<ul>
							<li><a href="#">박스오피스</a></li>
							<li><a href="#">현재 상영작</a></li>
							<li><a href="#">개봉 예정작</a></li>
						</ul>
                    </li>
	          	</ul>
              </li>
	    	 </nav><!-- .navbar -->
	    	  <!-- 로고 -->
	    	  <h1 class="logo me-auto" style="margin: 0 auto;">
		    	 <a href="./">
		     		<img src="assets/img/logo.png">
		     	 </a>
		       <!-- 로고 -->
	      </h1>
	      <nav id="navbar" class="navbar">
	      <ul>
	          <li class="dropdown"><a href="MovieList.mv"><span>영화</span><i class="bi bi-chevron-down"></i></a>
	          	 <ul>
	          		<li><a href="MovieList.mv">박스오피스</a></li>
                    <li><a href="ComingMovieList.mv">상영 예정작</a></li>
	             </ul>
	          </li>
	        <li><a class="nav-link scrollto" href="#services">극장</a></li>
	        <li><a class="nav-link scrollto" href="Notice_list.ad">공지사항</a></li>
			<li class="dropdown"><a href="MyPageMain.my">${sessionScope.sId } 님<i class="bi bi-chevron-down"></i></a>
		   	 <ul>
					<li class="dropdown"><a href="#"><span>회원정보</span> <i class="bi bi-chevron-right"></i></a>
						<ul>
							<li><a href="#">회원정보수정</a></li>
							<li><a href="#">회원탈퇴</a></li>
						</ul>
					</li>
					<c:if test='${sessionScope.sId eq "admin"}'> 
						<li  class="dropdown"><a class="nav-link scrollto" href="Admin.ad">관리자페이지<i class="bi bi-chevron-right"></i></a>
							<ul>
								<li><a href="MemberList.ad">회원관리</a></li>
								<li><a href="AdminMovieList.ad">영화관리</a></li>
								<li><a href="AdminTheaterList.ad">극장관리</a></li>
								<li><a href="Notice_list.ad">공지관리</a></li>
							</ul>
						</li>
					</c:if>		
					<li><a href="#">예매내역</a></li>
					<li><a href="CouponList.my">쿠폰함</a></li>
					<li><a href="MovieLikeList.my">찜목록</a></li>
					<li><a href="ReviewList.my">리뷰내역</a></li>
					<li><a href="#">문의내역</a></li>
				</ul>
			</li>
	          <li><a class="nav-link scrollto" href="MemberLogout.sm">로그아웃</a></li>
	          <li><a class="getstarted scrollto" href="Reserve.mv">빠른예매</a></li>
	        </ul>
	      </nav><!-- .navbar -->
	
	    </div>
	  </header><!-- End Header -->
	</c:otherwise>
</c:choose>

<!-- ======= Header ======= -->
</body>
</html>