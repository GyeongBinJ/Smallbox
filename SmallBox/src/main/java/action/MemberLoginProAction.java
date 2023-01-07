package action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import encrypt.MyMessageDigest;
import svc.MemberLoginProService;
import vo.ActionForward;
import vo.MemberBean;

public class MemberLoginProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		ActionForward forward = null;
		
		try {
			
			MemberBean member = new MemberBean();
			member.setMember_id(request.getParameter("member_id"));
//			member.setMember_passwd(request.getParameter("member_passwd"));
			
//			// -------------------------------------------------------------------------------
			// 패스워드 암호화(해싱) 기능 추가
			// encrypt.MyMessageDigest 클래스 인스턴스 생성
			MyMessageDigest md = new MyMessageDigest("SHA-256");
			// MyMessageDigest 객체의 hashing() 메서드를 호출하여 암호화 수행
			// => 리턴되는 암호문(해싱된 패스워드)를 저장
			member.setMember_passwd(md.hashing(request.getParameter("member_passwd")));
//			// -------------------------------------------------------------------------------
			
			// MemberLoginProService - loginMember()
			// => 파라미터 : MemberBean 객체    리턴타입 : int(loginResult)
			MemberLoginProService service = new MemberLoginProService();
			boolean isRightUser = service.loginMember(member);
			
			if(isRightUser == false) { // 아이디 틀림
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('로그인에 실패하였습니다. 아이디와 비밀번호를 확인하여 주시길 바랍니다.')");
				out.println("history.back()");
				out.println("</script>");
			} else { // else if(loginResult == 1) 과 동일 = 로그인 성공
				// 세션 객체에 로그인 성공한 아이디를 "sId" 라는 속성명으로 저장
				// => 단, 서블릿 클래스에서 세션 객체에 직접 접근이 불가능함(내장 객체가 없음)
				//    따라서, request 객체로부터 세션 객체를 얻어와야 함 = getSession() 메서드
				//    (리턴타입 HttpSession 타입)
				HttpSession session = request.getSession();
				session.setAttribute("sId", member.getMember_id());
				
				// 메인페이지로 리다이렉트
				forward = new ActionForward();
				forward.setPath("./");
				forward.setRedirect(true);
			} 
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return forward;
	}

}



