package svc;

import java.sql.Connection;

import dao.TheaterDAO;
import db.JdbcUtil;
import vo.TheaterBean;

public class AdminTheaterModifyProService {

	public boolean ModifyTheater(TheaterBean theater) {
		System.out.println("AdminTheaterModifyProService - ModifyTheater()");
		boolean isModifySuccess = false;
		
		// 공통작업
		Connection con = JdbcUtil.getConnection();
		TheaterDAO dao = TheaterDAO.getInstance();
		dao.setConnection(con);
		
		// TheaterDAO의 ModifyTheater() 호출하여 상영일정 수정
		// 파라미터 : TheaterBean(theater), 리턴타입 : int(updateCount)
		int updateCount = dao.ModifyTheater(theater);
		
		if(updateCount > 0) { // 수정 성공
			JdbcUtil.commit(con);
			isModifySuccess = true;
		} else { // 수정 실패
			JdbcUtil.rollback(con);
		}
		JdbcUtil.close(con);
		
		return isModifySuccess; // AdminTheaterModifyProAction
	}

}
