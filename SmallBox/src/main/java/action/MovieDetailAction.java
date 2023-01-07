package action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import svc.CommentListService;
import svc.CommentStarAverageService;
import svc.MovieDetailService;
import vo.ActionForward;
import vo.CommentBean;
import vo.MovieBean;

public class MovieDetailAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("Action : MovieListAction");
		ActionForward forward = null;
		
		//=====Movie Detail===Movie Detail Service로 이동==================================
		int movie_idx = Integer.parseInt(request.getParameter("movie_idx"));
		
		MovieDetailService service = new MovieDetailService();
		MovieBean movie = service.getMovieDetail(movie_idx);
		
		request.setAttribute("movie", movie);
		//===============================================================================
		
		
		//===Movie Review List===movie detail action 에서 Comment List Service로 이동========
		CommentListService cservice = new CommentListService();
		List<CommentBean> commentList = cservice.getCommentList(movie_idx);
		
		request.setAttribute("reviewList", commentList);
		//===============================================================================
		
		
		//===star Average===Movie Detail Action에서 Comment Star Average Service로 이동======
		CommentStarAverageService aservice = new CommentStarAverageService();
		double staravg = aservice.getStarAverage(movie_idx);
		
		request.setAttribute("staravg", staravg);
		//===============================================================================

		
		forward = new ActionForward();
		forward.setPath("movie/movie_detail.jsp");
		forward.setRedirect(false);
		
		return forward;
	}

}
