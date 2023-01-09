package svc;

import java.sql.Connection;
import java.util.List;

import dao.CommentDAO;
import db.JdbcUtil;
import vo.CommentBean;
import vo.PosterBean;

public class ReviewListService {

	// 마이페이지 - 각 회원의 리뷰 내역 조회
	public List<PosterBean> getReviewList(String member_id, int startRow, int commentLimit) {
		List<PosterBean> reviewList = null;
		
		Connection con = JdbcUtil.getConnection();
		CommentDAO dao = CommentDAO.getInstance();
		dao.setConnection(con);
		
		reviewList = dao.selectReviewList(member_id, startRow, commentLimit);
		
		JdbcUtil.commit(con);
		JdbcUtil.close(con);
		
		return reviewList;
	}

	// 마이페이지 - 각 회원의 총 리뷰 갯수 조회
	public int getCommentListCount(String member_id) {
		int commentCount = 0;
		
		Connection con = JdbcUtil.getConnection();
		CommentDAO dao = CommentDAO.getInstance();
		dao.setConnection(con);
		
		commentCount = dao.selectCommentListCount(member_id);
		
		JdbcUtil.commit(con);
		JdbcUtil.close(con);
		
		return commentCount;
	}
	
}
