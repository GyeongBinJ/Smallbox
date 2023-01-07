package svc;

import java.sql.Connection;
import java.util.List;

import dao.NoticeDAO;
import db.JdbcUtil;
import vo.NoticeBean;

public class NoticeListService {
	
	public List<NoticeBean> getNoticeList(String keyword, int startRow, int listLimit) {
		
		List<NoticeBean> noticeList = null;
		
		// 공통작업-1. Connection 객체 가져오기
		Connection con = JdbcUtil.getConnection();
		
		// 공통작업-2. BoardDAO 객체 가져오기
		NoticeDAO dao = NoticeDAO.getInstance();
		
		// 공통작업-3. BoardDAO 객체에 Connection 객체 전달하기
		dao.setConnection(con);
		
		// BoardDAO 객체의 selectBoardList() 메서드를 호출하여 글목록 조회 작업 수행
		// => 파라미터 : 검색어, 시작행번호, 목록갯수   리턴타입 : List<BoardBean>(boardList)
		noticeList = dao.selectNoticeList(keyword, startRow, listLimit);
		
		// 공통작업-4. Connection 객체 반환하기
		JdbcUtil.commit(con);
		JdbcUtil.close(con);
		
		// 조회 결과 리턴
		return noticeList;
		
	}

	public int getNoticeListCount(String keyword) {
		int listCount = 0;
		
		// 공통작업-1. Connection 객체 가져오기
		Connection con = JdbcUtil.getConnection();
		
		// 공통작업-2. BoardDAO 객체 가져오기
		NoticeDAO dao = NoticeDAO.getInstance();
		
		// 공통작업-3. BoardDAO 객체에 Connection 객체 전달하기
		dao.setConnection(con);
		
		// BoardDAO 객체의 selectBoardListCount() 메서드를 호출하여 글목록 갯수 조회 작업 수행
		// => 파라미터 : 검색어     리턴타입 : int(listCount)
		listCount = dao.selectNoticeListCount(keyword);
		
		// 공통작업-4. Connection 객체 반환하기
		JdbcUtil.commit(con);
		JdbcUtil.close(con);
		
		return listCount;
	}

}