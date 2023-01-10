package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.JdbcUtil;
import vo.NoticeBean;

public class NoticeDAO {
	
private NoticeDAO() {}
	
	private static NoticeDAO instance = new NoticeDAO();

	public static NoticeDAO getInstance() {
		return instance;
	}
	
	private Connection con;

	public void setConnection(Connection con) {
		this.con = con;
	}

	public int insertNotice(NoticeBean notice) {
		System.out.println("NoticeDAO - insertNotice()");
		
		int insertCount = 0;
		
		PreparedStatement pstmt = null, pstmt2 = null;
		ResultSet rs = null;
		
		try {
			int notice_idx = 1;
			
			String sql = "SELECT MAX(notice_idx) FROM notice";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				notice_idx = rs.getInt(1) + 1;
			}
			System.out.println("새 글 번호: " + notice_idx);
			
			sql = "INSERT INTO notice VALUES (?,?,?,?,?,0,now())";
			
			pstmt2 = con.prepareStatement(sql);
			pstmt2.setInt(1, notice_idx);
			pstmt2.setString(2, notice.getNotice_subject());
			pstmt2.setString(3, notice.getNotice_content());
			pstmt2.setString(4, notice.getNotice_file());
			pstmt2.setString(5, notice.getNotice_real_file());
			
			insertCount = pstmt2.executeUpdate();
			
			
		} catch (SQLException e) {
			System.out.println("SQL 구문 오류! - insertNotice()");
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			JdbcUtil.close(pstmt2);
		}
		
		return insertCount;
	}

	public List<NoticeBean> selectNoticeList(String keyword, int startRow, int listLimit) {
		List<NoticeBean> noticeList = null;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			// board 테이블의 모든 레코드 조회
			// => 제목에 검색어를 포함하는 레코드 조회(WHERE subject LIKE '%검색어%')
			//    (단, 쿼리에 직접 '%?%' 형태로 작성 시 ? 문자를 파라미터로 인식하지 못함
			//    (따라서, setXXX() 메서드에서 문자열 결합으로 "%" + "검색어" + "%" 로 처리)
			// => 정렬 : 참조글번호(board_re_ref) 기준 내림차순, 
			//           순서번호(board_re_seq) 기준 오름차순
			// => 조회 시작 레코드 행번호(startRow) 부터 listLimit 갯수(10) 만큼만 조회
			String sql = "SELECT * FROM notice "
								+ "WHERE notice_subject "
								+ "LIKE ? "
								+ "ORDER BY notice_idx DESC "
								+ "LIMIT ?,?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%" + keyword + "%");
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, listLimit);
			rs = pstmt.executeQuery();
			
			// 전체 목록 저장할 List 객체 생성
			noticeList = new ArrayList<NoticeBean>();
			
			// 조회 결과가 있을 경우
			while(rs.next()) {
				// BoardBean 객체(board) 생성 후 조회 데이터 저장
				NoticeBean notice = new NoticeBean();
				notice.setNotice_idx(rs.getInt("notice_idx"));
				notice.setNotice_subject(rs.getString("notice_subject"));
				notice.setNotice_content(rs.getString("notice_content"));
				notice.setNotice_file(rs.getString("notice_file"));
				notice.setNotice_real_file(rs.getString("notice_real_file"));
				notice.setNotice_readCount(rs.getInt("notice_readcount"));
				notice.setNotice_date(rs.getTimestamp("notice_date"));
				System.out.println(notice);
				
				// 전체 목록 저장하는 List 객체에 1개 게시물 정보가 저장된 BoardBean 객체 추가
				noticeList.add(notice);
			}
			
		} catch (SQLException e) {
			System.out.println("NoticeDAO - selectNoticeList()");
			e.printStackTrace();
		} finally {
			// DB 자원 반환
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
		
		return noticeList;
	}
	
	public int selectNoticeListCount(String keyword) {
		int listCount = 0;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			// board 테이블의 모든 레코드 갯수 조회
			// => 제목에 검색어를 포함하는 레코드 조회(WHERE subject LIKE '%검색어%')
			//    (단, 쿼리에 직접 '%?%' 형태로 작성 시 ? 문자를 파라미터로 인식하지 못함
			//    (따라서, setXXX() 메서드에서 문자열 결합으로 "%" + "검색어" + "%" 로 처리)
			String sql = "SELECT COUNT(*) "
								+ "FROM notice "
								+ "WHERE notice_subject LIKE ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%" + keyword + "%");
			rs = pstmt.executeQuery();
			
			// 조회 결과가 있을 경우 listCount 변수에 저장
			if(rs.next()) {
				listCount = rs.getInt(1);
			}
			
		} catch (SQLException e) {
			System.out.println("NoticeDAO - selectNoticeListCount()");
			e.printStackTrace();
		} finally {
			// DB 자원 반환
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
		
		return listCount;
	}

	public NoticeBean selectNotice(int notice_idx) {
		NoticeBean notice = null;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			// board 테이블에서 글번호(board)가 일치하는 1개 레코드 조회
			String sql = "SELECT * FROM notice "
								+ "WHERE notice_idx=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, notice_idx);
			rs = pstmt.executeQuery();
			
			// 조회 결과가 있을 경우
			if(rs.next()) {
				// BoardBean 객체(board) 생성 후 조회 데이터 저장
				notice = new NoticeBean();
				notice.setNotice_idx(rs.getInt("notice_idx"));
				notice.setNotice_subject(rs.getString("notice_subject"));
				notice.setNotice_content(rs.getString("notice_content"));
				notice.setNotice_file(rs.getString("notice_file"));
				notice.setNotice_real_file(rs.getString("notice_real_file"));
				notice.setNotice_readCount(rs.getInt("notice_readcount"));
				notice.setNotice_date(rs.getTimestamp("notice_date"));
				System.out.println(notice);
			}
			
		} catch (SQLException e) {
			System.out.println("NoticeDAO - selectNotice()");
			e.printStackTrace();
		} finally {
			// DB 자원 반환
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
		
		return notice;
	}

	public int updateReadcount(int notice_idx) {
		int updateCount = 0;
		
		PreparedStatement pstmt = null;
		
		try {
			// 글번호가 일치하는 레코드의 조회수(readcount) 1만큼 증가
			String sql = "UPDATE notice "
								+ "SET notice_readcount=notice_readcount+1 "
								+ "WHERE notice_idx=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, notice_idx);
			updateCount = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("NoticeDAO - updateReadcount()");
			e.printStackTrace();
		} finally {
			// DB 자원 반환
			JdbcUtil.close(pstmt);
		}
		
		return updateCount;
	}

	public int deleteNotice(int notice_idx) {
		int deleteCount = 0;
		
		PreparedStatement pstmt = null;
		
		try {
			// 글번호에 해당하는 레코드 삭제
			String sql = "DELETE FROM notice "
								+ "WHERE notice_idx=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, notice_idx);
			deleteCount = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("BoardDAO - deleteBoard()");
			e.printStackTrace();
		} finally {
			// DB 자원 반환
			JdbcUtil.close(pstmt);
		}
		
		return deleteCount;
	}
}
