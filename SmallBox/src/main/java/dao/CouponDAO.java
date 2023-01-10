package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.JdbcUtil;
import vo.CouponBean;
import vo.MemberBean;

public class CouponDAO {
	PreparedStatement pstmt = null, pstmt2 = null;
	ResultSet rs = null;
	//----------------------------------------------------
	private static CouponDAO instance = new CouponDAO();

	public static CouponDAO getInstance() {
		return instance;
	}

	//-----------------------------------------------------
	private Connection con;

	public void setConnection(Connection con) {
		this.con = con;
	}
	
	// 쿠폰 지급
	public int insertCoupon(CouponBean coupon) {
		System.out.println("CouponDAO - insertCoupon()");
		int insertCount = 0;
		
		try {
			int coupon_idx = 1; // 새 글 번호
			
			String sql = "SELECT MAX(coupon_idx) FROM coupon";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				coupon_idx = rs.getInt(1) +1;
				sql = "INSERT INTO coupon VALUES(?, ?, ?, ?, now(), ?)";
				pstmt2 = con.prepareStatement(sql);
				pstmt2.setString(1, coupon.getMember_id());
				pstmt2.setInt(2, coupon_idx);
				pstmt2.setString(3, coupon.getCoupon_type());
				pstmt2.setInt(4, coupon.getCoupon_rate());
				pstmt2.setDate(5, coupon.getCoupon_end_date());
				insertCount = pstmt2.executeUpdate();
			}
		} catch (SQLException e) {
			System.out.println("SQL 구문 오류 발생! - insertCoupon()!");
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt2);
			JdbcUtil.close(pstmt);
		}
		
		return insertCount;
	}
	
	// 쿠폰 목록 마이페이지 쿠폰목록 조회와 겹쳐서 주석처리해놓음 구동 이상없으면 추후 지울 것
