package svc;

import java.sql.Connection;

import dao.CommentDAO;
import db.JdbcUtil;

public class ReviewDeleteService {

	// 리뷰 삭제 - 파라미터 : 리뷰 번호
	public boolean deleteReview(int comment_idx) {
		boolean isDeleteSuccess = false;
		
		Connection con = JdbcUtil.getConnection();
		CommentDAO dao = CommentDAO.getInstance();
		dao.setConnection(con);
		
		int deleteCount = dao.deleteComment(comment_idx);
		
		if(deleteCount > 0) {
			JdbcUtil.commit(con);
			isDeleteSuccess = true;
		} else {
			JdbcUtil.rollback(con);
		}
		
		JdbcUtil.close(con);
		
		return isDeleteSuccess;
	}
	

}
