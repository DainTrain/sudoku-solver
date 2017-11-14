public class Test {
	public static void main(String[] args) {
		int[] x = new int[20];
		x[9] = 1;
		x[10] = 2;
		x[11] = 3;
		for (int i=0; i<x.length; i++) {
			System.out.println(x[i]);
		}
		boolean[] z = new boolean[20];
		z[0] = true;
		z[1] = true;
		z[2] = true;
		for (int i=0; i<x.length; i++) {
			System.out.println(z[i]);
		}
		Integer[] y = new Integer[10];
		for (int i=0; i<y.length; i++) {
			System.out.println(y[i]);
		}
	}
}