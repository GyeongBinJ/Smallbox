package svc;

import java.sql.Connection;

import dao.CommentDAO;
import db.JdbcUtil;
import vo.CommentBean;

public class CommentWriteProServive {

	public boolean insertComment(CommentBean cmt) {
		System.out.println("service : CommentWriteProServive");
		
		boolean isInsertComment = false;
		//-------------------공통------------------------------
		Connection con = JdbcUtil.getConnection();
		CommentDAO dao = CommentDAO.getInstance();
		dao.setConnection(con);
		//-----------------------------------------------------
				
		int insertCount = dao.insertComment(cmt);	
		
		if(insertCount > 0) { // 성공 시
			JdbcUtil.commit(con);
			isInsertComment = true;
		} else { // 실패 시
			JdbcUtil.rollback(con);
		}
				
		//-------------------공통------------------------------
		JdbcUtil.close(con);
		//-----------------------------------------------------
		
		return isInsertComment;
	}

}
