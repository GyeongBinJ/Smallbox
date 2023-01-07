package svc;

import java.sql.Connection;
import java.util.List;

import dao.CommentDAO;
import db.JdbcUtil;
import vo.CommentBean;

public class CommentListService {

	public List<CommentBean> getCommentList(int movie_idx) {
		List<CommentBean> commentList = null;
		//-------------------공통------------------------------
		Connection con = JdbcUtil.getConnection();
		CommentDAO dao = CommentDAO.getInstance();
		dao.setConnection(con);
		//-----------------------------------------------------
		
		commentList = dao.selectCommentList(movie_idx);
		
		//-------------------공통------------------------------
		JdbcUtil.commit(con);
		JdbcUtil.close(con);
		//-----------------------------------------------------
		
		return commentList;
	}

	
}
