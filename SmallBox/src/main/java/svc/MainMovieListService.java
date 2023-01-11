package svc;

import java.sql.Connection;
import java.util.List;

import dao.MovieDAO;
import db.JdbcUtil;
import vo.MovieBean;

public class MainMovieListService {

	    // 메인페이지 - 개봉작순위에 들어갈 영화정보 
		public List<MovieBean> getAdminMovieList(String keyword, int startRow, int listLimit) {
			List<MovieBean> movieList = null;
			
			Connection con = JdbcUtil.getConnection();
			MovieDAO dao = MovieDAO.getInstance();
			dao.setConnection(con);
			
			// DAO에서 조회한 목록 정보가 담긴 List 객체를 Action 클래스로 리턴
			movieList = dao.selectMovieList(keyword, startRow, listLimit);
			
			JdbcUtil.commit(con);

			JdbcUtil.close(con);
			
			return movieList;
		}
		
		// 메인페이지 - 개봉작 순위 표출 갯수
		public int getMovieListCount(String keyword) {
			int listCount = 4;
			
			Connection con = JdbcUtil.getConnection();
			MovieDAO dao = MovieDAO.getInstance();
			dao.setConnection(con);
			
			// DAO에서 조회한 게시글 갯수가 담긴 listCount 변수를 Action 클래스로 리턴
			listCount = dao.selectBoardListCount(keyword);
			
			JdbcUtil.commit(con);
			
			JdbcUtil.close(con);
			
			return listCount;
		}
	
}
