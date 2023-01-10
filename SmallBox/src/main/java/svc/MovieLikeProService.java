package svc;

import java.sql.Connection;

import dao.MovieDAO;
import db.JdbcUtil;
import vo.MovieBean;

public class MovieLikeProService {

	// 찜 작업 수행
	public boolean MovieLike(int movie_idx, String member_id) {
		boolean isLikeSuccess = false;
		
		Connection con = JdbcUtil.getConnection();
		MovieDAO dao = MovieDAO.getInstance();
		dao.setConnection(con);
		
		// DAO에서 수행한 DB 작업 결과를 insertlikeCount 변수에 저장
		int insertlikeCount = dao.insertMovieLike(movie_idx, member_id);
		
		if(insertlikeCount > 0) { // DB 작업 성공시 (= 찜 성공시)
			JdbcUtil.commit(con);
			isLikeSuccess = true; // isLikeSuccess 변수를 true로 변경
		} else { // DB 작업 실패시 (= 찜 실패시)
			JdbcUtil.rollback(con); // DB 작업 수행 X
		}
		
		JdbcUtil.close(con);
		
		return isLikeSuccess; // isLikeSuccess를 MovieLikeProAction으로 리턴
	}	

	// 찜이 되어있는지 판별 (이미 찜이면 true / 찜 아니면 false)
	public boolean selectLike(int movie_idx, String member_id) {
		boolean isLike = false;
		
		Connection con = JdbcUtil.getConnection();
		MovieDAO dao = MovieDAO.getInstance();
		dao.setConnection(con);
		
		// DAO에서 수행한 찜 여부 조회 결과를 insertlikeCount 변수에 저장
		int likeCount = dao.selectLike(movie_idx, member_id);
		
		if(likeCount > 0) { // 찜이 이미 되어있으면
			JdbcUtil.commit(con);
			isLike = true; // isLikeSuccess 변수를 true로 변경
		} 
		
		JdbcUtil.close(con);
		
		return isLike;
	}

	// 찜 해제 작업
	public boolean CancelMovieLike(int movie_idx, String member_id) {
		boolean isCancelLikeSuccess = false;
		
		Connection con = JdbcUtil.getConnection();
		MovieDAO dao = MovieDAO.getInstance();
		dao.setConnection(con);
		
		// DAO에서 수행한 DB 작업 결과를 deletelikeCount 변수에 저장
		int deletelikeCount = dao.deleteMovieLike(movie_idx, member_id);
//		System.out.print(deletelikeCount);
		
		if(deletelikeCount > 0) { // // DB 작업 성공시 (= 찜 해제(삭제) 성공시)
			JdbcUtil.commit(con); 
			isCancelLikeSuccess = true; // isCancelLikeSuccess 변수를 true로 변경
		} else { // DB 작업 실패시 (= 찜 해제(삭제) 실패시)
			JdbcUtil.rollback(con); // DB 작업 수행 X
		}
		
		JdbcUtil.close(con);
		
		return isCancelLikeSuccess;
	}
	
}
