package svc;

import java.sql.Connection;

import dao.MemberDAO;
import db.JdbcUtil;
import vo.MemberBean;

public class isRightUserService {

	public boolean passwdFind(MemberBean member) {
		boolean isExistUser = false; // 아이디 없음
		
		// 공통작업-1. Connection 객체 가져오기
		Connection con = JdbcUtil.getConnection();
		
		// 공통작업-2. MemberDAO 객체 가져오기
		MemberDAO dao = MemberDAO.getInstance();
		
		// 공통작업-3. MemberDAO 객체에 Connection 객체 전달하기
		dao.setConnection(con);
		
		// MemberDAO 객체의 isExistUser() 메서드를 호출하여 답글 쓰기 작업 요청
		// => 파라미터 : MemberBean 객체   리턴타입 : boolean(isExistUser)
		isExistUser = dao.isExistUser(member);
		
		JdbcUtil.commit(con);
		
		// 공통작업-4. Connection 객체 반환하기
		JdbcUtil.close(con);
		
		return isExistUser;
	}

	public boolean updatePasswd(String imsiPW, MemberBean member) {
		boolean updatePasswd = false; // 패스워드 변경실패
		
		// 공통작업-1. Connection 객체 가져오기
		Connection con = JdbcUtil.getConnection();
		
		// 공통작업-2. MemberDAO 객체 가져오기
		MemberDAO dao = MemberDAO.getInstance();
		
		// 공통작업-3. MemberDAO 객체에 Connection 객체 전달하기
		dao.setConnection(con);
		
		// MemberDAO 객체의 updateImsiPW() 메서드를 호출하여 답글 쓰기 작업 요청
		// => 파라미터 : MemberBean, imsiPW 객체   리턴타입 : boolean(updatePasswd)
		updatePasswd = dao.updateImsiPW(imsiPW, member);
		if(updatePasswd) {
			JdbcUtil.commit(con);
		} else {
			JdbcUtil.rollback(con);
		}
		
		// 공통작업-4. Connection 객체 반환하기
		JdbcUtil.close(con);
		
		return updatePasswd;
	}

}
