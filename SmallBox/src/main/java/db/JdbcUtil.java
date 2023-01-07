package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;

public class JdbcUtil {
	
	// 1. 데이터베이스 접근(공통 작업)을 통해 Connection 객체를 생성하여 외부로 리턴하는
	//    getConnection() 메서드 정의(데이터베이스 작업 1단계 & 2단계 수행)
	// => 파라미터 : 없음      리턴타입 : java.sql.Connection(con)
	// => 단, JdbcUtil 클래스의 인스턴스 생성 없이도 메서드 호출이 가능하도록
	//    static 메서드로 정의
	public static Connection getConnection() {
		// context.xml 에 설정된 DBCP(Connection Pool) 로부터 Connection 객체 가져와서 리턴
		// 데이터베이스 연결 객체를 저장할 Connection 타입 변수 선언
		Connection con = null;
		
		try {
			// 1. 톰캣으로부터 톰캣 객체에서 관리하는 context.xml 파일에 대한 정보를 관리하는
			//    Context 객체 가져오기(context.xml 파일 내의 <Context> 태그에 해당하는 부분)
			// => InitialContext 객체 생성 후 Context 타입으로 업캐스팅
			Context initCtx = new InitialContext();
			
			// 2. 생성된 Context 객체(initCtx)로부터 context.xml 에 정의된 Resource 태그 부분 가져오기
			// => Context 객체의 lookup() 메서드를 호출하여 "java:comp/env" 문자열 전달
			// => 리턴타입 Object 인 객체를 Context 타입으로 다운캐스팅
//		Context envCtx = (Context)initCtx.lookup("java:comp/env");
			
			// 3. Resource 태그 내의 name 속성(= 리소스 이름 jdbc/MySQL) 가져오기 위해
			//    생성된 Context 객체(envCtx)의 lookup() 메서드를 호출하여 리소스 이름 전달
			// => 리턴타입 Object 인 객체를 context.xml 내의 type 속성에 지정된 
			//    객체명(javax.sql.DataSource 타입)으로 다운캐스팅
//		DataSource ds = (DataSource)envCtx.lookup("jdbc/MySQL");
			
			// ----------- 참고> 2번과 3번 과정을 하나의 문장으로 결합하는 경우 -------------
			DataSource ds = (DataSource)initCtx.lookup("java:comp/env/jdbc/MySQL");
			// ------------------------------------------------------------------------------
			
			// 4. DataSource 객체(= 커넥션풀)로부터 미리 생성된 Connection 객체 가져오기
			con = ds.getConnection();

			// 5. 자동 커밋(= Auto Commit) 기능 해제(옵션)
			//    => 기본적으로 JDBC 사용 시 Auto Commit 기능이 동작되도록 설정되어 있음
			//       (true : Auto Commit 설정, false : Auto Commit 해제)
			con.setAutoCommit(false); // 자동 커밋 기능 해제
			// => 별도로 commit, rollback 작업을 수행할 메서드 필요
			
			// 6. 현재 커넥션 정보 확인
			BasicDataSource bds = (BasicDataSource)ds;
//			System.out.println("MaxTotal : " + bds.getMaxTotal()); // 최대 커넥션 수
//			System.out.println("Active : " + bds.getNumActive()); // 현재 사용 중인 커넥션 수
//			System.out.println("Idle : " + bds.getNumIdle()); // 유휴 상태 커넥션 수
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		// 데이터베이스 연결 객체가 저장된 Connection 타입 변수값 리턴
		return con;
	}
	
	// 데이터베이스 작업에 대한 Commit, Rollback 작업을 수행할 메서드 정의
	// => 파라미터 : Connection 객체(con)
	public static void commit(Connection con) {
		try {
			con.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void rollback(Connection con) {
		try {
			con.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// 데이터베이스 자원 반환을 공통으로 수행할 close() 메서드 정의
	// => 파라미터 : Connection 타입(con) 또는 PreparedStatement 타입(pstmt) 또는
	//               ResultSet 타입(rs)
	// => 각각의 파라미터를 따로 전달받아 각각 close() 작업을 수행하도록 메서드 따로 정의
	//    단, 각각의 메서드 이름은 모두 close() 로 정의하고 파라미터만 다르게 지정
	//    (= 메서드 오버로딩 = Method Overloading)
	// => 인스턴스 생성 없이도 메서드 호출이 가능하도록 static 메서드로 정의
	public static void close(Connection con) {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void close(PreparedStatement pstmt) {
		try {
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void close(ResultSet rs) {
		try {
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}









