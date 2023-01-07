package svc;

import java.sql.Connection;

import dao.MemberDAO;
import db.JdbcUtil;
import vo.MemberBean;

public class MemberDeleteProService {
	public boolean deleteMember(MemberBean member) {
		System.out.println("BoardDeleteProService - deleteMember()");
		System.out.println("서비스비밀번호 : " + member.getMember_passwd());
				
			boolean isDeleteSuccess = false;
			
			Connection con = JdbcUtil.getConnection();
			
			MemberDAO dao = MemberDAO.getInstance();
			
			dao.setConnection(con);
			
			int insertCount = dao.deleteMember(member);
			System.out.println(insertCount);
			if(insertCount > 0) {
				isDeleteSuccess = true;
				JdbcUtil.commit(con);
			} else {
				JdbcUtil.rollback(con);
			}
			System.out.println("서비스 : " + isDeleteSuccess);
			JdbcUtil.close(con);
			
			// 작업결과 리턴
			return isDeleteSuccess; 
	}
}
