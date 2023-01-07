package svc;

import java.sql.Connection;

import dao.MovieDAO;
import db.JdbcUtil;
import vo.MovieBean;

public class MovieModifyProService {

	// 등록된 영화 수정 작업
	public boolean modifyBoard(MovieBean movie) {
		boolean isModifySuccess = false;
		
		Connection con = JdbcUtil.getConnection();
		MovieDAO dao = MovieDAO.getInstance();
		dao.setConnection(con);
		
		int updateCount = dao.updateMovie(movie);
		
		if(updateCount>0) { // 글 수정 성공하면
			// isWriteSuccess를 true로 변경 후 action으로 리턴
			isModifySuccess = true; 
			JdbcUtil.commit(con);
		} else { // 작업 실패시
			// rollback - DB 작업 수행 X, isWriteSuccess(false)를 action으로 리턴
			JdbcUtil.rollback(con);
		}
		
		JdbcUtil.close(con);
		
		return isModifySuccess;
	}

}
