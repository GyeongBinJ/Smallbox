package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import svc.CheckMovieLikeService;
import vo.ActionForward;

public class CheckMovieLikeProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		ActionForward forward = null;
		
		// ajax로 넘긴 영화 번호, 회원 아이디  받아오기
		HttpSession session = request.getSession();
		String member_id = (String)session.getAttribute("sId");
		int movie_idx = Integer.parseInt(request.getParameter("movie_idx"));
		
		CheckMovieLikeService service = new CheckMovieLikeService();
		boolean isLike = service.selectLike(movie_idx, member_id);
		
		// 이미 찜이면 true / 찜 아니면 false 리턴
		
		session.setAttribute("isLike", isLike);
		
		forward = new ActionForward();
		forward.setPath("movie/movie_list.jsp");
		forward.setRedirect(false); 
		
		return forward;
	}

}
