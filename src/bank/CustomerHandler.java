package bank;

import java.util.Scanner;

public class CustomerHandler {
	static boolean run = true; // 실행
	static Scanner sc = new Scanner(System.in);
	static Customer ct = new Customer(); // DB를 dto에 저장
	static CheckCustomer cc = new CheckCustomer();
	static String id, pw, name, birth, tel = null;
	static String id2; // 다른클래스로 넘길 아이디값

	public CustomerHandler() {
	}

	// 회원가입
	public static void join() {
		// ID
		while (run) {
			System.out.print("ID : ");
			ct.setId(sc.next());
			if (cc.chkid(ct.getId())) {
				System.out.println("[중복된 아이디입니다. 다시 입력하세요.]");
				continue;
			}
			if (ct.getId().length() < 5 || ct.getId().length() > 15) {
				System.out.println("5~15자 이내의 영어,숫자아이디만 가능합니다");
				continue;
			} else {
				int c1 = 0, c2 = 0;
				for (int i = 0; i < ct.getId().length(); i++) {
					char c = ct.getId().charAt(i);
					if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
						c1++;	
					}else if (c >= '0' && c <= '9') {
						c2++;
					}
				}
			}
			break;
		}
		// PW
		while (run) {
			System.out.print("PW : ");
			ct.setPw(sc.next());
			// 아스키코드로 숫자 유효성검사
			int i = 0;
			for (i = 0; i < ct.getPw().length(); i++) {
				char a = ct.getPw().charAt(i);
				if (a <= 48 || a >= 57) {
					break;
				}
			}
			// 비밀번호 유효성 검사(4자리/공백/숫자/확인)
			if (i == ct.getPw().length()) {
				if (ct.getPw().length() != 4) {
					System.out.println("[패스워드는 4자리로 입력해주세요.]");
					continue;
				}
				System.out.print("PW 확인 : ");
				String pwchk = sc.next();
				if (ct.getPw().trim().isEmpty() || pwchk.trim().isEmpty()) {
					System.out.println("[패스워드 또는 패스워드 확인이 공백입니다.]");
					continue;
				} else if (!ct.getPw().equals(pwchk)) {
					System.out.println("[패스워드와 패스워드 확인이 일치하지 않습니다.]");
					continue;
				}
			} else {
				System.out.println("[숫자만 입력해주세요.]");
				continue;
			}
			break;
		}

		// 이름
		while (run) {
			System.out.print("이름 : ");
			ct.setName(sc.next());
			int i = 0;
			for (i = 0; i < ct.getName().length(); i++) {
				char a = ct.getName().charAt(i);
				if ((a >= 'a' && a <= 'z') || (a >= 'A' && a <= 'Z')) {
					break;
				}
			}
			if (i == ct.getName().length()) {
				break;
			} else {
				System.out.println("[한글만 입력해주세요.]");
			}
		}

		// 생년월일
		while (run) {
			System.out.print("생년월일(6자리): ");
			ct.setBirth(sc.next());
			// 아스키코드로 숫자 유효성검사
			int i = 0;
			for (i = 0; i < ct.getBirth().length(); i++) {
				char a = ct.getBirth().charAt(i);
				if (a <= 48 || a >= 57) {
					break;
				}
			}
			// 생년월일 6자리 입력
			if (i == ct.getBirth().length()) {
				if (ct.getBirth().length() != 6) {
					System.out.println("[생년월일은 6자리로 입력하세요.]");
					continue;
				}
				break;
			} else {
				System.out.println("[숫자만 입력해주세요.]");
			}
		}

		// 연락처
		while (run) {
			String b, c = null;
			System.out.print("연락처(010제외 8자리 입력): ");
			ct.setTel(sc.next());
			// 아스키코드로 숫자 유효성검사
			int i = 0;
			for (i = 0; i < ct.getTel().length(); i++) {
				char a = ct.getTel().charAt(i);
				if (a <= 48 || a >= 57) {
					break;
				}
			}
			// 연락처 8자리 입력
			if (i == ct.getTel().length()) { // for문 빠져나오도록 입력
				if (ct.getTel().length() != 8) {
					System.out.println("[연락처 8자리를 입력하세요.]");
					continue;
				}
				if (!cc.chktel(ct.getTel())) {
					// 연락처 유효성 검사
					b = ct.getTel().substring(0, 4);
					c = ct.getTel().substring(4, 8);
					ct.setTel("010-" + b + "-" + c);
					break;
				} else {
					System.out.println("[중복된 핸드폰 번호입니다.]");
				}
			} else {
				System.out.println("[숫자만 입력해주세요.]");
			}
		}
		System.out.println("휴대폰 번호 : " + ct.getTel());

		ct = new Customer(1, ct.getId(), ct.getPw(), ct.getName(), ct.getBirth(), ct.getTel());
		Session.map.put(id, ct);
		
		cc.join(ct);

	}

	// 로그인
	public static void login() {
		while (run) {
			try {
				System.out.println("|로그인|");
				System.out.print("ID : ");
				ct.setId(sc.next());
				System.out.print("PW : ");
				ct.setPw(sc.next());
				if (cc.login(ct) != false) {
					System.out.println("'" + ct.getName() + "'" + "님 환영합니다.");
					System.out.println("[로그인에 성공하였습니다.] \n");
					id2 = ct.getId(); // id값 넘기기
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
		Menu.Menuchk(); // 메뉴체크 이동
	}
}
