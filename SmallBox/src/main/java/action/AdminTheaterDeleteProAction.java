package action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import svc.AdminTheaterDeleteProService;
import vo.ActionForward;

public class AdminTheaterDeleteProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("AdminTheaterDeleteProAction");
		ActionForward forward = null;
		
		// 상영일정 삭제 작업을 위한 극장번호 저장
		int theater_idx = Integer.parseInt(request.getParameter("theater_idx"));
		
		try {
			// AdminTheaterDeleteProService - deleteTheater() 호출하여 상영일정 삭제
			// 파라미터 : theater_idx, 리턴타입 : boolean(isDeleteSuccess)
			AdminTheaterDeleteProService service = new AdminTheaterDeleteProService();
			boolean isDeleteSuccess = service.deleteTheater(theater_idx);
			
			if(!isDeleteSuccess) { // 삭제 실패
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('삭제 실패!')");
				out.println("history.back()");
				out.println("</script>");
				
			} else { // 삭제 성공
				forward = new ActionForward();
				forward.setPath("AdminTheaterList.ad"); // 극장 목록으로 이동
				forward.setRedirect(true);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return forward;
	}

}
