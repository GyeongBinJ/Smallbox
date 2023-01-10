package action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.cj.Session;

import svc.MovieLikeProService;
import vo.ActionForward;
import vo.MovieBean;

public class MovieLikeProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		ActionForward forward = null;
		
		// 세션객체의 sId 속성을 member_Id 변수에 저장
		HttpSession session = request.getSession();
		String member_id = (String)session.getAttribute("sId");
//		System.out.print(member_id);
		
		int movie_idx = Integer.parseInt(request.getParameter("movie_idx"));
//		System.out.print(movie_idx);
		
		try {
			MovieBean movie = new MovieBean();
			movie.setMovie_idx(movie_idx);
			
			// 찜에 필요한 정보 : 멤버 아이디와 영화 번호
			MovieLikeProService service = new MovieLikeProService();
			// 찜이 되어있는지 판별 (이미 찜이면 true / 찜 아니면 false)
			boolean isLike = service.selectLike(movie_idx, member_id);
			
			if(isLike) { // 찜이 되어있다면 찜 해제 작업 수행
				 
				boolean isCancelLikeSuccess = service.CancelMovieLike(movie_idx, member_id);
					
				response.setContentType("text/html; charset=UTF-8"); // setContentType을 설정해야 HTML 문서로 인식됨
					
				PrintWriter out = response.getWriter();
					
				out.println("찜"); // 찜해제 작업 수행 후 버튼을 찜으로 표기
//				}
			} else { // 찜이 되어있지 않다면 찜 작업 수행
				
				boolean isLikeSuccess = service.MovieLike(movie_idx, member_id);
//				
				response.setContentType("text/html; charset=UTF-8"); // setContentType을 설정해야 HTML 문서로 인식됨
					
				PrintWriter out = response.getWriter();
					
				out.println("찜해제"); // 찜작업 수행 후 버튼을 찜해제로 표기
					
//				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
//		forward = new ActionForward();
//		forward.setPath("MovieList.mv"); // 영화 목록으로 이동
//		forward.setRedirect(true);
		
		return forward;
	}

}
