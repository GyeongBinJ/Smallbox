package svc;

import java.sql.Connection;

import dao.ReserveDAO;
import db.JdbcUtil;
import vo.ReserveBean;

public class ReserveDetailService {
	// 글 상세정보 조회
	// => 단, 글번호와 함께 조회수 증가 여부를 파라미터로 전달
	public ReserveBean getReserve(int res_num) {
		ReserveBean reserve = null;
		
		// 공통작업-1. Connection 객체 가져오기
		Connection con = JdbcUtil.getConnection();
		
		// 공통작업-2. ReserveDAO 객체 가져오기
		ReserveDAO dao = ReserveDAO.getInstance();
		
		// 공통작업-3. ReserveDAO 객체에 Connection 객체 전달하기
		dao.setConnection(con);
		
		// ReserveDAO 의 selectReserve() 메서드 호출하여 게시물 상세 정보 조회 작업 수행
		// => 파라미터 : 글번호    리턴타입 : ReserveBean(reserve)
		reserve = dao.selectReserve(res_num);
		
		// 공통작업-4. Connection 객체 반환하기
		JdbcUtil.close(con);
		
		return reserve;
	}

}











