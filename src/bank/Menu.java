package bank;

import java.util.Scanner;

public class Menu {
	static Scanner sc = new Scanner(System.in); // 입력
	static boolean run = true; // 실행
	static Customer ct = new Customer();
	static CheckAccount ca = new CheckAccount();
	static Account ac = new Account();
	static Manager mg;
	static CheckCustomer cc;
	static CheckManager cm;
	static String account2;

	public Menu() {

		while (true) {
			System.out.println("**BANK MAIN**");
			System.out.println("------------------------");
			System.out.println("1.회원 | 2.관리자 |0.종료");
			System.out.println("------------------------");
			System.out.print("번호선택 : ");
			int num = sc.nextInt(); // 변수 입력
			switch (num) { // 변수 선택
			case 1:
				run = true;
				while (run) {
					System.out.println("**CUSTMOER MAIN**");
					System.out.println("------------------------");
					System.out.println("1.회원가입 | 2.로그인 | 0.종료");
					System.out.println("------------------------");
					System.out.print("번호선택 : ");
					num = sc.nextInt(); // 변수 입력
					switch (num) { // 변수 선택
					case 1:
						CustomerHandler.join();
						break;
					case 2:
						CustomerHandler.login();
						break;
					case 0:
						run = false;
						System.out.println("***coming back to the main***");
						break;
					default:
						System.out.println("알수없는 입력입니다.");
					}
				}
				break;
			case 2:
				run = true;
				ManagerHandler.managerconnect();
				while (run) {
					System.out.println("**MANAGER MAIN**");
					System.out.println("------------------");
					System.out.println("1.회원가입 | 2.로그인 | 0.종료");
					System.out.println("------------------");
					System.out.print("번호선택 : ");
					num = sc.nextInt(); // 변수 입력
					switch (num) { // 변수 선택
					case 1:
						ManagerHandler.join();
						break;
					case 2:
						ManagerHandler.login();
						break;
					case 0:
						run = false;
						System.out.println("***coming back to the main***");
						break;
					default:
						System.out.println("알수없는 입력입니다.");
					}
				}
				break;
			case 0:
				System.out.println("***프로그램 종료***");
				System.exit(0);
				break;
			default:
				System.out.println("알수없는 입력입니다.");
			}
		}
	}

	public static void Menuchk() {
		String id = CustomerHandler.id2; // 아이디 넘기기
		while (run) {
			try {
				if (Session.get1(id) == null) {
					System.out.println("[로그인 후 이용하세요.]");
					ManagerHandler.login();
				} else {
					ca.showAccount(Session.get1(id).getCno());
					System.out.print("계좌번호 입력(계좌가 없을 경우 아무번호나 입력해주세요.):");
					String account = sc.next();
					// 계좌 확인;
					if (ca.chkac(account)) {
						System.out.println("                            ["+Session.get1(id).getName()+"님의 계좌정보]");
						System.out.println("========================================================================");
						System.out.println("reg.No\t  계좌번호 \t\t계좌비밀번호\t\t잔고\t\t고객번호");
						System.out.println("========================================================================");
						ca.getAccount(account);
						System.out.println("========================================================================");
						account2 = account; // account값 넘기기
						System.out.println("*****'" + Session.get1(id).getName() + "'" + "님 은행에 오신 것을 환영합니다.*****");
						AccountMenu();
					} else {
						System.out.println("[" + Session.get1(id).getName() + "]님의 정보가 없거나 일치하지 않습니다.");
						System.out.println("메뉴를 선택하세요>> |1.계좌개설 | 2.다시입력 | 3.나가기 |");
						System.out.print("번호 입력 :");
						int num = sc.nextInt(); // 변수 입력
						switch (num) { // 변수 선택
						case 1:
							System.out.println("계좌개설로 이동>>");
							AccountHandler.addaccount();
							break;
						case 2:
							continue;
						case 3:
							run = false;
							break;
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void AccountMenu() {
		while (run) {
			System.out.println("**MENU(ACCOUNT)**");
			System.out.println("------------------------");
			System.out.println("1.계좌개설 | 2.입금 | 3.출금 | 4.거래내역 | 5.회원정보수정 | 0.로그아웃");
			System.out.println("------------------------");
			System.out.print("번호선택 : ");
			int num = sc.nextInt(); // 변수 입력
			switch (num) { // 변수 선택
			case 1:
				AccountHandler.addaccount();
				break;
			case 2:
				AccountHandler.deposit();
				break;
			case 3:
				AccountHandler.withdraw();
				break;
			case 4:
				AccountHandler.transInfo();
				break;
			case 5:
				AccountHandler.updateInfo();
				break;
			case 0:
				AccountHandler.logout();
				run = false;
				System.out.println("***coming back to the main***");
				break;
			default:
				System.out.println("알수없는 입력입니다.");
			}

		}
	}

	public static void ManagerMenu() {
		String mid = ManagerHandler.mid2; // 아이디 넘기기
		while (run) {
			if (Session.get2(mid) == null) {
				System.out.println("[로그인 후 이용하세요.]");
				ManagerHandler.login();
			} else {
				System.out.println("**MENU(MANAGER)**");
				System.out.println("------------------------");
				System.out.println("1.회원내역 | 2.회원삭제 | 0.로그아웃");
				System.out.println("------------------------");
				System.out.print("번호선택 : ");
				int num = sc.nextInt(); // 변수 입력
				switch (num) { // 변수 선택
				case 1:
					ManagerHandler.showCustomerList();
					break;
				case 2:
					ManagerHandler.deletecustomer();
					break;
				case 0:
					ManagerHandler.managerlogout();
					run = false;
					System.out.println("***coming back to the main***");
					break;
				default:
					System.out.println("알수없는 입력입니다.");
				}
			}
		}

	}
}
