package controller;

import java.io.IOException;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import action.AdminMovieListProAction;
import action.CouponListProAction;
import action.MovieLikeListProAction;
import action.MovieLikeProAction;
import action.MovieListProAction;
import action.MyPageMainAction;
import action.QnaDeleteProAction;
import action.QnaDetailAction;
import action.QnaListAction;
import action.QnaReplyFormAction;
import action.QnaReplyProAction;
import action.QnaWriteProAction;
import action.ReserveCancelProAction;
import action.ReserveListAction;
import action.ReviewListProAction;
import vo.ActionForward;

@WebServlet("*.my")
public class MyPageFrontController extends HttpServlet {
	
	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String command = request.getServletPath();
		
		Action action = null; 
		ActionForward forward = null; 
		
		// ----------------------------------------------------------------------
//		if(command.equals("/MyPageMain.my")) { // 마이페이지 메인으로 이동
//			forward = new ActionForward();
//			forward.setPath("mypage/mypage_main.jsp");
//			forward.setRedirect(false);
		if(command.equals("/MyPageMain.my")) { // 마이페이지 메인 이동 + 여러 정보 출력
			action = new MyPageMainAction();
			forward = action.execute(request, response);
		} else if(command.equals("/MovieLikeList.my")) { // 마이페이지 - 찜 목록 출력
 			action = new MovieLikeListProAction();
			forward = action.execute(request, response);
		} else if(command.equals("/CouponList.my")) { // 마이페이지 - 쿠폰 목록 출력
 			action = new CouponListProAction();
			forward = action.execute(request, response);
		} else if(command.equals("/ReviewList.my")) { // 마이페이지 - 리뷰 목록 출력
 			action = new ReviewListProAction();
			forward = action.execute(request, response);
		//0108 고은 파트 업로드==========================	
		} else if (command.equals("/Reserved.my")) { // 마이페이지 - 예약 목록 출력
			action = new ReserveListAction();
			forward = action.execute(request, response);
		} else if (command.equals("/ReserveCancel.my")) { // 마이페이지 - 예약 취소
			action = new ReserveCancelProAction();
			forward = action.execute(request, response);
		} else if(command.equals("/QnaWriteForm.my")) { // 마이페이지 - 1:1문의 작성
			forward = new ActionForward();
			forward.setPath("mypage/qna_write.jsp");
			forward.setRedirect(false);
		} else if(command.equals("/QnaWritePro.my")) { // 마이페이지 - 1:1문의 작성 작업
			action = new QnaWriteProAction();
			forward = action.execute(request, response);
		} else if(command.equals("/QnaList.my")) { // 마이페이지 - 1:1문의 내역
			action = new QnaListAction();
			forward = action.execute(request, response);
		} else if(command.equals("/QnaDetail.my")) { // 마이페이지 - 1:1문의 상세보기
			action = new QnaDetailAction();
			forward = action.execute(request, response);
		} else if(command.equals("/QnaDeleteForm.my")) { // 마이페이지 - 1:1문의 삭제
			forward = new ActionForward();
			forward.setPath("mypage/qna_delete.jsp");
			forward.setRedirect(false); // 생략도 가능
		} else if(command.equals("/QnaDeletePro.my")) { // 마이페이지 - 1:1문의 내역 삭제
			action = new QnaDeleteProAction();
			forward = action.execute(request, response);
		} else if(command.equals("/QnaReplyForm.my")) { //마이페이지 - 1:1문의 관리자 답변
			// 답글 작성 폼 비즈니스 작업 요청
			// QnaReplyFormAction 의 execute() 메서드 호출
			action = new QnaReplyFormAction();
			forward = action.execute(request, response);
		} else if(command.equals("/QnaReplyPro.my")) { //마이페이지 - 1:1문의 관리자 답변작업
			// 답글 작성 비즈니스 작업 요청
			// QnaReplyProAction 의 execute() 메서드 호출
			action = new QnaReplyProAction();
			forward = action.execute(request, response);
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

		
	} 
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
