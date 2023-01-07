package svc;

import java.sql.Connection;

import dao.MemberDAO;
import db.JdbcUtil;
import vo.MemberBean;

public class MemberModifyService {

	public boolean updateMember(MemberBean member) {
		boolean updateMember = false;
		
		Connection con = JdbcUtil.getConnection();
		MemberDAO dao = MemberDAO.getInstance();
		dao.setConnection(con);
		
		updateMember = dao.updateMember(member, true);
		
		if(updateMember) {
			JdbcUtil.commit(con);
			
		} else {
			JdbcUtil.rollback(con);
		}
		JdbcUtil.close(con);
		return updateMember;
	}

	public MemberBean getMemberInfo(String sId) {
		MemberBean member2 = null;
		
		Connection con = JdbcUtil.getConnection();
		MemberDAO dao = MemberDAO.getInstance();
		dao.setConnection(con);
		
		member2 = dao.getMemberInfo(sId);
		JdbcUtil.commit(con);
		JdbcUtil.close(con);
		return member2;
	}
	
  public boolean isRightUser(MemberBean member) {
		boolean isRightUser = false;
		
		Connection con = JdbcUtil.getConnection();
		MemberDAO dao = MemberDAO.getInstance();
		dao.setConnection(con);
		
		isRightUser = dao.isRightUser(member);
		
		if(isRightUser == true) {
			JdbcUtil.commit(con);
			
		} else {
			JdbcUtil.rollback(con);
		}
		JdbcUtil.close(con);
		return isRightUser;
	}
}
 