package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import svc.NoticeDetailService;
import vo.ActionForward;
import vo.NoticeBean;

public class NoticeModifyFormAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		ActionForward forward = null;
		
		int notice_idx = Integer.parseInt(request.getParameter("notice_idx"));
		System.out.println(notice_idx);
		
		NoticeDetailService service = new NoticeDetailService();
		NoticeBean notice = service.getNotice(notice_idx, false);
		
		System.out.println(notice);
		
		request.setAttribute("notice", notice);
		
		forward = new ActionForward();
		forward.setPath("admin/admin_notice_modify.jsp");
		forward.setRedirect(false);
		
		return forward;
	}

}
