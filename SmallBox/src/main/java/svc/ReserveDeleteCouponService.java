package svc;

import java.sql.Connection;

import dao.ReserveDAO;
import db.JdbcUtil;

public class ReserveDeleteCouponService {

	public boolean deleteCoupon(int coupon_idx) {
		System.out.println("service : ReservePaymentProService");
		
		boolean isReserveSuccess = false;
		//-------------------공통------------------------------
		Connection con = JdbcUtil.getConnection();		
		ReserveDAO dao = ReserveDAO.getInstance();
		dao.setConnection(con);
		//-----------------------------------------------------
		
		int delCount = dao.deleteUsedCoupon(coupon_idx);
		
		if(delCount > 0) {
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
