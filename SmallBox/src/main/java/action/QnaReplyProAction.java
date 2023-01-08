package action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import svc.QnaReplyProService;
import svc.QnaWriteProService;
import vo.ActionForward;
import vo.QnaBean;

public class QnaReplyProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("QnaReplyProAction");
		
		ActionForward forward = null;
		
		HttpSession session = request.getSession();
		String member_id = (String)session.getAttribute("sId");
		String qna_subject = request.getParameter("qna_subject");
		String qna_content = request.getParameter("qna_content");
//		System.out.println("아이디테스트 : " + member_id);
		int qna_re_ref = Integer.parseInt(request.getParameter("qna_re_ref"));
		int qna_re_lev = Integer.parseInt(request.getParameter("qna_re_lev"));
		int qna_re_seq = Integer.parseInt(request.getParameter("qna_re_seq"));
		
		try {
			// 전달받은 파라미터 데이터를 QnaBean 클래스 인스턴스 생성 후 저장
			QnaBean qna = new QnaBean();
			qna.setMember_id(member_id);
			qna.setQna_subject(qna_subject);
			qna.setQna_content(qna_content);
//			System.out.println(member_id+qna_subject+qna_content);
			// 입력받지 않고 hidden 속성으로 전달한 ref, lev, seq 값도 저장 필수! 
			qna.setQna_re_ref(qna_re_ref);
			qna.setQna_re_lev(qna_re_lev);
			qna.setQna_re_seq(qna_re_seq);
			System.out.println(qna);
			// -------------------------------------------------------------------------
			// QnaReplyProService 클래스 인스턴스 생성 후
			// registReplyQna() 메서드를 호출하여 글쓰기 작업 요청
			// => 파라미터 : QnaBean 객체   리턴타입 : boolean(isWriteSuccess)
			QnaReplyProService service = new QnaReplyProService();
			boolean isWriteSuccess = service.registReplyQna(qna);
			
			// 답글 쓰기 요청 처리 결과 판별
			if(!isWriteSuccess) { // 실패 시
				// 자바스크립트 사용하여 "문의 답변 쓰기 실패!" 출력 후 이전페이지로 돌아가기
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('문의 답변 쓰기 실패!')");
				out.println("history.back()");
				out.println("</script>");
			} else { // 성공 시
				// 포워딩 정보 저장을 위한 ActionForward 객체 생성
				// 포워딩 경로 : QnaList.me, 포워딩 방식 : Redirect
				// => 페이지번호 전달
				forward = new ActionForward();
				forward.setPath("QnaList.my?pageNum=" + request.getParameter("pageNum"));
				forward.setRedirect(true);
			}
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return forward; //MypageFrontController 로 리턴
	}

}
