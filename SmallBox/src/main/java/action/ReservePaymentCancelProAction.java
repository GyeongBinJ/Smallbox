package action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import svc.ReserveCancelProService;
import vo.ActionForward;

public class ReservePaymentCancelProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("ReserveCancelProAction");
		ActionForward forward = null;
		
//		String member_id = request.getParameter("member_id");
//		int res_idx = Integer.parseInt(request.getParameter("res_idx")); // 결제 중 취소를 위한 idx 저장
		int res_num = Integer.parseInt(request.getParameter("res_num"));
		System.out.println(res_num);
		
		try {
			// ReserveCancelProService 클래스의 cancelReserve() 메서드를 호출하여 예약취소작업 수행
			//  => 파라미터 : 예약번호(res_num)    리턴타입 : boolean(isCancelSuccess)
			ReserveCancelProService service = new ReserveCancelProService();
			boolean isCancelSuccess = service.cancelReserve2(res_num);
			
				// 결제 취소 성공판별
				if(!isCancelSuccess) {// 결제 취소 실패
					response.setContentType("text/html; charset=UTF-8");
					PrintWriter out = response.getWriter();
					out.println("<script>");
					out.println("alert('결제 중 취소 실패!')");
					out.println("history.back()");
					out.println("</script>");
					
				} else { // 결제 취소 성공
					forward = new ActionForward();
					forward.setPath("main.jsp");
					forward.setRedirect(true);
				}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return forward;
	}
	
}
