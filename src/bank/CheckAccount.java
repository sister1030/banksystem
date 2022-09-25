package bank;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CheckAccount {
	private Connection conn;
	private Statement stmt;
	private static PreparedStatement pstmt;
	private ResultSet rs;
	private Customer ct = new Customer();
	static String id = CustomerHandler.id2;

	// 계좌 정보 계좌번호로 불러오기
	public void getAccount(String account) {
		Connection conn = ConnectDB.getInstance().getConnection();
		List<Account> aclist = new ArrayList<>();
		String sql = "SELECT ANO,ACCOUNT,ACCOUNTPW,BALANCE,CNO FROM ACCOUNT WHERE ACCOUNT=?";
		try {
			/// 3. Statement 객체 생성
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,account);
			// 4. SQL 문장을 실행하고 결과를 리턴
			rs = pstmt.executeQuery();
			if (rs == null) {
				System.out.println("저장된 데이터가 없습니다.");
			} else {
				while (rs.next()) {
					Account ac = new Account();
					int ano = rs.getInt(1);
					String accountpw = rs.getString("accountpw");
					int balance = rs.getInt("balance");
					int cno = rs.getInt("cno");
					ac = new Account(ano, account, accountpw, balance, cno);

					Session.put(account, ac);
					aclist.add(ac);

				}
				// 결과물 출력
				for (Account ac : aclist) {
					System.out.println("No. : " + ac.getAno() + "\t" + "계좌번호 : " + ac.getAccount() + "\t" + "계좌 비밀번호 : "
							+ ac.getAccountpw() + "\t" + "잔고 : " + ac.getBalance() + "\t" + "고객번호 : " + ac.getCno() + "\t");
				}
			}
		} catch (SQLException e) {
			System.out.println("[정보 조회 실패 : " + e.getMessage() + "]");
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (Exception e) {
			}
		}
	}
	
	// 계좌 정보 회원번호로 불러오기
		public void showAccount(int cno) {
			Connection conn = ConnectDB.getInstance().getConnection();
			List<Account> aclist = new ArrayList<>();
			String sql = "SELECT ANO,ACCOUNT,ACCOUNTPW,BALANCE FROM ACCOUNT WHERE CNO=?";
			try {
				/// 3. Statement 객체 생성
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1,cno);
				// 4. SQL 문장을 실행하고 결과를 리턴
				rs = pstmt.executeQuery();
				if (rs == null) {
					System.out.println("저장된 데이터가 없습니다.");
				} else {
					while (rs.next()) {
						Account ac = new Account();
						int ano = rs.getInt(1);
						String account = rs.getString("account");
						String accountpw = rs.getString("accountpw");
						int balance = rs.getInt("balance");
						ac = new Account(ano, account, accountpw, balance, cno);

						aclist.add(ac);
					}
				}
				// 결과물 출력
				System.out.println("                            [계좌정보]");
				System.out.println("========================================================================");
				for (Account ac : aclist) {
					System.out.println("No. : " + ac.getAno() + "\t" + "계좌번호 : " + ac.getAccount() + "\t" + "계좌 비밀번호 : "
							+ ac.getAccountpw() + "\t" + "잔고 : " + ac.getBalance() + "\t");
				}
				System.out.println("========================================================================");
			} catch (SQLException e) {
				System.out.println("[정보 조회 실패 : " + e.getMessage() + "]");
			} finally {
				try {
					if (stmt != null)
						stmt.close();
				} catch (Exception e) {
				}
			}
		}

	// 계좌 정보 입력
	public int join(Account ac) {
		Connection conn = ConnectDB.getInstance().getConnection();
		String id = CustomerHandler.id2;
		int key = 0;
		String sql = "INSERT INTO ACCOUNT(ano,account,accountpw,balance,cno) ";
		sql += " VALUES(?, ?, ?, ?, ?)";
		try {
			new ConnectDB(); // DriverManager.getConnection 관련 초기화 코드
			pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, 0);
			pstmt.setString(2, ac.getAccount());
			pstmt.setString(3, ac.getAccountpw());
			pstmt.setInt(4, ac.getBalance());
			pstmt.setInt(5, Session.get1(id).getCno());
			key = pstmt.executeUpdate();
			rs = pstmt.getGeneratedKeys(); // 쿼리 실행 후 생성된 키 값 반환
			if (rs.next()) {
				key = rs.getInt(1); // 키값 초기화
				System.out.println(" 회원(계좌)번호 : " + key); // 출력
			}
			String msg = key > -1 ? "성공" : "실패";
			System.out.println(msg);
			return key;
		} catch (SQLException e) {
			System.out.println("[회원가입 실패 : " + e + "]");
			e.printStackTrace();
			key = -1;
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				System.out.println("[로그인 실패 : " + e + "]");
			}
		}
		return key;
	}

	// 계좌 체크
	public boolean chkac(String account) {
		Connection conn = ConnectDB.getInstance().getConnection();
		String sql = "SELECT ACCOUNT FROM ACCOUNT WHERE ACCOUNT=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, account);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				String key = rs.getString("account");
				if (key != null) {
					return true;
				}
			}
		} catch (SQLException e) {
			System.out.println("계좌 체크 실패 > " + e);
			e.printStackTrace();
		}
		return false;
	}

	// 회원 수정(잔고)
			public static void updateAccount(String account, int balance) {
				Scanner sc = new Scanner(System.in);
				Connection conn = ConnectDB.getInstance().getConnection();
				String sql = "UPDATE ACCOUNT SET BALANCE=? where ACCOUNT=?";
				try {
					Account ac = Session.get3(account);
					pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1, balance);
					pstmt.setString(2, account);
					int key = pstmt.executeUpdate();
					ac = new Account(ac.getAno(), account, ac.getAccountpw(), balance, ac.getCno());
					//System.out.println("번호:"+ac.getAno()+"계좌:"+account+"비번:"+ac.getAccountpw()+"잔고:"+balance+"고객번호:"+ac.getCno());
				} catch (Exception e) {
					System.out.println("예외발생: " + e.getMessage());
					e.printStackTrace();
				}
			}
