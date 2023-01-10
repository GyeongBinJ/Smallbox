package svc;

import java.sql.Connection;
import java.util.List;

import dao.CouponDAO;
import db.JdbcUtil;
import vo.CouponBean;

public class ReserveCheckCouponService {

	public List<CouponBean> getCouponList(String sId) {
		List<CouponBean> couponList = null;
		
		//-------------------공통------------------------------
		Connection con = JdbcUtil.getConnection();
		CouponDAO dao = CouponDAO.getInstance();
		dao.setConnection(con);
		//-----------------------------------------------------

		couponList = dao.selectMemberCouponList(sId);
		
		//-------------------공통------------------------------
		JdbcUtil.commit(con);
		JdbcUtil.close(con);
		//-----------------------------------------------------
		System.out.println("service의 쿠폰리스트 : " + couponList);
		return couponList;
		
	}

}
