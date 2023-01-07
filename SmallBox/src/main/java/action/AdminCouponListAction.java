package action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import svc.CouponListService;
import vo.ActionForward;
import vo.CouponBean;

public class AdminCouponListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
//		System.out.println("CouponListAction");

		ActionForward forward = null;
		// CouponListService 객체를 통해 게시물 목록 조회 후
		// 조회 결과(List 객체)를 request 객체를 통해 admin_coupon_result.jsp 페이지로 전달
		CouponListService service = new CouponListService();
		String member_id = request.getParameter("member_id");
		List<CouponBean> couponList = service.getCouponList(member_id);
		
		// 글목록(List 객체)을 request 객체에 저장 - setAttribute()
				
		request.setAttribute("couponList", couponList);
		
		forward = new ActionForward();
		forward.setPath("admin/admin_coupon_result.jsp");
		forward.setRedirect(false);
		
		return forward;
	}

	


}
