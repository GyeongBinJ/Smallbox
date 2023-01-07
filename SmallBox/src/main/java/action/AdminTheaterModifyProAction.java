package action;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.Time;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import svc.AdminTheaterModifyProService;
import vo.ActionForward;
import vo.TheaterBean;

public class AdminTheaterModifyProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("AdminTheaterModifyProAction");
		ActionForward forward = null;
		
		// 상영일정 수정 작업을 위한 극장번호 저장
		int theater_idx = Integer.parseInt(request.getParameter("theater_idx"));
		
		try {
			// 수정되어 전달받은 파라미터를 저장하기 위해 TheaterBean 클래스 인스턴스 생성 후 저장
			TheaterBean theater = new TheaterBean();
			theater.setTheater_idx(theater_idx);
			theater.setTheater_title(request.getParameter("theater_title"));
			theater.setTheater_date(Date.valueOf(request.getParameter("theater_date")));
			// 주의 ! time 객체의 형식은 xx:xx:xx 이므로 영화관 등록시 형식 맞춰야 함
			theater.setTheater_time(Time.valueOf(request.getParameter("theater_time")));
			theater.setTheater_seat_cnt(Integer.parseInt(request.getParameter("theater_seat_cnt")));
	
			// AdminTheaterModifyProService - ModifyTheater() 호출
			// 파라미터 : TheaterBean(theater), 리턴타입 : boolean(isModifySuccess)
			AdminTheaterModifyProService service = new AdminTheaterModifyProService();
			boolean isModifySuccess = service.ModifyTheater(theater);

			if(!isModifySuccess) { // 수정 실패
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('수정 실패!')");
				out.println("history.back()");
				out.println("</script>");
				
			} else { // 수정 성공
				forward = new ActionForward();
				forward.setPath("AdminTheaterDetail.ad?theater_idx=" + theater.getTheater_idx());
				forward.setRedirect(true);
			}
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return forward;
	}

}
