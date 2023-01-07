package svc;

import java.sql.Connection;

import dao.MemberDAO;
import db.JdbcUtil;
import vo.MemberBean;

public class MemberFindIdService {

	public String FindIdMember(MemberBean member) {
		String id = "";
		System.out.println("MemberFindIdService");
		Connection con = JdbcUtil.getConnection();
		
		MemberDAO dao = MemberDAO.getInstance();
		
		dao.setConnection(con);
		
		// dao 객체에서 findIdMember 메서드 호출
		// 파라미터 : 멤버빈  리턴 : 문자열(id)
		id = dao.findIdMember(member); 
		
		JdbcUtil.commit(con);
		JdbcUtil.close(con);
		
		return id;
	}

}
