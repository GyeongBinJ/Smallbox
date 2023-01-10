package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import action.AdminCouponDeleteProAction;
import action.AdminCouponInsertProAction;
import action.AdminCouponListAction;
import action.AdminCouponModifyProAction;
import action.AdminMovieDetailProAction;
import action.AdminMovieListProAction;
import action.AdminTheaterDeleteProAction;
import action.AdminTheaterDetailAction;
import action.AdminTheaterInsertFormAction;
import action.AdminTheaterListAction;
import action.AdminTheaterModifyFormAction;
import action.AdminTheaterModifyProAction;
import action.MemberListAction;
import action.MovieDeleteProAction;
import action.MovieInsertProAction;
import action.MovieModifyFormAction;
import action.MovieModifyProAction;
import action.NoticeDeatilAction;
import action.NoticeDeleteProAction;
import action.NoticeListAction;
import action.NoticeModifyFormAction;
import action.NoticeModifyProAction;
import action.NoticeWriteProAction;
import action.AdminTheaterInsertProAction;
import vo.ActionForward;

@WebServlet("*.ad")
public class AdminFrontController extends HttpServlet {
	
	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("AdminFrontController");
		
		// POST 방식 요청에 대한 한글 인코딩 처리
		request.setCharacterEncoding("UTF-8");
				
		// 서블릿 주소 추출
		String command = request.getServletPath();
		System.out.println("command : " + command);
		
		// 공통으로 사용할 변수 선언
		Action action = null;
		ActionForward forward = null; // 포워딩 정보를 저장할 ActionForward 타입 변수
	
