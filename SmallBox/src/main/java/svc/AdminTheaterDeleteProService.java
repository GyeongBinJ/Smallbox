package svc;

import java.sql.Connection;

import dao.TheaterDAO;
import db.JdbcUtil;

public class AdminTheaterDeleteProService {

	public boolean deleteTheater(int theater_idx) {
		System.out.println("AdminTheaterDeleteProService - deleteTheater()");
		boolean isDeleteSuccess = false;
		
		// 공통작업
		Connection con = JdbcUtil.getConnection();
		TheaterDAO dao = TheaterDAO.getInstance();
		dao.setConnection(con);
		
		// TheaterDAO 의 deleteTheater() 호출하여 상영일정 삭제
		// 파라미터 : theater_idx, 리턴타입 : int(deleteCount)
		int deleteCount = dao.deleteTheater(theater_idx);
		
		if(deleteCount > 0) { // 삭제 성공
			JdbcUtil.commit(con);
			isDeleteSuccess = true;
		} else { // 삭제 실패
			JdbcUtil.rollback(con);
		}
		JdbcUtil.close(con);
		
		return isDeleteSuccess; // AdminTheaterDeleteProAction
	}
	
}
