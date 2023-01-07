package action;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import svc.AuthcodeService;
import vo.ActionForward;
import vo.AuthBean;

public class MemberAuthCheckProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		ActionForward forward = null;
		System.out.println("MemberAuthCheckProAction");
		
		
		try {
			request.setCharacterEncoding("UTF-8");
			
			String id = request.getParameter("id");
			String authCode = request.getParameter("authCode");
			
//			AuthBean auth = new AuthBean();
				
			AuthcodeService service = new AuthcodeService();
			String authcodeConfirm = service.authcodeConfirm(id, authCode);
			if(authCode == authcodeConfirm) {
				System.out.println("확인 완료!");
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				System.out.print(authcodeConfirm);
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return forward;
	}

}
