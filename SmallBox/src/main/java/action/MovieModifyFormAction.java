package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import svc.AdminMovieDetailService;
import vo.ActionForward;
import vo.MovieBean;

public class MovieModifyFormAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		ActionForward forward = null;
		
		int movie_idx = Integer.parseInt(request.getParameter("movie_idx"));
		
		// 영화 상세정보를 뷰에 출력해야 하므로 상세정보 조회를 요청하는 AdminMovieDetailService 재사용
		AdminMovieDetailService service = new AdminMovieDetailService();
		MovieBean movie = service.getAdminMovieDetail(movie_idx);
		
		request.setAttribute("movie", movie);
		
		forward = new ActionForward();
		forward.setPath("admin/admin_movie_modify.jsp"); 
		forward.setRedirect(false);  // 조회내용 출력을 위해 requset 객체를 들고 뷰페이지로 이동해야함 -> Dispatch 방식
		
		return forward;
	}

}
