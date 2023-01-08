package action;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import svc.QnaListService;
import vo.ActionForward;
import vo.QnaBean;
import vo.PageInfo;

public class QnaListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("QnaListAction");
		
		ActionForward forward = null;
		
		HttpSession session = request.getSession();
		String member_id = (String)session.getAttribute("sId");
		System.out.println("member_id : " + member_id);
		// QnaListService 객체를 통해 게시물 목록 조회 후
		// 조회 결과(List 객체)를 request 객체를 통해 qna_list.jsp 페이지로 전달
		// ---------------------------------------------------------------------------
		// 페이징 처리를 위한 변수 선언
		int listLimit = 10; // 한 페이지에서 표시할 게시물 목록을 10개로 제한
		int pageNum = 1; // 현재 페이지 번호 설정(pageNum 파라미터 사용)
		if(request.getParameter("pageNum") != null) {
			pageNum = Integer.parseInt(request.getParameter("pageNum"));
		}
		int startRow = (pageNum - 1) * listLimit; // 조회 시작 행번호 계산
//		System.out.println("startRow = " + startRow);
		// 파라미터로 전달받은 아이디(sId) 가져와서 변수에 저장
		// QnaListService 클래스 인스턴스 생성
		QnaListService service = new QnaListService();
		// QnaListService 객체의 getQnaList() 메서드를 호출하여 게시물 목록 조회
		// => 파라미터 : 검색어, 시작행번호, 목록갯수   리턴타입 : List<QnaBean>(qnaList)
		List<QnaBean> qnaList;
		if(member_id.equals("admin")) {
			qnaList = service.selectAdminQnaList(startRow, listLimit);
		} else {
			qnaList = service.selectQnaList(member_id, startRow, listLimit);
		}
		// ---------------------------------------------------------
		// 페이징 처리
		// 한 페이지에서 표시할 페이지 목록(번호) 갯수 계산
		// 1. QnaListService - selectQnaListCount() 메서드를 호출하여 아이디가 sId인 회원의 게시물 수 조회(페이지 목록 계산에 사용)
		// => 파라미터 : 아이디   리턴타입 : int(listCount)
		if(member_id.equals("admin")) {
			int listCount = service.selectAdminQnaListCount();
			System.out.println("admin으로 접속했을 때 list 총 게시물 수 : " + listCount);
			// 2. 한 페이지에서 표시할 페이지 목록 갯수 설정
			int pageListLimit = 10; // 한 페이지에서 표시할 페이지 목록을 3개로 제한
			// 3. 전체 페이지 목록 수 계산
			int maxPage = listCount / listLimit 
							+ (listCount % listLimit == 0 ? 0 : 1); 
			// 4. 시작 페이지 번호 계산
			int startPage = (pageNum - 1) / pageListLimit * pageListLimit + 1;
			// 5. 끝 페이지 번호 계산
			int endPage = startPage + pageListLimit - 1;
			// 6. 만약, 끝 페이지 번호(endPage)가 전체(최대) 페이지 번호(maxPage) 보다
			//    클 경우, 끝 페이지 번호를 최대 페이지 번호로 교체
			if(endPage > maxPage) {
				endPage = maxPage;
			}
			// PageInfo 객체 생성 후 페이징 처리 정보 저장
			PageInfo pageInfo = new PageInfo(listCount, pageListLimit, maxPage, startPage, endPage);
			// ----------------------------------------------------------------------
			// 글목록(List 객체)과 페이징정보(PageInfo 객체)를 request 객체에 저장 - setAttribute()
			request.setAttribute("qnaList", qnaList);
			request.setAttribute("pageInfo", pageInfo);
		} else {
			int listCount = service.selectQnaListCount(member_id);
			int pageListLimit = 10; // 한 페이지에서 표시할 페이지 목록을 3개로 제한
			int maxPage = listCount / listLimit 
							+ (listCount % listLimit == 0 ? 0 : 1); 
			int startPage = (pageNum - 1) / pageListLimit * pageListLimit + 1;
			int endPage = startPage + pageListLimit - 1;
			if(endPage > maxPage) {
				endPage = maxPage;
			}
			PageInfo pageInfo = new PageInfo(listCount, pageListLimit, maxPage, startPage, endPage);
			request.setAttribute("qnaList", qnaList);
			request.setAttribute("pageInfo", pageInfo);
		}
		
		// ActionForward 객체 생성 후 qna/qna_list.jsp 페이지 포워딩 설정
		// => URL 및 request 객체 유지 : Dispatch 방식
		forward = new ActionForward();
		forward.setPath("mypage/qna_list.jsp");
		forward.setRedirect(false); // 생략 가능
		
		return forward;
	}

}















