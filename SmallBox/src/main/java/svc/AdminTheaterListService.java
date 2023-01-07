package svc;

import java.sql.Connection;
import java.util.List;

import dao.TheaterDAO;
import db.JdbcUtil;
import vo.TheaterBean;

public class AdminTheaterListService {

	// 상영 일정 목록 조회 - 예매
	public List<TheaterBean> getTheaterList() {
		System.out.println("AdminTheaterListService - getTheaterList()");
		
		// 상영 일정 목록을 저장할 List객체 생성
		List<TheaterBean> theaterList = null;
		
		// 공통작업
		Connection con = JdbcUtil.getConnection();
		TheaterDAO dao = TheaterDAO.getInstance();
		dao.setConnection(con);
		
		// TheaterDAO의 selectTheaterList() 메서드를 호출하여 상영일정 목록 조회
		theaterList = dao.selectTheaterList();
		
		JdbcUtil.commit(con);
		JdbcUtil.close(con);
		
		return theaterList; // ReserveViewListAction
	}
	
	// 상영 일정 목록 조회
	public List<TheaterBean> getTheaterList(String keyword, int startRow, int listLimit) {
		System.out.println("AdminTheaterListService - getTheaterList()");
		
		// 상영 일정 목록을 저장할 List객체 생성
		List<TheaterBean> theaterList = null;
		
		// 공통작업
		Connection con = JdbcUtil.getConnection();
		TheaterDAO dao = TheaterDAO.getInstance();
		dao.setConnection(con);
		
		// TheaterDAO의 selectTheaterList() 메서드를 호출하여 상영일정 목록 조회
		// 파라미터 : keyword, startRow, listLimit , 리턴타입 : List<TheaterBean>(theaterList)
		theaterList = dao.selectTheaterList(keyword, startRow, listLimit);
		
		JdbcUtil.commit(con);
		JdbcUtil.close(con);
		
		return theaterList; // AdminTheaterListAction
	}
	
	// 상영 일정 목록 갯수 조회
	public int getTheaterListCount(String keyword) {
		System.out.println("AdminTheaterListService - getTheaterListCount()");
		int listCount = 0;
		
		// 공통작업
		Connection con = JdbcUtil.getConnection();
		TheaterDAO dao = TheaterDAO.getInstance();
		dao.setConnection(con);
		
		// TheaterDAO의 getTheaterListCount() 메서드를 호출하여 상영일정 목록 갯수 조회
		// 파라미터 : keyword, 리턴타입 : int(listCount)
		listCount = dao.getTheaterListCount(keyword);
		
		JdbcUtil.commit(con);
		JdbcUtil.close(con);
		
		return listCount; // AdminTheaterListAction
}

}