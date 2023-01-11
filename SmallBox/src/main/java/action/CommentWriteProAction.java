package action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import svc.CommentContainsUserService;
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
				
		//리뷰 테이블 안에 SELECT member_id FROM comment WHERE movie_idx=? 
		//해서 조회가 된다면 1 리턴 이미 리뷰를 작성한 회원입니다. 히스토리 백
		// 조회가 안된다면 0리턴,  밑에 코드 실행 
				
		CommentContainsUserService cService = new CommentContainsUserService();
		int containsUser = cService.containsUser(movie_idx, member_id);

		if(containsUser > 0) { // 영화에 댓글 쓴 아이디가 조회 될 경우(=댓글 이미 썼을 경우)
			
			response.setContentType("text/html; charset=UTF-8");
			
			try {
				PrintWriter out = response.getWriter();
				
				out.println("<script>");
				out.println("alert('리뷰는 한 번만 작성할 수 있습니다!')");
				out.println("history.back()");
				out.println("</script>");
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		} else { // 영화에 댓글 쓴 아이디가 없을경우(=댓글을 쓴적이 없을경우)
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
			
		}
//		System.out.println("리뷰라이트프로액션의 cmt :" + cmt);
				
		return forward;
	}

}
