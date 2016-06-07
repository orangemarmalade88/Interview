import java.util.Random;

public class Shuffler {
	void shuffle(int[] array) {
		Random r = new Random();
		for (int i = array.length; i > 0; i--) {
			int index = r.nextInt(i);
			swap(array, index, i - 1);
		}
	}

	void swap(int[] array, int i, int j) {
		int temp = array[i];
		array[i] = array[j];
		array[j] = temp;
	}

	public static void main(String[] args) {
		int[] a = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
		new Shuffler().shuffle(a);
		for (int b : a) {
			System.out.println(b);
		}
	}
}
