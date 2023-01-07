package svc;

import java.sql.Connection;

import dao.MovieDAO;
import db.JdbcUtil;

public class CancelMovieLikeProService {

	// 찜 해제 작업
	public boolean CancelMovieLike(int movie_idx, String member_id) {
		boolean isCancelLikeSuccess = false;
		
		Connection con = JdbcUtil.getConnection();
		MovieDAO dao = MovieDAO.getInstance();
		dao.setConnection(con);
		
		// DAO에서 수행한 DB 작업 결과를 deletelikeCount 변수에 저장
		int deletelikeCount = dao.deleteMovieLike(movie_idx, member_id);
		System.out.print(deletelikeCount);
		
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
