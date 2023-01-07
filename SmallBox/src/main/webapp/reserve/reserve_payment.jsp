<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>   
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>  
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>결제하기</title>
<style type="text/css">
input[type=checkbox] {
	display: none;
}
input[type=checkbox] + label{
	display: inline-block;
	border: 1px solid;
	text-align: center;
	line-height: 30px;
	margin-bottom: 5px;
	width: 130px;
	height: 30px;
}
input[type=checkbox]:checked + label{
	background-color: pink;
}

</style>

</head>
<body>
<!-- 받아온 정보 변수에 저장 -->
<c:set var="theater_idx" value="${param.theater_idx }" />
<c:set var="selectedSeat" value="${param.selectedSeat }" />
<c:set var="sId" value="${param.sId }" />
<c:set var="movie_title" value="${param.movie_title }" />
<c:set var="reserved_date" value="${param.reserved_date }" />
<c:set var="selected_time" value="${param.selected_time }" />
<c:set var="select_number_adult" value="${param.select_number_adult }" />
<c:set var="select_number_teen" value="${param.select_number_teen }" />
<c:set var="select_number_elderly" value="${param.select_number_elderly }" />

<!-- 받아온 정보 변수에 저장 끝 -->

<h1>결제</h1>
<!-- 예매한 영화 정보 표시 영역 -->
${movie_title }<br>
극장 번호 : ${theater_idx }<br>
id : ${sId }<br>
<hr>
부산 | SmallBox 1관 <!-- 상영관 고정 -->
${reserved_date } | ${selected_time }<br>
<hr>
좌석 | ${selectedSeat }
<hr>
<!-- 받아온 인원 수 표시 영역 -->
<!-- 성인, 청소년, 우대 요금으로 나눠서 계산, 요금 기억이 안나서 내맘대로 함 , 선택 한 인원이 있을 때 표시-->
<c:if test="${select_number_adult gt 0}">
		성인 : ${select_number_adult } 명
</c:if>
<c:if test="${select_number_teen gt 0}">
		청소년 : ${select_number_teen } 명
</c:if>
<c:if test="${select_number_elderly gt 0}">
		우대 : ${select_number_elderly } 명
</c:if>
<hr>
<!-- 할인 전 최종 금액 -->
<c:set var="priceBeforeDc" value="${(select_number_adult * 15000) + (select_number_teen * 13000) + (select_number_elderly * 8000)}" />
금액  |  ${priceBeforeDc }
<hr>
<!-- 예매한 영화 정보 표시 영역 끝-->

<!-- 쿠폰 사용 영역 -->
<div>
쿠폰 할인 적용
<form action="ReserveCheckCoupon.mv" method="post">
	<input type="hidden" name="sId" value="${sId }">
 	<input type="submit" value="조회하기">
<!--
	조회하기 누르면 컨트롤러로 이동 ReserveCheckCoupon.mv 매핑 주소 확인 후 ReserveCheckCouponAction으로 이동
	ReserveCheckCouponAction에서 ReserveCheckCouponService로 이동
	ReserveCheckCouponService에서 CouponDao의 getUserCouponList 메서드 호출
	String sql = "SELECT * FROM coupon WHERE member_id = ?";
	action에서 
	List<CouponBean> couponList = service.getUserCouponList(sId);
	request.setAttribute("couponList", couponList);
-->
</form>
<hr>
<!-- 쿠폰 조회 결과 영역 -->
	쿠폰 명  | 할인율	|<br>
	-------+--------+---<br>
	웰컴 쿠폰 | 10%	|사용<br>
	-------+--------+---<br>
	생일 쿠폰 | 20%	|사용<br>
	-------+--------+---<br>
<!-- 쿠폰 조회 결과 영역 -->
<!--
<table>
	<tr>
		<td>쿠폰</td>
		<td>할인율</td>
		<td></td>
	</tr>
	
	<c:forEach var="coupon" items="${couponList }">
	<tr>
		<td>${coupon.coupon_type }</td>
		<td>${coupon.coupon_rate }</td>
		<td>
			<input type="button" value="사용" onclick="confirmUse('${coupon.coupon_idx }')">
		</td>
	</tr>
	</c:forEach>
</table>
			
-->
		<script type="text/javascript">
		
// 		function confirmUse(coupon_idx) {
// 			let result = confirm("쿠폰을 사용하시겠습니까?");
			//쿠폰 사용한다하면 쿠폰 삭제작업 진행
// 			if(result) {
				//쿠폰 사용한다 하면 할인율(조회 필요) discout변수에 넣어 넘기기
// 				let dc = 0; //할인율 받아오기
// 				document.payment.discount.value = dc;
				//쿠폰 삭제 매핑주소로 이동
// 				location.href="UseCoupon.ad?coupon_idx=" + coupon_idx;
// 			}
// 		}
		
		</script>
</div>
<!-- 쿠폰 사용 영역 끝-->

<!-- 결제창으로 넘길 영역 -->
<!-- 할인금액 변수 -->
<form action="ReservePaymentPro.mv" method="post" name="payment">
<fmt:parseNumber var="discountPrice" value="${priceBeforeDc * discount * 0.1}" integerOnly="true" />
할인 적용  |  ${discountPrice }
<hr>
<!-- 최종 결제 금액 변수 -->
<c:set var="price" value="${priceBeforeDc - discountPrice }" />
최종 결제 금액  |  ${price }
<hr>


결제 수단 선택 &nbsp;&nbsp;
<input type="checkbox" checked="checked" value="credit" id="byCredit">
<label for="byCredit">신용/체크카드</label>
<input type="checkbox" disabled="disabled" value="phone" id="byPhone">
<label for="byPhone">휴대폰결제</label>
<input type="checkbox" disabled="disabled" value="fast" id="byFast">
<label for="byFast">간편결제</label>
<input type="checkbox" disabled="disabled" value="cash" id="byCash">
<label for="byCash">내통장결제</label>

<!-- hidden으로 넘길 때 reserve 테이블에 있는 변수랑 같이 맞춤 -->
<input type="hidden" name="theater_idx" value="${theater_idx }">
<input type="hidden" name="theater_title" value="${movie_title }">
<input type="hidden" name="member_id" value="${sId }">
<input type="hidden" name="res_date" value="${reserved_date }">
<input type="hidden" name="res_time" value="${selected_time }">
<input type="hidden" name="res_seat" value="${selectedSeat }">
<input type="hidden" name="res_price" value="${price }">
<hr>
<input type="submit" value="결제">
</form>
<!-- 결제창으로 넘길 영역 끝 -->


</body>
</html>