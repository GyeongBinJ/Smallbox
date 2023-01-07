package action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import svc.QnaWriteProService;
import vo.ActionForward;
import vo.QnaBean;

public class QnaWriteProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("QnaWriteProAction");
		
		ActionForward forward = null;
		
		HttpSession session = request.getSession();
		String member_id = (String)session.getAttribute("sId");
		String qna_subject = request.getParameter("qna_subject");
		String qna_content = request.getParameter("qna_content");
//		System.out.println("아이디테스트 : " + member_id);
		
		try {
			QnaBean qna = new QnaBean();
			qna.setMember_id(member_id);
			qna.setQna_subject(qna_subject);
			qna.setQna_content(qna_content);
			
			System.out.println("qna : "+qna);
			// -------------------------------------------------------------------------
			// QnaWriteProService 클래스 인스턴스 생성 후
			// registQna() 메서드를 호출하여 글쓰기 작업 요청
			// => 파라미터 : QnaBean 객체   리턴타입 : boolean(isWriteSuccess)
			QnaWriteProService service = new QnaWriteProService();
			boolean isWriteSuccess = service.registQna(qna);
			// 글쓰기 요청 처리 결과 판별
			if(!isWriteSuccess) { // 실패 시
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('1:1 문의 등록 실패!')");
				out.println("history.back()");
				out.println("</script>");
				// ActionForward 객체 생성하지 않음!! => null 값 전달
			} else { // 성공 시
				// 포워딩 정보 저장을 위한 ActionForward 객체 생성
				// 포워딩 경로 : QnaList.my, 포워딩 방식 : Redirect
				forward = new ActionForward();
				forward.setPath("QnaList.my");
				forward.setRedirect(true);
			}
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return forward; // QnaFrontController 로 리턴
	}

}










