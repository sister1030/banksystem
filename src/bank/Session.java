package bank;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Session {
	static int cno;
	static String pw;
	static String name;
	static String birth;
	static String tel;
	static int mno;
	static String mpw;
	static int ano;
	static String accountpw;
	static int balance;	

	public Session() {
	}

	// 맵 생성
	static HashMap<String, Customer> map = new HashMap<String, Customer>(); // 고객
	static HashMap<String, Manager> map2 = new HashMap<String, Manager>(); // 관리자
	static HashMap<String, Account> map3 = new HashMap<String, Account>(); // 계좌
	static Map<String, List<Transaction>> map4 = new TreeMap<String, List<Transaction>>(); // 거래내역
	
	// 고객
	public static void put(String id, Customer ct) {
		map.put(id, new Customer(ct.getCno(), ct.getId(), ct.getPw(), ct.getName(), ct.getBirth(), ct.getTel())); // 맵에 저장
	}

	public static Customer get1(String id) {
		return map.get(id);
	}

	// 관리자
	public static void put(String mid, Manager mg) {
		map2.put(mid, new Manager(mg.getMno(), mg.getMid(), mg.getMpw())); // 맵에 저장
	}

	// 키값 확인
	public static Manager get2(String mid) {
		return map2.get(mid);
	}

	// 계좌
	public static void put(String account, Account ac) {
		map3.put(account, new Account(ac.getAno(), ac.getAccount(), ac.getAccountpw(), ac.getBalance(), ac.getCno())); // 맵에 저장
	}

	// 키값 확인
	public static Account get3(String account) {
		return map3.get(account);
	}
	
	// 거래내역
	public static void put(String account, List<Transaction> t) {
	map4.put(account, new ArrayList<Transaction>()); // 맵에 저장
	}

}
