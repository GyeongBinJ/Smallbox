package svc;

import java.sql.Connection;

import dao.MovieDAO;
import db.JdbcUtil;

public class MovieDeleteProService {

	// 영화 삭제
	public boolean deleteMovie(int movie_idx) {
		boolean isDeleteSuccess = false;
		
		Connection con = JdbcUtil.getConnection();
		MovieDAO dao = MovieDAO.getInstance();
		dao.setConnection(con);
		
		int deleteCount = dao.removeMovie(movie_idx);
		
		if(deleteCount > 0) { 
			JdbcUtil.commit(con);
			isDeleteSuccess = true;
		} else {
			JdbcUtil.rollback(con);
		}
		
		JdbcUtil.close(con);
		
		return isDeleteSuccess;
	}

}
