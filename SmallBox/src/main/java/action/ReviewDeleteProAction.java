package action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import svc.ReviewDeleteService;
import vo.ActionForward;

public class ReviewDeleteProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		ActionForward forward = null;
		
		int comment_idx = Integer.parseInt(request.getParameter("comment_idx"));
		
		try {
			ReviewDeleteService service = new ReviewDeleteService();
			boolean isDeleteSuccess = service.deleteReview(comment_idx);
			
			if(isDeleteSuccess) { // 리뷰 삭제 DB 작업 성공시
				forward = new ActionForward();
				forward.setPath("ReviewList.my");
				forward.setRedirect(true); // Redirect 방식으로 이동
				
			} else { // 리뷰 삭제 DB 작업 실패시
				response.setContentType("text/html; charset=UTF-8"); // setContentType을 설정해야 HTML 문서로 인식됨
				
				PrintWriter out = response.getWriter();
			
				out.println("<script>");
				out.println("alert('글삭제 실패!')");
				out.println("history.back()"); 
				out.println("</script>");
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return forward;
	}

}
