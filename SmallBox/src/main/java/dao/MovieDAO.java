package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.JdbcUtil;
import vo.MovieBean;
import vo.StarMovieBean;

public class MovieDAO {

	PreparedStatement pstmt = null, pstmt2 = null;
	ResultSet rs = null;
	
	//----------------------------------------------------
	// 싱글톤 패턴
	private static MovieDAO instance = new MovieDAO();

	public static MovieDAO getInstance() {
		return instance;
	}

	//-----------------------------------------------------
	private Connection con;

	public void setConnection(Connection con) {
		this.con = con;
	}
	
	//-----------------------------------------------------
	// DB 작업
	
	// 관리자 페이지 - 영화 등록
	public int insertAdminMovie(MovieBean movie) {
		int insertCount = 0;
		
		try {
			// 1. 영화 번호 구하기
			int movie_idx = 1;
			
			String sql = "SELECT MAX(movie_idx) FROM movie";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				movie_idx = rs.getInt(1) + 1;
			}
			
			// 2. 영화 등록
			sql = "INSERT INTO movie VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
			pstmt2 = con.prepareStatement(sql);
			pstmt2.setInt(1, movie_idx);
			pstmt2.setString(2, movie.getMovie_title());
			pstmt2.setString(3, movie.getMovie_grade());
			pstmt2.setString(4, movie.getMovie_genre());
			pstmt2.setDate(5, movie.getMovie_open_date());
			pstmt2.setInt(6, movie.getMovie_runtime());
			pstmt2.setString(7, movie.getMovie_intro());
			pstmt2.setString(8, movie.getMovie_actors());
			pstmt2.setString(9, movie.getMovie_picture());
			pstmt2.setString(10, movie.getMovie_real_picture());
			pstmt2.setString(11, movie.getMovie_teaser());
			pstmt2.setInt(12, movie.getMovie_viewer()); // 디폴트 0
			
			insertCount = pstmt2.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("SQL 구문 오류! - insertMovie()");
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			JdbcUtil.close(pstmt2);
		}
		
