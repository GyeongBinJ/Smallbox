package svc;

import java.sql.Connection;

import dao.QnaDAO;
import db.JdbcUtil;
import vo.QnaBean;

public class QnaWriteProService {
	// 글쓰기 작업 요청을 위한 registQna() 메서드 정의
	// => 파라미터 : QnaBean 객체   리턴타입 : boolean(isWriteSuccess)
	public boolean registQna(QnaBean qna) {
		System.out.println("QnaWriteProService - registQna()");

		boolean isWriteSuccess = false;
		
		Connection con = JdbcUtil.getConnection();
		
		QnaDAO dao = QnaDAO.getInstance();
		
		dao.setConnection(con);
		
		int insertCount = dao.insertQna(qna);
		
		if(insertCount > 0) { // 성공 시
			JdbcUtil.commit(con);
			isWriteSuccess = true;
			
		} else { // 실패 시
			JdbcUtil.rollback(con);
		}
		
		JdbcUtil.close(con);
		
		// 8. 작업 요청 처리 결과 리턴
		return isWriteSuccess; // QnaWriteProAction 으로 리턴
	}
	
}














