package svc;

import java.sql.Connection;

import dao.ReserveDAO;
import db.JdbcUtil;

public class ReserveToReviewProService {
	public int getMovie_idx(int res_idx) {
		int movie_idx = 0;
		
		//-------------------공통------------------------------
		Connection con = JdbcUtil.getConnection();
		ReserveDAO dao = ReserveDAO.getInstance();
		dao.setConnection(con);
		//-----------------------------------------------------
		
		movie_idx = dao.setMovie_idx(res_idx);
		
		//-------------------공통------------------------------
		JdbcUtil.commit(con);
		JdbcUtil.close(con);
		//-----------------------------------------------------
		System.out.println("service : dao 에서 받은 movie_idx : " + movie_idx);
		return movie_idx;
	}
	
		
}