		return insertCount;
	}

	// 관리자 페이지 - 극장 등록을 위한 영화제목 목록 출력(파라미터X)
	public List<MovieBean> selectMovieList() {
		System.out.println("MovieDAO - selectMovieList()");
		List<MovieBean> movieList = null;
		
		try {
			String sql = "SELECT * FROM movie";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			movieList = new ArrayList<MovieBean>();
			
			while(rs.next()) {
				MovieBean movie = new MovieBean();
				movie.setMovie_idx(rs.getInt("movie_idx"));
				movie.setMovie_title(rs.getString("movie_title"));
				movie.setMovie_grade(rs.getString("movie_grade"));
				movie.setMovie_genre(rs.getString("movie_genre"));
				movie.setMovie_open_date(rs.getDate("movie_open_date"));
				movie.setMovie_runtime(rs.getInt("movie_runtime"));
				movie.setMovie_intro(rs.getString("movie_intro"));
				movie.setMovie_actors(rs.getString("movie_actors"));
				movie.setMovie_picture(rs.getString("movie_picture"));
				movie.setMovie_real_picture(rs.getString("movie_real_picture"));
				movie.setMovie_teaser(rs.getString("movie_teaser"));
				movie.setMovie_viewer(rs.getInt("movie_viewer"));
				
				movieList.add(movie);
			}
		} catch (SQLException e) {
			System.out.println("SQL 구문 오류! - selectMovieList()");
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
		return movieList; // AdminMovieListService
	}
	
	
	// 관리자 페이지 , 영화 목록 , 박스오피스 - 영화 목록 출력
	public List<MovieBean> selectMovieList(String keyword, int startRow, int listLimit) {
		List<MovieBean> movieList = null;
		
		try {
			
			// keyword(검색어)를 포함하는 제목을 가진 영화 startRow ~ listLimit개만큼 출력
			String sql = "SELECT * FROM movie WHERE movie_title LIKE ? ORDER BY movie_viewer DESC LIMIT ?,?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%"+keyword+"%");
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, listLimit);
			rs = pstmt.executeQuery();
			
			movieList = new ArrayList<MovieBean>();
			
			while(rs.next()) {
				MovieBean movie = new MovieBean();
				movie.setMovie_idx(rs.getInt("movie_idx"));
				movie.setMovie_title(rs.getString("movie_title"));
				movie.setMovie_grade(rs.getString("movie_grade"));
				movie.setMovie_genre(rs.getString("movie_genre"));
				movie.setMovie_open_date(rs.getDate("movie_open_date"));
				movie.setMovie_runtime(rs.getInt("movie_runtime"));
				movie.setMovie_intro(rs.getString("movie_intro"));
				movie.setMovie_actors(rs.getString("movie_actors"));
				movie.setMovie_picture(rs.getString("movie_picture"));
				movie.setMovie_real_picture(rs.getString("movie_real_picture"));
				movie.setMovie_teaser(rs.getString("movie_teaser"));
				movie.setMovie_viewer(rs.getInt("movie_viewer"));
				
				movieList.add(movie);
			}
			
		} catch (SQLException e) {
			System.out.println("SQL 구문 오류! - selectMovieList()");
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
		
		return movieList;
	}

	// 관리자 페이지 - 총 게시물 수 조회 : 페이징 처리를 위한 작업
	public int selectBoardListCount(String keyword) {
		int listCount = 0;
		
		try {
			// 영화 제목에 keyword(검색어)를 포함하는 영화의 갯수 
			String sql = "SELECT COUNT(*) FROM movie WHERE movie_title Like ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%" + keyword + "%");
			rs = pstmt.executeQuery();
			
			if(rs.next()) { // 조건에 일치하는 조회 결과가 있다면
				listCount = rs.getInt(1); // listCount 변수에 갯수(Count) 저장
			}
			
		} catch (SQLException e) {
			System.out.println("SQL 구문 오류! - selectBoardListCount()");
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
		
		return listCount;
	}
	
	// 관리자 페이지 - 영화 상세 정보 조회
	public MovieBean selectMovie(int movie_idx) {
		MovieBean movie = null;
		
		try {
			
			// 영화 번호와 일치하는 영화의 상세정보 조회
			String sql = "SELECT * FROM movie WHERE movie_idx = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, movie_idx);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				movie = new MovieBean();
				
				movie.setMovie_idx(movie_idx);
				movie.setMovie_title(rs.getString("movie_title"));
				movie.setMovie_grade(rs.getString("movie_grade"));
				movie.setMovie_genre(rs.getString("movie_genre"));
				movie.setMovie_open_date(rs.getDate("movie_open_date"));
				movie.setMovie_runtime(rs.getInt("movie_runtime"));
				movie.setMovie_intro(rs.getString("movie_intro"));
				movie.setMovie_actors(rs.getString("movie_actors"));
				movie.setMovie_picture(rs.getString("movie_picture"));
				movie.setMovie_real_picture(rs.getString("movie_real_picture"));
				movie.setMovie_teaser("https://www.youtube.com/embed/"+rs.getString("movie_teaser"));
				movie.setMovie_viewer(rs.getInt("movie_viewer"));
			}
			
		} catch (SQLException e) {
			System.out.println("SQL 구문 오류! - selectMovie()");
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
		
		return movie;
	}

	// 관리자 페이지 - 영화 수정
	public int updateMovie(MovieBean movie) {
		int updateCount = 0;
		
		try {
			String sql = "UPDATE movie SET movie_title = ?, movie_grade = ?, movie_genre = ?, movie_open_date = ?"
					   + ", movie_runtime = ?, movie_intro = ?, movie_actors = ?";
						// 파일명이 null이 아닐 경우 (= 수정폼에서 파일을 수정할 경우) 에만 파일명 수정
						if(movie.getMovie_picture() != null) {
							sql += ", movie_picture = ?, movie_real_picture = ?"; 
 						}
				   sql += ", movie_teaser = ?, movie_viewer = ? WHERE movie_idx = ?";
				   
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, movie.getMovie_title());
			pstmt.setString(2, movie.getMovie_grade());
			pstmt.setString(3, movie.getMovie_genre());
			pstmt.setDate(4, movie.getMovie_open_date());
			pstmt.setInt(5, movie.getMovie_runtime());
			pstmt.setString(6, movie.getMovie_intro());
			pstmt.setString(7, movie.getMovie_actors());
			// 파일명(movie_picture)이 null이 아닐 경우에만 (= 수정폼에서 파일을 수정할 경우) 에만 파일명 수정
			if(movie.getMovie_picture() != null) {
				pstmt.setString(8, movie.getMovie_picture());
				pstmt.setString(9, movie.getMovie_real_picture());
				pstmt.setString(10, movie.getMovie_teaser());
				pstmt.setInt(11, movie.getMovie_viewer()); // 디폴트 0
				pstmt.setInt(12, movie.getMovie_idx()); 
			} else { // 파일 수정 안하면 기존 파일 유지
				pstmt.setString(8, movie.getMovie_teaser());
				pstmt.setInt(9, movie.getMovie_viewer()); // 디폴트 0
				pstmt.setInt(10, movie.getMovie_idx()); 
			}
			
			updateCount = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("SQL 구문 오류! - updateMovie()");
			e.printStackTrace();
		} finally {
			JdbcUtil.close(pstmt);
		}
		
		return updateCount;
	}

	// 관리자 페이지 - 영화 삭제
	public int removeMovie(int movie_idx) {
		int deleteCount = 0;
		
		try {
			// 영화 번호와 일치하는 영화 삭제
			String sql = "DELETE FROM movie WHERE movie_idx = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, movie_idx);
			
			deleteCount = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("SQL 구문 오류! - removeMovie()");
			e.printStackTrace();
		} finally {
			JdbcUtil.close(pstmt);
		}
		
		return deleteCount;
	}

	// 영화 목록 페이지 - 찜 작업 수행
	public int insertMovieLike(int movie_idx, String member_id) {
		int insertlikeCount = 0;
		
		try {
			
			// movie_like 테이블에 찜정보에 해당하는 movie_idx와 member_id 추가
			String sql = "INSERT INTO movie_like VALUES(?,?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, movie_idx);
			pstmt.setString(2, member_id);
			
			insertlikeCount = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("SQL 구문 오류! - insertMovieLike()");
			e.printStackTrace();
		} finally {
			JdbcUtil.close(pstmt);
		}
		
		return insertlikeCount;
	}
	
	// 영화 목록 페이지 - 찜 해제 작업 수행
	public int deleteMovieLike(int movie_idx, String member_id) {
		int deletelikeCount = 0;
		
		try {
			String sql = "DELETE FROM movie_like WHERE movie_idx = ? AND member_id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, movie_idx);
			pstmt.setString(2, member_id);
			
			deletelikeCount = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("SQL 구문 오류! - deleteMovieLike()");
			e.printStackTrace();
		} finally {
			JdbcUtil.close(pstmt);
		}
		
		return deletelikeCount;
	}

	// 마이페이지 찜 목록 - 찜한 영화의 정보 뿌리기
	public List<MovieBean> selectLikeList(String member_id, int startRow, int movieLimit) {
		List<MovieBean> likeList = null;
		
		try {
			// 1. WHERE절로 각 회원별 찜 목록을 조회
			// 2. movie 테이블의 movie_idx와 movie_like 테이블의 movie_idx을 조인해 movie 테이블의 모든 컬럼 조회
			// => (찜 목록에서 뿌릴 정보만 가져와도 됨)
			String sql = "SELECT DISTINCT * FROM movie JOIN movie_like ON movie.movie_idx = movie_like.movie_idx WHERE member_id = ? LIMIT ?,?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, member_id);
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, movieLimit);
			rs = pstmt.executeQuery();
			
			likeList = new ArrayList<MovieBean>();
			
			while(rs.next()) {
				MovieBean movie = new MovieBean();
				movie.setMovie_idx(rs.getInt("movie_idx"));
				movie.setMovie_title(rs.getString("movie_title"));
				movie.setMovie_grade(rs.getString("movie_grade"));
				movie.setMovie_genre(rs.getString("movie_genre"));
				movie.setMovie_open_date(rs.getDate("movie_open_date"));
				movie.setMovie_runtime(rs.getInt("movie_runtime"));
				movie.setMovie_intro(rs.getString("movie_intro"));
				movie.setMovie_actors(rs.getString("movie_actors"));
				movie.setMovie_picture(rs.getString("movie_picture"));
				movie.setMovie_real_picture(rs.getString("movie_real_picture"));
				movie.setMovie_teaser(rs.getString("movie_teaser"));;
				movie.setMovie_viewer(rs.getInt("movie_viewer"));
				
				// 찜정보 likeList에 MovieBean(영화정보) 객체 저장
				likeList.add(movie);
			}
			
		} catch (SQLException e) {
			System.out.println("SQL 구문 오류! - selectLikeList");
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
		
		return likeList;
	}
	
	// 찜 여부 확인을 위한 비교용 정보(mobie_idx) 출력
	public List<Integer> selectLikeList2(String member_id) {
		List<Integer> likeList = null;
		
		try {
			// 1. WHERE절로 각 회원별 찜 목록을 조회
			// 2. movie 테이블의 movie_idx와 movie_like 테이블의 movie_idx을 조인해 movie 테이블의 모든 컬럼 조회
			// => (찜 목록에서 뿌릴 정보만 가져와도 됨)
			String sql = "SELECT DISTINCT * FROM movie JOIN movie_like ON movie.movie_idx = movie_like.movie_idx WHERE member_id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, member_id);
			rs = pstmt.executeQuery();
			
			likeList = new ArrayList<Integer>();
			
			while(rs.next()) {
				// 찜정보 likeList에 MovieBean(영화정보) 객체 저장
				likeList.add(rs.getInt("movie_idx"));
			}
			
		} catch (SQLException e) {
			System.out.println("SQL 구문 오류! - selectLikeList");
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
		
		return likeList;
	}

	// 찜이 되어있는지 조회 (1 이상이면 찜O / 0이면 찜X)
	public int selectLike(int movie_idx, String member_id) {
		int likeCount = 0;
		
		try {
			String sql = "SELECT COUNT(*) FROM movie_like WHERE movie_idx = ? AND member_id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, movie_idx);
			pstmt.setString(2, member_id);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				likeCount = rs.getInt(1);
			}
			
		} catch (SQLException e) {
			System.out.println("SQL 구문 오류! - selectLike()");
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
		
		return likeCount;
	}

	// // 각 멤버(member_id로 구분)가 찜한 영화의 총 개수 조회
	public int selectLikeMovieCount(String member_id) {
		int movieCount = 0;
		
		try {
			String sql = "SELECT Count(*) FROM movie_like WHERE member_id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, member_id);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				movieCount = rs.getInt(1);
			}
			
		} catch (SQLException e) {
			System.out.println("SQL 구문 오류! - selectLikeMovieCount()");
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
		
		return movieCount;
	}



	// 개봉 예정작 리스트 조회
	public List<MovieBean> selectCommingMovieList(String keyword, int startRow, int listLimit) {
		List<MovieBean> commingMovieList = null;
		
		try {
			// 오늘 날짜보다 큰 넘들만 데리고 오기
			String sql = "SELECT * FROM movie m WHERE exists (SELECT * FROM movie v WHERE movie_open_date > curdate() AND m.movie_idx = v.movie_idx)"
					+ " AND movie_title LIKE ? ORDER BY movie_idx LIMIT ?,?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%"+keyword+"%");
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, listLimit);
			rs = pstmt.executeQuery();
			
			commingMovieList = new ArrayList<MovieBean>();
			
			while(rs.next()) {
				MovieBean movie = new MovieBean();
				movie.setMovie_idx(rs.getInt("movie_idx"));
				movie.setMovie_title(rs.getString("movie_title"));
				movie.setMovie_grade(rs.getString("movie_grade"));
				movie.setMovie_genre(rs.getString("movie_genre"));
				movie.setMovie_open_date(rs.getDate("movie_open_date"));
				movie.setMovie_runtime(rs.getInt("movie_runtime"));
				movie.setMovie_intro(rs.getString("movie_intro"));
				movie.setMovie_actors(rs.getString("movie_actors"));
				movie.setMovie_picture(rs.getString("movie_picture"));
				movie.setMovie_real_picture(rs.getString("movie_real_picture"));
				movie.setMovie_teaser(rs.getString("movie_teaser"));;
				movie.setMovie_viewer(rs.getInt("movie_viewer"));
				
				// 찜정보 likeList에 MovieBean(영화정보) 객체 저장
				commingMovieList.add(movie);
			}
			
		} catch (SQLException e) {
			System.out.println("SQL 구문 오류! - selectCommingMovieList()");
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
		
		return commingMovieList;
	}

	// 개봉 예정작 총 개수 (페이징 처리를 위함)
	public int selectCommingListCount(String keyword) {
		int listCount = 0;
		
		try {
			// 영화 제목에 keyword(검색어)를 포함하는 영화의 갯수 
			String sql = "SELECT Count(*) FROM movie m WHERE exists (SELECT * FROM movie v WHERE movie_open_date > curdate() AND m.movie_idx = v.movie_idx) AND movie_title LIKE ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%" + keyword + "%");
			rs = pstmt.executeQuery();
			
			if(rs.next()) { // 조건에 일치하는 조회 결과가 있다면
				listCount = rs.getInt(1); // listCount 변수에 갯수(Count) 저장
			}
			
		} catch (SQLException e) {
			System.out.println("SQL 구문 오류! - selectCommingListCount()");
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
		
		return listCount;
	} 
	
	// 영화 목록 - 영화 목록 출력 (평점 포함) -> 뷰 이용
	public List<StarMovieBean> selectStarMovieList(String keyword, int startRow, int listLimit) {
		List<StarMovieBean> starmovieList = null;
		
		try {
			
			// keyword(검색어)를 포함하는 제목을 가진 영화 startRow ~ listLimit개만큼 출력
			String sql = "SELECT * FROM star_movie WHERE movie_title LIKE ? ORDER BY movie_viewer DESC LIMIT ?,?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%"+keyword+"%");
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, listLimit);
			rs = pstmt.executeQuery();
			
			starmovieList = new ArrayList<StarMovieBean>();
			
			while(rs.next()) {
				StarMovieBean starmovie = new StarMovieBean();
				starmovie.setMovie_idx(rs.getInt("movie_idx"));
				starmovie.setMovie_title(rs.getString("movie_title"));
				starmovie.setMovie_open_date(rs.getDate("movie_open_date"));
				starmovie.setMovie_real_picture(rs.getString("movie_real_picture"));
				starmovie.setMovie_viewer(rs.getInt("movie_viewer"));
				starmovie.setComment_star(rs.getDouble("comment_star"));
				
				starmovieList.add(starmovie);
			}
			
		} catch (SQLException e) {
			System.out.println("SQL 구문 오류! - selectStarMovieList()");
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
		
		return starmovieList;
	}
		
}
	
