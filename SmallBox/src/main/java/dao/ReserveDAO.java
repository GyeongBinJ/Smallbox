package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.JdbcUtil;
import vo.ReserveBean;

public class ReserveDAO {

	//==============mvc==========================================
	private static ReserveDAO instance = new ReserveDAO();
	
	private ReserveDAO() {}

	public static ReserveDAO getInstance() {
		return instance;
	}
	
	private Connection con;
	
	public void setConnection(Connection con) {
		this.con = con;
	}
	//========================================================
	
	PreparedStatement pstmt = null, pstmt2 = null;
	ResultSet rs = null;
	

	public int insertReserve(ReserveBean reserve) {
		System.out.println("reserveDAO");
		int insertCount = 0;
		int res_idx = 1;
		try {
			//=====res_idx 구하기====================================
			String sql = "SELECT MAX(res_idx) FROM reserve";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				res_idx = rs.getInt(1) + 1;
			}
			//=====reserve insert======================================
			sql = "INSERT INTO reserve VALUES(?,?,?,?,?,?,?,?,1,?)";
			pstmt2 = con.prepareStatement(sql);
			pstmt2.setInt(1, res_idx);
			pstmt2.setInt(2, reserve.getRes_num());
			pstmt2.setInt(3, reserve.getTheater_idx());
			pstmt2.setString(4, reserve.getTheater_title());
			pstmt2.setString(5, reserve.getMember_id());
			pstmt2.setDate(6, reserve.getRes_date());
			pstmt2.setTime(7, reserve.getRes_time());
			pstmt2.setString(8, reserve.getRes_seat());
			pstmt2.setInt(9, reserve.getRes_price());
			
			insertCount = pstmt2.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("SQL 구문 오류 - ReserveDAO>insertReserve()");
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			JdbcUtil.close(pstmt2);
		} // ~~~~finally end~~~~

		return insertCount;
	}

	public List<String> getSelectedSeatList(ReserveBean reserve) {
		System.out.println("ReserveDAO : getSelectedSeatList");
		List<String> seatNumList = null;
		System.out.println("dao의 theater_idx : " + reserve.getTheater_idx());
		
		try {
			String sql = "SELECT res_seat FROM reserve WHERE theater_idx=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, reserve.getTheater_idx());
			rs = pstmt.executeQuery();
			
			seatNumList = new ArrayList<String>();
			
			while(rs.next()) {
				seatNumList.add(rs.getString(1));
			}
			
		} catch (SQLException e) {
			System.out.println("SQL 구문 오류 - commentDAO>selectCommentList()");
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		} // ~~~~finally end~~~~
		
//		System.out.println("dao의 예약된 좌석 리스트 : " + seatNumList);
		return seatNumList;
	}

	
	
	
}
