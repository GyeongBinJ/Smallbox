package svc;

import java.sql.Connection;
import java.util.List;

import dao.MovieDAO;
import db.JdbcUtil;
import vo.MovieBean;

public class ComingMovieListService {

	// 개봉 예정작 리스트 조회
	public List<MovieBean> getCommingMovieList(String keyword, int startRow, int listLimit) {
		List<MovieBean> commingMovieList = null;
		
		Connection con = JdbcUtil.getConnection();
		MovieDAO dao = MovieDAO.getInstance();
		dao.setConnection(con);
		
		commingMovieList = dao.selectCommingMovieList(keyword, startRow, listLimit);
		
		JdbcUtil.commit(con);
		
		JdbcUtil.close(con);
		
		return commingMovieList;
	}

	// 개봉 예정작 총 개수 (페이징 처리를 위함)
	public int getCommingMovieListCount(String keyword) {
		int listCount = 0;
		
		Connection con = JdbcUtil.getConnection();
		MovieDAO dao = MovieDAO.getInstance();
		dao.setConnection(con);
		
		listCount = dao.selectCommingListCount(keyword);
		
		JdbcUtil.commit(con);
		
		JdbcUtil.close(con);
		
		return listCount;
	}

}
