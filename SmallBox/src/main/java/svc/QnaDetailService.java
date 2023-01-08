package svc;

import java.sql.Connection;

import dao.QnaDAO;
import db.JdbcUtil;
import vo.QnaBean;

public class QnaDetailService {
	// 글 상세정보 조회(조회수작업 삭제!)
	public QnaBean getQna(int qna_idx) {
		QnaBean qna = null;
		
		// 공통작업-1. Connection 객체 가져오기
		Connection con = JdbcUtil.getConnection();
		
		// 공통작업-2. QnaDAO 객체 가져오기
		QnaDAO dao = QnaDAO.getInstance();
		
		// 공통작업-3. QnaDAO 객체에 Connection 객체 전달하기
		dao.setConnection(con);
		
		// QnaDAO 의 selectQna() 메서드 호출하여 게시물 상세 정보 조회 작업 수행
		// => 파라미터 : 글번호    리턴타입 : QnaBean(qna)
		qna = dao.selectQna(qna_idx);
		
		// 공통작업-4. Connection 객체 반환하기
		JdbcUtil.close(con);
		
		return qna;
	}

}











