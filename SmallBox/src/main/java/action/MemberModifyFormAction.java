package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import svc.MemberModifyService;
import vo.ActionForward;
import vo.MemberBean;

public class MemberModifyFormAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		ActionForward forward = null;
		
		String sId = request.getParameter("member_id");
		
		MemberModifyService service = new MemberModifyService();
		MemberBean member = service.getMemberInfo(sId);
		
		request.setAttribute("member", member);
		
		forward = new ActionForward();
		forward.setPath("member/Member_modify_form.jsp");
		forward.setRedirect(false);
		
		return forward;
	}

}
