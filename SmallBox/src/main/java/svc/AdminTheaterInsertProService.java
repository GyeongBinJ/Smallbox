package svc;

import java.sql.Connection;

import dao.TheaterDAO;
import db.JdbcUtil;
import vo.TheaterBean;

public class AdminTheaterInsertProService {

	public boolean insertTheater(TheaterBean theater) {
		System.out.println("TheaterInsertProService - insertTheater()");
		
		// 상영일정 등록 작업 결과를 저장할 변수
		boolean isInsertSuccess = false; 
		
		// 공통작업
		Connection con = JdbcUtil.getConnection();
		TheaterDAO dao = TheaterDAO.getInstance();
		dao.setConnection(con);
		
		// TheaterDAO 객체의 insertTheater() 메서드 호출하여 상영일정 등록 작업 요청
		// // 파라미터 : TheaterBean , 리턴타입 : int(insertCount)
		int insertCount = dao.insertTheater(theater);
		
		if(insertCount > 0) { // 상영일정 등록 성공
			JdbcUtil.commit(con);
			isInsertSuccess = true;
		} else { // 상영일정 등록 실패
			JdbcUtil.rollback(con);
		}
			JdbcUtil.close(con);
		
		return isInsertSuccess; // TheaterInsertProAction
	}

}
