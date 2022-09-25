package bank;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class AccountHandler {
	static boolean run = true; // 실행
	static Account ac; // DB를 dto에 저장
	static Customer ct;
	static CheckAccount ca = new CheckAccount();
	static CheckCustomer cc = new CheckCustomer();
	static Transaction ts;
	static CheckManager cm;
	static Scanner sc = new Scanner(System.in);
	static Random r = new Random(); // 계좌번호 랜덤 생성
	static String id = CustomerHandler.id2; // 로그인에서 아이디 값 넘김
	static String account = Menu.account2; // 계좌인증에서 계좌번호 넘김
	static int commission, money = 0;
	static List<Transaction> tslist = new ArrayList<>();

	public AccountHandler() {
	}

	// 계좌 개설
	public static void addaccount() {
		ac = new Account();
		// 계좌번호
		// 9자리 랜덤 계좌번호 생성
		while (run) {
			ac.setAccount(r.nextInt(999999999) + 1 + "");
			for (int i = 0; i < 9 - ac.getAccount().length(); i++) {
				break;
			}
			if (ac.getAccount().length() == 9) {
				String e = ac.getAccount().substring(0, 3);
				String f = ac.getAccount().substring(3, 9);
				ac.setAccount("1002-" + e + "-" + f);
				// 계좌번호 유효성 검사
				if (!ca.chkac(ac.getAccount())) {
					break;
				} else {
					System.out.println("[중복된 계좌 번호입니다.]");
					continue;
				}
			}
		}
		System.out.println(Session.get1(id).getName() + "님의 계좌번호 : " + ac.getAccount());

		// 계좌 비밀번호
		while (run) {
			System.out.print("ACCOUNT PW : ");
			ac.setAccountpw(sc.next());
			// 아스키코드로 숫자 유효성검사
			int i = 0;
			for (i = 0; i < ac.getAccountpw().length(); i++) {
				char a = ac.getAccountpw().charAt(i);
				if (a <= 48 || a >= 57) {
					break;
				}
			}
			// 비밀번호 유효성 검사(4자리/공백/숫자/확인)
			if (i == ac.getAccountpw().length()) {
				if (ac.getAccountpw().length() != 4) {
					System.out.println("[패스워드는 4자리로 입력해주세요.]");
					continue;
				}
				System.out.print("ACCOUNT PW 확인 : ");
				String pwchk = sc.next();
				if (ac.getAccountpw().trim().isEmpty() || pwchk.trim().isEmpty()) {
					System.out.println("[패스워드 또는 패스워드 확인이 공백입니다.]");
					continue;
				} else if (!ac.getAccountpw().equals(pwchk)) {
					System.out.println("[패스워드와 패스워드 확인이 일치하지 않습니다.]");
					continue;
				}
			} else {
				System.out.println("[숫자만 입력해주세요.]");
				continue;
			}
			break;
		}
		// 잔고
		while (run) {
			System.out.print("입금액 :");
			ac.setBalance(sc.nextInt());
			try {
				if (ac.getBalance() < 10000) {
					System.out.println("[10,000원이상의 금액을 입금하셔야합니다.]");
					continue;
				}
				break;
			} catch (Exception e) {
				System.out.println("다시 입력해 주세요.");
				continue;
			}
		}

		ac = new Account(1, ac.getAccount(), ac.getAccountpw(), ac.getBalance(), ac.getCno());
		Session.map3.put(ac.getAccount(), ac);

		int key = ca.join(ac);
		if (key != 0) {
			System.out.println("[" + Session.get1(id).getName() + "]님,계좌개설에 성공하였습니다.");
			Menu.Menuchk();
		} else {
			System.out.println("[계좌개설 실패 : 다시 시도해 주세요.]");
		}
	}

	public static void deposit() {
		ac = Session.get3(account);
		int amount = 0;
		while (run) {
			try {
				System.out.println("[계좌 비밀번호 인증]");
				System.out.println("계좌번호 : " + account);
				System.out.print("계좌비밀번호 : ");
				String accountpw = sc.next();
				// System.out.println(Session.get3(account));
				// 계좌번호와 비밀번호 확인
				if (Session.get3(account).getAccountpw().equals(accountpw)) {
					System.out.println("[인증 성공]");
				} else {
					System.out.println("[인증 실패]");
					continue;
				}
				// 입금
				try {
					System.out.print("입금액 : ");
					// 해당 객체가 Member의 자식 객체면 재정의된 메소드로 실행된다.
					amount = sc.nextInt();
					int balance = Session.get3(account).getBalance();
					balance += amount;
					ac.setBalance(balance);
					savelog(true, ac, amount, 0);
					ca.updateAccount(account, balance); // 잔고수정
					System.out.println("입금 되었습니다.");
					break;
				} catch (Exception e) {
					System.out.println("[입금 실패 : 정보 오류]");
					e.printStackTrace();
					continue;
				}
			} catch (InputMismatchException e) {
				System.out.println("[다시 입력해주세요.]");
			}

		}
		// 입금 영수증
		System.out.println("");
		System.out.println("  ━━━━* receipt *━━━━");
		System.out.println("   계좌번호 : " + Session.get3(account).getAccount());
		System.out.println("      성함 : " + Session.get1(id).getName());
		System.out.println("      입금액 : " + amount + "원");
		System.out.println("      잔액 : " + ac.getBalance() + "원");
		System.out.println("  ━━━━━━━━━━━━━━━━━━━\n");
		Menu.AccountMenu();
	}

	// 출금
	public static void withdraw() {
		ac = Session.get3(account);
		int amount = 0;
		while (run) {
			try {
				System.out.println("[계좌 비밀번호 인증]");
				System.out.println("계좌번호 : " + account);
				System.out.print("비밀번호 : ");
				String accountpw = sc.next();
				// 계좌번호와 비밀번호 확인;
				if (Session.get3(account).getAccountpw().equals(accountpw)) {
					System.out.println("[인증 성공]");
				} else {
					System.out.println("[인증 실패]");
					continue;
				}
				// 출금
				try {
					System.out.print("출금액 : ");
					amount = sc.nextInt();
					// 출금액이 음수이거나 잔고보다 많으면 출금 실패
					if (amount < 0 || amount > Session.get3(account).getBalance()) {
						System.out.println("[출금 실패 : 수수료를 합산한 잔액이 부족합니다.]");
						continue;
					}
					// 잔고가 0이면 출금 실패
					if (Session.get3(account).getBalance() <= 0) {
						System.out.println("[출금 실패 : 다시 입력하세요.]");
						continue;
					}
					commission = (int) (amount * 0.1); // 수수료10%
					money = amount + commission; // 잔고 + 수수료 = 출금액
					int balance = Session.get3(account).getBalance();
					balance -= money;
					ac.setBalance(balance);
					savelog(false, ac, amount, commission);
					ca.updateAccount(account, balance); // 잔고수정
					System.out.println("출금 되었습니다.");
					break;
				} catch (Exception e) {
					System.out.println("[출금 실패 : 정보 오류]");
					e.printStackTrace();
					continue;
				}
			} catch (InputMismatchException e) {
				System.out.println("[다시 입력해주세요.]");
			}
		}
		// 출금 영수증
		System.out.println("");
		System.out.println("  ━━━━* receipt *━━━━");
		System.out.println("   계좌번호 : " + Session.get3(account).getAccount());
		System.out.println("      성함 : " + Session.get1(id).getName());
		System.out.println("      출금액 : " + amount + "원");
		System.out.println("      수수료 : " + commission + "원");
		System.out.println("      잔액 : " + ac.getBalance() + "원");
		System.out.println("  ━━━━━━━━━━━━━━━━━━━\n");
		Menu.AccountMenu();
	}

	// 거래내역
	public static void transInfo() {
		System.out.println("[" + Session.get1(id).getName() + "님의 거래내역]");
		displaylog();
	}

	// 회원정보 조회 및 변경
	public static void updateInfo() {
		boolean run = true; // 실행
		String id = CustomerHandler.id2;
		while (run) { // 실행하는 동안(while)
			// InputMismatchException 예외처리
			try {
				System.out.println("**메뉴 선택*");
				System.out.println("1.회원정보 조회 | 2.비밀번호 변경 | 3.연락처 변경 | 4.계좌삭제 | 0.종료 ");
				System.out.print("번호선택 : ");
				int num = sc.nextInt(); // 변수 입력

				switch (num) { // 변수 선택
				case 1:
					cc = new CheckCustomer();
					ca = new CheckAccount();
					while (run) {
						cc.showCustomer(Session.get1(id).getCno());
						ca.showAccount(Session.get1(id).getCno());
						break;
					}
					break;
				case 2:
					cc.showCustomer(Session.get1(id).getCno());
					while (run) {
						// 회원정보 출력
						System.out.println("새로운 패스워드를 입력하세요.");
						System.out.print("PW : ");
						String pw = sc.next();
						// 아스키코드로 숫자 유효성검사
						int i = 0;
						for (i = 0; i < pw.length(); i++) {
							char a = pw.charAt(i);
							if (a <= 48 || a >= 57) {
								break;
							}
						}
						if (i == pw.length()) {
							if (i == pw.length()) {
								if (pw.length() != 4) {
									System.out.println("[패스워드는 4자리로 입력해주세요.]");
									continue;
								}
							}
							System.out.print("PW 확인 : ");
							String pwchk = sc.next();
							if (pw.trim().isEmpty() || pwchk.trim().isEmpty()) {
								System.out.println("[패스워드 또는 패스워드 확인이 공백입니다.]");
								continue;
							} else if (!pw.equals(pwchk)) {
								System.out.println("[패스워드와 패스워드 확인이 일치하지 않습니다.]");
								continue;
							}
						} else {
							System.out.println("[숫자만 입력해주세요.]");
							continue;
						}
						// 비밀번호 변경
						ca.updatepw(id, pw);
						break;
					}
					continue;
				case 3:
					cc.showCustomer(Session.get1(id).getCno());
					while (run) {
						System.out.println("새로운 연락처를 입력하세요.");
						System.out.print("연락처(010제외 8자리 입력): ");
						String tel = sc.next();
						// 아스키코드로 숫자 유효성검사
						int i = 0;
						for (i = 0; i < tel.length(); i++) {
							char a = tel.charAt(i);
							if (a <= 48 || a >= 57) {
								break;
							}
						}
						// 연락처 8자리 입력
						if (i == tel.length()) { // for문 빠져나오도록 입력
							if (tel.length() != 8) {
								System.out.println("[연락처 8자리를 입력하세요.]");
								continue;
							}
							// 연락처 유효성 검사
							if (!cc.chktel(tel)) {
								String b = tel.substring(0, 4);
								String c = tel.substring(4, 8);
								tel = "010-" + b + "-" + c;
							} else {
								System.out.println("[중복된 핸드폰 번호입니다.]");
								continue;
							}
						} else {
							System.out.println("[숫자만 입력해주세요.]");
							continue;
						}
						// 휴대폰 번호 변경
						ca.updatetel(id, tel);
						break;
					}
					continue;
				case 4:
					while (run) {
						ca.showAccount(Session.get1(id).getCno());
						System.out.print("삭제할 계좌 번호 : ");
						String account = sc.next();
						if (account != null) {
							System.out.println("해당 회원의 정보를 삭제하시겠습니까?(Y/N) : ");
							String input = sc.next();
							if (input.equalsIgnoreCase("y")) {
								boolean r = ca.deleteAccount(account);
								if (r) {
									System.out.println("[" + Session.get1(id).getId() + "]회원의 계좌정보가 정상적으로 삭제되었습니다.");
									break;
								} else {
									System.out.println("회원의 계좌정보가 정상적으로 삭제 되지 않았습니다.");
									continue;
								}
							} else {
								System.out.println("삭제를 취소하였습니다.");
								continue;
							}
						} else {
							System.out.println("입력하신 계좌번호에 해당하는 회원이 존재하지 않습니다.");
							continue;
						}

					}
					continue;
				case 0:
					run = false;
					break;
				default:
					System.out.println("알수없는 입력입니다.");
				}
			} catch (InputMismatchException e) {
				System.out.println("[ERROR : 정수만 입력 가능합니다. 다시시작하세요]");
				System.exit(0);
			}
		}
	}

	// 거래내역 저장
	public static void savelog(boolean transType, Account ac, int amount, int commission) {
		// List<Transaction> tslist = new ArrayList<>();
		DataSource ds = new DataSource();
		String date = ds.getYear() + ds.getMonth() + ds.getDate() + " " + ds.getDay() + " " + ds.getCurTime();
		String transactionTime = date;
		if (ac != null) {
			ts = new Transaction(transType, amount, ac.getBalance(), commission, transactionTime);
			Session.map4.put(account, tslist);
			Session.map4.get(account).add(ts);
		}
	}

	// 거래내역 출력
	public static void displaylog() {
		try {
			for (Transaction ts : tslist) {
				if (ts.isTransType() == true) {
					System.out.println("|입금액 :" + ts.getAmount() + "원\t" + "|잔고 :" + ts.getBalance() + "원\t" + "|수수료 :"
							+ "없음" + "\t" + "|거래시간 :" + ts.getTransactionTime());
				} else {
					System.out.println("|출금액 :" + ts.getAmount() + "원\t" + "|잔고 :" + ts.getBalance() + "원\t" + "|수수료 :"
							+ ts.getCommission() + "원\t" + "|거래시간 :" + ts.getTransactionTime());
				}
			}
		} catch (NullPointerException e) {
			System.out.println("거래내역이 존재하지 않습니다.");
		}
	}

	// 로그아웃
	public static void logout() {
		Session.map.put(id, null);
		System.out.println("[로그아웃되었습니다.]");
	}
}
