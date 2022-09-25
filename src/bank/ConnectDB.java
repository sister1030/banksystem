package bank;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectDB {
	private static ConnectDB instance;
	Connection conn = null;
	Statement stmt = null;
	ResultSet rs = null;
	
	public ConnectDB() {
		try {
			// 1. JDBC 드라이버 로딩
			Class.forName("com.mysql.jdbc.Driver");
			// 2. Connection 객체 생성
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/bank?&allowPublicKeyRetrieval=true", "root", "1234");
			// 연결된 DB
			stmt = conn.createStatement(); 
			System.out.println("DB 연결 성공");
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("");
			System.out.println("DB접속 오류");
			e.printStackTrace(); // 오류 메시지 출력
		}
	}
	
	//getInstance 메소드를 통해 한번만 생성된 객체를 가져온다.
	public static ConnectDB getInstance() {
		if (instance == null)
			instance = new ConnectDB();
		return instance;
	}

	// 한 번 연결된 객체를 계속 사용
	// 즉, 연결되지 않은 경우에만 연결을 시도하겠다는 의미
	// → 싱글톤(디자인 패턴)
	public Connection getConnection() {
		return this.conn;
	}
}
