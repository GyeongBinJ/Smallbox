package svc;

import java.sql.Connection;
import java.util.List;

import dao.MovieDAO;
import db.JdbcUtil;
import vo.MovieBean;

public class AdminMovieDetailService {

	// 관리자 페이지 - 영화 상세정보 조회
	public MovieBean getAdminMovieDetail(int movie_idx) {
		MovieBean movie = null;
		
		Connection con = JdbcUtil.getConnection();
		MovieDAO dao = MovieDAO.getInstance();
		dao.setConnection(con);
		
		// DAO에서 조회한 영화 상세정보를 저장한 MovieBean 객체를 액션으로 리턴
		movie = dao.selectMovie(movie_idx);
		
		JdbcUtil.commit(con);
		
		JdbcUtil.close(con);
		
		return movie;
	}
	
}
