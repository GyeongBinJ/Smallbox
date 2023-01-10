package svc;

import java.sql.Connection;

import dao.AuthDAO;
import db.JdbcUtil;
import vo.AuthBean;

public class AuthcodeService {

	public String authcodeConfirm(String authCode, String id) {
		String authcodeConfirm = "";
		
		Connection con = JdbcUtil.getConnection();
		
		AuthDAO dao = AuthDAO.getInstance();
		dao.setConnection(con);
		
		authcodeConfirm = dao.authcodeConfirm(id, authCode);

		JdbcUtil.commit(con);
		JdbcUtil.close(con);
		
		return authcodeConfirm;
	}
	
	// 인증코드 중복 확인시 인증코드 삭제!
	public void authCodeDelete(String authCode) {
		Connection con = JdbcUtil.getConnection();
		
		AuthDAO dao = AuthDAO.getInstance();
		dao.setConnection(con);
		
		dao.authcodeDelete(authCode);

		JdbcUtil.commit(con);
		JdbcUtil.close(con);
		
	}

}
