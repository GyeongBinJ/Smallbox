package dao;

import java.sql.Connection;



import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;


import db.JdbcUtil;
import vo.ActionForward;
import vo.MemberBean;

public class MemberDAO {
	
	PreparedStatement pstmt = null, pstmt2 = null;
	ResultSet rs = null;
	//----------------------------------------------------
	private static MemberDAO instance = new MemberDAO();

	public static MemberDAO getInstance() {
		return instance;
	}

	//-----------------------------------------------------
	private Connection con;

	public void setConnection(Connection con) {
		this.con = con;
	}
	//-----------------------------------------------------
	// 회원가입 작업
	public int insertMember(MemberBean member) {
		System.out.println("MemberDAO - insertMember()");
		int insertCount = 0;
		
		try {
			int member_idx = 1; // 기본값이 있으므로 밑의 if문에서 else가 따로 필요X
			String sql = "SELECT MAX(member_idx) FROM member";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				member_idx = rs.getInt(1) +1;
				sql = "INSERT INTO member VALUES(?, ?, ?, ?, ?, ?, now(), ?, 'N')";
				pstmt2 = con.prepareStatement(sql);
				pstmt2.setInt(1, member_idx);
				pstmt2.setString(2, member.getMember_id());
				pstmt2.setString(3, member.getMember_passwd());
				pstmt2.setString(4, member.getMember_name());
				pstmt2.setString(5, member.getMember_email());
				pstmt2.setString(6, member.getMember_phone());
				pstmt2.setDate(7, member.getMember_birth_date());
				insertCount = pstmt2.executeUpdate();
			}
		} catch (SQLException e) {
			System.out.println("SQL 구문 오류 발생! - insertMember()!");
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt2);
			JdbcUtil.close(pstmt);
		}
		
		return insertCount;
	}
	
	// 로그인 작업
	public boolean isRightUser(MemberBean member) {
		System.out.println("MemberDAO - isRightUser()");
		System.out.println(member.getMember_id() + ", " + member.getMember_passwd());
		
		boolean isRightUser = false;
		
		con = JdbcUtil.getConnection();
		
		try {
			// 아이디, 패스워드가 일치하는 레코드 검색
			String sql = "SELECT member_id FROM member WHERE member_id=? AND member_passwd=?";
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, member.getMember_id());
			pstmt.setString(2, member.getMember_passwd());
			
			rs = pstmt.executeQuery();
			
			// 조회 결과 레코드가 존재할 경우 isLoginSuccess 를 true 로 변경
			if(rs.next()) {
				isRightUser = true;
			}
			
		} catch (SQLException e) {
			System.out.println("SQL 구문 오류 발생! - loginMember()");
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
		
		return isRightUser;
		
	}
	
	// 회원 목록 조회 
	public List<MemberBean> selectMemberList() {
		List<MemberBean> memberList = null;
		
		try {
			// 전체 회원 목록 조회(임시로 아이디 오름차순 정렬)
			String sql = "SELECT * FROM member ORDER BY member_id ASC";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			// 전체 회원정보를 저장할 List 객체 생성
			memberList = new ArrayList<MemberBean>();
			
			while(rs.next()) {
				// 1명의 회원 정보를 저장할 MemberBean 객체 생성
				MemberBean member = new MemberBean();
				member.setMember_idx(rs.getInt("member_idx"));
				member.setMember_id(rs.getString("member_id"));
				member.setMember_passwd(rs.getString("member_passwd"));
				member.setMember_name(rs.getString("member_name"));
				member.setMember_email(rs.getString("member_email"));
				member.setMember_phone(rs.getString("member_phone"));
				member.setMember_join_date(rs.getDate("member_join_date"));
				member.setMember_birth_date(rs.getDate("member_birth_date"));
				
				//List 객체에 MemberBean 객체 추가
				memberList.add(member);
			}
		} catch (SQLException e) {
			System.out.println("selectMemberList - SQL 구문 오류!");
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
		
		return memberList;
	}
	
	// 회원리스트 페이징처리
	public List<MemberBean> selectMemberList(String keyword, int startRow, int listLimit) {
		List<MemberBean> memberList = null;
		
		try {
			// member 테이블의 모든 레코드 조회
			// => 회원 아이디에 검색어를 포함하는 레코드 조회(WHERE member_id LIKE '%검색어%')
			//    (단, 쿼리에 직접 '%?%' 형태로 작성 시 ? 문자를 파라미터로 인식하지 못함
			//    (따라서, setXXX() 메서드에서 문자열 결합으로 "%" + "검색어" + "%" 로 처리)
			// => 조회 시작 레코드 행번호(startRow) 부터 listLimit 갯수(5) 만큼만 조회
			String sql = "SELECT * FROM member "
								+ "WHERE member_id "
								+ "LIKE ? "
								+ "LIMIT ?,?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%" + keyword + "%");
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, listLimit);
			rs = pstmt.executeQuery();
			
			// 전체 목록 저장할 List 객체 생성
			memberList = new ArrayList<MemberBean>();
			
			// 조회 결과가 있을 경우
			while(rs.next()) {
				// MemberBean 객체(member) 생성 후 조회 데이터 저장
				MemberBean member = new MemberBean();
				member.setMember_idx(rs.getInt("member_idx"));
				member.setMember_id(rs.getString("member_id"));
				member.setMember_passwd(rs.getString("member_passwd"));
				member.setMember_name(rs.getString("member_name"));
				member.setMember_email(rs.getString("member_email"));
				member.setMember_phone(rs.getString("member_phone"));
				member.setMember_join_date(rs.getDate("member_join_date"));
				member.setMember_birth_date(rs.getDate("member_birth_date"));
				
				// 전체 목록 저장하는 List 객체에 1개 게시물 정보가 저장된 MemberBean 객체 추가
				memberList.add(member);
			}
			
		} catch (SQLException e) {
			System.out.println("MemberDAO - selectMemberList()");
			e.printStackTrace();
		} finally {
			// DB 자원 반환
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
		
		return memberList;
	}
	
	// 회원 정보 수정 updateMember()
	public boolean updateMember(MemberBean member, boolean isChangePass) {
		int updateMember = 0;
		try {
			// 패스워드 변경 여부에 따른 각 SQL 구문 작성
			String sql = "";
			if(isChangePass) { //패스워드 변경시
				sql = "UPDATE member "
								+ "SET "
										+ " member_name=?"
										+ " ,member_id=?,"
										+ " member_passwd=?,"
										+ " member_email=?,"
										+ " member_phone=?"
									+ "WHERE member_id=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, member.getMember_name());
				pstmt.setString(2, member.getMember_id());
				pstmt.setString(3, member.getMember_passwd());
				pstmt.setString(4, member.getMember_email());
				pstmt.setString(5, member.getMember_phone());
				pstmt.setString(6, member.getMember_id());
				
				isChangePass = true;
			} else { //패스워드 미변경시
				sql = "UPDATE member "
								+ "SET "
									+ " member_name=?,"
									+ " member_id=?,"
									+ " member_email=?,"
									+ " member_phone=?"
								+ "WHERE member_id=?";
				
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, member.getMember_name());
				pstmt.setString(2, member.getMember_id());
				pstmt.setString(3, member.getMember_email());
				pstmt.setString(4, member.getMember_phone());
				pstmt.setString(5, member.getMember_id());
			}
			
			updateMember = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("sql 구문 오류 - updateMember()");
			e.printStackTrace();
		} finally {
			JdbcUtil.close(pstmt);
		}
		return isChangePass;
			
	} // 회원 정보 수정 끝
	
	// 회원정보수정란 정보 가져오기
	public MemberBean getMemberInfo(String sId) {
		MemberBean member = null;
		
		try {
			String sql = "SELECT * FROM member WHERE member_id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, sId);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				member = new MemberBean();
				member.setMember_name(rs.getString("member_name"));
				member.setMember_id(rs.getString("member_id"));
				member.setMember_passwd(rs.getString("member_passwd"));
				member.setMember_email(rs.getString("member_email"));
				member.setMember_phone(rs.getString("member_phone"));
				System.out.println(member);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
		
		return member; 
	}
	
	// 회원 삭제
	public int deleteMember(MemberBean member) {
		System.out.println("MemberDAO - deleteMember()");
		System.out.println(member);
		System.out.println("DAO비밀번호 : " + member.getMember_passwd());
		
		int deleteCount = 0;
		
		try {
			String sql = "DELETE FROM member WHERE member_id=? AND member_passwd=?";
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, member.getMember_id());
			pstmt.setString(2, member.getMember_passwd());
			
			deleteCount = pstmt.executeUpdate();
			System.out.println("DAO : " + deleteCount);
		} catch (SQLException e) {
			System.out.println("SQL 구문 오류 발생! - deleteMember()");
			e.printStackTrace();
		} finally {
			JdbcUtil.close(pstmt);
		} 
		
		return deleteCount;
		
	}
	
	// 아이디 존재 여부 체크
	public boolean selectMemberId(MemberBean member) {
	      boolean isExist =false;
	      
	      String sql = "SELECT * FROM member WHERE member_id=?";
	      try {
	         pstmt = con.prepareStatement(sql);
	         pstmt.setString(1, member.getMember_id());
	         rs = pstmt.executeQuery();
	         
	         if(rs.next()) {
	            isExist =true;
	         }
	         System.out.println("DAO 의 isExist : " + isExist);
	      } catch (SQLException e) {
	         e.printStackTrace();
	      }finally {
	         JdbcUtil.close(rs);
	         JdbcUtil.close(pstmt);
	      }
	      return isExist;
	   }
	
	// 아이디, 이메일 일치여부
	public boolean isExistUser(MemberBean member) {
		System.out.println("MemberDAO - isExistUser()");
		
		boolean isExistUser = false;
		
		con = JdbcUtil.getConnection();
		
		try {
			// 아이디, 패스워드가 일치하는 레코드 검색
			String sql = "SELECT member_id FROM member WHERE member_id=? AND member_email=?";
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, member.getMember_id());
			pstmt.setString(2, member.getMember_email());
			
			rs = pstmt.executeQuery();
			
			// 조회 결과 레코드가 존재할 경우 isExistUser 를 true 로 변경
			if(rs.next()) {
				isExistUser = true;
			}
			
		} catch (SQLException e) {
			System.out.println("SQL 구문 오류 발생! - isExistUser()");
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
		
		return isExistUser;
	}
	
	// 아이디 찾기 
	public String findIdMember(MemberBean member) {
		String id = "";
//			System.out.println("findIdMember");
		try {
			String sql = "SELECT member_id"
								+ " FROM member"
								+ " WHERE member_name = ? AND member_phone = ? AND member_birth_date = ? ";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, member.getMember_name());
			pstmt.setString(2, member.getMember_phone());
			pstmt.setDate(3, member.getMember_birth_date());
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) { // 개인정보 일치할 경우 
				id = rs.getString("member_id");
//					System.out.println(id);
			}
		} catch (SQLException e) {
			System.out.println("MemberDAO - findIdMember() 오류");
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
		return id;
	}
	
	// 아이디 일치 여부
	public boolean memberCheckId(String id) {
		System.out.println("MemberDAO - memberCheckId()");
		boolean isId = false;
		
		try {
			String sql = "SELECT member_id FROM member WHERE member_id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				System.out.println("아이디 확인!");
				isId = true;
			}
		} catch (SQLException e) {
			System.out.println("SQL 구문 오류 발생! - memberCheckId()");
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
		return isId;
	}
	
	public int selectMemberListCount(String keyword) {
		int listCount = 0;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			// member 테이블의 모든 레코드 갯수 조회
			// => 제목에 검색어를 포함하는 레코드 조회(WHERE member_id LIKE '%검색어%')
			//    (단, 쿼리에 직접 '%?%' 형태로 작성 시 ? 문자를 파라미터로 인식하지 못함
			//    (따라서, setXXX() 메서드에서 문자열 결합으로 "%" + "검색어" + "%" 로 처리)
			String sql = "SELECT COUNT(*) "
								+ "FROM member "
								+ "WHERE member_id LIKE ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%" + keyword + "%");
			rs = pstmt.executeQuery();
			
			// 조회 결과가 있을 경우 listCount 변수에 1씩 저장
			if(rs.next()) {
				listCount = rs.getInt(1);
			}
			
		} catch (SQLException e) {
			System.out.println("CouponDAO - selectCouponListCount()");
			e.printStackTrace();
		} finally {
			// DB 자원 반환
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
		
		return listCount;
	}
	
	// 임시비밀번호로 변경
	public boolean updateImsiPW(String imsiPW, MemberBean member) {
		System.out.println("MemberDAO - updateImsiPW()");
		boolean updateImsiPW = false;
		try {
			String sql = "UPDATE member SET member_passwd = ? WHERE member_id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, imsiPW);
			pstmt.setString(2, member.getMember_id());
			if(pstmt.executeUpdate() > 0) {
				updateImsiPW = true;
			}
		} catch (SQLException e) {
			System.out.println("SQL 구문 오류 발생! - updateImsiPW()");
			e.printStackTrace();
		}
		
		return updateImsiPW;
	}
	
		
		
}
