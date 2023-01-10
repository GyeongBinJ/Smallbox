package action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import svc.MovieLikeProService;
import svc.ReserveDetailService;
import svc.ReserveToReviewProService;
import vo.ActionForward;
import vo.MovieBean;
import vo.ReserveBean;

public class ReserveToReviewProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("ReserveToReviewProAction");
		
		ActionForward forward = null;
		
		HttpSession session = request.getSession();
		String member_id = (String)session.getAttribute("sId");
		// 상세정보 조회에 필요한 글번호 가져오기
		int res_idx = Integer.parseInt(request.getParameter("res_idx"));
		System.out.println("res_idx = " + res_idx);
		
		// => pageNum 파라미터는 현재 작업에서 실제로 활용되는 데이터가 아니므로
		//    다음 페이지 포워딩 시 URL 또는 request 객체에 함께 전달하기만 하면 된다!
		// => 또한, Dispatch 방식으로 포워딩 시 URL 이 유지되므로 파라미터값 가져오기 불필요
		
		// ReserveToReviewProService 인스턴스 생성 후 getReserve() 메서드 호출하여 게시물 조회 요청
		// => 파라미터 : 예약번호    리턴타입 : ReserveBean(reserve)
		ReserveToReviewProService service = new ReserveToReviewProService();
		int movie_idx = service.getMovie_idx(res_idx);
		System.out.println("movie_idx : "+ movie_idx);
		
		// 뷰페이지로 데이터 전달을 위해 request 객체에 저장
		session.setAttribute("movie_idx", movie_idx);
		// ActionForward 객체를 통해 영화상세 페이지 포워딩 설정
		// => URL 유지 및 request 객체 유지를 위해 Dispatch 방식 포워딩
		forward = new ActionForward();
		forward.setPath("MovieDetail.mv?movie_idx=" + movie_idx);
		forward.setRedirect(true);
		
		return forward;
	}
}
