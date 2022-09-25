package bank;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CheckCustomer {
	PreparedStatement pstmt;
	Statement stmt;
	ResultSet rs;
	Customer ct;

	public CheckCustomer() {

	}

	// 회원 정보 아이디로 조회
	public void getCustomer(String id) {
		Connection conn = ConnectDB.getInstance().getConnection();
		List<Customer> ctlist = new ArrayList<>();
		String sql = "SELECT * FROM CUSTOMER WHERE ID=?";
		try {
			// 3. Statement 객체 생성
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			// 4. SQL 문장을 실행하고 결과를 리턴
			rs = pstmt.executeQuery();
			if (rs == null) {
				System.out.println("저장된 데이터가 없습니다.");
			} else {
				// 5. ResultSet에 저장된 데이터 얻기
				while (rs.next()) {
					// Customer객체에 저장
					Customer ct = new Customer();
					int cno = rs.getInt("cno");
					String pw = rs.getString("pw");
					String name = rs.getString("name");
					String birth = rs.getString("birth");
					String tel = rs.getString("tel");
					ct = new Customer(cno, id, pw, name, birth, tel);

					// 리스트에 추가
					ctlist.add(ct);
				}
			}
			// 결과물 출력
			for (Customer ct : ctlist) {
				System.out.println("┃NO :"+ ct.getCno() + " " + "┃ID :" + ct.getId() + " " + "┃PW :" + ct.getPw()+ " " + "┃NAME :" + ct.getName() + " " + "┃BIRTH :" + ct.getBirth() + " " + "┃TEL :" + ct.getTel());
			}
		} catch (SQLException e) {
			System.out.println("[정보 조회 실패 : " + e.getMessage() + "]");
		} 
	}
	
	// 회원번호에 해당하는 한명의 회원정보 조회
	public void showCustomer(int cno) {
		Connection conn = ConnectDB.getInstance().getConnection();
		String sql = "SELECT * FROM CUSTOMER WHERE CNO=?";
		Customer ct = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, cno);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				ct = new Customer();
				ct.setCno(rs.getInt("cno"));
				ct.setId(rs.getString("id"));
				ct.setPw(rs.getString("pw"));
				ct.setName(rs.getString("name"));
				ct.setBirth(rs.getString("birth"));
				ct.setTel(rs.getString("tel"));

				System.out.println("                           [회원정보]");
				System.out.println("========================================================================");
				System.out.println("┃No. : " +ct.getCno() + "\t" + "┃ID : " + ct.getId() + "\t" + "┃PW : " + ct.getPw() + "\t" + "┃이름 : "
						+ ct.getName() + "\t" +"┃생일 : " +ct.getBirth() + "\t"+ "┃연락처 : " +ct.getTel() + "\t");
				System.out.println("========================================================================");
			}

		} catch (Exception e) {
			System.out.println("예외발생: " + e.getMessage());
		}
	}

	// 고객 정보 입력
	public int join(Customer ct) {
		Connection conn = ConnectDB.getInstance().getConnection();
		int key = 0;
		String sql = " INSERT INTO CUSTOMER(cno, id, pw, name, birth, tel) ";
		sql += " VALUES(?, ?, ?, ?, ?, ?)";
		try {
			new ConnectDB(); // DriverManager.getConnection 관련 초기화 코드
			pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, 0);
			pstmt.setString(2, ct.getId());
			pstmt.setString(3, ct.getPw());
			pstmt.setString(4, ct.getName());
			pstmt.setString(5, ct.getBirth());
			pstmt.setString(6, ct.getTel());
			key = pstmt.executeUpdate();
			rs = pstmt.getGeneratedKeys(); // 쿼리 실행 후 생성된 키 값 반환
			if (rs.next()) {
				if (key != 0) {
					key = rs.getInt(1); // 키값 초기화
					System.out.println("[" + ct.getName() + "]님,회원가입에 성공하였습니다.");
					System.out.println("*****회원정보와 고객번호를 확인하세요.*****");
					System.out.println("┃NO :" + key +" "+ "┃ID :" + ct.getId() + " " + "┃PW :" + ct.getPw() + " " + "┃이름 :"
							+ ct.getName() + " " + "┃생년월일 :" + ct.getBirth() + " " + "┃연락처 :" + ct.getTel() + " ");
					System.out.println(">> 로그인 화면으로 이동합니다.");
				} else {
					System.out.println("[회원가입 실패 : 다시 시도해 주세요.]");
				}
			}
			String msg = key > -1 ? "성공" : "실패";
			System.out.println(msg);
			return key;
		} catch (SQLException e) {
			System.out.println("[로그인 실패 : " + e + "]");
			key = -1;
		}
		return key;
	}

	// 아이디 체크
	public boolean chkid(String id) {
		Connection conn = ConnectDB.getInstance().getConnection();
		String sql = "SELECT ID FROM CUSTOMER WHERE ID=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				int key = rs.getInt(1);
				if (key > 0) {
					return true;
				}
			}
		} catch (SQLException e) {
			System.out.println("[아이디 체크 실패 : " + e + "]");
			e.printStackTrace();
		}
		return false;
	}

	// 연락처 체크
	public boolean chktel(String tel) {
		Connection conn = ConnectDB.getInstance().getConnection();
		String sql = "SELECT TEL FROM CUSTOMER WHERE TEL=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, tel);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				int key = rs.getInt(1);
				if (key > 0) {
					return true;
				}
			}
		} catch (SQLException e) {
			System.out.println("[연락처 체크 실패 : " + e + "]");
		}
		return false;
	}

	// 로그인
	public boolean login(Customer ct) {
		Connection conn = ConnectDB.getInstance().getConnection();
		String sql = "SELECT * FROM CUSTOMER WHERE ID=? AND PW=?";
		String id = ct.getId();
		String pw = ct.getPw();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				if ((rs.getString("id").equals(id)) == true && rs.getString("pw").equals(pw)) {
					ct.setCno(rs.getInt(1));
					ct.setName(rs.getString("name"));
					ct.setBirth(rs.getString("birth"));
					ct.setTel(rs.getString("tel"));
					Session.put(id, ct); // 세션에 정보 저장
					return true;
				} else {
					System.out.println("[로그인 실패 | 비밀번호가 일치하지 않습니다.]");
					return false;
				}
			}
		} catch (SQLException e) {
			System.out.println("[로그인  실패 | " + e + "]");
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (Exception e2) {
			}
		}
		return false;
	}

}
