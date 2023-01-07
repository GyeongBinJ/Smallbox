package svc;

import java.sql.Connection;

import dao.MovieDAO;
import db.JdbcUtil;
import vo.MovieBean;

public class MovieInsertProService {

	public boolean registMovie(MovieBean movie) {
		boolean isWriteSuccess = false;
		
		Connection con = JdbcUtil.getConnection();
		MovieDAO dao = MovieDAO.getInstance();
		dao.setConnection(con);
		
		// DAO의 영화 등록 DB 작업 결과를 리턴 받음
		int insertCount = dao.insertAdminMovie(movie);
		
		if(insertCount>0) { // 작업 성공시
			// isWriteSuccess를 true로 변경 후 action으로 리턴
			isWriteSuccess = true;
			JdbcUtil.commit(con);
			
		} else { // 작업 실패시
			// rollback - DB 작업 수행 X, isWriteSuccess(false)를 action으로 리턴
			JdbcUtil.rollback(con);
		}
		
		JdbcUtil.close(con);
		
		return isWriteSuccess;
	}

}
