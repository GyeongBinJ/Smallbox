package action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import svc.ReserveListService;
import vo.ActionForward;
import vo.ReserveBean;

public class AdminReserveListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("AdminReserveListAction");
		ActionForward forward = null;
		
		// 관리자 메인에서 예매목록을 출력하기 위해 목록 조회
		ReserveListService service = new ReserveListService();
		// BoardListService 객체의 getReserveList() 메서드를 호출하여 모든 예매내용 조회
		List<ReserveBean> reserveList = service.getReserveList();
		
		request.setAttribute("reserveList", reserveList);
		
		// => URL 및 request 객체 유지 : Dispatch 방식
		forward = new ActionForward();
		forward.setPath("Admin.ad");
		forward.setRedirect(false); // 생략 가능
		
		return forward;
	}

}
