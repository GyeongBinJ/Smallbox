<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
	var locked = 0;
	var i;
	var image;
	var el;
	
	function mouseover(star) {
		if (locked==1)
			return;
		
		for (i = 1; i <= star; i++){
			image = 'image' + i;
			el = document.getElementById(image);
			el.src = "image/star1.png";
		} // ~~~ for end~~~
		
	} // ~~~~function mouseover end~~~~
	
	function mouseout(star) {
		if (locked==1)
			return;
		
		for(i = 1; i <=star;i++){
			image = 'image'+i;
			el = document.getElementById(image);
			el.src = "image/star0.png";
		}// ~~~ for end~~~
	} // ~~~~function mouseout end~~~~

	function lock(star) {
		mouseover(star);
		locked=1;
	}  // ~~~~function lock end~~~~
	
	function onClk(star) {
		lock(star);
		alert("별점 : " + star);
		document.commentForm.star.value=star;
	} // ~~~~function onClk end~~~~
	
	function confirmDelete(comment_idx) {
		let result = confirm("댓글을 삭제 하시겠습니까?");
		
		if(result) {
			location.href="DelComment.mv?comment_idx=" + comment_idx;
		}
	}
	
	
</script>
</head>
<body>
	<header>
		<jsp:include page="../inc/top.jsp"></jsp:include>
	</header>
	
	<h1>영화 상세 정보</h1>
	
		<table border="1">
		<tr>
			<th width="150">영화명</th>
			<th width="150">등급</th>
			<th width="100">장르</th>
			<th width="150">개봉 날짜</th>
			<th width="100">상영시간(runtime)</th>
			<th width="150">줄거리</th>
			<th width="150">등장인물</th>
			<th width="150">영화 포스터</th>
			<th width="50">누적 관람객 수</th>
		</tr>
		
		<tr>
			<td>${movie.movie_title }</td>
			<td>${movie.movie_grade }</td>
			<td>${movie.movie_genre }</td>
			<td>${movie.movie_open_date }</td>
			<td>${movie.movie_runtime }</td>
			<td>${movie.movie_intro }</td>
			<td>${movie.movie_actors }</td>
			<td>${movie.movie_picture }</td>
			<td>${movie.movie_viewer }</td>
		</tr>
		</table>
		<hr>
		<!-- 리뷰 작성 영역, 임시로 누구나 작성할 수 있도록 하고 이후에 예매 내역에 해당 영화 제목과 일치하는 제목이 있는
				     id만 작성할 수 있도록 함(지금 해당 내용을 작성하실 수 있으면 해보시는 것도 좋을 것 같아요!
				     review_writePro.jsp파일은 개인이 만들기! -->
		<h2>영화 리뷰 쓰기</h2>
	
		<c:set var="sId" value="${sessionScope.sId }" />

<%-- 		session아이디에 저장한 session id : <c:out value="${sId}" /> --%>
		<!-- 댓글 영역 session Id 가 있을 경우 표시됨 -->
		<c:if test="${not empty sId}">
		
		<form action="CommentWritePro.mv" method="post" name ="commentForm">
		<table border="1">
			<tr>
				<td width="150" height="20">
					<div id="rating" align="center">
						<span>
							<img id="image1" onmouseover=mouseover(1) onclick=onClk(1) onmouseout=mouseout(1) src="image/star0.png" width="20" height="20">
							<img id="image2" onmouseover=mouseover(2) onclick=onClk(2) onmouseout=mouseout(2) src="image/star0.png" width="20" height="20">
							<img id="image3" onmouseover=mouseover(3) onclick=onClk(3) onmouseout=mouseout(3) src="image/star0.png" width="20" height="20">
							<img id="image4" onmouseover=mouseover(4) onclick=onClk(4) onmouseout=mouseout(4) src="image/star0.png" width="20" height="20">
							<img id="image5" onmouseover=mouseover(5) onclick=onClk(5) onmouseout=mouseout(5) src="image/star0.png" width="20" height="20">
						<!-- 	함수명 onclick 으로 하니까 인식을 못하네요; -->
						</span>
					</div>
					<input type="hidden" name="star" >
				</td>
				<td width="700">댓글 : <input type="text" name = "comment_content"></td>
				
				<td width="70">
					<input type="hidden" name="movie_title" value="${movie.movie_title }">	
					<input type="hidden" name="movie_idx" value="${movie.movie_idx }">	
					<input type="hidden" name="member_id" value="${sId }">	
					<input type="submit" name="submit" value="등록">
				</td>			
			</tr>
		
		</c:if>
		<!-- 댓글 영역 session Id 가 있을 경우 표시됨 -->
		
		
		
		</table>
	
	</form>
	<hr>
	
	<!--평균평점과 영화 제목 표시 영역 -->
	<h3>평균 평점 : ${staravg }</h3>
	
	<hr>
	
	<!-- replyViewArea 영역(댓글 표시 영역) -->
	<div id="reviewArea">
		<table border="1">
			<tr>
				<td width="150">별점</td>
				<td width="150">작성자</td>			
				<td width="500">내용</td>			
				<td width="150">작성일</td>			
<!-- 				<td>수정</td>			 -->
				<td>삭제</td>			
			</tr>
		
		<c:forEach var="review" items="${reviewList }">
			<tr>
			<c:set var="member_id" value="${review.member_id }" />
			<c:set var="sId" value="${sessionScope.sId }" />
				<td>${review.comment_star }</td>
				<td>${member_id }</td>
				<td>${review.comment_content }</td>
				<td>${review.comment_date }</td>
<!-- 				session id 와 리뷰 쓴 아이디가 같을 때 삭제 버튼 표시 -->
				<c:if test="${sId eq member_id }">	
<!-- 				<td> -->
<%-- 					<input type="button" value="수정" onclick="location.href='DelComment.mv?comment_idx=${review.comment_idx }&member_id=${review.member_id }'"> --%>
<!-- 				</td> -->
				<td>
					<input type="button" value="삭제" onclick="confirmDelete('${review.comment_idx }')">
				</td>
				</c:if>
			</tr>
		</c:forEach>
		</table>
</div>
</body>
</html>














