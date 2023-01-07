package action;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import svc.MemberCheckIdService;
import vo.ActionForward;

public class MemberCheckIdAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		ActionForward forward = null;
		System.out.println("MemberCheckIdAction");
		
		
		try {
			request.setCharacterEncoding("UTF-8");
			
			String id = request.getParameter("id");
			
			MemberCheckIdService service = new MemberCheckIdService();
			boolean isId = service.memberCheckId(id);
			if(isId) {
				System.out.println("아이디 조회 완료");
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.print("true");
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
