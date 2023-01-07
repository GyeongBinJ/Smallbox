package action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import svc.NoticeDeleteProService;
import svc.NoticeDetailService;
import vo.ActionForward;
import vo.NoticeBean;

public class NoticeDeleteProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		ActionForward forward = null;
		
		int notice_idx = Integer.parseInt(request.getParameter("notice_idx"));
		System.out.println(notice_idx);
		
		try {
			
				NoticeDeleteProService service = new NoticeDeleteProService();
				NoticeDetailService service2 = new NoticeDetailService();
				NoticeBean notice = service2.getNotice(notice_idx, false);
				// => 주의! 레코드 삭제 전 정보 조회 먼저 수행해야한다!
				
				// BoardDeleteProService 클래스의 removeBoard() 메서드를 호출하여 글 삭제 작업 수행
				//  => 파라미터 : 글번호(board_num)    리턴타입 : boolean(isDeleteSuccess)
				boolean isDeleteSuccess = service.removeNotice(notice_idx);
				
				// 삭제 결과를 판별하여 실패 시 자바스크립트 오류 메세지 출력 및 이전페이지로 이동하고
				// 성공 시 ActionForward 객체를 통해 "BoardList.bo" 페이지로 포워딩(Redirect)
				// (=> URL 에 페이지 번호를 붙여서 요청)
				if(!isDeleteSuccess) { // 삭제 실패 시 
					response.setContentType("text/html; charset=UTF-8");
					PrintWriter out = response.getWriter();
					out.println("<script>");
					out.println("alert('삭제 실패!')");
					out.println("history.back()");
					out.println("</script>");
				} else { // 삭제 성공 시
					String uploadPath = "upload"; // 업로드 가상 디렉토리(이클립스)
					String realPath = request.getServletContext().getRealPath(uploadPath);
					
					System.out.println("NoticeBean : " + notice);
					System.out.println("realPath : " + realPath);
					
					// 업로드 된 실제 파일 삭제
					File f = new File(realPath, notice.getNotice_real_file());
					
					// 해당 디렉토리 및 파일 존재 여부 판별
					if(f.exists()) { // 존재할 경우
						// File 객체의 delete() 메서드를 호출하여 해당 파일 삭제
						f.delete();
					}
					
					forward = new ActionForward();
					forward.setPath("NoticeList.ad?pageNum=" + request.getParameter("pageNum"));
					forward.setRedirect(true);
				}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
		return forward;
	}

}
