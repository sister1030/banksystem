package bank;


public class Account {

	private int ano; // 계좌번호
	private String account; // 계좌
	private String accountpw; // 계좌 비밀번호
	private int balance; // 잔고
	private int cno; // 계좌번호

	// 기본 생성자
	public Account() {

	}

	public Account(int ano, String account, String accountpw, int balance, int cno) {
		this.ano = ano;
		this.account = account;
		this.accountpw = accountpw;
		this.balance = balance;
		this.cno = cno;
		
	}

	public String getAccount() {
		return account;
	}

	public String getAccountpw() {
		return accountpw;
	}

	public int getBalance() {
		return balance;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public void setAccountpw(String accountpw) {
		this.accountpw = accountpw;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	public int getAno() {
		return ano;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}

	public int getCno() {
		return cno;
	}
	
	public void setCno(int cno) {
		this.cno = cno;
	}
	
	@Override
	public String toString() {
		return    "┃NO :" + ano +"\n"
				+ "┃계좌번호 :" + account + "\n" 
				+ "┃계좌비밀번호 :" + accountpw + "\n" 
				+ "┃잔고 :" + balance + "\n";
	}

}
