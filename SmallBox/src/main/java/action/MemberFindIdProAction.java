package action;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import svc.MemberFindIdService;
import vo.ActionForward;
import vo.MemberBean;

public class MemberFindIdProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		ActionForward forward = null;
//		System.out.println("MemberFindIdProAction");
		try {
			request.setCharacterEncoding("UTF-8");
			
			// 파라미터로 가져온 이름, 폰번호, 생년월일을 멤버빈 객체에 저장
			MemberBean member = new MemberBean();
			member.setMember_name(request.getParameter("name"));
			member.setMember_phone(request.getParameter("phone"));
			member.setMember_birth_date(Date.valueOf(request.getParameter("birth_date")));
			// 저장된 멤버빈 객체를 파라미터로 가지고 다니고 
			// id를 저장할 문자열 타입 변수 설정
			MemberFindIdService service = new MemberFindIdService();
			String id = service.FindIdMember(member); 
			if(id == "") { // 이름, 폰번호, 생년월일중 하나라도 틀렸다!
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('기입 정보 틀림!')");
				out.println("history.back()");
				out.println("</script>");
			} else {  // 기입 정보가 일치했다면! id 값 보여주기!
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<h3>" + id +  "</h3>");
		}
		}catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return forward;
	}

}
