package svc;

import java.sql.Connection;

import dao.TheaterDAO;
import db.JdbcUtil;
import vo.TheaterBean;

public class AdminTheaterDetailService {

	public TheaterBean getTheater(int theater_idx) {
		System.out.println("AdminTheaterDetailService - getTheater()");
		TheaterBean theater = null;
		
		// 공통작업
		Connection con = JdbcUtil.getConnection();
		TheaterDAO dao = TheaterDAO.getInstance();
		dao.setConnection(con);
		
		// TheaterDAO 의 selectTheaterDetail() 메서드 호출하여 1개의 상영일정 상세정보조회
		// 파라미터 : theater_idx, 리턴타입 : TheaterBean(theater)
		theater = dao.selectTheaterDetail(theater_idx);
		
		JdbcUtil.commit(con);
		JdbcUtil.close(con);
		
		return theater; // AdminTheaterDetailAction
	}


}
