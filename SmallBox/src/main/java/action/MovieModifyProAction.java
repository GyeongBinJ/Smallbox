package action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import svc.AdminMovieDetailService;
import svc.MovieModifyProService;
import vo.ActionForward;
import vo.MovieBean;

public class MovieModifyProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		ActionForward forward = null;
		
		String realPath = "";
		// 수정 작업 결과에 따라 삭제할 파일이 달라지므로 파일명을 저장할 변수 선언
		// 작업 성공시 : 기존 파일 삭제 , 작업 실패시 : 새로 업로드 한 파일 삭제 
		String deleteFileName = "";
		
		try {
			// 파일 업로드 작업
			String uploadPath = "upload"; // 업로드 폴더(이클립스에서 관리)
			realPath = request.getServletContext().getRealPath(uploadPath); // 실제 업로드 폴더(톰캣이 관리)
			int fileSize = 1024 * 1024 * 10;

			MultipartRequest multi = new MultipartRequest(request, realPath, fileSize, "UTF-8", new DefaultFileRenamePolicy());
		
			int movie_idx = Integer.parseInt(multi.getParameter("movie_idx"));
			
			MovieBean movie = new MovieBean();
			movie.setMovie_idx(movie_idx);
			movie.setMovie_title(multi.getParameter("movie_title"));
			movie.setMovie_grade(multi.getParameter("movie_grade"));
			
			// 선택된 체크박스를 배열로 받아와서 DB에 저장! .. 근데 마지막 선택한 것만 들어가네..
			String[] genre = multi.getParameterValues("movie_genre");
			
			for(String movie_genre : genre) {
				movie.setMovie_genre(movie_genre.toString());
			}
			
			movie.setMovie_open_date(Date.valueOf(multi.getParameter("movie_open_date")));
			movie.setMovie_runtime(Integer.parseInt(multi.getParameter("movie_runtime")));
			movie.setMovie_intro(multi.getParameter("movie_intro"));
			movie.setMovie_actors(multi.getParameter("movie_actors"));
			movie.setMovie_picture(multi.getOriginalFileName("movie_picture")); // 원본파일
			movie.setMovie_real_picture(multi.getFilesystemName("movie_picture"));  // 실제 업로드 파일
			movie.setMovie_teaser(multi.getParameter("movie_teaser"));
			movie.setMovie_viewer(Integer.parseInt(multi.getParameter("movie_viewer")));
			
			// 관리자페이지 - 영화 등록 수정 작업 
			MovieModifyProService service = new MovieModifyProService();
			boolean isModifySuccess = service.modifyBoard(movie);
			
			if(isModifySuccess) { // 영화 수정 성공 시 
				
				forward = new ActionForward();
				forward.setPath("AdminMovieList.ad?pageNum=" + multi.getParameter("pageNum"));
				forward.setRedirect(true); // 수정 작업 완료, 영화 목록 출력 작업 -> Redirect 방식 이동
				
				// 삭제할 파일명 = 기존 파일
				// 단, 수정할 새 파일을 선택했을 경우에만 파일명 지정
				// => 만약 파일 수정 안하면 기존 파일이 유지됨
				if(movie.getMovie_picture() != null) {
					deleteFileName = multi.getParameter("movie_real_picture");
				}
				
			} else { // 영화 수정 실패 시
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('영화 수정 실패!')");
				out.println("history.back()");
				out.println("</script>");
				
				// 삭제할 파일명 = 실제 파일명
				deleteFileName = movie.getMovie_real_picture();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally { // 
			// 예외가 발생하더라도 파일 삭제는 무조건 수행하도록 finally 블록 작성
			// File 객체 생성(파라미터로 디렉토리명, 파일명 전달)
			File f = new File(realPath, deleteFileName);
			
			// 해당 디렉토리 및 파일 존재 여부 판별
			if(f.exists()) { // 존재할 경우
				// File 객체의 delete() 메서드를 호출하여 해당 파일 삭제
				f.delete();
			}
		}
		
		return forward;
	}

}
