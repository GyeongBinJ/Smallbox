package svc;

import java.sql.Connection;

import dao.MovieDAO;
import db.JdbcUtil;

public class CheckMovieLikeService {

	// 찜이 되어있는지 판별 (이미 찜이면 true / 찜 아니면 false)
	public boolean selectLike(int movie_idx, String member_id) {
		boolean isLike = false;
		
		Connection con = JdbcUtil.getConnection();
		MovieDAO dao = MovieDAO.getInstance();
		dao.setConnection(con);
		
		// DAO에서 수행한 찜 여부 조회 결과를 insertlikeCount 변수에 저장
		int likeCount = dao.selectLike(movie_idx, member_id);
		System.out.print(member_id);
		
		if(likeCount > 0) { // 찜이 이미 되어있으면
			JdbcUtil.commit(con);
			isLike = true; // isLikeSuccess 변수를 true로 변경
		} 
		System.out.print(isLike);
		
		JdbcUtil.close(con);
		
		return isLike;
	}

}
