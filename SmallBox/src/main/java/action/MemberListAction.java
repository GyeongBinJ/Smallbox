package action;

import java.io.IOException;

import java.io.PrintWriter;
import java.sql.Connection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.MemberDAO;
import db.JdbcUtil;
import svc.MemberListService;
import vo.ActionForward;
import vo.MemberBean;
import vo.PageInfo;

public class MemberListAction implements Action {
	
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		ActionForward forward = null;
//		System.out.println("MemberListAction");
		// 세션아이디가 어드민이 아닐 경우 자바스크립트를 사용하여 돌려보내기
		try {
			HttpSession session = request.getSession();
			if(session.getAttribute("sId") == null || !session.getAttribute("sId").equals("admin") ) {
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('잘못된 접근입니다.')");
				out.println("history.back()");
				out.println("</script>");
			} else { // 관리자일 경우
				int listLimit = 5; // 한 페이지에서 표시할 게시물 목록을 5개로 제한
				int pageNum = 1; // 현재 페이지 번호 설정(pageNum 파라미터 사용)
				
				if(request.getParameter("pageNum") != null) {
					pageNum = Integer.parseInt(request.getParameter("pageNum"));
				}

				int startRow = (pageNum - 1) * listLimit; // 조회 시작 행번호 계산

				
				String keyword = request.getParameter("keyword");

				if(keyword == null){
					keyword = "";
				}
				// MemberListService - getMemberList()
				// 파라미터 : 키워드, 시작행, 표시할 목록갯수	
				//  리턴타입 : List<MemberBean>(memberList)
				MemberListService service = new MemberListService();
				List<MemberBean> memberList = service.getMemberList(keyword, startRow, listLimit);
				
				// ---------------------------------------------------------
				// 페이징 처리
				// 한 페이지에서 표시할 페이지 목록(번호) 갯수 계산
				// 1. MemberListService - memberListCount() 메서드를 호출하여 전체 게시물 수 조회(페이지 목록 계산에 사용)
				// => 파라미터 : 검색어   리턴타입 : int(listCount)
				int listCount = service.memberListCount(keyword);
				
				// 2. 한 페이지에서 표시할 페이지 목록 갯수 설정
				int pageListLimit = 5; // 한 페이지에서 표시할 페이지 목록을 5개로 제한
				
				// 3. 전체 페이지 목록 수 계산
				int maxPage = listCount / listLimit 
								+ (listCount % listLimit == 0 ? 0 : 1); 
				
				// 4. 시작 페이지 번호 계산
				int startPage = (pageNum - 1) / pageListLimit * pageListLimit + 1;
				
				// 5. 끝 페이지 번호 계산
				int endPage = startPage + pageListLimit - 1;
				
				// 6. 만약, 끝 페이지 번호(endPage)가 전체(최대) 페이지 번호(maxPage) 보다
				//    클 경우, 끝 페이지 번호를 최대 페이지 번호로 교체
				if(endPage > maxPage) {
					endPage = maxPage;
				}
				
				PageInfo pageInfo = new PageInfo(listCount, pageListLimit, maxPage, startPage, endPage);

				// request 객체에 목록 저장
				request.setAttribute("memberList", memberList);
				request.setAttribute("pageInfo", pageInfo);

				// admin/admin_member.jsp 포워딩
				forward = new ActionForward();
				forward.setPath("admin/admin_member.jsp");
				forward.setRedirect(false);
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return forward;
	}
	
	
}
