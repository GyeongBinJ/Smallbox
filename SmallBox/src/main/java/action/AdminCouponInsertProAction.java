package action;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import svc.CouponInsertProService;
import svc.CouponListService;
import vo.ActionForward;
import vo.CouponBean;

public class AdminCouponInsertProAction implements Action {
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("CouponInsertAction");
		ActionForward forward = null;
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		CouponBean coupon = new CouponBean();
		coupon.setMember_id(request.getParameter("member_id"));
		coupon.setCoupon_type(request.getParameter("coupon_type"));
		coupon.setCoupon_rate(Integer.parseInt(request.getParameter("coupon_rate")));
		coupon.setCoupon_end_date(Date.valueOf(request.getParameter("coupon_end_date")));
		
		
		CouponInsertProService service = new CouponInsertProService();
		boolean insertCoupon = service.insertCoupon(coupon);
		
		if(!insertCoupon) { // 실패했을 경우
			try {
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('쿠폰등록 실패!')");
				out.println("history.back();");
				out.println("/<script>");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else { // 성공했을 경우
			
			forward = new ActionForward();
			HttpSession session = request.getSession();
			String sId = (String)session.getAttribute("member_id");
			
			forward.setPath("CouponList.ad?member_id="+ request.getParameter("member_id"));
			forward.setRedirect(true);

		}
		
		
		return forward;
	}

}
