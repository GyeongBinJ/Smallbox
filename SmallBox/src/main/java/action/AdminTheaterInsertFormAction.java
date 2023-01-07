package action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import svc.AdminMovieListService;
import vo.ActionForward;
import vo.MovieBean;

public class AdminTheaterInsertFormAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("AdminTheaterInsertFormAction");
		ActionForward forward = null;
		
		// 상영 일정 등록을 위해 movie 테이블에서 영화목록 가져오기
		AdminMovieListService service = new AdminMovieListService();
		List<MovieBean> movieList = service.getAdminMovieList();
		
		// 뷰페이지로 데이터 전달을 위해 request 객체에 저장
		request.setAttribute("movieList", movieList);
		
		forward = new ActionForward();
		forward.setPath("admin/admin_theater_insert.jsp");
		forward.setRedirect(false);
		
		return forward;
	}

}
