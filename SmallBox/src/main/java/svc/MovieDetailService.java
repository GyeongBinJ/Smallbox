package svc;

import java.sql.Connection;

import dao.MovieDAO;
import db.JdbcUtil;
import vo.MovieBean;

public class MovieDetailService {

	public MovieBean getMovieDetail(int movie_idx) {
		System.out.println("service : MovieDetailService");
	
		MovieBean movie = null;

		//-------------------공통------------------------------
		Connection con = JdbcUtil.getConnection();
		MovieDAO dao = MovieDAO.getInstance();
		dao.setConnection(con);
		//-----------------------------------------------------
		
		movie = dao.selectMovie(movie_idx);
				
		//-------------------공통------------------------------
		JdbcUtil.close(con);
		//-----------------------------------------------------
				
		return movie;
	}

}
