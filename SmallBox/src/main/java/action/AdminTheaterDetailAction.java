package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import svc.AdminTheaterDetailService;
import vo.ActionForward;
import vo.TheaterBean;

public class AdminTheaterDetailAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("AdminTheaterDetailAction");
		ActionForward forward = null;
		
		// 1개의 상영일정 상세 정보 조회에 필요한 극장번호
		int theater_idx = Integer.parseInt(request.getParameter("theater_idx"));
		
		// AdminTheaterDetailService 인스턴스 생성 후 getTheater() 메서드
		// AdminTheaterDetailService의 getTheater() 메서드 호출하여 1개의 상영일정 상세정보조회
		// 파라미터 : theater_idx, 리턴타입 : TheaterBean(theater)
		AdminTheaterDetailService service = new AdminTheaterDetailService();
		TheaterBean theater = service.getTheater(theater_idx);
		
		// 뷰페이지로 데이터 전달을 위해 request 객체에 저장
		request.setAttribute("theater", theater);
		
		// 포워딩 설정
		forward = new ActionForward();
		forward.setPath("admin/admin_theater_detail.jsp");
		forward.setRedirect(false);
		
		return forward; // AdminFrontController
	}

	
	

}
