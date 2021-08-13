package booking;

public class StringShift {
	public static String getShiftedString(String s, int leftShifts,
			int rightShifts) {
		int l = s.length();
		int rs = rightShifts % l - leftShifts % l;
		rs = rs >= 0 ? rs : l + rs;

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < l; i++) {
			sb.append(s.charAt((i + rs) % l));
		}
		return sb.toString();
	}

}
