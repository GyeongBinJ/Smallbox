<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta charset="UTF-8">
<title>Reserve</title>
<style type="text/css">
.reserve-container {
    margin-top: 20px;
    display: flex;
    justify-content: center;
    height: 800px;
    /* border: 1px solid #dddddd; */
}

.reserve-container>div {
    border: 1px solid #dddddd;
}

.reserve-title {
    border-bottom: 1px solid #dddddd;
    background-color: #444444;
    text-align: center;
    color: #dddddd;
    padding: 5px;
    font-size: 13px;
    font-weight: bold;
}

#movie-select {
	width: 200px; 
	padding: .8em .5em; 
	font-size: 15px;
	appearance: none;
}
</style>	            
<script src="js/jquery-3.6.3.js"></script>
<script type="text/javascript">
	$(function() {
// 		alert("예매!");
	});
</script>
</head>
<body>
<h1>예매</h1>
    <form action="reserve/reserve_seat.jsp">
	    <div class="reserve-container">
	        <div class="movie-part">
	            <div class="reserve-title">영화</div>
	            <div class="sort-wrapper">
	                <div class="sort-rate sort-selected">예매율순</div>
	            </div>
	            <div class="movie-list">
	            <select id="movie-select" onchange="categoryChange(this)">
					<option value="">영화를 선택하세요.</option>
	<!-- 			이 밑으로는 디비에서 불러온 값을 넣습니다. 반복문을 사용하여 작업해주시면 됩니다. -->
				<c:forEach var="movie" items="${movieList }">
					
					<option name="selectedMovie" value="${movie.movie_title }">	${movie.movie_grade }
							<a href="MovieDetail.mv?movie_idx=${movie.movie_idx }">${movie.movie_title }</a>
					</option>		
				</c:forEach>
					
	
			</select>
				</div>
	        </div>
	        <div class="theater-part">
	            <div class="reserve-title">극장</div>
	            <div>SmallBox 1관</div>
	        </div>
	        <div class="day-part">
	            <div class="reserve-title">날짜</div>
	            <div class="reserve-date"></div>
	        </div>
	        <div class="time-part">
	            <div class="reserve-title">시간</div>
	            <div class="theater-list">
		            <select id="time">
<!-- 		            여기서 해당되는 영화 이름의 시간만 불러올 수 있을까? -->
<!-- 					select onchange()함수를 이용하면 될거 같은데 여기서 적용이 안된다 -->
		            	<option value="">시간을 선택하세요.</option>
		            	<c:forEach var="theater" items="${theaterList }">
						<option name="selected_date" value="${theater.theater_date }">
							${theater.theater_title } ${theater.theater_date }
						</option>
					</c:forEach>
		            </select>
		            <select>
		            <c:forEach var="theater" items="${theaterList }">
						<option name="selected_time" value="${theater.theater_title }">
							 ${theater.theater_time }
						</option>
					</c:forEach>
		            
		            
		            </select>
	            </div>
	        </div>
	
	    </div>
	    
	    	<input type="hidden" name="reserve_title" value="">
	    	<input type="hidden" name="reserve_date" value="">
	    	<input type="hidden" name="reserve_time" value="">
	    	<span id=selectd_movie>선택한 영화: </span>
	    	<span>극장: small box 1관</span>
	    	<span id=selected_date>일시: </span>
	    	
	        <input type="submit" value="좌석 선택하기" class="movie-date-wrapper">
    </form>
</body>
</html>































