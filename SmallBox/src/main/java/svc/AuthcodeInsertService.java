package svc;

import java.sql.Connection;

import dao.AuthDAO;
import db.JdbcUtil;
import vo.AuthBean;

public class AuthcodeInsertService {


	public boolean authcodeInsert(AuthBean auth) {
		boolean insertAuthcode = false;
		
		Connection con = JdbcUtil.getConnection();
		
		AuthDAO dao = AuthDAO.getInstance();
		dao.setConnection(con);
		
		boolean insertAuthSuccess = dao.insertAuthcode(auth);
		
		if(insertAuthSuccess) {// 성공 시
			JdbcUtil.commit(con);
			
			insertAuthSuccess = true;
		} else {// 실패 시
			JdbcUtil.rollback(con);
		}
		
		JdbcUtil.close(con);
		
		
		return insertAuthcode;
	}

}
