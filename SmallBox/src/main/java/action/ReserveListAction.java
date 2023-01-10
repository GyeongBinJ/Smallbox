package action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import svc.ReserveListService;
import vo.ActionForward;
import vo.PageInfo;
import vo.ReserveBean;

public class ReserveListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("ReserveListAction");
		
		ActionForward forward = null;
		// ---------------------------------------------------------
		//아이디 가져와서 변수에 저장
		// 세션에 저장해서 전달한 회원 아이디 받아오기
		HttpSession session = request.getSession();
		String sId = (String)session.getAttribute("sId");

		// 페이징 처리를 위한 변수 선언
		int listLimit = 10; // 한 페이지에서 표시할 게시물 목록을 10개로 제한
		int pageNum = 1; // 현재 페이지 번호 설정(pageNum 파라미터 사용)
		if(request.getParameter("pageNum")=="") {
		} else if(request.getParameter("pageNum")!=null) {
			pageNum = Integer.parseInt(request.getParameter("pageNum"));
		}
			System.out.println("pageNum : "+request.getParameter("pageNum"));
		int startRow = (pageNum - 1) * listLimit; // 조회 시작 행번호 계산
		// ---------------------------------------------------------
		// BoardListService 클래스 인스턴스 생성
		ReserveListService service = new ReserveListService();
		// BoardListService 객체의 getReserveList() 메서드를 호출하여 게시물 목록 조회
		// => 파라미터 : 검색어, 시작행번호, 목록갯수   리턴타입 : List<ReserveBean>(reserveList)
		List<ReserveBean> reserveList = service.selectReserveList(sId, startRow, listLimit);
		
		// ---------------------------------------------------------
		// 페이징 처리
		// 한 페이지에서 표시할 페이지 목록(번호) 갯수 계산
		// 1. ReserveListService - selectReserveListCount() 메서드를 호출하여 아이디가 sId인 회원의 게시물 수 조회(페이지 목록 계산에 사용)
		// => 파라미터 : 아이디   리턴타입 : int(listCount)
		int listCount = service.selectReserveListCount(sId);
		int pageListLimit = 10; // 한 페이지에서 표시할 페이지 목록을 3개로 제한
		int maxPage = listCount / listLimit 
						+ (listCount % listLimit == 0 ? 0 : 1); 
		int startPage = (pageNum - 1) / pageListLimit * pageListLimit + 1;
		int endPage = startPage + pageListLimit - 1;
		if(endPage > maxPage) {
			endPage = maxPage;
		}
		PageInfo pageInfo = new PageInfo(listCount, pageListLimit, maxPage, startPage, endPage);
		request.setAttribute("reserveList", reserveList);
		request.setAttribute("pageInfo", pageInfo);
		// ----------------------------------------------------------------------
		
		// ActionForward 객체 생성 후 mypage/reserved_list.jsp 페이지 포워딩 설정
		// => URL 및 request 객체 유지 : Dispatch 방식
		forward = new ActionForward();
		forward.setPath("mypage/mypage_reserved_list.jsp");
		forward.setRedirect(false); // 생략 가능
		
		return forward;
	}

}















