package svc;

import java.sql.Connection;
import java.util.List;

import dao.TheaterDAO;
import db.JdbcUtil;
import vo.TheaterBean;

public class ReserveSelectProService {

	public List<TheaterBean> getTheaterList(String movie_title, String reserve_date) {
		System.out.println("ReserveSelectProService - getTheaterList");
		List<TheaterBean> theaterList = null;
		
		Connection con = JdbcUtil.getConnection();
		TheaterDAO dao = TheaterDAO.getInstance();
		dao.setConnection(con);
		
		theaterList = dao.selectTheaterList(movie_title, reserve_date);
		
		JdbcUtil.commit(con);
		JdbcUtil.close(con);
		
		return theaterList;
	}

	

}
