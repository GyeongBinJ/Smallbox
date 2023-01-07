package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import svc.CouponListService;
import svc.MemberModifyService;
import svc.MovieLikeListService;
import svc.ReviewListService;
import vo.ActionForward;
import vo.MemberBean;

public class MyPageMainAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		ActionForward forward = null;
		
		// 세션에 저장해서 전달한 회원 아이디 받아오기
		HttpSession session = request.getSession();
		String member_id = (String)session.getAttribute("sId");
		
		// 1. 회원 정보 조회 -> 회원 이름 / 이메일 가져오기
		MemberModifyService memberservice = new MemberModifyService(); // 원래 관리자-회원목록조회에서 가져와야함
		MemberBean member = memberservice.getMemberInfo(member_id);
		
		// 2. 회원별 찜 개수 조회 
		MovieLikeListService movielikeservice = new MovieLikeListService();
		int movieCount = movielikeservice.getMovieListCount(member_id);
		
		// 3. 회원별 쿠폰 개수 조회
		CouponListService couponservice = new CouponListService();
		int couponCount = couponservice.getCouponListCount(member_id);
		
		// 4. 회원별 리뷰 개수 조회
		ReviewListService reviewservice = new ReviewListService();
		int commentCount = reviewservice.getCommentListCount(member_id);
		
		request.setAttribute("member", member);
		session.setAttribute("movieCount", movieCount);
		session.setAttribute("couponCount", couponCount);
		session.setAttribute("commentCount", commentCount);
		
		forward = new ActionForward();
		forward.setPath("mypage/mypage_main.jsp");
		forward.setRedirect(false);
		
		return forward;
	}

}
