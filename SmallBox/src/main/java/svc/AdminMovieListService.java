package svc;

import java.sql.Connection;
import java.util.List;

import dao.MovieDAO;
import db.JdbcUtil;
import vo.MovieBean;
import vo.StarMovieBean;

public class AdminMovieListService {
	
	// 관리자 페이지 - 상영일정 등록을 위한 영화 목록 조회(파라미터X)
	public List<MovieBean> getAdminMovieList() {
		System.out.println("AdminMovieListService - getAdminMovieList()");
		List<MovieBean> movieList = null;
		
		// 공통작업
		Connection con = JdbcUtil.getConnection();
		MovieDAO dao = MovieDAO.getInstance();
		dao.setConnection(con);
		
		// DAO에서 조회한 목록 정보가 담긴 List 객체를 Action 클래스로 리턴
		movieList = dao.selectMovieList();
//			System.out.println(movieList);
	
		JdbcUtil.commit(con);
		JdbcUtil.close(con);
		
		return movieList; // AdminTheaterInsertFormAction
	}

	// 관리자 페이지 - 영화 목록 조회 + 검색기능
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
	
	// 관리자 페이지 - 총 게시글 갯수 조회
	public int getMovieListCount(String keyword) {
		int listCount = 0;
		
		Connection con = JdbcUtil.getConnection();
		MovieDAO dao = MovieDAO.getInstance();
		dao.setConnection(con);
		
		// DAO에서 조회한 게시글 갯수가 담긴 listCount 변수를 Action 클래스로 리턴
		listCount = dao.selectBoardListCount(keyword);
		
		JdbcUtil.commit(con);
		
		JdbcUtil.close(con);
		
		return listCount;
	}

	// 각 회원별 찜 목록 가져오기 (찜여부 확인을 위함)
	public List<Integer> getLikeList(String member_id) {
		List<Integer> likeList = null;
		
		Connection con = JdbcUtil.getConnection();
		MovieDAO dao = MovieDAO.getInstance();
		dao.setConnection(con);
		
		likeList = dao.selectLikeList2(member_id);
		
		JdbcUtil.commit(con);
		
		JdbcUtil.close(con);
		
		return likeList;
	}

	public List<StarMovieBean> getStarMovieList(String keyword, int startRow, int listLimit) {
		List<StarMovieBean> starmovieList = null;
		
		Connection con = JdbcUtil.getConnection();
		MovieDAO dao = MovieDAO.getInstance();
		dao.setConnection(con);
		
		// DAO에서 조회한 목록 정보가 담긴 List 객체를 Action 클래스로 리턴
		starmovieList = dao.selectStarMovieList(keyword, startRow, listLimit);
		
		JdbcUtil.commit(con);

		JdbcUtil.close(con);
		
		return starmovieList;
	}

}
