package action;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import svc.CouponDeleteProService;
import vo.ActionForward;

public class AdminCouponDeleteProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("CouponDeleteProAction");
		ActionForward forward = null;
		String coupon_end_date = request.getParameter("coupon_end_date");
		
		try {
			CouponDeleteProService service = new CouponDeleteProService();
				boolean isDeleteSuccess = service.deleteCoupon(coupon_end_date);
				if(!isDeleteSuccess) {
					response.setContentType("text/html; charset=UTF-8"); // jsp 첫줄 
					PrintWriter out = response.getWriter(); // html 태그 사용위한 메서드
					out.println("<script>"); // html 태그 등의 문자열 형태로 전달
					out.println("alert('삭제 실패!!')"); 
					out.println("history.back()"); 
					out.println("</script>"); 	
					
				} else {
					forward = new ActionForward();
					HttpSession session = request.getSession();
					String sId = (String)session.getAttribute("member_id");
					
					forward = new ActionForward();
					forward.setPath("CouponList.ad?member_id=" + request.getParameter("member_id"));
					forward.setRedirect(true);
				}
				
//			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return forward;
	}

}
