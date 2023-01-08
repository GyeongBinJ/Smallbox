package svc;

import java.sql.Connection;

import dao.QnaDAO;
import db.JdbcUtil;
import vo.QnaBean;

public class QnaReplyProService {

	// 답글 작업 요청
	// => 파라미터 : QnaBean 객체   리턴타입 : boolean(isWriteSuccess)
	public boolean registReplyQna(QnaBean qna) {
		System.out.println("registReplyQna");
		
		boolean isWriteSuccess = false;
		
		// 공통작업-1. Connection 객체 가져오기
		Connection con = JdbcUtil.getConnection();
		
		// 공통작업-2. QnaDAO 객체 가져오기
		QnaDAO dao = QnaDAO.getInstance();
		
		// 공통작업-3. QnaDAO 객체에 Connection 객체 전달하기
		dao.setConnection(con);
		
		// QnaDAO 객체의 insertReplyQna() 메서드를 호출하여 답글 쓰기 작업 요청
		// => 파라미터 : QnaBean 객체   리턴타입 : int(insertCount)
		int insertCount = dao.insertReplyQna(qna);
		
		// 작업 처리 결과에 따른 트랜잭션 처리
		if(insertCount > 0) { // 성공 시
			JdbcUtil.commit(con);
			isWriteSuccess = true;
		} else { // 실패 시
			JdbcUtil.rollback(con);
		}
		
		// 공통작업-4. Connection 객체 반환하기
		JdbcUtil.close(con);
		
		return isWriteSuccess; // QnaReplyProAction 으로 리턴
	}

}
