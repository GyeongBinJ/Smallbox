package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import svc.NoticeDetailService;
import vo.ActionForward;
import vo.NoticeBean;

public class NoticeDeatilAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("NoticeDeatilAction");
		
		ActionForward forward = null;
		
		// 상세정보 조회에 필요한 글번호 가져오기
		int notice_idx = Integer.parseInt(request.getParameter("notice_idx"));
//		System.out.println("board_num = " + board_num);
		// => pageNum 파라미터는 현재 작업에서 실제로 활용되는 데이터가 아니므로
		//    다음 페이지 포워딩 시 URL 또는 request 객체에 함께 전달하기만 하면 된다!
		// => 또한, Dispatch 방식으로 포워딩 시 URL 이 유지되므로 파라미터값 가져오기 불필요
		
		// BoardDetailService 인스턴스 생성 후 getBoard() 메서드 호출하여 게시물 조회 요청
		// => 파라미터 : 글번호, 조회수 증가 여부(true)    리턴타입 : BoardBean(board)
		NoticeDetailService service = new NoticeDetailService();
		NoticeBean notice = service.getNotice(notice_idx, true);
//		System.out.println(board);
		
		// 뷰페이지로 데이터 전달을 위해 request 객체에 저장
		request.setAttribute("notice", notice);
		
		// ActionForward 객체를 통해 qna_board_view.jsp 페이지 포워딩 설정
		// => URL 유지 및 request 객체 유지를 위해 Dispatch 방식 포워딩
		forward = new ActionForward();
		forward.setPath("notice_view.jsp");
		forward.setRedirect(false);
		
		return forward;
	}

}
