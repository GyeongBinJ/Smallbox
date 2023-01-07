package svc;

import java.sql.Connection;

import dao.NoticeDAO;
import db.JdbcUtil;
import vo.NoticeBean;

public class NoticeDetailService {
	public NoticeBean getNotice(int notice_idx, boolean isUpdateReadcount) {
		NoticeBean notice = null;
		
		// 공통작업-1. Connection 객체 가져오기
		Connection con = JdbcUtil.getConnection();
		
		// 공통작업-2. BoardDAO 객체 가져오기
		NoticeDAO dao = NoticeDAO.getInstance();
		
		// 공통작업-3. BoardDAO 객체에 Connection 객체 전달하기
		dao.setConnection(con);
		
		// BoardDAO 의 selectBoard() 메서드 호출하여 게시물 상세 정보 조회 작업 수행
		// => 파라미터 : 글번호    리턴타입 : BoardBean(board)
		notice = dao.selectNotice(notice_idx);
		
		// 리턴받은 BoardBean 객체가 null 이 아니고, isUpdateReadcount 가 true 경우 
		// updateReadcount() 메서드를 호출하여 조회수 증가 작업 수행하고 
		// 작업이 성공했을 경우 commit 작업 수행 및 BoardBean 객체의 조회수 값 1 증가시키기
		// => 파라미터 : 글번호, isUpdateReadcount   리턴타입 : int(updateCount)
		if(notice != null && isUpdateReadcount) {
			int updateCount = dao.updateReadcount(notice_idx);
			
			if(updateCount > 0) {
				JdbcUtil.commit(con);
				
				// 만약, 조회수 증가 전 조회 작업을 먼저 수행했을 경우
				// 수동으로 BoardBean 객체의 조회수를 1만큼 증가시켜야함
				notice.setNotice_readCount((notice.getNotice_readCount() + 1));
				
			}
		}
		
		// 공통작업-4. Connection 객체 반환하기
		JdbcUtil.close(con);
		
		return notice;
	}

	
}
