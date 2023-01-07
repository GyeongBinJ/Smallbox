package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import svc.CommentDeleteService;
import svc.CommentGetmovieIdxService;
import vo.ActionForward;
import vo.CommentBean;

public class CommentDeleteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		ActionForward forward = null;
		
		int comment_idx = Integer.parseInt(request.getParameter("comment_idx"));
		System.out.println("delete Action의 comment_idx = " + comment_idx);
		
		CommentGetmovieIdxService gservice = new CommentGetmovieIdxService();
		CommentBean comment = gservice.getMovieIdx(comment_idx);
		
		int movie_idx = comment.getMovie_idx();
		System.out.println("comment delete action의 movie_idx = " + movie_idx);
		
		CommentDeleteService service = new CommentDeleteService();
		boolean isDeleteSuccess = service.deleteComment(comment_idx);
		
		forward = new ActionForward();
		forward.setPath("MovieDetail.mv?movie_idx=" + movie_idx);
		forward.setRedirect(true);
		
		return forward;
	}

}
