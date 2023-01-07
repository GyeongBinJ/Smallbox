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

}