//	// 회원정보 + 계좌정보
//	public void selectjoin(int cno) {
//
//		Connection conn = ConnectDB.getInstance().getConnection();
//		String sql = "SELECT C.CNO,C.ID,C.PW,C.NAME,C.BIRTH,C.TEL,A.ANO,A.ACCOUNT,A.ACCOUNTPW,A.BALANCE ";
//		sql += "FROM CUSTOMER C JOIN ACCOUNT A";
//		sql += "ON C.CNO = A.CNO WHERE C.ID=" + id + "";
//		try {
//			stmt = conn.createStatement();
//			// pstmt.setInt(1, cno);
//			rs = stmt.executeQuery(sql);
//			if (rs == null) {
//				System.out.println("저장된 데이터가 없습니다.");
//			} else {
//				if (rs.next()) {
//					AccountDTO dto = new AccountDTO();
//					// cno = rs.getInt("cno");
//					String id = rs.getString("id");
//					String pw = rs.getString("pw");
//					String name = rs.getString("name");
//					String bitrh = rs.getString("birth");
//					String tel = rs.getString("tel");
//
//					int ano = rs.getInt("ano");
//					String account = rs.getString("account");
//					String accountpw = rs.getString("accountpw");
//					int balance = rs.getInt("balance");
//					dto = new AccountDTO(dto.getCno(), dto.getId(), dto.getPw(), dto.getName(), dto.getBirth(),
//							dto.getTel(), dto.getAno(), dto.getAccount(), dto.getAccountpw(), dto.getBalance());
//
//					System.out.println(cno + "\t" + id + "\t" + pw + "\t" + name + "\t" + bitrh + "\t" + tel + "\t"
//							+ accountpw + "\t" + balance);
//
//				} else {
//					System.out.println("[정보조회  실패 | 정보가 일치하지 않습니다.]");
//				}
//			}
//		} catch (SQLException e) {
//			System.out.println("[정보조회  실패 | " + e + "]");
//			e.printStackTrace();
//		} finally {
//			try {
//				if (rs != null)
//					rs.close();
//				if (stmt != null)
//					stmt.close();
//			} catch (Exception e) {
//				System.out.println("[정보조회  실패 | " + e + "]");
//			}
//		}
//	}

	// 회원 수정(비밀번호)
	public static void updatepw(String id, String pw) {
		Connection conn = ConnectDB.getInstance().getConnection();
		Scanner sc = new Scanner(System.in);
		String sql = "UPDATE CUSTOMER SET PW=? where id=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, pw);
			pstmt.setString(2, id);
			int key = pstmt.executeUpdate();
			System.out.println(Session.get1(id).getName() + "님의 정보를 수정하였습니다.");
		} catch (Exception e) {
			System.out.println(Session.get1(id).getName() + "님의 정보 수정하지 못하였습니다.");
			System.out.println("예외발생: " + e.getMessage());
			e.printStackTrace();
		} finally {
			System.out.println("[변경된 패스워드 : " + pw + "]");
		}
	}

	// 회원 수정(연락처)
	public static void updatetel(String id, String tel) {
		Scanner sc = new Scanner(System.in);
		Connection conn = ConnectDB.getInstance().getConnection();
		String sql = "UPDATE CUSTOMER SET TEL=? where id=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, tel);
			pstmt.setString(2, id);
			int key = pstmt.executeUpdate();
			System.out.println(Session.get1(id).getName() + "님의 정보를 수정하였습니다.");
		} catch (Exception e) {
			System.out.println(Session.get1(id).getName() + "님의 정보 수정하지 못하였습니다.");
			System.out.println("예외발생: " + e.getMessage());
			e.printStackTrace();
		} finally {
			System.out.println("[변경된 연락처 : " + tel + "]");
		}
	}

	// 계좌정보 삭제
	public boolean deleteAccount(String account) {
		Connection conn = ConnectDB.getInstance().getConnection();
		String sql = "DELETE FROM ACCOUNT WHERE ACCOUNT=?";
		int key = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, account);
			key = pstmt.executeUpdate();
			if (key > 0) {
				System.out.println("[" + account + "계좌 정보 삭제 완료]");
				return true;
			} else {
				System.out.println("[" + account + "계좌 정보 삭제 실패]");
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return false;
	}
}
