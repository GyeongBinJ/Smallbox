package svc;

import java.sql.Connection;
import java.sql.Date;

import dao.CouponDAO;
import db.JdbcUtil;

public class CouponDeleteProService {

	
	public boolean deleteCoupon(String coupon_end_date) {
		System.out.println("CouponDeleteProService");
		boolean isDeleteSuccess = false;
		Connection con = JdbcUtil.getConnection();
		CouponDAO dao = CouponDAO.getInstance();
		dao.setConnection(con);
		int deleteCount = dao.deleteCoupon(coupon_end_date);
		
		if(deleteCount > 0) {
			isDeleteSuccess = true;
			JdbcUtil.commit(con);
		} else {
			JdbcUtil.rollback(con);
		}
		JdbcUtil.close(con);
		return isDeleteSuccess;
	}
}
