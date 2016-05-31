package twosigma;

/*

 There are N students in a class. Some of them are friends, while some are not. Their friendship is transitive in nature, i.e.,
 if A is friend of B and B is friend of C, then A is also friend of C. A friend circle is a group of students who are directly
 or indirectly friends.

 You have to complete a function in friendCircles(char[][] friends) which returns the number of friend circles in the class. Its
 argument, friends, is a NxN matrix which consists characters 'Y' or 'N': if friend[i][j] = 'Y', then ith student and jth student
 are friends with each other, otherwise not. You have to return the total number of friend circles in the class.

 Constraints:
 - 1 <= N <= 300
 - Each element of matrix friends will be 'Y' or 'N'
 - Number of rows and columns will be equal in friends
 - friends[i][i] = 'Y', which 0 <= i < N
 - friends[i][j] = friends[j][i], which 0 <= i < j < N

 Input format:
 The function "friendCircles" contains a 2d string array "friends" as its argument

 Output format:
 Return a single integer denoting number of different circles that are present in the class.

 Sample Input:
 YYNN
 YYYN
 NYYN
 NNNY

 Sample Output:
 2

 */

public class FriendCircles {

	public int friendCircles(String[] friends) {
		int n = friends.length;

		// Pre-process the input, isFriend[i][j] means i and j are friends
		boolean[][] isFriend = new boolean[n][n];
		for (int i = 0; i < n; i++) {
			String s = friends[i];
			for (int j = 0; j < n; j++) {
				char c = s.charAt(j);
				if (c == 'Y')
					isFriend[i][j] = true;
			}
		}

		// visit every one unless they already formed a circle with someone else
		boolean[] visited = new boolean[n];
		int count = 0;
		for (int i = 0; i < n; i++) {
			if (!visited[i]) {
				dfs(isFriend, visited, i);
				count++;
			}
		}
		return count;
	}

	private void dfs(boolean[][] isFriend, boolean[] visited, int i) {
		visited[i] = true;
		boolean[] friendsList = isFriend[i];
		for (int j = 0; j < visited.length; j++) {
			if (friendsList[j] && !visited[j])
				dfs(isFriend, visited, j);
		}
	}
}
