package action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import svc.MovieDeleteProService;
import vo.ActionForward;

public class MovieDeleteProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		ActionForward forward = null;
		
		int movie_idx = Integer.parseInt(request.getParameter("movie_idx"));
		
		try {
			MovieDeleteProService service = new MovieDeleteProService();
			boolean isDeleteSuccess = service.deleteMovie(movie_idx);
			
			if(isDeleteSuccess) {
				forward = new ActionForward();
				forward.setPath("AdminMovieList.ad");
				forward.setRedirect(true); // Redirect 방식으로 이동
				
			} else {
				response.setContentType("text/html; charset=UTF-8"); // setContentType을 설정해야 HTML 문서로 인식됨
				
				PrintWriter out = response.getWriter();
			
				out.println("<script>");
				out.println("alert('글삭제 실패!')");
				out.println("history.back()"); 
				out.println("</script>");
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		return forward;
	}

}
