package svc;

import java.sql.Connection;
import java.util.List;

import dao.ReserveDAO;
import db.JdbcUtil;
import vo.ReserveBean;

public class ReserveListService {

	// 게시물 목록 조회 - getReserveList()
	// => 파라미터 : 아이디   리턴타입 : List<ReserveBean>(reserveList)
	public List<ReserveBean> getReserveList(String sId) {
		List<ReserveBean> reserveList = null;
		
		// 공통작업-1. Connection 객체 가져오기
		Connection con = JdbcUtil.getConnection();
		
		// 공통작업-2. ReserveDAO 객체 가져오기
		ReserveDAO dao = ReserveDAO.getInstance();
		
		// 공통작업-3. ReserveDAO 객체에 Connection 객체 전달하기
		dao.setConnection(con);
		
		// BoardDAO 객체의 selectBoardList() 메서드를 호출하여 글목록 조회 작업 수행
		// => 파라미터 : 검색어, 시작행번호, 목록갯수   리턴타입 : List<BoardBean>(boardList)
		reserveList = dao.selectReserveList(sId);
		System.out.println("예약리스트(reserveList)" + reserveList);
		// 공통작업-4. Connection 객체 반환하기
		JdbcUtil.close(con);
		
		// 조회 결과 리턴
		return reserveList;
	}

	// 목록 갯수 조회 - getBoardListCount()
	// => 파라미터 : 검색어   리턴타입 : int(listCount)
//	public int getBoardListCount(String memberId) {
//		int listCount = 0;
//		
//		// 공통작업-1. Connection 객체 가져오기
//		Connection con = JdbcUtil.getConnection();
//		
//		// 공통작업-2. BoardDAO 객체 가져오기
//		ReserveDAO dao = ReserveDAO.getInstance();
//		
//		// 공통작업-3. BoardDAO 객체에 Connection 객체 전달하기
//		dao.setConnection(con);
//		
//		// BoardDAO 객체의 selectBoardListCount() 메서드를 호출하여 글목록 갯수 조회 작업 수행
//		// => 파라미터 : 검색어     리턴타입 : int(listCount)
//		listCount = dao.selectReserveListCount(memberId);
//		
//		// 공통작업-4. Connection 객체 반환하기
//		JdbcUtil.close(con);
//		
//		return listCount;
//	}
	
}













