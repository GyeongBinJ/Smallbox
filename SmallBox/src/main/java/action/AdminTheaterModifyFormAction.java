package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import svc.AdminTheaterDetailService;
import vo.ActionForward;
import vo.TheaterBean;

public class AdminTheaterModifyFormAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("AdminTheaterModifyFormAction");
		ActionForward forward = null;
		
		// 상영일정 수정 폼 요청을 위한 극장번호 저장
		int theater_idx = Integer.parseInt(request.getParameter("theater_idx"));
		
		// 상영 영화 상세정보 조회 작업 요청
		// 주의! 중복된 부분이므로 AdminTheaterModifyFormService 클래스는 생성하지 않는다
		// AdminTheaterDetailService의 getTheater() 메서드 호출하여 해당 정보 불러오기
		// 파라미터 : theater_idx, 리턴타입 : TheaterBean(theater)
		AdminTheaterDetailService service = new AdminTheaterDetailService();
		TheaterBean theater = service.getTheater(theater_idx);
		
		// 뷰페이지로 데이터 전달을 위해 request 객체에 저장
		request.setAttribute("theater", theater);
		
		forward = new ActionForward();
		forward.setPath("admin/admin_theater_modify.jsp");
		forward.setRedirect(false);
		
		return forward; // AdminFrontController
	}

}
