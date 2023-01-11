package svc;

import java.sql.Connection;
import java.util.List;

import dao.CommentDAO;
import db.JdbcUtil;
import vo.CommentBean;

public class CommentContainsUserService {

	public int containsUser(int movie_idx, String member_id) {
		int containsUser = 0;
		
		
		//-------------------공통------------------------------
		Connection con = JdbcUtil.getConnection();
		CommentDAO dao = CommentDAO.getInstance();
		dao.setConnection(con);
		//-----------------------------------------------------
		
		containsUser = dao.isContainsUser(movie_idx, member_id);
		
		if(containsUser > 0) {
			JdbcUtil.commit(con);
			containsUser = 1;
			
		} else {
			JdbcUtil.rollback(con);
		}
		
		
		//-------------------공통------------------------------
		JdbcUtil.close(con);
		//-----------------------------------------------------
		
		
		return containsUser;
	}

}
