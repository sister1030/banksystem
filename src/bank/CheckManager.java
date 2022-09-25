package bank;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CheckManager {
	PreparedStatement pstmt;
	static Statement stmt;
	static ResultSet rs;
	Manager mg;
	Customer ct;

	public CheckManager() {

	}
	// 관리자 정보 입력
	public int join(Manager mg) {
		Connection conn = ConnectDB.getInstance().getConnection();
		int key = 0;
		String sql = " INSERT INTO MANAGER(mno, mid, mpw) ";
		sql += " VALUES(?, ?, ?)";
		try {
			new ConnectDB(); // DriverManager.getConnemgion 관련 초기화 코드
			pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, 0);
			pstmt.setString(2, mg.getMid());
			pstmt.setString(3, mg.getMpw());
			key = pstmt.executeUpdate();
			rs = pstmt.getGeneratedKeys(); // 쿼리 실행 후 생성된 키 값 반환
			if (rs.next()) {
				key = rs.getInt(1); // 키값 초기화
				System.out.println(" 관리자번호 : " + key); // 출력
			}
			String msg = key > -1 ? "성공" : "실패";
			System.out.println(msg);
			return key;
		} catch (SQLException e) {
			System.out.println("[회원가입 실패 : " + e + "]");
			key = -1;
		}
		return key;
	}

	// 아이디 체크
	public boolean chkmid(String mid) {
		Connection conn = ConnectDB.getInstance().getConnection();
		String sql = "SELECT MID FROM MANAGER WHERE MID=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, mid);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				int key = rs.getInt(1);
				if (key > 0) {
					return true;
				}
			}
		} catch (SQLException e) {
			System.out.println("[비밀번호 체크 실패 : " + e + "]");
		}
		return false;
	}

	// 관리자 로그인
	public boolean login(Manager mg) {
		Connection conn = ConnectDB.getInstance().getConnection();
		String sql = "SELECT * FROM MANAGER WHERE MID=? AND MPW=?";
		String mid = mg.getMid();
		String mpw = mg.getMpw();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, mid);
			pstmt.setString(2, mpw);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				if ((rs.getString("mid").equals(mid)) == true && rs.getString("mpw").equals(mpw)) {
					mg = new Manager(1, mg.getMid(), mg.getMpw());
					mg.setMno(rs.getInt(1));
					Session.put(mid, mg);
					return true;
				} else {
					System.out.println("[로그인 실패 | 비밀번호가 일치하지 않습니다.]");
					return false;
				}
			}
		} catch (SQLException e) {
			System.out.println("[로그인  실패 | " + e + "]");
			e.printStackTrace();
		}
		return false;
	}

	// 저장된 회원 목록
	public List<Customer> customerlist() {
		List<Customer> ctlist = new ArrayList<Customer>();
		Connection conn = ConnectDB.getInstance().getConnection();
		String sql = "SELECT * FROM CUSTOMER ORDER BY CNO";
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			// 5. ResultSet에 저장된 데이터 얻기
			while (rs.next()) {
				// Customer객체에 저장
				ct = new Customer();
				ct.setCno(rs.getInt(1));
				ct.setId(rs.getString("id"));
				ct.setPw(rs.getString("pw"));
				ct.setName(rs.getString("name"));
				ct.setBirth(rs.getString("birth"));
				ct.setTel(rs.getString("tel"));

				// 리스트에 추가
				ctlist.add(ct);

			}
		} catch (SQLException e) {
			System.out.println("[정보 불러오기 실패 : " + e.getMessage() + "]");
		}
		return ctlist;
	}

	// 회원삭제
	public boolean delete(String id) {
		Connection conn = ConnectDB.getInstance().getConnection();
		String sql = "DELETE FROM CUSTOMER WHERE ID=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			int key = pstmt.executeUpdate();
			if (key > 0) {
				System.out.println("[" + id + "]회원 정보 삭제 완료");
				return true;
			} else {
				System.out.println("[" + id + "]회원 정보 삭제 실패");
				return false;
			}
		} catch (Exception e) {
			System.out.println("예외발생: " + e.getMessage());
		} 
		return false;
	}

}
