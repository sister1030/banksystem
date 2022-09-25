package bank;

public class Manager {

	private int mno; // 관리자 번호
	private String mid; // 아이디
	private String mpw; // 비밀번호

	// 생성자
	public Manager() {

	}

	public Manager(int mno, String mid, String mpw) {
		super();
		this.mno = mno;
		this.mid = mid;
		this.mpw = mpw;
	}

	public int getMno() {
		return mno;
	}

	public String getMid() {
		return mid;
	}

	public String getMpw() {
		return mpw;
	}

	public void setMno(int mno) {
		this.mno = mno;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public void setMpw(String mpw) {
		this.mpw = mpw;
	}
	

	@Override
	public String toString() {
		return  "┃NO :" + mno +" "+ "┃ID :" +mid + " " + "┃PW :" + mpw + " ";
	}

}
