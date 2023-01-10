package svc;

import java.sql.Connection;
import java.util.List;

import dao.ReserveDAO;
import db.JdbcUtil;
import vo.ReserveBean;

public class ReserveListService {

	// 게시물 목록 조회 - selectReserveList()
	// => 파라미터 : 아이디   리턴타입 : List<ReserveBean>(reserveList)
	public List<ReserveBean> selectReserveList(String sId, int startRow, int listLimit) {
		List<ReserveBean> reserveList = null;
		
		// 공통작업-1. Connection 객체 가져오기
		Connection con = JdbcUtil.getConnection();
		
		// 공통작업-2. ReserveDAO 객체 가져오기
		ReserveDAO dao = ReserveDAO.getInstance();
		
		// 공통작업-3. ReserveDAO 객체에 Connection 객체 전달하기
		dao.setConnection(con);
		
		reserveList = dao.selectReserveList(sId, startRow, listLimit);
		System.out.println("예약리스트(reserveList)" + reserveList);
		// 공통작업-4. Connection 객체 반환하기
		JdbcUtil.close(con);
		
		// 조회 결과 리턴
		return reserveList;
	}

	// 목록 갯수 조회 - selectReserveListCount()
	// => 파라미터 : 검색어   리턴타입 : int(listCount)
	public int selectReserveListCount(String sId) {
		int listCount = 0;
		
		// 공통작업-1. Connection 객체 가져오기
		Connection con = JdbcUtil.getConnection();
		
		// 공통작업-2. ReserveDAO 객체 가져오기
		ReserveDAO dao = ReserveDAO.getInstance();
		
		// 공통작업-3. ReserveDAO 객체에 Connection 객체 전달하기
		dao.setConnection(con);
		
		// ReserveDAO 객체의 selectBoardListCount() 메서드를 호출하여 글목록 갯수 조회 작업 수행
		// => 파라미터 : 검색어     리턴타입 : int(listCount)
		listCount = dao.selectReserveListCount(sId);
		
		// 공통작업-4. Connection 객체 반환하기
		JdbcUtil.close(con);
		
		return listCount;
	}
	
}













