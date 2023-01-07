package action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import svc.CouponListService;
import svc.MovieLikeListService;
import vo.ActionForward;
import vo.CouponBean;
import vo.MovieBean;
import vo.MovieLikeBean;
import vo.PageInfo;

public class MovieLikeListProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		ActionForward forward = null;
		
		HttpSession session = request.getSession();
		String member_id = (String)session.getAttribute("sId");
		
		// [ 페이징 처리에 필요한 작업 ]
		int movieLimit = 8; // 한 페이지에 출력될 영화의 수
		int pageNum = 1; // 현재 페이지 번호 설정 (pageNum 파라미터가 null이면 기본값 1로 설정)
		
		// pageNum 파라미터가 존재하면 해당값을 pageNum으로 설정
		if(request.getParameter("pageNum")!=null) {
			pageNum = Integer.parseInt(request.getParameter("pageNum"));
		}
		
		int startRow = (pageNum-1) * movieLimit; // 현재 페이지의 첫 영화의 행번호(=시작 글번호)
		
		// ------------------------------------------------
		
		// 각 회원 아이디(member_id)가 찜한 영화의 정보 가져오기
		// movie_like 테이블의 movie_idx와 movie 테이블의 movie_idx를 조인하는 작업 요청
		MovieLikeListService service = new MovieLikeListService();
		List<MovieBean> likeList = service.getMovieLikeList(member_id, startRow, movieLimit);
		
		// ------------------------------------------------
		// [ 페이징 처리 ]
		// 각 회원이 찜한 영화 수 조회 (DB 작업 필요)
		int movieCount = service.getMovieListCount(member_id);
		
		int pageListLimit = 10; // 한 페이지에 표시할 페이지 목록 수
		int maxPage = movieCount/movieLimit + (movieCount%movieLimit!=0? 1 : 0);
		int startPage = (pageNum-1) / pageListLimit * pageListLimit + 1;
		int endPage = startPage * pageListLimit - 1;
		if(endPage>maxPage) {
			endPage = maxPage;
		}
		
		// PageInfo 객체 생성 후 페이징 처리 정보 저장
		PageInfo pageInfo = new PageInfo(movieCount, pageListLimit, maxPage, startPage, endPage);
		
		// --------------------------------------------------
		
		// 뷰페이지에서 사용하기 위해서 페이징 정보도 requset에 저장해서 넘겨야함
		request.setAttribute("pageInfo", pageInfo);
		// MovieBean 객체에 저장된 movie 테이블의 정보를 request의 likeList 속성에 저장해서 view로 전달
		// 찜 목록에 뿌릴거라서 이름을 likeList라고 했는데 헷갈리시면 바꿔도 돼요 
		// 이름은 likeList지만 안에 든 정보는 MovieBean(영화정보) 입니다~~!
		request.setAttribute("likeList", likeList);
		
		forward = new ActionForward();
		forward.setPath("mypage/movie_like_list.jsp");
		forward.setRedirect(false);
		
		return forward;
	}

}
