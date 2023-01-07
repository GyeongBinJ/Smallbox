package svc;

import java.sql.Connection;

import dao.NoticeDAO;
import db.JdbcUtil;

public class NoticeDeleteProService {
	public boolean removeNotice(int notice_idx) {
		boolean isDeleteSuccess = false;
		
		// 공통작업-1. Connection 객체 가져오기
		Connection con = JdbcUtil.getConnection();
		
		// 공통작업-2. BoardDAO 객체 가져오기
		NoticeDAO dao = NoticeDAO.getInstance();
		
		// 공통작업-3. BoardDAO 객체에 Connection 객체 전달하기
		dao.setConnection(con);
		
		// BoardDAO 의 deleteBoard() 메서드를 호출하여 글 삭제 작업 수행
		// => 파라미터 : 글번호    리턴타입 : int(deleteCount)
		int deleteCount = dao.deleteNotice(notice_idx);
		
		// 리턴받은 결과를 판별하여 commit, rollback
		if(deleteCount > 0) {
			JdbcUtil.commit(con);
			isDeleteSuccess = true;
		} else {
			JdbcUtil.rollback(con);
		}
		
		// 공통작업-4. Connection 객체 반환하기
		JdbcUtil.close(con);
		
		return isDeleteSuccess;
	}
}
