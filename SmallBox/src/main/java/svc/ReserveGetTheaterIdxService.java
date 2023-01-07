package svc;

import java.sql.Connection;

import dao.TheaterDAO;
import db.JdbcUtil;
import vo.TheaterBean;

public class ReserveGetTheaterIdxService {

	public int getTheaterIdx(TheaterBean theater) {
		int theater_idx = 0;
		
		//-------------------공통------------------------------
		Connection con = JdbcUtil.getConnection();
		TheaterDAO dao = TheaterDAO.getInstance();
		dao.setConnection(con);
		//-----------------------------------------------------
		
		theater_idx = dao.getTheaterIdx(theater);
		
		//-------------------공통------------------------------
		JdbcUtil.commit(con);
		JdbcUtil.close(con);
		//-----------------------------------------------------
//		System.out.println("service : dao 에서 받은 theater_idx : " + theater_idx);
		return theater_idx;
	}

}
