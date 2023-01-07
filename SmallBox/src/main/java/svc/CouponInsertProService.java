package svc;

import java.sql.Connection;

import dao.CouponDAO;
import db.JdbcUtil;
import vo.CouponBean;

public class CouponInsertProService {
	
	public boolean insertCoupon(CouponBean coupon) {
		System.out.println("CouponInsertProService - insertCoupon()");
		
		boolean insertCoupon = false;
		
		Connection con = JdbcUtil.getConnection();
		
		CouponDAO dao = CouponDAO.getInstance();
		dao.setConnection(con);
		
		int insertCouponSuccess = dao.insertCoupon(coupon);
		if(insertCouponSuccess > 0) {// 성공 시
			JdbcUtil.commit(con);
			
			insertCoupon = true;
		} else {// 실패 시
			JdbcUtil.rollback(con);
		}
		
		JdbcUtil.close(con);
		
		// 작업결과 리턴
		return insertCoupon; 
	}
	
}
