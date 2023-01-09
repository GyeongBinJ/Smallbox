package svc;

import java.sql.Connection;

import dao.ReserveDAO;
import db.JdbcUtil;

public class ReserveCancelProService {
	// 예약 취소 가능 여부(= 영화시간 이전인지) 판별 요청 수행할 isReservedMember() 메서드 정의
	// => 파라미터 : 글번호, 패스워드    리턴타입 : boolean(isBoardWriter)
	public boolean isTimeOk(int res_idx) { //, String res_date ->일단 메서드에서 뺐음.
		boolean isTimeOk = false;
		
		// 공통작업-1. Connection 객체 가져오기
		Connection con = JdbcUtil.getConnection();
		
		// 공통작업-2. ReserveDAO 객체 가져오기
		ReserveDAO dao = ReserveDAO.getInstance();
		
		// 공통작업-3. ReserveDAO 객체에 Connection 객체 전달하기
		dao.setConnection(con);
		
		// ReserveDAO 의 isTimeOk() 메서드를 호출하여 패스워드 확인 작업 수행
		// => 파라미터 : 예약번호, 패스워드    리턴타입 : boolean(isTimeOk)
		isTimeOk = dao.isTimeOk(res_idx);//, res_date
		
		// 공통작업-4. Connection 객체 반환하기
		JdbcUtil.close(con);
		
		return isTimeOk;
	}

	// 예약번호(res_num)에 해당하는 예약 취소(삭제) 작업 수행하는 cancelReserve() 메서드
	//  => 파라미터 : 예약번호(res_num)    리턴타입 : int(cancelCount)
	public boolean cancelReserve(int res_idx) {
		boolean isCancelSuccess = false;
		
		// 공통작업-1. Connection 객체 가져오기
		Connection con = JdbcUtil.getConnection();
		
		// 공통작업-2. BoardDAO 객체 가져오기
		ReserveDAO dao = ReserveDAO.getInstance();
		
		// 공통작업-3. BoardDAO 객체에 Connection 객체 전달하기
		dao.setConnection(con);
		
		// BoardDAO 의 cancelReserve() 메서드를 호출하여 글 삭제 작업 수행
		// => 파라미터 : 글번호    리턴타입 : int(deleteCount)
		int cancelCount = dao.cancelReserve(res_idx);
		
		// 리턴받은 결과를 판별하여 commit, rollback
		if(cancelCount > 0) {
			JdbcUtil.commit(con);
			isCancelSuccess = true;
		} else {
			JdbcUtil.rollback(con);
		}
		
		// 공통작업-4. Connection 객체 반환하기
		JdbcUtil.close(con);
		
		return isCancelSuccess;
	}

}











