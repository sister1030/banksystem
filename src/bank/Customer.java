package bank;

public class Customer {

	private int cno; // 고객번호
	private String id; // 아이디
	private String pw; // 비밀번호
	private String name; // 이름
	private String birth; // 생년월일
	private String tel; // 연락처

	// 생성자
	public Customer() {
		super();
	}

	public Customer(int cno, String id, String pw, String name, String birth, String tel) {
		super();
		this.cno = cno;
		this.id = id;
		this.pw = pw;
		this.name = name;
		this.birth = birth;
		this.tel = tel;
	}

	public int getCno() {
		return cno;
	}

	public String getId() {
		return id;
	}

	public String getPw() {
		return pw;
	}

	public String getName() {
		return name;
	}

	public String getBirth() {
		return birth;
	}

	public String getTel() {
		return tel;
	}

	public void setCno(int cno) {
		this.cno = cno;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	@Override
	public String toString() {
		return  "┃NO :" + cno +"\t"+ "┃ID :" +id + "\t" + "┃PW :" + pw + "\t" 
				+ "┃이름 :"+ name + "\t" + "┃생년월일 :" + birth + "\t" + "┃연락처 :" + tel + "\t";
	}
	
}
