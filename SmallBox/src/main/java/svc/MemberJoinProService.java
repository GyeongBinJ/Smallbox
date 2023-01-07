package svc;

import java.sql.Connection;


import dao.MemberDAO;
import db.JdbcUtil;
import vo.MemberBean;

public class MemberJoinProService {

	public boolean joinMember(MemberBean member) {
		System.out.println("MemberJoinProService - joinMember()");
				
			boolean isJoinSeuccess = false;
			
			Connection con = JdbcUtil.getConnection();
			
			MemberDAO dao = MemberDAO.getInstance();
			
			dao.setConnection(con);
			
			
			int insertCount = dao.insertMember(member);
			System.out.println(insertCount);
			if(insertCount > 0) {
				JdbcUtil.commit(con);
				
				isJoinSeuccess = true;
			} else {
				JdbcUtil.rollback(con);
			}
			
			JdbcUtil.close(con);
			
			// 작업결과 리턴
			return isJoinSeuccess; 
	}
}

