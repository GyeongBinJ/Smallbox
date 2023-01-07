package svc;

import java.sql.Connection;

import dao.CouponDAO;
import db.JdbcUtil;
import vo.CouponBean;

public class CouponModifyProService {

	public boolean modifyCoupon(CouponBean coupon) {
		System.out.println("CouponModifyProService");
		boolean isModifySuccess = false;
		Connection con = JdbcUtil.getConnection();
		CouponDAO dao = CouponDAO.getInstance();
		dao.setConnection(con);
		int updateCount = dao.updateCoupon(coupon);
		
		if(updateCount > 0) {
			JdbcUtil.commit(con);
			isModifySuccess = true;
		} else {
			JdbcUtil.rollback(con);
		}
		
		JdbcUtil.close(con);
		return isModifySuccess;
	}

}
