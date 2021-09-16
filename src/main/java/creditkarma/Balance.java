package creditkarma;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Balance {
	// Given an account defined as:
	//
	// Account
	// balance (decimal number)
	// name: (string)
	// accountType (e.g. credit card, checking, savings)
	// openedDate: (time stamp)
	//
	// for example
	// balance: 1000.00
	// name : Chase Freedom
	// accountType : credit card
	// opened Date: 6/19/2004
	//
	// Compare 2 sets of accounts for a user - one set from the current month,
	// and one set from the prior month, e.g.:

	// previous: [ { "balance":2000.0, "name":"Chase Freedom",
	// "type":"credit_card", "openedDate":1111680799 }, { "balance":100.0,
	// "name":"Citibank", "type":"credit_card", "openedDate":1000680799 } ]
	// current: [ { "balance":1000.0, "name":"Chase Freedom",
	// "type":"credit_card", "openedDate":1111680799 }, { "balance":500.0,
	// "name":"American Express", "type":"credit_card", "openedDate":1222680799
	// } ]

	// * print out "<name>: <balance change>" for accounts that are in both
	// months, e.g. "Chase Freedom: -1000"
	// * print out "<name>: added" for those that are in the current month but
	// not the previous month, e.g. "American Express: added"
	// * print out "<name>: deleted" for those that are in the previous month
	// but not the current month, e.g. "Citibank: deleted"

	/*
	 * Click `Run` to execute the snippet below!
	 */

	/*
	 * To execute Java, please define "static void main" on a class named
	 * Solution.
	 *
	 * If you need more classes, simply define them inline.
	 */

	class Account {
		double balance;
		String name;
		String accountType;
		long timestamp;
	}

	public String diff(List<Account> prev, List<Account> cur) {
		StringBuilder sb = new StringBuilder();
		Map<String, Account> map = new HashMap<>();
		for (Account a : prev) {
			map.put(a.name + a.timestamp, a);
		}
		for (Account a : cur) {
			if (map.containsKey(a.name + a.timestamp)) {
				sb.append(a.name).append(": ");
				sb.append(a.balance - map.get(a.name + a.timestamp).balance);
				sb.append("\n");
				map.remove(a.name + a.timestamp);
			} else {
				sb.append(a.name).append(": added");
				sb.append("\n");
			}

		}
		for (Account a : map.values()) {
			sb.append(a.name).append(": deleted");
			sb.append("\n");

		}
		return sb.toString();
	}

	// previous: [ { "balance":2000.0, "name":"Chase Freedom",
	// "type":"credit_card", "openedDate":1111680799 }, { "balance":100.0,
	// "name":"Citibank", "type":"credit_card", "openedDate":1000680799 } ]
	// current: [ { "balance":1000.0, "name":"Chase Freedom",
	// "type":"credit_card", "openedDate":1111680799 }, { "balance":500.0,
	// "name":"American Express", "type":"credit_card", "openedDate":1222680799
	// } ]

	public static void main(String[] args) {
		Balance s = new Balance();

		Account a1 = s.new Account();
		a1.balance = 2000.0;
		a1.name = "Chase Freedom";
		a1.accountType = "credit_card";
		a1.timestamp = 1111680799;

		Account a2 = s.new Account();
		a2.balance = 100.0;
		a2.name = "Citibank";
		a2.accountType = "credit_card";
		a2.timestamp = 1000680799;

		List<Account> prev = List.of(a1, a2);

		Account b1 = s.new Account();
		b1.balance = 1000.0;
		b1.name = "Chase Freedom";
		b1.accountType = "credit_card";
		b1.timestamp = 1111680799;

		Account b2 = s.new Account();
		b2.balance = 500.0;
		b2.name = "American Express";
		b2.accountType = "credit_card";
		b2.timestamp = 1222680799;

		List<Account> cur = List.of(b1, b2);

		System.out.println(s.diff(prev, cur));
	}

}
