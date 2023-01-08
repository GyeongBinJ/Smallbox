package action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import svc.NoticeModifyProService;
import vo.ActionForward;
import vo.NoticeBean;

public class NoticeModifyProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("NoticeModifyProAction");
		ActionForward forward = null;
		
		String realPath = "";
		String deleteFileName = "";
		try {
			String uploadPath = "upload"; // 업로드 가상 디렉토리(이클립스)
			realPath = request.getServletContext().getRealPath(uploadPath);
			
			// 만약, 해당 디렉토리가 존재하지 않을 경우 디렉토리 생성
			// => java.io.File 클래스 인스턴스 생성(파라미터로 해당 디렉토리 전달)
			File f = new File(realPath);
			System.out.println("실제 업로드 경로 : " + realPath);
			// => 단, File 객체가 생성되더라도 해당 디렉토리 또는 파일을 직접 생성 X
			// 실제 경로에 대상 존재 여부 판별
			if(!f.exists()) { // 해당 경로가 존재하지 않을 경우
				// File 객체의 mkdir() 메서드를 호출하여 경로 생성 
				f.mkdir();
			}
			
			int fileSize = 1024 * 1024 * 10;
			// --------------------------------------------------------------------
			// 파일 업로드 처리(enctype="mutlipart/form-data") 를 위해
			// MultipartRequest 객체 생성 => cos.jar 라이브러리 필요
			MultipartRequest multi = new MultipartRequest(
					request,  // 1) 실제 요청 정보(파라미터)가 포함된 request 객체
					realPath, // 2) 실제 업로드 경로
					fileSize, // 3) 업로드 파일 최대 사이즈
					"UTF-8",  // 4) 한글 파일명 처리 위한 인코딩 방식
					new DefaultFileRenamePolicy() // 5) 중복 파일명을 처리할 객체
			);
			
			// 전달받은 파라미터 데이터를 BoardBean 클래스 인스턴스 생성 후 저장
			NoticeBean notice = new NoticeBean();
			notice.setNotice_idx(Integer.parseInt(multi.getParameter("notice_idx")));
			notice.setNotice_subject(multi.getParameter("notice_subject"));
			notice.setNotice_content(multi.getParameter("notice_content"));
			notice.setNotice_file(multi.getOriginalFileName("notice_file"));
			notice.setNotice_real_file(multi.getFilesystemName("notice_file"));
			
			System.out.println(notice);
			
			NoticeModifyProService service = new NoticeModifyProService();
			
			boolean isModifySuccess = service.modifyNotice(notice);
				
				
			if(isModifySuccess) {
				forward = new ActionForward();
				forward.setPath("NoticeDetail.ad?notice_idx=" + notice.getNotice_idx() + "&pageNum=" + multi.getParameter("pageNum"));
				forward.setRedirect(true);
				
				if(notice.getNotice_file() != null) {
					deleteFileName = multi.getParameter("notice_real_file");
					System.out.println("파라미터: " + multi.getParameter("notice_real_file"));
					
				}
			} else {
				response.setContentType("text/html; charset=UTF-8");
				
				PrintWriter out = response.getWriter();
					out.println("<script>");
					out.println("alert('수정 실패!')");
					out.println("history.back()");
					out.println("</script>");
					
					deleteFileName = notice.getNotice_real_file();
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				// 예외가 발생하더라도 파일 삭제 무조건 수행
				System.out.println("deleteFileName" + deleteFileName);
				File f = new File(realPath, deleteFileName);
				
				if(f.exists()) {
					f.delete();
				}
			}
		
		return forward;
	}

}
