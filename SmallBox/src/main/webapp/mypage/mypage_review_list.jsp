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
  
  <link href="assets/css/style.css" rel="stylesheet">
  <link href="assets/css/couponlist.css" rel="stylesheet">
   <!-- jquery -->
<script src="js/jquery-3.6.3.js"></script>
<script type="text/javascript">
	// 삭제버튼 클릭시
	function delete_comment(comment_idx) {
		
			$.ajax({
			      type: "POST",
			      url: "ReviewDelete.my", 
			      data: { 
			    	  comment_idx : comment_idx
			      }, 
			      success: function(result) {
			    	  
			    	  alert("삭제 하시겠습니까?");
			    	  
			    	  $(".inner-page").load(location.href+' .inner-page'); // 특정페이지 새로고침
			    	  
			      }
		      });

			}

</script>
<!-- jquery -->
  
  
</head>
<body>
	<!-- 비회원 접근 불가 -->
	<c:if test="${empty sessionScope.sId }">
		<script type="text/javascript">
		 	alert("접근 불가합니다.");
		 	history.back();
		</script>
	</c:if>
	<!-- 비회원 접근 불가 -->

	<header>
		<jsp:include page="../inc/top.jsp"></jsp:include>
	</header>
	
	 <!-- 본문 -->
	 <main id="main">
	 
	   <!-- ======= Breadcrumbs ======= -->
    <section class="breadcrumbs">
      <div class="container">

        <div class="d-flex justify-content-between align-items-center">
          <h2>리뷰내역</h2>
          <ol>
            <li><a href="index.html">Home</a></li>
            <li><a href="MyPageMain.my">MyPage</a></li>
            <li>리뷰내역</li>
          </ol>
        </div>

      </div>
    </section><!-- End Breadcrumbs -->
    
 	<!-- 본문 -->
	<!-- 사이드바 -->
    <div id="sd-in" style="width:1560px;margin: 0 auto;margin-bottom: 50px;">
   		<dropdown style="display: inline-block;width:200px;vertical-align: top !important; margin-top: 8em;">
  		<input id="toggle1" type="checkbox" checked>
  		<label class="animate">MENU<i class="fa fa-bars float-right"></i></label>
	  		<ul class="animate">
			    <li class="animate"><a href="MyPageMain.my">마이페이지</a></li>
			    <li class="animate"><a href="MemberModifyForm.sm">회원정보수정</a></li>
			    <li class="animate"><a href="Reserved.my">예매내역</a></li>
			    <li class="animate"><a href="CouponList.my">쿠폰함</a></li>
			    <li class="animate"><a href="MovieLikeList.my">찜목록</a></li>
			    <li class="animate"><a href="ReviewList.my">리뷰내역</a></li>
			    <li class="animate"><a href="QnaList.my">문의내역</a></li>
			    <li class="animate"><a href="MemberDeleteForm.sm">회원탈퇴</a></li>
	  		</ul>
  		</dropdown>
  	<!-- 사이드바 -->
	   
   
    <section class="inner-page" style="display: inline-block;">
		
		<table style="text-align: center;margin-top: 80px;">
		    <thead>
		    <tr>
		        <th width="150">포스터</th>
		        <th width="200">내용</th>
		        <th width="100">평점</th>
		        <th width="150">작성일</th>
		        <th width="150"></th>
		
		    </tr>
		    </thead>
	    <tbody>
			
			<!-- ReviewListProAction으로 부터 전달받은 request 객체의 reviewList(리뷰 정보)를 꺼내서 출력 -->
			<c:forEach var="comment" items="${reviewList }">
			<tr>
				<td><img src="<%=request.getContextPath() %>/upload/${comment.movie_real_picture}"  width="150" height="200"></td>
				<td>${comment.comment_content }</td>
				<td>${comment.comment_star }</td>
				<td>${comment.comment_date }</td>
				<td><input type="button" class="pagebtn"value="삭제" onclick="delete_comment('${comment.comment_idx }')"></td>
			</tr>	
			</c:forEach>
		</table>
	
			<section id="pageList" style="text-align: center;margin-left: 320px;"> <!-- 페이징 처리 영역 -->
	
			<!-- 만약, pageNum 파라미터가 비어있을 경우 pageNum 변수 선언 및 기본값 1로 설정 -->
			<c:choose>
				<c:when test="${empty param.pageNum }">
					<c:set var="pageNum" value="1" />
				</c:when>
				<c:otherwise>
					<c:set var="pageNum" value="${param.pageNum }" />
				</c:otherwise>
			</c:choose>
		
			
			<!-- 
			현재 페이지 번호(pageNum)가 1보다 클 경우에만 [이전] 링크 동작
			=> 클릭 시 BoardList.bo 서블릿 주소 요청하면서 
			   현재 페이지 번호(pageNum) - 1 값을 page 파라미터로 전달
			-->
			<c:choose>
				<c:when test="${pageNum > 1}">
					<input type="button" class="pagebtn" value="이전" onclick="location.href='ReviewList.my?pageNum=${pageNum - 1}'">
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
						<a href="ReviewList.my?pageNum=${i }">${i }</a>
					</c:otherwise>
				</c:choose>
			</c:forEach>
	
			<!-- 현재 페이지 번호(pageNum)가 총 페이지 수보다 작을 때만 [다음] 링크 동작 -->
			<c:choose>
				<c:when test="${pageNum < pageInfo.maxPage}">
					<input type="button" class="pagebtn" value="다음" onclick="location.href='ReviewList.my?pageNum=${pageNum + 1}'">
				</c:when>
				<c:otherwise>
					<input type="button" class="pagebtn" value="다음">
				</c:otherwise>
			</c:choose>
		</section> <!-- pageList section 끝 -->
    </section> <!-- inner section 끝 -->
    

  </main><!-- End #main -->
	  
	<!-- footer  -->
	 <footer>
		<jsp:include page="../inc/bottom.jsp"></jsp:include>
	</footer>
</body>
</html>