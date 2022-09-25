package bank;

import java.util.List;
import java.util.Scanner;

public class ManagerHandler {
	static boolean run = true; // 실행
	static Scanner sc = new Scanner(System.in);
	static Manager mg = new Manager(); // DB를 dto에 저장
	static CheckCustomer cc = new CheckCustomer();
	static CheckManager cm = new CheckManager();
	static Customer ct;
	static String mno, mid, mpw = null;
	static String mid2; // 다른클래스로 넘길 아이디값

	public ManagerHandler() {

	}

	// 관리자 접속 로그인 (비밀번호 1111)
	public static void managerconnect() {
		int cpw;
		while (run) {
			try {
				System.out.println("|관리자 모드 접속|");
				System.out.print("PW > ");
				cpw = sc.nextInt();
				if (cpw == 1111) {
					System.out.println("[접속 성공하였습니다.] \n");
					break;
				} else {
					System.out.println("[접속 실패했습니다.]");
					continue;
				}
			} catch (Exception e) {
				System.out.println("[접속 실패 :" + e.getMessage() + "]");
				e.printStackTrace();
			}
		}
	}

	// 회원가입
	public static void join() {
		// ID
		while (run) {
			System.out.print("ID : ");
			mg.setMid(sc.next());
			if (cm.chkmid(mg.getMid())) {
				System.out.println("[중복된 아이디입니다. 다시 입력하세요.]");
				continue;
			}
			break;
		}
		// PW
		while (run) {
			System.out.print("PW : ");
			mg.setMpw(sc.next());
			// 아스키코드로 숫자 유효성검사
			int i = 0;
			for (i = 0; i < mg.getMpw().length(); i++) {
				char a = mg.getMpw().charAt(i);
				if (a <= 48 || a >= 57) {
					break;
				}
			}
			// 비밀번호 유효성 검사(4자리/공백/숫자/확인)
			if (i == mg.getMpw().length()) {
				if (mg.getMpw().length() != 4) {
					System.out.println("[패스워드는 4자리로 입력해주세요.]");
					continue;
				}
				System.out.print("PW 확인 : ");
				String pwchk = sc.next();
				if (mg.getMpw().trim().isEmpty() || pwchk.trim().isEmpty()) {
					System.out.println("[패스워드 또는 패스워드 확인이 공백입니다.]");
					continue;
				} else if (!mg.getMpw().equals(pwchk)) {
					System.out.println("[패스워드와 패스워드 확인이 일치하지 않습니다.]");
					continue;
				}
			} else {
				System.out.println("[숫자만 입력해주세요.]");
				continue;
			}
			break;
		}

		mg = new Manager(1, mg.getMid(), mg.getMpw());
		Session.map2.put(mid, mg);

		int key = cm.join(mg);
		if (key != 0) {
			System.out.println("[관리자]님,회원가입에 성공하였습니다.");
			System.out.println(">> 로그인 화면으로 이동합니다.");
		} else {
			System.out.println("[회원가입 실패 : 다시 시도해 주세요.]");
		}
	}

	// 로그인
	public static void login() {
		while (run) {
			try {
				System.out.println("|로그인|");
				System.out.print("ID : ");
				mg.setMid(sc.next());
				System.out.print("PW : ");
				mg.setMpw(sc.next());
				if (cm.login(mg) != false) {
					System.out.println("['관리자' 로그인에 성공하였습니다.] \n");
					mid2 = mg.getMid(); // id값 넘기기
					break;
				} else {
					System.out.println("[로그인 실패했습니다.]");
					continue;
				}
			} catch (Exception e) {
				System.out.println("[로그인 실패 :" + e.getMessage() + "]");
				e.printStackTrace();
			}
		}
		Menu.ManagerMenu(); // 관리자메뉴 이동
	}

	// 저장된 회원 목록
	public static void showCustomerList() {
		List<Customer> list = cm.customerlist();
		System.out.println("                             Customer List");
		System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
		if (list != null && list.size() > 0) {
			System.out.println("reg.No\t  아이디 \t\t비밀번호\t\t생년월일\t\t연락처");
			System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

			 for (Customer ct : list){
	                System.out.println(ct);
	            }      
			 
		} else {
			System.out.println("저장된 데이터가 없습니다. ");
		}
		System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━총 "
				+ ((list == null) ? "0" : list.size()) + " 명=\n");
	}

	// 회원 삭제
	public static void deletecustomer() {
		while (run) {
			System.out.println("삭제할 회원의 아이디를 입력해주세요");
			String id = sc.next();
			cc.getCustomer(id);
			if (id != null) {
				System.out.println("해당 회원의 정보를 삭제하시겠습니까?(Y/N) : ");
				String input = sc.next();
				if (input.equalsIgnoreCase("y")) {
					boolean r = cm.delete(id);
					if (r) {
						System.out.println("["+ id +"]회원의 정보가 정상적으로 삭제되었습니다.");
						break;
					} else {
						System.out.println("회원의 정보가 정상적으로 삭제 되지 않았습니다.");
						continue;
					}
				} else {
					System.out.println("삭제를 취소하였습니다.");
					continue;
				}
			} else {
				System.out.println("입력하신 회원번호에 해당하는 회원이 존재하지 않습니다.");
				continue;
			}

		}
	}	
	// 로그아웃
		public static void managerlogout() {
			Session.map2.put(mid, null);
			System.out.println("[로그아웃되었습니다.]");
		}
}
