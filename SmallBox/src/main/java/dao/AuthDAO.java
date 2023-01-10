package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.JdbcUtil;
import vo.AuthBean;
import vo.CouponBean;

public class AuthDAO {
	PreparedStatement pstmt = null, pstmt2 = null, pstmt3 = null;
	ResultSet rs = null;
	//----------------------------------------------------
	private static AuthDAO instance = new AuthDAO();

	public static AuthDAO getInstance() {
		return instance;
	}

	//-----------------------------------------------------
	private Connection con;

	public void setConnection(Connection con) {
		this.con = con;
	}

	public String authCodeCheck() {
		
		return null;
	}
	
	// 인증번호 디비 확인 작업 + 인증번호 등록
	public boolean insertAuthcode(AuthBean auth) {
		System.out.println("AuthDAO - insertAuthcode()");
		boolean insertAuthSuccess =false;
		
		try {
			String sql = "SELECT auth_authCode FROM auth WHERE auth_id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, auth.getAuth_id());
			rs = pstmt.executeQuery();
				if(rs.next()) { // 아이디가 존재한다면! 난수로 update!
//					System.out.println("셀렉트 성공");
//					System.out.println(auth.getAuth_authCode());
//					System.out.println(auth.getAuth_id());
					sql = "UPDATE auth SET auth_authCode = ? WHERE auth_id = ?";
					pstmt2 = con.prepareStatement(sql);
					pstmt2.setString(1, auth.getAuth_authCode());
					pstmt2.setString(2, auth.getAuth_id());
					int count = pstmt2.executeUpdate();
					if(count > 0) {
						insertAuthSuccess = true;
					}
				} else {
//					System.out.println("값 없음2");
					sql = "INSERT INTO auth VALUES(?, ?)";
					pstmt3 = con.prepareStatement(sql);
					pstmt3.setString(1, auth.getAuth_id());
					pstmt3.setString(2, auth.getAuth_authCode());
					int count = pstmt3.executeUpdate();
					System.out.println("count = " + count );
					if(count > 0) {
						insertAuthSuccess = true;
					}					
				}
		} catch (SQLException e) {
			System.out.println("sql구문 오류! - insertAuthcode()");
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt3);
			JdbcUtil.close(pstmt2);
			JdbcUtil.close(pstmt);
		}
		return insertAuthSuccess;
	}
	
	
	
	// 인증코드 확인작업
	public String authcodeConfirm(String authCode, String id) {
		String authcodeConfirm = "";
		System.out.println("authDAO - authcodeConfirm()");
		System.out.println(authCode);
		try {
			String sql = "SELECT auth_authCode FROM auth WHERE auth_id = ? AND auth_authCode=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, authCode);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				authcodeConfirm = authCode;
				System.out.println(authcodeConfirm);
			}
		} catch (SQLException e) {
			System.out.println("authcodeConfirm - SQL 구문 오류!");
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
		
		return authcodeConfirm;
	}
	
	// 인증코드 두번 확인시 삭제
	public void authcodeDelete(String authCode) {
		System.out.println("authcodeDelete() - dao");
		try {
			String sql = "DELETE FROM auth WHERE auth_authCode = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, authCode);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcUtil.close(pstmt);
		}
		
		
	}

}
