package svc;

import java.sql.Connection;
import java.util.List;

import dao.QnaDAO;
import db.JdbcUtil;
import vo.QnaBean;

public class QnaListService {

	// 회원용 게시물 목록 조회 - selectQnaList()
	// => 파라미터 : 아이디, 시작행번호, 목록갯수   리턴타입 : List<QnaBean>(boardList)
	public List<QnaBean> selectQnaList(String member_id, int startRow, int listLimit) {
		System.out.println("selectQnaList()");
		List<QnaBean> qnaList = null;
		
		// 공통작업-1. Connection 객체 가져오기
		Connection con = JdbcUtil.getConnection();
		
		// 공통작업-2. QnaDAO 객체 가져오기
		QnaDAO dao = QnaDAO.getInstance();
		
		// 공통작업-3. BoardDAO 객체에 Connection 객체 전달하기
		dao.setConnection(con);
		
		// QnaDAO 객체의 selectQnaList() 메서드를 호출하여 글목록 조회 작업 수행
		// => 파라미터 : 검색어, 시작행번호, 목록갯수   리턴타입 : List<QnaBean>(qnaList)
		qnaList = dao.selectQnaList(member_id, startRow, listLimit);
		
		// 공통작업-4. Connection 객체 반환하기
		JdbcUtil.close(con);
		
		// 조회 결과 리턴
		return qnaList;
	}
	// 관리자용 게시물 목록 조회 - selectAdminQnaList()
		// => 파라미터 : 시작행번호, 목록갯수   리턴타입 : List<QnaBean>(boardList)
		public List<QnaBean> selectAdminQnaList(int startRow, int listLimit) {
			System.out.println("selectAdminQnaList");
			List<QnaBean> qnaList = null;
			
			// 공통작업-1. Connection 객체 가져오기
			Connection con = JdbcUtil.getConnection();
			
			// 공통작업-2. QnaDAO 객체 가져오기
			QnaDAO dao = QnaDAO.getInstance();
			
			// 공통작업-3. BoardDAO 객체에 Connection 객체 전달하기
			dao.setConnection(con);
			
			// QnaDAO 객체의 selectQnaList() 메서드를 호출하여 글목록 조회 작업 수행
			// => 파라미터 : 검색어, 시작행번호, 목록갯수   리턴타입 : List<QnaBean>(qnaList)
			qnaList = dao.selectAdminQnaList(startRow, listLimit);
			
			// 공통작업-4. Connection 객체 반환하기
			JdbcUtil.close(con);
			
			// 조회 결과 리턴
			return qnaList;
		}

	// 회원용 목록 갯수 조회 - selectQnaListCount()
	// => 파라미터 : 아이디   리턴타입 : int(listCount)
	public int selectQnaListCount(String member_id) {
		System.out.println("selectQnaListCount");
		int listCount = 0;
		
		// 공통작업-1. Connection 객체 가져오기
		Connection con = JdbcUtil.getConnection();
		
		// 공통작업-2. QnaDAO 객체 가져오기
		QnaDAO dao = QnaDAO.getInstance();
		
		// 공통작업-3. QnaDAO 객체에 Connection 객체 전달하기
		dao.setConnection(con);
		
		// QnaDAO 객체의 selectQnaListCount() 메서드를 호출하여 글목록 갯수 조회 작업 수행
		// => 파라미터 : 검색어     리턴타입 : int(listCount)
		listCount = dao.selectQnaListCount(member_id);
		
		// 공통작업-4. Connection 객체 반환하기
		JdbcUtil.close(con);
		
		return listCount;
	}
	// 관리자용 목록 갯수 조회 - selectAdminQnaListCount()
		// => 파라미터 : 없음   리턴타입 : int(listCount)
		public int selectAdminQnaListCount() {
			System.out.println("selectAdminQnaList");
			int listCount = 0;
			
			// 공통작업-1. Connection 객체 가져오기
			Connection con = JdbcUtil.getConnection();
			
			// 공통작업-2. QnaDAO 객체 가져오기
			QnaDAO dao = QnaDAO.getInstance();
			
			// 공통작업-3. QnaDAO 객체에 Connection 객체 전달하기
			dao.setConnection(con);
			
			// QnaDAO 객체의 selectQnaListCount() 메서드를 호출하여 글목록 갯수 조회 작업 수행
			// => 파라미터 : 검색어     리턴타입 : int(listCount)
			listCount = dao.selectAdminQnaListCount();
			
			// 공통작업-4. Connection 객체 반환하기
			JdbcUtil.close(con);
			
			return listCount;
		}
}













