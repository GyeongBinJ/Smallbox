package svc;

import java.sql.Connection;

import dao.NoticeDAO;
import db.JdbcUtil;
import vo.NoticeBean;

public class NoticeModifyProService {

	public boolean modifyNotice(NoticeBean notice) {
		boolean isModifySuccess = false;
		
		Connection con = JdbcUtil.getConnection();
		NoticeDAO dao = NoticeDAO.getInstance();
		dao.setConnection(con);
		
		int updateCount = dao.updateNotice(notice);
		
		if(updateCount > 0) {
			JdbcUtil.commit(con);
			isModifySuccess = true;
		} else {
			JdbcUtil.rollback(con);
		}
		
		// 공통작업-4. Connection 객체 반환하기
		JdbcUtil.close(con);
		
		return isModifySuccess;
	}

}
