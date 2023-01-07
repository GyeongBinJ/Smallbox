package svc;

import java.sql.Connection;

import dao.MemberDAO;
import db.JdbcUtil;

public class MemberCheckIdService {

	public boolean memberCheckId(String id) {
		boolean isId = false;

		Connection con = JdbcUtil.getConnection();
		
		MemberDAO dao = MemberDAO.getInstance();
		dao.setConnection(con);
		
		 if(dao.memberCheckId(id)) {
			 JdbcUtil.commit(con);
			 isId = true;
		 }
		
		JdbcUtil.close(con);
		
		
		return isId;
	}

}
