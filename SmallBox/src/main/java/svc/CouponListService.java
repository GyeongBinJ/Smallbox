package svc;

import java.sql.Connection;
import java.util.List;

import dao.CouponDAO;
import db.JdbcUtil;
import vo.CouponBean;

public class CouponListService {
	
	// 관리자페이지 - 각 회원(member_id)의 쿠폰 내역 조회
	public List<CouponBean> getCouponList(String member_id) {
		List<CouponBean> couponList = null;
		
		Connection con = JdbcUtil.getConnection();
		CouponDAO dao = CouponDAO.getInstance();
		dao.setConnection(con);
		
		// DAO에서 DB작업을 수행해서 조회한 각 회원(member_id)의 쿠폰 정보를 저장해 액션으로 리턴
		couponList = dao.selectMemberCouponList(member_id);
		
		JdbcUtil.commit(con);
		
		JdbcUtil.close(con);
		
		return couponList;
	}
	
	// 마이페이지 - 각 회원(member_id)의 쿠폰 내역 조회
	public List<CouponBean> getCouponList(String member_id, int startRow, int couponLimit) {
		List<CouponBean> couponList = null;
		
		Connection con = JdbcUtil.getConnection();
		CouponDAO dao = CouponDAO.getInstance();
		dao.setConnection(con);
		
		// DAO에서 DB작업을 수행해서 조회한 각 회원(member_id)의 쿠폰 정보를 저장해 액션으로 리턴
		couponList = dao.selectMemberCouponList(member_id, startRow, couponLimit);
		
		JdbcUtil.commit(con);
		
		JdbcUtil.close(con);
		
		return couponList;
	}

	// 마이페이지 - 회원별 쿠폰 보유갯수 조회
	public int getCouponListCount(String member_id) {
		int couponCount = 0;
		
		Connection con = JdbcUtil.getConnection();
		CouponDAO dao = CouponDAO.getInstance();
		dao.setConnection(con);
		
		// DAO에서 DB작업을 수행해서 조회한 쿠폰의 총 갯수를 변수에 저장
		couponCount = dao.selectMemberCouponCount(member_id);
		
		JdbcUtil.commit(con);
		
		JdbcUtil.close(con);

		return couponCount;
	}

}
