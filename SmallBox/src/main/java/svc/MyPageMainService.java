package svc;

import java.sql.Connection;

import dao.CommentDAO;
import dao.CouponDAO;
import dao.MemberDAO;
import dao.MovieDAO;
import db.JdbcUtil;
import vo.MemberBean;

public class MyPageMainService {
	
	// 1. 회원 정보 조회 -> 회원 이름 / 이메일 가져오기
	public MemberBean getMemberInfo(String sId) {
		MemberBean member2 = null;
		
		Connection con = JdbcUtil.getConnection();
		MemberDAO dao = MemberDAO.getInstance();
		dao.setConnection(con);
		
		member2 = dao.getMemberInfo(sId);
		JdbcUtil.commit(con);
		JdbcUtil.close(con);
		return member2;
	}
	
	// 2. 회원별 찜 개수 조회 
	public int getMovieListCount(String member_id) {
		int movieCount = 0;
		
		Connection con = JdbcUtil.getConnection();
		MovieDAO dao = MovieDAO.getInstance(); 
		dao.setConnection(con);
		
		// DAO에서 DB작업의 결과로 리턴받은 movieCount를 MovieLikeListProAction 으로 리턴
		movieCount = dao.selectLikeMovieCount(member_id);
		
		JdbcUtil.commit(con);
		
		JdbcUtil.close(con);
		
		return movieCount;
	}
	
	// 3. 회원별 쿠폰 개수 조회
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
	
	// 4. 회원별 리뷰 개수 조회
	public int getCommentListCount(String member_id) {
		int commentCount = 0;
		
		Connection con = JdbcUtil.getConnection();
		CommentDAO dao = CommentDAO.getInstance();
		dao.setConnection(con);
		
		commentCount = dao.selectCommentListCount(member_id);
		
		JdbcUtil.commit(con);
		JdbcUtil.close(con);
		
		return commentCount;
	}

}
