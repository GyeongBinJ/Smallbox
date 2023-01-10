package action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import svc.CouponListService;
import vo.ActionForward;
import vo.CouponBean;
import vo.PageInfo;

public class AdminCouponListTotalAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("AdminCouponListTotalAction");
		ActionForward forward = null;
		
		HttpSession session = request.getSession();
		if(session.getAttribute("sId") == null || !session.getAttribute("sId").equals("admin") ) {
			try {
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('잘못된 접근입니다.')");
				out.println("history.back()");
				out.println("</script>");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
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
			
			// CouponListService 객체를 통해 게시물 목록 조회 후
			// 조회 결과(List 객체)를 request 객체를 통해 admin_coupon_result.jsp 페이지로 전달
			CouponListService service = new CouponListService();
//			List<CouponBean> couponListTotal = service.getCouponList();
			List<CouponBean> couponListTotal = service.searchCouponList(keyword, startRow, listLimit);
			
			System.out.println(couponListTotal);
			
			// ---------------------------------------------------------
			// 페이징 처리
			// 한 페이지에서 표시할 페이지 목록(번호) 갯수 계산
			// 1. MemberListService - memberListCount() 메서드를 호출하여 전체 게시물 수 조회(페이지 목록 계산에 사용)
			// => 파라미터 : 검색어   리턴타입 : int(listCount)
			int listCount = service.searchCouponListCount(keyword);
			
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
			request.setAttribute("couponListTotal", couponListTotal);
			request.setAttribute("pageInfo", pageInfo);
			
			System.out.println(pageInfo);
			forward = new ActionForward();
			forward.setPath("admin/admin_coupon_total_result.jsp");
			forward.setRedirect(false);
				
		}
		return forward;
	}

}
