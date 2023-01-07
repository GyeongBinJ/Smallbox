package action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import svc.CommentWriteProServive;
import vo.ActionForward;
import vo.CommentBean;

public class CommentWriteProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("Action : CommentWriteProAction");
		ActionForward forward = null;
		
		//일단 변수 만들었음 확인을 위해
		String member_id = request.getParameter("member_id");
		int star = Integer.parseInt(request.getParameter("star"));
		String comment_content = request.getParameter("comment_content");
		int movie_idx = Integer.parseInt(request.getParameter("movie_idx"));
		String title = request.getParameter("movie_title");
						
//		System.out.println("리뷰 액션의 아이디 : " + member_id);
//		System.out.println("리뷰 액션의 별 : " + star);
//		System.out.println("리뷰 액션의 댓글 : " + comment_content);
//		System.out.println("리뷰 액션의 영화번호 : " + movie_idx);
//		System.out.println("리뷰 액션의 영화제목 : " + title);
				
		CommentBean cmt = new CommentBean();
		cmt.setMovie_idx(movie_idx);
		cmt.setMember_id(member_id);
		cmt.setComment_star(star);
		cmt.setComment_content(comment_content);
				
//		System.out.println("리뷰라이트프로액션의 cmt :" + cmt);
				
		CommentWriteProServive service = new CommentWriteProServive();
				
		boolean isinsertCmtSuccess = service.insertComment(cmt);
				
		if(!isinsertCmtSuccess) { // 리뷰 쓰기 실패 시
			response.setContentType("text/html; charset=UTF-8");
			
			try {
				PrintWriter out = response.getWriter();
				
				out.println("<script>");
				out.println("alert('리뷰 쓰기 실패!')");
				out.println("history.back()");
				out.println("</script>");
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		} else { // 성공 시
			forward = new ActionForward();
			forward.setPath("MovieDetail.mv?movie_idx="+movie_idx);
			forward.setRedirect(true);
		}
				
		return forward;
	}

}
