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
	//=========0108고은 마이페이지 예매관리===============================
	// 예매내역(목록) 조회
	public List<ReserveBean> selectReserveList(String sId, int startRow, int listLimit) {
		List<ReserveBean> reserveList = null;
		try {
			String sql = "SELECT * FROM reserve "
								+ "WHERE member_id=? "
								+ "ORDER BY res_idx DESC "
								+ "LIMIT ?,?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1,sId);
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, listLimit);
			rs = pstmt.executeQuery();
			// 전체 목록 저장할 List 객체 생성
			reserveList = new ArrayList<ReserveBean>();
			// 조회 결과가 있을 경우
			while(rs.next()) {
				
				// ReserveBean 객체(reserve) 생성 후 조회 데이터 저장
				ReserveBean reserve = new ReserveBean();
				reserve.setRes_idx(rs.getInt("res_idx"));
				reserve.setRes_num(rs.getInt("res_num"));
				reserve.setTheater_idx(rs.getInt("theater_idx"));
				reserve.setTheater_title(rs.getString("theater_title"));
				reserve.setMember_id(rs.getString("member_id"));
				reserve.setRes_date(rs.getDate("res_date"));
				reserve.setRes_time(rs.getTime("res_time"));
				reserve.setRes_seat(whereIsMySeat(rs.getString("res_seat")));
				reserve.setRes_pay_type(rs.getInt("res_pay_type"));
				reserve.setRes_price(rs.getInt("res_price"));
				System.out.println("reserve : "+reserve);
				// 전체 목록 저장하는 List 객체에 1개 게시물 정보가 저장된 ReserveBean 객체 추가
				reserveList.add(reserve);
			}
		} catch (SQLException e) {
			System.out.println("ReserveDAO - selectReserveList()");
			e.printStackTrace();
		} finally {
			// DB 자원 반환
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
		return reserveList;
	}
	//행열 변환하기메서드 추가
	public String whereIsMySeat(String res_seat) {
		String row = String.valueOf(res_seat.charAt(0));
		String column = res_seat.substring(1);
		if(column=="10") {
		} else {
			if(column.startsWith("0")) {
				column = column.substring(1);
			}
		}
		System.out.println("row : "+row);
		System.out.println("column : "+column);
		switch(row){
	    case "1" : 
	        row="A"; 
	        break;
	    case "2" : 
	        row="B"; 
	        break;
	    case "3" : 
	        row="B"; 
	        break; 
	    case "4" : 
	        row="C"; 
	        break; 
	    case "5" : 
	        row="E"; 
	        break; 
	    case "6" : 
	        row="F"; 
	        break; 
	    default :
	        row="G";    
		}
	        res_seat = row+"행 "+column+"열";
		return res_seat;
	}

	//예매 취소가능한 시간 판별(전날까지만 예매취소할수있게하였음!)
	public boolean isTimeOk(int res_idx) {
		boolean isTimeOk = false;//isTimeOk - 예매 가능한시간판별(예매일 이전인가?)
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			String sql = 
					"SELECT * FROM reserve WHERE res_date "
					+"IN (SELECT res_date FROM reserve WHERE res_date > date_format(now(),'%Y-%m-%d')) "
					+ "AND res_idx=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, res_idx);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				isTimeOk = true;
			}
		} catch (SQLException e) {
			System.out.println("sql - isTimeOk()");
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
		return isTimeOk;
	}
	//예약 내역 취소(삭제)작업
	public int cancelReserve(int res_num) {
		int cancelCount = 0;
		PreparedStatement pstmt = null;
		try {
			String sql = "DELETE FROM reserve WHERE res_idx=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, res_num);
			cancelCount = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("cancelReserve()");
			e.printStackTrace();
		} finally {
			JdbcUtil.close(pstmt);
		}
		return cancelCount;
	}
	// 예약 상세정보 조회
	public ReserveBean selectReserve(int res_num) {
		ReserveBean reserve = null;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			// reserve 테이블에서 예약번호(res_idx)가 일치하는 1개 레코드 조회
			String sql = "SELECT * FROM reserve "
								+ "WHERE res_idx=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, res_num);
			rs = pstmt.executeQuery();
			
			// 조회 결과가 있을 경우
			if(rs.next()) {
				// BoardBean 객체(board) 생성 후 조회 데이터 저장
				reserve = new ReserveBean();
				reserve.setRes_idx(rs.getInt("res_idx"));
				reserve.setRes_num(rs.getInt("res_num"));
				reserve.setTheater_idx(rs.getInt("theater_idx"));
				reserve.setTheater_title(rs.getString("theater_title"));
				reserve.setMember_id(rs.getString("member_id"));
				reserve.setRes_date(rs.getDate("res_date"));
				reserve.setRes_time(rs.getTime("res_time"));
				reserve.setRes_seat(rs.getString("res_seat"));
				reserve.setRes_pay_type(rs.getInt("res_pay_type"));
				reserve.setRes_price(rs.getInt("res_price"));
//						System.out.println(reserve);
			}
		} catch (SQLException e) {
			System.out.println("ReserveDao - selectReserve()");
			e.printStackTrace();
		} finally {
			// DB 자원 반환
					JdbcUtil.close(rs);
					JdbcUtil.close(pstmt);
		}
		return reserve;
	}
	// 예약한 영화의 movie_idx를 theater_title로 판별하기
	public int setMovie_idx(int res_idx) {
		int movie_idx = 0;
		System.out.println("dao - movie_idx : "+movie_idx);
		System.out.println("dao - res_idx : "+res_idx);
		try {
			String sql = "SELECT 영화.movie_idx FROM movie 영화 JOIN reserve 예매 ON 영화.movie_title=예매.theater_title WHERE res_idx=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, res_idx);
			rs = pstmt.executeQuery();
			rs.next();
			movie_idx = rs.getInt(1);
		} catch (SQLException e) {
			System.out.println("SQL 구문 오류! - setMovie_idx()");
			e.printStackTrace();
		} finally {
			JdbcUtil.close(pstmt);
		}
		System.out.println("setMovie_idx()메서드로 설정된 movie_idx : "+movie_idx);
		return movie_idx;
	}
	// 예매 내역 개수 조회
	public int selectReserveListCount(String sId) {
		int listCount = 0;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			// qna 테이블의 모든 레코드 갯수 조회
			String sql = "SELECT COUNT(*) "
						+ "FROM reserve WHERE member_id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, sId);
			rs = pstmt.executeQuery();
			
			// 조회 결과가 있을 경우 listCount 변수에 저장
			if(rs.next()) {
				listCount = rs.getInt(1);
			}
			
		} catch (SQLException e) {
			System.out.println("ReserveDAO - selectReserveListCount()");
			e.printStackTrace();
		} finally {
			// DB 자원 반환
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
		
		return listCount;
	}
	//=========0108고은 마이페이지 예매관리===============================
	
	//=========0109해연 예매 추가 ===============================
	
	//예약 완료 후 전체 좌석 수에서 예약한 좌석 수 빼기
		public int subtractSeat(int theater_idx, int people) {
			System.out.println("ReserveDAO : subtractSeat");
			int subCount = 0;
			int theater_seat_cnt = 0;
			
			try {
				String sql = "SELECT theater_seat_cnt FROM theater WHERE theater_idx=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, theater_idx);
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					theater_seat_cnt = rs.getInt(1)-people;
					System.out.println("dao : theater_seat_cnt : " + theater_seat_cnt);
				}
				
				sql = "UPDATE theater SET theater_seat_cnt=? WHERE theater_idx=?";
				pstmt2 = con.prepareStatement(sql);
				pstmt2.setInt(1, theater_seat_cnt);
				pstmt2.setInt(2, theater_idx);
				
				subCount = pstmt2.executeUpdate();
			} catch (SQLException e) {
					System.out.println("SQL 구문 오류 - ReserveDAO>subtractSeat()");
					e.printStackTrace();
				} finally {
					JdbcUtil.close(rs);
					JdbcUtil.close(pstmt);
					JdbcUtil.close(pstmt2);
				} // ~~~~finally end~~~~
			
			return subCount;
		}
		//예약 완료 후 사용한 쿠폰 없애기

		public int deleteUsedCoupon(int coupon_idx) {
			System.out.println("ReserveDAO : deleteUsedCoupon");
			int delCount = 0;
			
			try {
				String sql = "DELETE FROM coupon WHERE coupon_idx=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, coupon_idx);
			
				delCount = pstmt.executeUpdate();
			
			} catch (SQLException e) {
				System.out.println("SQL 구문 오류 : deleteUsedCoupon");
				e.printStackTrace();
			} finally {
				JdbcUtil.close(pstmt);
			} // ~~~~finally end~~~~

			return delCount;
		}	
	
		// 결제 취소건에 대해 예매내역 삭제 작업
        public int cancelReserve2(int res_num) {
           System.out.println("ReserveDAO - cancelReserve2()");
           int cancelCount = 0;
           PreparedStatement pstmt = null;
           
           try {
              String sql = "DELETE FROM reserve WHERE res_num=?";
              pstmt = con.prepareStatement(sql);
              pstmt.setInt(1, res_num);
              cancelCount = pstmt.executeUpdate();
              
           } catch (SQLException e) {
              System.out.println("SQL 구문 오류 - cancelReserve2()");
              e.printStackTrace();
           } finally {
              JdbcUtil.close(pstmt);
           }
           return cancelCount;
        }
	
	//=========0109해연 예매 추가 ===============================
}
