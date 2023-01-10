package action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import svc.AdminMovieListService;
import vo.ActionForward;
import vo.MovieBean;
import vo.PageInfo;
import vo.StarMovieBean;

public class MovieListProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		ActionForward forward = null;
		
		HttpSession session = request.getSession();
		String member_id = (String)session.getAttribute("sId");
		
		// [ 페이징 처리에 필요한 작업 ]
		int listLimit = 8; // 한 페이지에 출력될 게시물 수
		int pageNum = 1; // 현재 페이지 번호 설정 (pageNum 파라미터가 null이면 기본값 1로 설정)
		
		// pageNum 파라미터가 존재하면 해당값을 pageNum으로 설정
		if(request.getParameter("pageNum")!=null) {
			pageNum = Integer.parseInt(request.getParameter("pageNum"));
		}
		
		int startRow = (pageNum-1) * listLimit; // 현재 페이지의 첫 게시물의 행번호(=시작 글번호)
		
		// ------------------------------------------------
		
		// [ 검색 작업 ]
		// 파라미터로 전달받은 검색어(keyword) 가져와서 변수에 저장
		String keyword = request.getParameter("keyword");

		// 만약, 전달받은 검색어가 null이면 널스트링으로 변경 (일반 목록일 경우 전체 검색 수행)
		if(keyword==null) { // 검색어 칸이 비어있으면 null이 아닌 ""이 들어간 것
			keyword="";
		}
		
		// ------------------------------------------------
		
		// 영화 게시글 목록 조회 + 검색 기능
		AdminMovieListService service = new AdminMovieListService();
		List<StarMovieBean> starmovieList = service.getStarMovieList(keyword, startRow, listLimit);
		
		// 영화 목록 페이지 접속시 각 회원별 찜 목록 가져오기
		List<Integer> likeList = service.getLikeList(member_id);
		
		// ------------------------------------------------
		// [ 페이징 처리 ]
		// 전체 게시물 수 조회 (DB 작업 필요)
		int listCount = service.getMovieListCount(keyword);
		
		int pageListLimit = 10; // 한 페이지에 표시할 페이지 목록 수
		int maxPage = listCount/listLimit + (listCount%listLimit!=0? 1 : 0);
		int startPage = (pageNum-1) / pageListLimit * pageListLimit + 1;
		int endPage = startPage * pageListLimit - 1;
		if(endPage>maxPage) {
			endPage = maxPage;
		}
		
		// PageInfo 객체 생성 후 페이징 처리 정보 저장
		PageInfo pageInfo = new PageInfo(listCount, pageListLimit, maxPage, startPage, endPage);
		
		// --------------------------------------------------
		
		// 뷰페이지에서 사용하기 위해서 페이징 정보도 requset에 저장해서 넘겨야함
		request.setAttribute("starmovieList", starmovieList);
		request.setAttribute("pageInfo", pageInfo);
		// 찜리스트 저장
		request.setAttribute("likeList", likeList);
//		System.out.print(likeList);
		
		// 조회 작업이므로 if문으로 조건 판별없이 바로 포워딩 정보 저장
		forward = new ActionForward();
		forward.setPath("movie/movie_list.jsp");
		forward.setRedirect(false); // 목록 출력을 위해 requset 객체를 들고 뷰페이지로 이동해야함 -> Dispatch 방식
		
		return forward;
	}

}
