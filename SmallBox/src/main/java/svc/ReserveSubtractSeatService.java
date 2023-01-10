package svc;

import java.sql.Connection;

import dao.ReserveDAO;
import db.JdbcUtil;

public class ReserveSubtractSeatService {

	public Boolean substractSeat(int theater_idx, int people) {
		System.out.println("service : ReserveSubtractSeatService");
		
		boolean isSubtractSeatSuccess = false;
		//-------------------공통------------------------------
		Connection con = JdbcUtil.getConnection();
		ReserveDAO dao = ReserveDAO.getInstance();
		dao.setConnection(con);
		//-----------------------------------------------------
		
		int subCount = dao.subtractSeat(theater_idx, people);
		
		if(subCount > 0) {
			JdbcUtil.commit(con);
			isSubtractSeatSuccess = true;
		} else {
			JdbcUtil.rollback(con);
		}
		//-------------------공통------------------------------
		JdbcUtil.close(con);
		//-----------------------------------------------------
		return isSubtractSeatSuccess;
	}

	
	
	
	
}
