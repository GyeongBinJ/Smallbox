package action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import svc.AdminMovieDetailService;
import vo.ActionForward;
import vo.MovieBean;
import vo.PageInfo;

public class AdminMovieDetailProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		ActionForward forward = null;
		
		int	pageNum = Integer.parseInt(request.getParameter("pageNum"));
		
		int movie_idx = Integer.parseInt(request.getParameter("movie_idx"));
		
		// 관리자 페이지에서 등록한 영화의 상세정보 출력
		// 각 영화번호에 맞는 상세정보를 출력해야 하기 때문에 파라미터로 movie_idx 전달
		AdminMovieDetailService service = new AdminMovieDetailService();
		MovieBean movie = service.getAdminMovieDetail(movie_idx);
		
		// 뷰페이지에서 데이터를 출력할때 사용하기 위해 request 객체에 MovieBean 객체 저장 
		request.setAttribute("movie", movie);
		
		forward = new ActionForward();
		forward.setPath("admin/admin_movie_view.jsp?pageNum=" + pageNum);
		forward.setRedirect(false); // 조회내용 출력을 위해 requset 객체를 들고 뷰페이지로 이동해야함 -> Dispatch 방식
		
		return forward;
	}

}