//	public List<CouponBean> selectCouponList(String member_id) {
//		List<CouponBean> couponList = null;
//		System.out.println("couponDAO - selectCouponList()");
//		try {
//			// 전체 회원 목록 조회(임시로 아이디 오름차순 정렬) 
//			String sql = "SELECT * FROM coupon WHERE member_id=? ORDER BY coupon_idx ASC ";
//			pstmt = con.prepareStatement(sql);
//			pstmt.setString(1, member_id);
//			rs = pstmt.executeQuery();
//			
//			couponList = new ArrayList<CouponBean>();
//			while(rs.next()) {
//				// 쿠폰한개의 정보 저장할 couponDTO 생성
//				CouponBean coupon = new CouponBean();
//				coupon.setMember_id(rs.getString("member_id"));
//				coupon.setCoupon_idx(rs.getInt("coupon_idx"));
//				coupon.setCoupon_type(rs.getString("coupon_type"));
//				coupon.setCoupon_rate(rs.getInt("coupon_rate"));
//				coupon.setCoupon_date(rs.getDate("coupon_date"));
//				coupon.setCoupon_end_date(rs.getDate("coupon_end_date"));
//				
//				//List 객체에 couponDTO 객체 추가
//				couponList.add(coupon);
//			}
//		} catch (SQLException e) {
//			System.out.println("selectCouponList - SQL 구문 오류!");
//			e.printStackTrace();
//		} finally {
//			JdbcUtil.close(rs);
//			JdbcUtil.close(pstmt);
//		}
//		
//		return couponList;
//		}

	public int deleteCoupon(String coupon_end_date) {
		int deleteCount = 0;
		
		PreparedStatement pstmt = null;
		
		try {
			// 쿠폰 만료일에 해당하는 레코드 삭제
			String sql = "DELETE FROM coupon "
								+ "WHERE coupon_end_date=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, coupon_end_date);
			deleteCount = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("couponDAO - deleteCoupon()");
			e.printStackTrace();
		} finally {
			// DB 자원 반환
			JdbcUtil.close(pstmt);
		}
		
		return deleteCount;
	}
	
	// 쿠폰 수정
	public int updateCoupon(CouponBean coupon) {
		int updateCount = 0;
		PreparedStatement pstmt = null;
		try {
			String sql = "UPDATE coupon "
								+ "SET coupon_type=?, coupon_rate=?, coupon_end_date=?"
								+ " WHERE member_id=? AND coupon_idx=?"; //조건이 필요할지 모르겠음.. 
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, coupon.getCoupon_type());
			pstmt.setInt(2, coupon.getCoupon_rate());
			pstmt.setDate(3, coupon.getCoupon_end_date());
			pstmt.setString(4, coupon.getMember_id());
			pstmt.setInt(5, coupon.getCoupon_idx());

//			System.out.println(coupon);
			updateCount = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("couponDAO - updateCoupon()");
			e.printStackTrace();
		} finally {
			JdbcUtil.close(pstmt);
		}
		return updateCount;
	}
	
		// 관리자페이지 - 각 회원(member_id)의 쿠폰 내역 조회
		public List<CouponBean> selectMemberCouponList(String member_id) {
			List<CouponBean> couponList = null;
			
			try {
				String sql = "SELECT * FROM coupon WHERE member_id = ? ORDER BY coupon_date DESC";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, member_id);
				rs = pstmt.executeQuery();
				
				couponList = new ArrayList<CouponBean>();
				
				while(rs.next()) {
					CouponBean coupon = new CouponBean();
					coupon.setMember_id(rs.getString("member_id"));
					coupon.setCoupon_idx(rs.getInt("coupon_idx"));
					coupon.setCoupon_type(rs.getString("coupon_type"));
					coupon.setCoupon_rate(rs.getInt("coupon_rate"));
					coupon.setCoupon_date(rs.getDate("coupon_date"));
					coupon.setCoupon_end_date(rs.getDate("coupon_end_date"));
					
					couponList.add(coupon);
				}
				
			} catch (SQLException e) {
				System.out.println("SQL 구문 오류! - selectMemberCouponList()");
				e.printStackTrace();
			} finally {
				JdbcUtil.close(rs);
				JdbcUtil.close(pstmt);
			}
			
			
			return couponList;
		}
		
		// 마이페이지 - 각 회원(member_id)의 쿠폰 내역 조회
		public List<CouponBean> selectMemberCouponList(String member_id, int startRow, int couponLimit) {
			List<CouponBean> couponList = null;
			
			try {
				String sql = "SELECT * FROM coupon WHERE member_id = ? ORDER BY coupon_date DESC LIMIT ?, ?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, member_id);
				pstmt.setInt(2, startRow);
				pstmt.setInt(3, couponLimit);
				rs = pstmt.executeQuery();
				
				couponList = new ArrayList<CouponBean>();
				
				while(rs.next()) {
					CouponBean coupon = new CouponBean();
					coupon.setMember_id(rs.getString("member_id"));
					coupon.setCoupon_idx(rs.getInt("coupon_idx"));
					coupon.setCoupon_type(rs.getString("coupon_type"));
					coupon.setCoupon_rate(rs.getInt("coupon_rate"));
					coupon.setCoupon_date(rs.getDate("coupon_date"));
					coupon.setCoupon_end_date(rs.getDate("coupon_end_date"));
					
					couponList.add(coupon);
				}
				
			} catch (SQLException e) {
				System.out.println("SQL 구문 오류! - selectMemberCouponList()");
				e.printStackTrace();
			} finally {
				JdbcUtil.close(rs);
				JdbcUtil.close(pstmt);
			}
			
			
			return couponList;
		}

		// 마이페이지 - 회원별 쿠폰 보유갯수 조회 : 쿠폰 페이징 처리 / 마이페이지 쿠폰 갯수 뿌리기에 사용
		public int selectMemberCouponCount(String member_id) {
			int CouponCount = 0;
			
			try {
				String sql = "SELECT Count(*) FROM coupon WHERE member_id = ?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, member_id);
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					CouponCount = rs.getInt(1);
				}
				
			} catch (SQLException e) {
				System.out.println("SQL 구문 오류! - selectMemberCouponCount()");
				e.printStackTrace();
			} finally {
				JdbcUtil.close(rs);
				JdbcUtil.close(pstmt);
			}
			
			return CouponCount;
		}
		
		// 관리자 페이지 - 쿠폰 전체 목록 조회
		public List<CouponBean> selectMemberCouponList() {
			List<CouponBean> couponListTotal = null;
//			System.out.println("selectMemberCouponList()");
			try {
				String sql = "SELECT * FROM coupon ORDER BY coupon_end_date ASC";
				pstmt = con.prepareStatement(sql);
				rs = pstmt.executeQuery();
				
				couponListTotal = new ArrayList<CouponBean>();
				
				while(rs.next()) {
					CouponBean coupon = new CouponBean();
					coupon.setMember_id(rs.getString("member_id"));
					coupon.setCoupon_idx(rs.getInt("coupon_idx"));
					coupon.setCoupon_type(rs.getString("coupon_type"));
					coupon.setCoupon_rate(rs.getInt("coupon_rate"));
					coupon.setCoupon_date(rs.getDate("coupon_date"));
					coupon.setCoupon_end_date(rs.getDate("coupon_end_date"));
					
					couponListTotal.add(coupon);
				}
				
			} catch (SQLException e) {
				System.out.println("SQL 구문 오류! - selectMemberCouponList()");
				e.printStackTrace();
			} finally {
				JdbcUtil.close(rs);
				JdbcUtil.close(pstmt);
			}
			
			
			return couponListTotal;
		}
		
		// 회원가입시 웰컴쿠폰
		public int welcomeCoupon(MemberBean member) {
			System.out.println("CouponDAO - welcomeCoupon()");
			int insertCount = 0;
			
			try {
				int coupon_idx = 1; // 새 글 번호
				
				String sql = "SELECT MAX(coupon_idx) FROM coupon";
				pstmt = con.prepareStatement(sql);
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					coupon_idx = rs.getInt(1) +1;
					sql = "INSERT INTO coupon VALUES(?, ?, ?, 10, now(), DATE_ADD(NOW(), INTERVAL 14 DAY))";
					pstmt2 = con.prepareStatement(sql);
					pstmt2.setString(1, member.getMember_id());
					pstmt2.setInt(2, coupon_idx);
					pstmt2.setString(3, "웰컴 쿠폰!");
					insertCount = pstmt2.executeUpdate();
				}
			} catch (SQLException e) {
				System.out.println("SQL 구문 오류 발생! - insertCoupon()!");
				e.printStackTrace();
			} finally {
				JdbcUtil.close(rs);
				JdbcUtil.close(pstmt2);
				JdbcUtil.close(pstmt);
			}		
			return insertCount;
		}
		
		// 페이징,,
		public List<CouponBean> searchCouponList(String keyword, int startRow, int listLimit) {
			System.out.println("CouponDAO - searchCouponList()");
			List<CouponBean> searchCouponList = null;
			
			try {
				String sql = "SELECT * FROM coupon WHERE coupon_type LIKE ? ORDER BY coupon_end_date ASC LIMIT ?, ?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, "%" + keyword + "%");
				pstmt.setInt(2, startRow);
				pstmt.setInt(3, listLimit);
				rs = pstmt.executeQuery();
				
				searchCouponList = new ArrayList<CouponBean>();
				
				while(rs.next()) {
					CouponBean coupon = new CouponBean();
					coupon.setMember_id(rs.getString("member_id"));
					coupon.setCoupon_idx(rs.getInt("coupon_idx"));
					coupon.setCoupon_type(rs.getString("coupon_type"));
					coupon.setCoupon_rate(rs.getInt("coupon_rate"));
					coupon.setCoupon_date(rs.getDate("coupon_date"));
					coupon.setCoupon_end_date(rs.getDate("coupon_end_date"));
					
					System.out.println(coupon);
					
					searchCouponList.add(coupon);
				}
				
			} catch (SQLException e) {
				System.out.println("SQL 구문 오류! - searchCouponList()");
				e.printStackTrace();
			} finally {
				JdbcUtil.close(rs);
				JdbcUtil.close(pstmt);
			}
			
			
			return searchCouponList;
		}
		
		// 페이징 목록갯수
		public int searchCouponListCount(String keyword) {
			int searchCouponListCount = 0;
			
			try {
				String sql = "SELECT Count(*) FROM coupon WHERE coupon_type LIKE ?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, "%" + keyword + "%");
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					searchCouponListCount = rs.getInt(1);
				}
				
			} catch (SQLException e) {
				System.out.println("SQL 구문 오류! - searchCouponListCount()");
				e.printStackTrace();
			} finally {
				JdbcUtil.close(rs);
				JdbcUtil.close(pstmt);
			}
			
			
			return searchCouponListCount;
		}
	
}
