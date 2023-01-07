package svc;

import java.sql.Connection;

import dao.CommentDAO;
import db.JdbcUtil;
import vo.CommentBean;

public class CommentGetmovieIdxService {

	public CommentBean getMovieIdx(int comment_idx) {
		CommentBean comment = null;
		//-------------------공통------------------------------
		Connection con = JdbcUtil.getConnection();
		CommentDAO dao = CommentDAO.getInstance();
		dao.setConnection(con);
		//-----------------------------------------------------
		comment = dao.getMovieIdx(comment_idx);
		//-------------------공통------------------------------
		JdbcUtil.commit(con);
		JdbcUtil.close(con);
		//-----------------------------------------------------	
		
		return comment;		
	}


}
