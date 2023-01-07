package action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import svc.AdminMovieListService;
import svc.AdminTheaterListService;
import vo.ActionForward;
import vo.MovieBean;
import vo.TheaterBean;

public class ReserveViewListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		ActionForward forward = null;
		
		AdminMovieListService service = new AdminMovieListService();
		AdminTheaterListService service2 = new AdminTheaterListService();
		
		List<MovieBean> movieList = service.getAdminMovieList();
		
		request.setAttribute("movieList", movieList);
		
		List<TheaterBean> theaterList = service2.getTheaterList();
		request.setAttribute("theaterList", theaterList);
		
//		System.out.println("action의 movielist : " + movieList);
		forward = new ActionForward();
		forward.setPath("reserve/reserve_view_Backup.jsp");
		forward.setRedirect(false); 
		
		return forward;
	}

}
