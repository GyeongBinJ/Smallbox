package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.JdbcUtil;
import vo.QnaBean;

//0108 고은 수정된 QndaDAO 업로드==qna테이블에 member_id컬럼 추가필요============================

// 실제 비즈니스 로직을 수행하는 BoardDAO 클래스 정의
// => 각 Service 클래스 인스턴스에서 BoardDAO 인스턴스에 접근 시 고유 데이터가 불필요하므로
//    BoardDAO 인스턴스는 애플리케이션에서 단 하나만 생성하여 공유해도 된다!
//    따라서, 싱글톤 디자인 패턴을 적용하여 클래스를 정의하면 메모리 낭비를 막을 수 있다!
public class QnaDAO {
	// ------------ 싱글톤 디자인 패턴을 활용한 BoardDAO 인스턴스 생성 작업 -------------
	// 1. 외부에서 인스턴스 생성이 불가능하도록 생성자를 private 접근제한자로 선언
	// 2. 자신의 클래스 내에서 직접 인스턴스를 생성하여 멤버변수에 저장
	//    => 인스턴스 생성없이 클래스가 메모리에 로딩될 때 함께 로딩되도록 static 변수로 선언
	//    => 외부에서 접근하여 함부로 값을 변경할 수 없도록 private 접근제한자로 선언
	// 3. 생성된 인스턴스를 외부로 리턴하는 Getter 메서드 정의
	//    => 인스턴스 생성없이 클래스가 메모리에 로딩될 때 함께 로딩되도록 static 메서드로 선언
	//    => 누구나 접근 가능하도록 public 접근제한자로 선언
	private QnaDAO() {}
	
	private static QnaDAO instance = new QnaDAO();

	public static QnaDAO getInstance() {
		return instance;
	}
	// ----------------------------------------------------------------------------------
	// 데이터베이스 접근에 사용할 Connection 객체를 Service 객체로부터 전달받기 위한
	// Connection 타입 멤버변수 선언 및 Setter 메서드 정의
	private Connection con;

	public void setConnection(Connection con) {
		this.con = con;
	}
	// ----------------------------------------------------------------------------------
	// 1:1문의 작업 수행
	// => Service 로부터 전달받은 QnaBean 객체를 사용하여 INSERT 작업 수행
	// => 파라미터 : QnaBean 객체   리턴타입 : int(insertCount)
	public int insertQna(QnaBean qna) {
		System.out.println("QnaDAO - insertQna()");
		
		// INSERT 작업 결과를 리턴받아 저장할 변수 선언
		int insertCount = 0;
		
		// 데이터베이스 작업에 필요한 변수 선언
		PreparedStatement pstmt = null, pstmt2 = null;
		ResultSet rs = null;
		
		try {
			// 새 글 번호 계산을 위해 기존 board 테이블의 모든 번호(board_num) 중 가장 큰 번호 조회
			// => 조회 결과 + 1 값을 새 글 번호로 지정하고, 조회 결과가 없으면 기본값 1 로 설정
			// => MySQL 구문의 MAX() 함수 사용(SELECT MAX(컬럼명) FROM 테이블명)
			int qna_idx = 1; // 새 글 번호
			
			String sql = "SELECT MAX(qna_idx) FROM qna";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()) { // 조회 결과가 있을 경우(= 기존 게시물이 하나라도 존재할 경우)
				// (만약, 게시물이 존재하지 않을 경우 DB 에서 NULL 로 표기, rs.next() 가 false)
				qna_idx = rs.getInt(1) + 1; // 기존 게시물 번호 중 가장 큰 번호(= 조회 결과) + 1
			}
//			System.out.println("새 글 번호 : " + qna_idx);
			// --------------------------------------------------------------------------------
			// 전달받은 데이터(QnaBean 객체)를 사용하여 INSERT 작업 수행
			// => 참조글번호(qna_re_ref)는 새 글 번호와 동일한 번호로 지정
			// => 들여쓰기레벨(qna_re_lev)과 순서번호(qna_re_seq)는 0으로 지정
			// => INSERT 구문 실행 후 리턴값을 insertCount 변수에 저장
			sql = "INSERT INTO qna VALUES (?,?,?,now(),?,0,0,?)";
			pstmt2 = con.prepareStatement(sql);
			pstmt2.setInt(1, qna_idx); // 글번호
			pstmt2.setString(2, qna.getQna_subject()); //제목
			pstmt2.setString(3, qna.getQna_content()); //내용
			pstmt2.setInt(4, qna_idx); // 참조글번호(글쓰기는 글번호와 동일하게 사용)
//			pstmt2.setInt(5, 0); // 들여쓰기레벨
//			pstmt2.setInt(6, 0); // 순서번호
			pstmt2.setString(5, qna.getMember_id());
			
			insertCount = pstmt2.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("SQL 구문 오류! - insertQna()");
			e.printStackTrace();
		} finally {
			// DB 자원 반환
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			JdbcUtil.close(pstmt2);
			// 주의! Connection 객체는 Service 클래스가 관리하므로 DAO 에서 반환 금지!
		}
		
