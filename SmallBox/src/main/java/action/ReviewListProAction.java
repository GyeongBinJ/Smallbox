package action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import svc.ReviewListService;
import vo.ActionForward;
import vo.PageInfo;
import vo.PosterBean;

public class ReviewListProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		ActionForward forward = null;
		
		// 세션에 저장해서 전달한 회원 아이디 받아오기
		HttpSession session = request.getSession();
		String member_id = (String)session.getAttribute("sId");
		
		// [ 페이징 처리에 필요한 작업 ]
		int commentLimit = 5; // 한 페이지에 출력될 리뷰의 수
		int pageNum = 1; // 현재 페이지 번호 설정 (pageNum 파라미터가 null이면 기본값 1로 설정)
		
		// pageNum 파라미터가 존재하면 해당값을 pageNum으로 설정
		if(request.getParameter("pageNum")!=null) {
			pageNum = Integer.parseInt(request.getParameter("pageNum"));
		}
		
		int startRow = (pageNum-1) * commentLimit; // 현재 페이지의 첫 리뷰의 행번호(=시작 글번호)
		
		// ------------------------------------------------
		
		// 각 회원의 리뷰 내역을 조회하는 DB 작업을 요청하기 위해 서비스의 getReviewList() 호출
		ReviewListService service = new ReviewListService();
		List<PosterBean> reviewList = service.getReviewList(member_id, startRow, commentLimit);
		
		// ------------------------------------------------
		// [ 페이징 처리 ]
		// 각 회원이 가진 전체 리뷰 수 조회 (DB 작업 필요)
		int commentCount = service.getCommentListCount(member_id);
		
		int pageListLimit = 10; // 한 페이지에 표시할 페이지 목록 수
		int maxPage = commentCount/commentLimit + (commentCount%commentLimit!=0? 1 : 0);
		int startPage = (pageNum-1) / pageListLimit * pageListLimit + 1;
		int endPage = startPage * pageListLimit - 1;
		if(endPage>maxPage) {
			endPage = maxPage;
		}
		
		// PageInfo 객체 생성 후 페이징 처리 정보 저장
		PageInfo pageInfo = new PageInfo(commentCount, pageListLimit, maxPage, startPage, endPage);
		
		// --------------------------------------------------
		
		// request 객체에 CommentBean 객체를 저장해 뷰페이지로 전달
		request.setAttribute("reviewList", reviewList);
		// 뷰페이지에서 사용하기 위해서 페이징 정보도 requset에 저장해서 넘겨야함
		request.setAttribute("pageInfo", pageInfo);
		
		forward = new ActionForward();
		forward.setPath("mypage/mypage_review_list.jsp");
		forward.setRedirect(false);
		
		return forward;
	}

}
