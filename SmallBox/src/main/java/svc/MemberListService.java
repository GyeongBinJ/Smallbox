package svc;

import java.sql.Connection;
import java.util.List;

import dao.CouponDAO;
import dao.MemberDAO;
import db.JdbcUtil;
import vo.CouponBean;
import vo.MemberBean;

public class MemberListService {

	public List<MemberBean> getMemberList(String keyword, int startRow, int listLimit) {
		List<MemberBean> memberList = null;
//		System.out.println("MemberListService");
		// 공통작업-1. Connection 객체 가져오기
		Connection con = JdbcUtil.getConnection();
		
		// 공통작업-2. MemberDAO 객체 가져오기
		MemberDAO dao = MemberDAO.getInstance();
		
		// 공통작업-3. MemberDAO 객체에 Connection 객체 전달하기
		dao.setConnection(con);
		
		memberList = dao.selectMemberList(keyword, startRow, listLimit);
		
		// 공통작업-4. Connection 객체 반환하기
		JdbcUtil.commit(con);
		JdbcUtil.close(con);
		
		return memberList;
	}
	
	public int memberListCount(String keyword) {
		int listCount = 0;
		Connection con = JdbcUtil.getConnection();
		MemberDAO dao = MemberDAO.getInstance();
		dao.setConnection(con);
		listCount = dao.selectMemberListCount(keyword);
		
		JdbcUtil.commit(con);
		JdbcUtil.close(con);
		
		return listCount;
	}
}
