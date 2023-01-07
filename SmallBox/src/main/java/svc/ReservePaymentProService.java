package svc;

import java.sql.Connection;

import dao.ReserveDAO;
import db.JdbcUtil;
import vo.ReserveBean;

public class ReservePaymentProService {

	public boolean reserveAndPayment(ReserveBean reserve) {
		System.out.println("service : ReservePaymentProService");
		
		boolean isReserveSuccess = false;
		//-------------------공통------------------------------
		Connection con = JdbcUtil.getConnection();		
		ReserveDAO dao = ReserveDAO.getInstance();
		dao.setConnection(con);
		//-----------------------------------------------------
		
		int reserveCount = dao.insertReserve(reserve);
		
		if(reserveCount > 0) {
			JdbcUtil.commit(con);
			isReserveSuccess = true;
		} else {
			JdbcUtil.rollback(con);
		}
		//-------------------공통------------------------------
		JdbcUtil.close(con);
		//-----------------------------------------------------
				
		return isReserveSuccess;
	}

}
