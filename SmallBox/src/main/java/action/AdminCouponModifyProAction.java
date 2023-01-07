package action;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import svc.CouponInsertProService;
import svc.CouponModifyProService;
import vo.ActionForward;
import vo.CouponBean;

public class AdminCouponModifyProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("CouponModifyProAction");
		ActionForward forward = null;
		
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String member_id = request.getParameter("member_id");
		int coupon_idx = Integer.parseInt(request.getParameter("coupon_idx"));
		CouponBean coupon = new CouponBean();
		coupon.setMember_id(member_id);
		coupon.setCoupon_idx(coupon_idx);
		coupon.setCoupon_type(request.getParameter("coupon_type"));
		coupon.setCoupon_rate(Integer.parseInt(request.getParameter("coupon_rate")));
//		coupon.setCoupon_date(Date.valueOf(request.getParameter("coupon_date")));
		coupon.setCoupon_end_date(Date.valueOf(request.getParameter("coupon_end_date")));
//		System.out.println(coupon);
		
		CouponModifyProService service = new CouponModifyProService();
		boolean modifyCoupon = service.modifyCoupon(coupon);
		
		if(!modifyCoupon) { // 실패했을 경우
			try {
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('쿠폰정보 수정 실패!')");
				out.println("history.back();");
				out.println("/<script>");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else { // 성공했을 경우
			// 포워딩 정보를 저장할 ActionForward 인스턴스 생성 (forward)
			
			HttpSession session = request.getSession();
			String sId = (String)session.getAttribute("member_id");
			forward = new ActionForward();
			forward.setPath("CouponList.ad?member_id=" + request.getParameter("member_id"));
			forward.setRedirect(true);

		}
		
		
		return forward;

	}
}
