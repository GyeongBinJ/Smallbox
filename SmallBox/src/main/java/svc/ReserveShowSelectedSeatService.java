package svc;

import java.sql.Connection;
import java.util.List;

import dao.ReserveDAO;
import db.JdbcUtil;
import vo.ReserveBean;

public class ReserveShowSelectedSeatService {

	public List<String> getReservedSeatList(ReserveBean reserve) {
		List<String> seatNumList = null;
		
		//-------------------공통------------------------------
		Connection con = JdbcUtil.getConnection();
		ReserveDAO dao = ReserveDAO.getInstance();
		dao.setConnection(con);
		//-----------------------------------------------------
		
		seatNumList = dao.getSelectedSeatList(reserve);
		
		//-------------------공통------------------------------
		JdbcUtil.commit(con);
		JdbcUtil.close(con);
		//-----------------------------------------------------
		
		System.out.println("service : return 받은 seatNumList : "+seatNumList);
		return seatNumList;
	}

}