		return insertCount; // Service 로 리턴
	}
	
	// 회원용 글목록 조회
		public List<QnaBean> selectQnaList(String sId, int startRow, int listLimit) {
		List<QnaBean> qnaList = null;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			// qna 테이블의 모든 레코드 조회
			// => 정렬 : 참조글번호(qna_re_ref) 기준 내림차순, 
			//           순서번호(qna_re_seq) 기준 오름차순
			// => 조회 시작 레코드 행번호(startRow) 부터 listLimit 갯수(10) 만큼만 조회
			String sql = "SELECT * "
					+ "FROM qna a, (SELECT qna_re_ref FROM qna WHERE member_id = ?) id_ref "
					+ "WHERE a.qna_re_ref = id_ref.qna_re_ref "
					+ "ORDER BY a.qna_re_ref DESC, a.qna_re_seq ASC "
					+ "LIMIT ?,?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, sId);
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, listLimit);
			rs = pstmt.executeQuery();
			
			// 전체 목록 저장할 List 객체 생성
			qnaList = new ArrayList<QnaBean>();
			// 조회 결과가 있을 경우
			while(rs.next()) {
				// QnaBean 객체(qna) 생성 후 조회 데이터 저장
				QnaBean qna = new QnaBean();
				qna.setQna_idx(rs.getInt("qna_idx"));
				qna.setQna_subject(rs.getString("qna_subject"));
				qna.setQna_content(rs.getString("qna_content"));
				qna.setQna_date(rs.getTimestamp("qna_date"));
				qna.setQna_re_ref(rs.getInt("qna_re_ref"));
				qna.setQna_re_lev(rs.getInt("qna_re_lev"));
				qna.setQna_re_seq(rs.getInt("qna_re_seq"));
				qna.setMember_id(rs.getString("member_id"));
				System.out.println("qna : " + qna);
				// 전체 목록 저장하는 List 객체에 1개 게시물 정보가 저장된 BoardBean 객체 추가
				qnaList.add(qna);
			}
			
		} catch (SQLException e) {
			System.out.println("QnaDAO - selectQnaList()");
			e.printStackTrace();
		} finally {
			// DB 자원 반환
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
		
		return qnaList;
	}
		// 관리자용 글목록 조회
		public List<QnaBean> selectAdminQnaList(int startRow, int listLimit) {
		List<QnaBean> qnaList = null;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			// qna 테이블의 모든 레코드 조회
			// => 정렬 : 참조글번호(qna_re_ref) 기준 내림차순, 
			//           순서번호(qna_re_seq) 기준 오름차순
			// => 조회 시작 레코드 행번호(startRow) 부터 listLimit 갯수(10) 만큼만 조회
			String sql = "SELECT * FROM qna "
								+ "ORDER BY qna_re_ref DESC, qna_re_seq ASC "
								+ "LIMIT ?,?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, listLimit);
			rs = pstmt.executeQuery();
			
			// 전체 목록 저장할 List 객체 생성
			qnaList = new ArrayList<QnaBean>();
			
			// 조회 결과가 있을 경우
			while(rs.next()) {
				// QnaBean 객체(qna) 생성 후 조회 데이터 저장
				QnaBean qna = new QnaBean();
				qna.setQna_idx(rs.getInt("qna_idx"));
				qna.setQna_subject(rs.getString("qna_subject"));
				qna.setQna_content(rs.getString("qna_content"));
				qna.setQna_date(rs.getTimestamp("qna_date"));
				qna.setQna_re_ref(rs.getInt("qna_re_ref"));
				qna.setQna_re_lev(rs.getInt("qna_re_lev"));
				qna.setQna_re_seq(rs.getInt("qna_re_seq"));
				qna.setMember_id(rs.getString("member_id"));
//						System.out.println(qna);
				
				// 전체 목록 저장하는 List 객체에 1개 게시물 정보가 저장된 BoardBean 객체 추가
				qnaList.add(qna);
			}
			
		} catch (SQLException e) {
			System.out.println("QnaDAO - selectQnaList()");
			e.printStackTrace();
		} finally {
			// DB 자원 반환
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
		
		return qnaList;
	}
	
	// 회원용 글목록 갯수 조회
	public int selectQnaListCount(String sId) {
		int listCount = 0;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			// qna 테이블의 모든 레코드 갯수 조회
			String sql = "SELECT COUNT(*) "
						+ "FROM qna a, (SELECT qna_re_ref FROM qna WHERE member_id=?) id_ref "
						+ "WHERE a.qna_re_ref = id_ref.qna_re_ref";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, sId);
			rs = pstmt.executeQuery();
			
			// 조회 결과가 있을 경우 listCount 변수에 저장
			if(rs.next()) {
				listCount = rs.getInt(1);
			}
			
		} catch (SQLException e) {
			System.out.println("QnaDAO - selectQnaListCount()");
			e.printStackTrace();
		} finally {
			// DB 자원 반환
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
		
		return listCount;
	}
	// 관리자용 글목록 갯수 조회
		public int selectAdminQnaListCount() {
			int listCount = 0;
			
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			try {
				// qna 테이블의 모든 레코드 갯수 조회
				String sql = "SELECT COUNT(*) "
									+ "FROM qna";
				pstmt = con.prepareStatement(sql);
				rs = pstmt.executeQuery();
				
				// 조회 결과가 있을 경우 listCount 변수에 저장
				if(rs.next()) {
					listCount = rs.getInt(1);
				}
				
			} catch (SQLException e) {
				System.out.println("QnaDAO - selecAdminQnaListCount()");
				e.printStackTrace();
			} finally {
				// DB 자원 반환
				JdbcUtil.close(rs);
				JdbcUtil.close(pstmt);
			}
			
			return listCount;
		}
	// 글 상세정보 조회
		public QnaBean selectQna(int qna_idx) {
			QnaBean qna = null;
			
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			try {
				String sql = "SELECT * FROM qna "
									+ "WHERE qna_idx=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, qna_idx);
				rs = pstmt.executeQuery();
				
				// 조회 결과가 있을 경우
				if(rs.next()) {
					// QnaBean 객체(qna) 생성 후 조회 데이터 저장
					qna = new QnaBean();
					qna.setQna_idx(rs.getInt("qna_idx"));
					qna.setQna_subject(rs.getString("qna_subject"));
					qna.setQna_content(rs.getString("qna_content"));
					qna.setQna_date(rs.getTimestamp("qna_date"));
					qna.setQna_re_ref(rs.getInt("qna_re_ref"));
					qna.setQna_re_lev(rs.getInt("qna_re_lev"));
					qna.setQna_re_seq(rs.getInt("qna_re_seq"));
					qna.setMember_id(rs.getString("member_id"));
	//				System.out.println(qna);
				}
				
			} catch (SQLException e) {
				System.out.println("QnaDAO - selectQna()");
				e.printStackTrace();
			} finally {
				// DB 자원 반환
				JdbcUtil.close(rs);
				JdbcUtil.close(pstmt);
			}
			
			return qna;
		}
	
	// 글 삭제
	public int deleteQna(int qna_idx) {
		int deleteCount = 0;
		
		PreparedStatement pstmt = null;
		
		try {
			// 글번호에 해당하는 레코드 삭제
			String sql = "DELETE FROM qna "
								+ "WHERE qna_idx=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, qna_idx);
			deleteCount = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("QnaDAO - deleteQna()");
			e.printStackTrace();
		} finally {
			// DB 자원 반환
			JdbcUtil.close(pstmt);
		}
		
		return deleteCount;
	}
	
	
		// 답글 쓰기
		public int insertReplyQna(QnaBean qna) {
			// INSERT 작업 결과를 리턴받아 저장할 변수 선언
			int insertCount = 0;
			
			// 데이터베이스 작업에 필요한 변수 선언
			PreparedStatement pstmt = null, pstmt2 = null;
			ResultSet rs = null;
		
			try {
				// 새 글 번호 계산을 위해 기존 board 테이블의 모든 번호(qna_idx) 중 가장 큰 번호 조회
				// => 조회 결과 + 1 값을 새 글 번호로 지정하고, 조회 결과가 없으면 기본값 1 로 설정
				// => MySQL 구문의 MAX() 함수 사용(SELECT MAX(컬럼명) FROM 테이블명)
				int qna_idx = 1; // 새 글 번호
				
				String sql = "SELECT MAX(qna_idx) FROM qna";
				pstmt = con.prepareStatement(sql);
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					qna_idx = rs.getInt(1) + 1;
				}
				
		//			System.out.println("새 글 번호 : " + qna_idx);
				// ----------------------------------------------------------------------------
				int ref = qna.getQna_re_ref(); // 원본글의 참조글번호
				int lev = qna.getQna_re_lev(); // 원본글의 들여쓰기레벨
				int seq = qna.getQna_re_seq(); // 원본글의 순서번호
				
				// 기존 답글들에 대한 순서번호 증가 = UPDATE 구문
				// => 원본글의 참조글번호(qna_re_ref)와 같고
				//    원본글의 순서번호(qna_re_seq) 보다 큰 레코드들의
				//    순서번호를 + 1 씩 증가시키기
				sql = "UPDATE qna"
						+ "		SET"
						+ "			qna_re_seq = qna_re_seq + 1"
						+ "		WHERE"
						+ "			qna_re_ref = ?"
						+ "			AND qna_re_seq > ?";
				pstmt2 = con.prepareStatement(sql);
				pstmt2.setInt(1, ref);
				pstmt2.setInt(2, seq);
				pstmt2.executeUpdate();
				
				JdbcUtil.close(pstmt2);
				
				// 새 답글에 사용될 원본글의 lev, seq 값 + 1 처리
				lev++;
				seq++;
				
				// ------------------------------------------------------------
				// 답글 INSERT
				// => 글쓰기와 달리 ref, lev, seq 값은 설정된 값으로 변경
				sql = "INSERT INTO qna VALUES (?,?,?,now(),?,?,?,?)";
				pstmt2 = con.prepareStatement(sql);
				pstmt2.setInt(1, qna_idx);
				pstmt2.setString(2, qna.getQna_subject());
				pstmt2.setString(3, qna.getQna_content());
				pstmt2.setInt(4, ref);
				pstmt2.setInt(5, lev);
				pstmt2.setInt(6, seq);
				pstmt2.setString(7,  qna.getMember_id());
				insertCount = pstmt2.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("SQL 구문 오류! - insertReplyQna()");
			e.printStackTrace();
		} finally {
			// DB 자원 반환
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			JdbcUtil.close(pstmt2);
		}
		
		return insertCount;
	}
	
}












