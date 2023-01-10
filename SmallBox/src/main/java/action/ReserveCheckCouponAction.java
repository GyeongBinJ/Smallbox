package action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import svc.ReserveCheckCouponService;
import vo.ActionForward;
import vo.CouponBean;

public class ReserveCheckCouponAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("Action : MovieListAction");
		ActionForward forward = null;
		
		String sId = request.getParameter("sId");
		System.out.println("reserve check coupon의 sessionid : " + sId); // 잘 넘어옴
		
		//쿠폰 조회
		ReserveCheckCouponService service = new ReserveCheckCouponService();
		List<CouponBean> couponList = service.getCouponList(sId);
		request.setAttribute("couponList", couponList);
		System.out.println("pro action의 쿠폰리스트 : " + couponList);
		
		
		forward = new ActionForward();
		forward.setPath("reserve/couponList.jsp");
		forward.setRedirect(false);

		return forward;
	}

}