		// ----------------------------------------------------------------------
		if(command.equals("/Admin.ad")) { // 관리자 메인페이지
			forward = new ActionForward();
			forward.setPath("admin/admin.jsp");
			forward.setRedirect(false); // 생략도 가능
	// 상영일정	-------------------------------------------------------------	
		} else if(command.equals("/AdminTheaterInsertForm.ad")) {
			System.out.println("상영 일정 등록 폼!");
			action = new AdminTheaterInsertFormAction();
			forward = action.execute(request, response);
		} else if(command.equals("/AdminTheaterInsertPro.ad")) {
			System.out.println("상영 일정 등록 작업!");
			action = new AdminTheaterInsertProAction();
			forward = action.execute(request, response);
		} else if(command.equals("/AdminTheaterList.ad")) {
			System.out.println("상영 일정 목록!");
			action = new AdminTheaterListAction();
			forward = action.execute(request, response);
		} else if(command.equals("/AdminTheaterDetail.ad")) {
			System.out.println("상영 일정 상세정보!");
			action = new AdminTheaterDetailAction();
			forward = action.execute(request, response);
		} else if(command.equals("/AdminTheaterModifyForm.ad")) {
			System.out.println("상영 일정 수정 폼!");
			forward = new ActionForward();
			action = new AdminTheaterModifyFormAction();
			forward = action.execute(request, response);
		} else if(command.equals("/AdminTheaterModifyPro.ad")) {
			System.out.println("상영 일정 수정 작업!");
			action = new AdminTheaterModifyProAction();
			forward = action.execute(request, response);
		} else if(command.equals("/AdminTheaterDeletePro.ad")) {
			System.out.println("상영 일정 삭제 작업!");
			action = new AdminTheaterDeleteProAction();
			forward = action.execute(request, response);
		// 영화작업	-------------------------------------------------------------
		// 영화 등록폼 출력
		} else if (command.equals("/MovieInsertForm.ad")) {
			forward = new ActionForward();
			forward.setPath("admin/admin_movie_insert.jsp");
			forward.setRedirect(false); // 생략 가능
		// 영화 등록 비즈니스 작업 -> ㅇㅋ
		} else if (command.equals("/MovieInsertPro.ad")) {
			action = new MovieInsertProAction();
			forward = action.execute(request, response);
		
		// 영화 목록 출력 비즈니스 작업 -> ㅇㅋ
		} else if (command.equals("/AdminMovieList.ad")) {
			action = new AdminMovieListProAction();
			forward = action.execute(request, response);
		
		// 영화 등록글 확인 비즈니스 작업 -> ㅇㅋ
		} else if (command.equals("/AdminMovieDetailPro.ad")) {
			action = new AdminMovieDetailProAction();
			forward = action.execute(request, response);
		
		// 영화 수정 폼 출력 비즈니스 작업 -> ㅇㅋ
		} else if (command.equals("/MovieModifyForm.ad")) {
			action = new MovieModifyFormAction();
			forward = action.execute(request, response);
		
		// 영화 수정 비즈니스 작업 -> ㅇㅋ
		} else if (command.equals("/MovieModifyPro.ad")) {
			action = new MovieModifyProAction();
			forward = action.execute(request, response);
		
		// 영화 삭제 비즈니스 작업 -> 뷰 페이지 만들기 귀찮은데 그냥 자바스크립트 태그로 알림창만 뜨게
		} else if (command.equals("/MovieDeletePro.ad")) {
			action = new MovieDeleteProAction();
			forward = action.execute(request, response);
			
		
	// 공지사항	-------------------------------------------------------------
		} else if(command.equals("/Admin_notice_write.ad")) {
			System.out.println("관리자 공지 등록 페이지");
			// Dispatcher 방식으로 포워딩 = /Admin_notice_write.ad 주소가 유지됨
			forward = new ActionForward();
			forward.setPath("admin/admin_notice_insert.jsp");
			forward.setRedirect(false);	
		} else if(command.equals("/Admin_notice_writePro.ad")) {
			// 글쓰기 폼 출력 -> 내용 입력 받은 후 글쓰기 버튼 클릭시 
			// 글쓰기 작업 요청을 위한 서블릿 주소 요청
			System.out.println("관리자 공지 등록 요청");
			action = new NoticeWriteProAction();
			forward = action.execute(request, response);
		} else if(command.equals("/Notice_list.ad")) {
			System.out.println("공지 목록 페이지 요청");
			action = new NoticeListAction();
			forward = action.execute(request, response);
			
		} else if(command.equals("/NoticeDetail.ad")) {
			System.out.println("공지 상세 목록 페이지 요청");
			action = new NoticeDeatilAction();
			forward = action.execute(request, response);
			
		} else if(command.equals("/NoticeDeleteForm.ad")) {
			forward = new ActionForward();
			forward.setPath("admin/notice_delete.jsp");
			forward.setRedirect(false);
			
		} else if(command.equals("/NoticeDeletePro.ad")) {
			System.out.println("공지 삭제 요청");
			action = new NoticeDeleteProAction();
			forward = action.execute(request, response);
			
		} else if(command.equals("/NoticeModifyForm.ad")) {
			System.out.println("공지 수정 폼 요청");
			action = new NoticeModifyFormAction();
			forward = action.execute(request, response);
		} else if(command.equals("/Admin_notice_modifyPro.ad")) {
			System.out.println("공지 수정 작업 요청");
			
			action = new NoticeModifyProAction();
			forward = action.execute(request, response);	
	// 회원	-------------------------------------------------------------	
		} else if (command.equals("/MemberList.ad")) { // 회원 목록 
			action = new MemberListAction();
			forward = action.execute(request, response);
		
	// 쿠폰	-------------------------------------------------------------	
		} else if (command.equals("/CouponInsert.ad")) { // 쿠폰 등록 폼
			forward = new ActionForward();
			forward.setPath("admin/admin_coupon_insert.jsp");
			forward.setRedirect(false); // 생략 가능
		
		} else if (command.equals("/CouponInsertPro.ad")) { // 쿠폰 등록 작업
			action = new AdminCouponInsertProAction();
			forward = action.execute(request, response);	
			
		} else if (command.equals("/CouponList.ad")) { // 쿠폰 리스트 출력
			action = new AdminCouponListAction();
			forward = action.execute(request, response);
			
		} else if (command.equals("/CouponModify.ad")) { // 쿠폰 수정
			forward = new ActionForward();
			forward.setPath("admin/admin_coupon_modify.jsp");
			forward.setRedirect(false); 
		
		} else if (command.equals("/CouponModifyPro.ad")) { // 쿠폰 수정 작업
			action = new AdminCouponModifyProAction();
			forward = action.execute(request, response);	
		
		} else if (command.equals("/CouponDelete.ad")) { // 만료된 쿠폰 삭제
			action = new AdminCouponDeleteProAction();
			forward = action.execute(request, response);
		
		// ==============================영준 작업 딱히 어디둬야할지 몰라서...===========================================	
		// 극장 페이지 이동 매핑
		} else if (command.equals("/teatherList.ad")) {
			forward = new ActionForward();
			forward.setPath("about/teather_list.jsp");
		} else if (command.equals("/team.ad")) {
			forward = new ActionForward();
			forward.setPath("about/project_team.jsp");
		}
		
		// ----------------------------------------------------------------------
		// ActionForward 객체 내용에 따라 각각 다른 방식의 포워딩 작업 수행(공통)
		// 1. ActionForward 객체가 null 이 아닐 경우 판별
			if(forward != null) {
				// 2. ActionForward 객체에 저장된 포워딩 방식 판별
				if(forward.isRedirect()) { // Redirect 방식
					// Redirect 방식의 포워딩 작업 수행
					// => 포워딩 경로는 ActionForward 객체의 getPath() 메서드 활용
					response.sendRedirect(forward.getPath());
				} else { // Dispatch 방식
					// Dispatch 방식의 포워딩 작업 수행
					RequestDispatcher dispatcher = request.getRequestDispatcher(forward.getPath());
					dispatcher.forward(request, response);
				}
			}
			
		} // doProcess() 메서드 끝
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

}
