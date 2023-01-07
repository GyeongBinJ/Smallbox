package action;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.MemberDAO;
import encrypt.MyMessageDigest;
import svc.MemberDeleteProService;
import vo.ActionForward;
import vo.MemberBean;

public class MemberDeleteProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		ActionForward forward = new ActionForward();
		System.out.println("액션비밀번호 : " + request.getParameter("member_passwd"));
		
        try {
			// 한글처리
			request.setCharacterEncoding("UTF-8");
			
			
			// 전달된 정보 저장
			MemberBean member = new MemberBean();
			member.setMember_id(request.getParameter("member_id"));
			
			// -------------------------------------------------------------------------------
			// 패스워드 암호화(해싱) 기능 추가
			// encrypt.MyMessageDigest 클래스 인스턴스 생성
			MyMessageDigest md = new MyMessageDigest("SHA-256");
			// MyMessageDigest 객체의 hashing() 메서드를 호출하여 암호화 수행
			// => 리턴되는 암호문(해싱된 패스워드)를 저장
			member.setMember_passwd(md.hashing(request.getParameter("member_passwd")));
			// -------------------------------------------------------------------------------
			
			MemberDeleteProService service = new MemberDeleteProService();
			boolean deleteCount = service.deleteMember(member);
			System.out.println("액션 : " + deleteCount);
			
			// 삭제 완료 알림창 띄우고 메인페이지 이동
			if (deleteCount == true) {
			    // 탈퇴가 완료되면 세션 초기화 및 메인으로 이동
				HttpSession session = request.getSession();
				session.invalidate(); // invalidate 세션 초기화 

				forward = new ActionForward();
				forward.setPath("./");
				forward.setRedirect(true);
				
			} else {
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
			    out.write("<script>");
			    out.write("alert('비밀번호가 일치하지 않습니다!');");
			    out.write("history.back();");			
			    out.write("</script>");
			    out.close();
			}
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
        return forward;
    }

}
