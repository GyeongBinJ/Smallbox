package svc;

import java.sql.Connection;

import dao.CommentDAO;
import db.JdbcUtil;

public class CommentDeleteService {

	public boolean deleteComment(int comment_idx) {
		boolean isDeleteComment = false;
		
		//-------------------공통------------------------------
		Connection con = JdbcUtil.getConnection();
		CommentDAO dao = CommentDAO.getInstance();
		dao.setConnection(con);
		//-----------------------------------------------------
				
		int deleteCount = dao.deleteComment(comment_idx);	
		
		if(deleteCount > 0) { // 성공 시
			JdbcUtil.commit(con);
			isDeleteComment = true;
		} else { // 실패 시
			JdbcUtil.rollback(con);
		}
				
		//-------------------공통------------------------------
		JdbcUtil.close(con);
		//-----------------------------------------------------
		
		return isDeleteComment;
	}
	
	
}
