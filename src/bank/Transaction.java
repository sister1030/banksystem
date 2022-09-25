package bank;

public class Transaction {


	private boolean transType; // 입금 or 출금	
	private int amount; // 거래금액	
	private int balance; // 거래일자	
	private int commission; // 거래일자	
	private String transactionTime; // 거래시간
	
	public Transaction() {}
	
	
	public Transaction(boolean transType, int amount, int balance, int commission, String transactionTime) {
		this.transType = transType;
		this.amount = amount;
		this.balance = balance;
		this.commission = commission;
		this.transactionTime = transactionTime;
	}
	
	public boolean isTransType() {
		return transType;
	}


	public void setTransType(boolean transType) {
		this.transType = transType;
	}

	public int getAmount() {
		return amount;
	}


	public void setAmount(int amount) {
		this.amount = amount;
	}


	public int getBalance() {
		return balance;
	}


	public int getCommission() {
		return commission;
	}


	public String getTransactionTime() {
		return transactionTime;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}


	public void setCommission(int commission) {
		this.commission = commission;
	}


	public void setTransactionTime(String transactionTime) {
		this.transactionTime = transactionTime;
	}
	
}

