package action;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import encrypt.MyMessageDigest;
import svc.MemberModifyService;
import vo.ActionForward;
import vo.MemberBean;

public class MemberModifyProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		// 수정 시 한글깨짐 방지용 UTF-8
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		ActionForward forward = null;
		
		MemberBean member = new MemberBean();

		member.setMember_name(request.getParameter("member_name"));
		member.setMember_id(request.getParameter("member_id"));
		member.setMember_email(request.getParameter("member_email"));
		member.setMember_phone(request.getParameter("member_phone"));
		
		MyMessageDigest md = new MyMessageDigest("SHA-256");
		member.setMember_passwd(md.hashing(request.getParameter("oldPasswd")));
		
		String newPasswd = request.getParameter("newPasswd");
		String newPasswdCheck = request.getParameter("newPasswdCheck");
		
		if(newPasswd != null && newPasswd != "") {
			if(newPasswd.equals(newPasswdCheck)) {
				response.setContentType("text/html; charset=UTF-8");
				member.setMember_passwd(md.hashing(request.getParameter("newPasswdCheck")));
			}
		}
		
		MemberModifyService service = new MemberModifyService();
		
		boolean updateMember = service.updateMember(member); 

		if(updateMember) {
			forward = new ActionForward();
			forward.setPath("./");
			forward.setRedirect(true);
			
		} else{
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out;
				try {
					out = response.getWriter();
					out.println("<script>");
					out.println("alert('회원 수정 실패!');");
					out.println("history.back()");
					out.println("</script>");
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return forward;
		
	}

}
