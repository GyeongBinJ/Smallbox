package svc;

import java.sql.Connection;

import dao.MemberDAO;
import db.JdbcUtil;
import vo.MemberBean;

public class MemberLoginProService {

	public boolean loginMember(MemberBean member) {
		boolean isRightUser = false; // 아이디 없음
		
		// 공통작업-1. Connection 객체 가져오기
		Connection con = JdbcUtil.getConnection();
		
		// 공통작업-2. MemberDAO 객체 가져오기
		MemberDAO dao = MemberDAO.getInstance();
		
		// 공통작업-3. MemberDAO 객체에 Connection 객체 전달하기
		dao.setConnection(con);
		
		// MemberDAO 객체의 selectMember() 메서드를 호출하여 답글 쓰기 작업 요청
		// => 파라미터 : MemberBean 객체   리턴타입 : int(loginResult)
		isRightUser = dao.isRightUser(member);

		// 공통작업-4. Connection 객체 반환하기
		JdbcUtil.commit(con);
		JdbcUtil.close(con);
		
		return isRightUser;
	}
	
}

