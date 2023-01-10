package action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import svc.CancelMovieLikeProService;
import vo.ActionForward;

public class CancelMovieLikeProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		ActionForward forward = null;
		
		// 세션객체의 sId 속성을 member_Id 변수에 저장
		HttpSession session = request.getSession();
		String member_id = (String)session.getAttribute("sId");
		
		int movie_idx = Integer.parseInt(request.getParameter("movie_idx"));
		
		try {
			
			CancelMovieLikeProService service = new CancelMovieLikeProService();
			boolean isCancelLikeSuccess = service.CancelMovieLike(movie_idx, member_id);
			
			response.setContentType("text/html; charset=UTF-8"); // setContentType을 설정해야 HTML 문서로 인식됨
				
			PrintWriter out = response.getWriter();
				
//			out.println("넘겨줄게 업성요");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return forward;
	}

}
