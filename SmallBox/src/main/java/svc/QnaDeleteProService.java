package svc;

import java.sql.Connection;

import dao.QnaDAO;
import db.JdbcUtil;

public class QnaDeleteProService {

// 글번호(qna_idx)에 해당하는 게시물 삭제 작업 수행하는 removeQna() 메서드
	//  => 파라미터 : 글번호(qna_idx)    리턴타입 : int(deleteCount)
	public boolean removeQna(int qna_idx) {
		boolean isDeleteSuccess = false;
		
		// 공통작업-1. Connection 객체 가져오기
		Connection con = JdbcUtil.getConnection();
		
		// 공통작업-2. QnaDAO 객체 가져오기
		QnaDAO dao = QnaDAO.getInstance();
		
		// 공통작업-3. BoardDAO 객체에 Connection 객체 전달하기
		dao.setConnection(con);
		
		// QnaDAO 의 deleteQna() 메서드를 호출하여 글 삭제 작업 수행
		// => 파라미터 : 글번호    리턴타입 : int(deleteCount)
		int deleteCount = dao.deleteQna(qna_idx);
		
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











