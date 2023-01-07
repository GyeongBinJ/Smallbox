package action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import svc.AdminTheaterListService;
import vo.ActionForward;
import vo.PageInfo;
import vo.TheaterBean;

public class AdminTheaterListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("AdminTheaterListAction");
		ActionForward forward = null;
		
		int listLimit = 10; // 한 페이지에 표시할 게시물 목록 10개로 제한
		int pageNum = 1; // 현재 페이지 번호 설정
		if(request.getParameter("pageNum") != null) {
			pageNum = Integer.parseInt(request.getParameter("pageNum"));
		}
		int startRow = (pageNum - 1) * listLimit;

		String keyword = request.getParameter("keyword");

		if(keyword == null) {
			keyword = "";
		}
		
		// 상영일정 목록 조회를 위해 AdminTheaterListService 인스턴스 생성 후
		// getTheaterList() 메서드 호출
		// 파라미터 : keyword, startRow, listLimit, 리턴타입 : List<TheaterBean>
		AdminTheaterListService service = new AdminTheaterListService();
		List<TheaterBean> theaterList = service.getTheaterList(keyword, startRow, listLimit);
		
		// 게시물 목록 갯수 조회
		int listCount = service.getTheaterListCount(keyword);
//				System.out.println(listCount);
		int pageListLimit = 10; // 한 페이지에서 표시할 페이지 목록을 3개로 제한
		int maxPage = listCount / listLimit + (listCount % listLimit == 0 ? 0 : 1); 
		int startPage = (pageNum - 1) / pageListLimit * pageListLimit + 1;
		int endPage = startPage + pageListLimit - 1;
		if(endPage > maxPage) {
			endPage = maxPage;
		}
		
		// PageInfo 객체 생성 후 페이징 처리 정보 저장
		PageInfo pageInfo = new PageInfo(listCount, pageListLimit, maxPage, startPage, endPage);
		
		// 글목록과 페이징 정보를 request 객체에 저장
		request.setAttribute("theaterList", theaterList);
		request.setAttribute("pageInfo", pageInfo);
		
		forward = new ActionForward();
		forward.setPath("admin/admin_theater_list.jsp");
		forward.setRedirect(false);
		
		return forward; // AdminFrontController
	}

}
