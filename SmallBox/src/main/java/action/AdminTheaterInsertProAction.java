package action;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.Time;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import svc.AdminTheaterInsertProService;
import vo.ActionForward;
import vo.TheaterBean;

public class AdminTheaterInsertProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("AdminTheaterInsertProAction");
		ActionForward forward = null;
		
		try {
			// 전달받은 파라미터 TheaterBean 클래스 인스턴스 생성 후 저장
			TheaterBean theater = new TheaterBean();
			theater.setTheater_title(request.getParameter("theater_title"));
			theater.setTheater_date(Date.valueOf(request.getParameter("theater_date")));
			// 주의 ! time 객체의 형식은 xx:xx:xx 이므로 영화관 등록시 형식 맞춰야 함
			theater.setTheater_time(Time.valueOf(request.getParameter("theater_time")));
			theater.setTheater_seat_cnt(Integer.parseInt(request.getParameter("theater_seat_cnt")));
			
			// AdminTheaterInsertProService 클래스 인스턴스 생성 후
			// insertTheater() 메서드 호출하여 상영일정 등록 작업 요청
			// 파라미터 : TheaterBean , 리턴타입 : boolean(isInsertSuccess)
			AdminTheaterInsertProService service = new AdminTheaterInsertProService();
			boolean isInsertSuccess = service.insertTheater(theater);
			
			// 상영일정 등록 처리 결과 판별
			if(!isInsertSuccess) { // 상영일정 등록 실패
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('상영일정 등록 실패!')");
				out.println("history.back()");
				out.println("</script>");
			} else { // 상영일정 등록 성공
				forward = new ActionForward();
				forward.setPath("AdminTheaterList.ad"); // 상영 등록된 일정목록
				forward.setRedirect(true);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return forward; // AdminFrontController
	}

} // TheaterInsertAction 클래스 끝
