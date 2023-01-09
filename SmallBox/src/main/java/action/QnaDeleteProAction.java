package action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import svc.QnaDeleteProService;
import svc.QnaDetailService;
import vo.ActionForward;
import vo.QnaBean;

public class QnaDeleteProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		ActionForward forward = null;
		// 글번호 가져오기
		int qna_idx = Integer.parseInt(request.getParameter("qna_idx"));
		System.out.println("qna_idx" + qna_idx);
		System.out.println(request.getParameter("pageNum"));

	try {
		QnaDeleteProService service = new QnaDeleteProService();
		// QnaDetailService 객체의 getQna() 메서드 호출하여 삭제할 파일명 조회
		// => 파라미터 : 글번호 리턴타입 : QnaBean(qna)
		QnaDetailService service2 = new QnaDetailService();
		QnaBean qna = service2.getQna(qna_idx);
		// => 주의! 레코드 삭제 전 정보 조회 먼저 수행해야한다!
		
		// QnaDeleteProService 클래스의 removeQna() 메서드를 호출하여 글 삭제 작업 수행
		//  => 파라미터 : 글번호(qna_idx)    리턴타입 : boolean(isDeleteSuccess)
		boolean isDeleteSuccess = service.removeQna(qna_idx);
		
		// 삭제 결과를 판별하여 실패 시 자바스크립트 오류 메세지 출력 및 이전페이지로 이동하고
		// 성공 시 ActionForward 객체를 통해 "QnaList.bo" 페이지로 포워딩(Redirect)
		// (=> URL 에 페이지 번호를 붙여서 요청)
		if(!isDeleteSuccess) { // 삭제 실패 시 
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('문의 삭제 실패!')");
			out.println("history.back()");
			out.println("</script>");
		} else { // 삭제 성공 시
			forward = new ActionForward();
			forward.setPath("QnaList.my?pageNum=" + request.getParameter("pageNum"));
			forward.setRedirect(true);
		}
		
	} catch (IOException e) {
		e.printStackTrace();
	}
		return forward;
	}

}













