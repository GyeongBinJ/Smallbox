package action;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import svc.MovieInsertProService;
import vo.ActionForward;
import vo.MovieBean;

public class MovieInsertProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		ActionForward forward = null;
		
		try {
			// 파일 업로드 작업
			String uploadPath = "upload"; // 업로드 폴더(이클립스에서 관리)
			String realPath = request.getServletContext().getRealPath(uploadPath); // 실제 업로드 폴더(톰캣이 관리)
			int fileSize = 1024 * 1024 * 10;
			
			MultipartRequest multi = new MultipartRequest(request, realPath, fileSize, "UTF-8", new DefaultFileRenamePolicy());
					
			// MovieBean 객체에 뷰에서 전달받은 파라미터 저장
			MovieBean movie = new MovieBean();
			movie.setMovie_title(multi.getParameter("movie_title"));
			movie.setMovie_grade(multi.getParameter("movie_grade"));
			
			// 체크박스를 배열로 받아와서 DB에 저장! .. 근데 마지막 선택한 것만 들어가네..
			String[] genre = multi.getParameterValues("movie_genre");
			
			for(String movie_genre : genre) {
				movie.setMovie_genre(movie_genre.toString());
//				System.out.println(genre);
			}
			
			movie.setMovie_open_date(Date.valueOf(multi.getParameter("movie_open_date")));
			movie.setMovie_runtime(Integer.parseInt(multi.getParameter("movie_runtime")));
			movie.setMovie_intro(multi.getParameter("movie_intro"));
			movie.setMovie_actors(multi.getParameter("movie_actors"));
			movie.setMovie_picture(multi.getOriginalFileName("movie_picture")); // 원본파일
			movie.setMovie_real_picture(multi.getFilesystemName("movie_picture"));  // 실제 업로드 파일
			movie.setMovie_teaser(multi.getParameter("movie_teaser"));
			movie.setMovie_viewer(Integer.parseInt(multi.getParameter("movie_viewer")));
			
			MovieInsertProService service = new MovieInsertProService();
			// 글쓰기 작업 요청
			boolean isWriteSuccess = service.registMovie(movie);
			
			if(isWriteSuccess) { // 성공시 forward 객체에 포워딩 정보 전달
				forward = new ActionForward();
				forward.setPath("AdminMovieList.ad");
				forward.setRedirect(true); // redirect 방식으로 이동
				
			} else { // 실패시 자바스크립트 태그 출력
				response.setContentType("text/html; charset=UTF-8"); // setContentType을 설정해야 HTML 문서로 인식됨
				
				PrintWriter out = response.getWriter();
				
				out.println("<script>");
				out.println("alert('잘못된 접근 입니다!!')");
				out.println("history.back()"); 
				out.println("</script>");
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return forward;
	}

}
