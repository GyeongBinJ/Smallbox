package action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import svc.NoticeWriteProService;
import vo.ActionForward;
import vo.NoticeBean;

public class NoticeWriteProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("NoticeWriteProAction");
		
		ActionForward forward = null;
		try {
			
				String uploadPath = "upload";
				String realPath = request.getServletContext().getRealPath(uploadPath);
				System.out.println("실제 업로드 경로: " + realPath);
				int fileSize = 1024 * 1024 * 10;
				
				MultipartRequest multi = new MultipartRequest(
						request,
						realPath,
						fileSize,
						"UTF-8",
						new DefaultFileRenamePolicy()
						);
				
				NoticeBean notice = new NoticeBean();
				notice.setNotice_subject(multi.getParameter("notice_subject"));
				notice.setNotice_content(multi.getParameter("notice_content"));
				
//				System.out.println(multi.getOriginalFileName("notice_file"));
//				System.out.println(multi.getFilesystemName("notice_file"));
				
				notice.setNotice_file(multi.getOriginalFileName("notice_file"));
				notice.setNotice_real_file(multi.getFilesystemName("notice_file"));
//				System.out.println(notice);
				
				
//				Enumeration e = multi.getFileNames();
				
//				while(e.hasMoreElements()) {
					
//				String fileElement = e.nextElement().toString();
//			System.out.println(fileElement);
//			System.out.println("원본 파일명: " + multi.getOriginalFileName(fileElement));
//			System.out.println("실제 파일명: " + multi.getFilesystemName(fileElement));
			
//			}
		
			NoticeWriteProService service = new NoticeWriteProService();
			boolean isWriteSuccess = service.registNotice(notice);
			
			if(!isWriteSuccess) {
				File f = new File(realPath, notice.getNotice_real_file());
				
				if(f.exists()) {
					f.delete();
				}
				
				response.setContentType("text/html; charset=UTF-8");
				
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('글쓰기 실패!')");
				out.println("history.back()");
				out.println("</script>");
			} else {
				forward = new ActionForward();
				forward.setPath("Notice_list.ad");
				forward.setRedirect(true);
				// 자바 스크립트와 중첩 불가(사용하려면 다른 서블릿으로 이동해야함)
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
			
		
		
		
		return forward;
	}//execute

} // Action
