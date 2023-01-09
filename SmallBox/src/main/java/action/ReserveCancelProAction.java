package action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import svc.ReserveCancelProService;
import svc.ReserveDetailService;
import vo.ActionForward;
import vo.ReserveBean;

public class ReserveCancelProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("ReserveCancelProAction");
		
		ActionForward forward = null;
		
		String sId = request.getParameter("member_id");
		
		int res_idx = Integer.parseInt(request.getParameter("res_idx"));
		String res_date = (String)request.getParameter("res_date");
		try {
			//예약 취소 가능여부(=시간 확인) 판별 요청
		
			ReserveCancelProService service = new ReserveCancelProService();
			boolean isTimeOk = service.isTimeOk(res_idx);
			System.out.println(isTimeOk);
			
			//취소 할 수 없는 시간(=전날까지만 예매취소 할 수 있음!)일때
			if(!isTimeOk) {
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('영화 상영일 이전까지만 취소할 수 있습니다.')");
				out.println("history.back()");
				out.println("</script>");
			} else {
				//취소 가능
				ReserveDetailService service2 = new ReserveDetailService();
				ReserveBean reserve = service2.getReserve(res_idx);
				
				// ReserveCancelProService 클래스의 cancelReserve() 메서드를 호출하여 예약취소작업 수행
				//  => 파라미터 : 예약번호(res_idx)    리턴타입 : boolean(isCancelSuccess)
				boolean isCancelSuccess = service.cancelReserve(res_idx);
				
				//취소 결과 판별
				if(!isCancelSuccess) {//취소실패
					response.setContentType("text/html; charset=UTF-8");
					PrintWriter out = response.getWriter();
					out.println("<script>");
					out.println("alert('예약 취소 실패!')");
					out.println("history.back()");
					out.println("</script>");
				} else {//취소성공
					forward = new ActionForward();
					forward.setPath("Reserved.my?pageNum=" + request.getParameter("pageNum"));
					forward.setRedirect(true);
				}
			}
			
	} catch (IOException e) {
		e.printStackTrace();
	}
		return forward;
	}
}
