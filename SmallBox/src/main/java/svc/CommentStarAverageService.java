package svc;

import java.sql.Connection;

import dao.CommentDAO;
import db.JdbcUtil;

public class CommentStarAverageService {

	public double getStarAverage(int movie_idx) {
		double starAvg = 0;
		
		//-------------------공통------------------------------
		Connection con = JdbcUtil.getConnection();
		CommentDAO dao = CommentDAO.getInstance();
		dao.setConnection(con);
		//-----------------------------------------------------
				
		starAvg = dao.selectAvg(movie_idx);
		
		//-------------------공통------------------------------
		JdbcUtil.commit(con);
		JdbcUtil.close(con);
		//-----------------------------------------------------
		
		return starAvg;
	}

}
