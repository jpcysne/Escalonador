
public class InsertionSort {


	public static void main(String[] args) {

		int[] numeros = new int[5];

		numeros[0] = 3;
		numeros[1] = 5;
		numeros[2] = 4;
		numeros[3] = 2;
		numeros[4] = 1;

		for (int num : insertionSort(numeros)) {
			System.out.print(num + " - ");
		}

	}

	public static int[] insertionSort(int[] numeros) {

		int i, j, eleito;
		
		for (i = 1; i < numeros.length; i++) {
			
			eleito = numeros[i];
			
			j = i;
			
			while ((j > 0) && (numeros[j - 1] > eleito)) {
				numeros[j] = numeros[j - 1];
				j = j - 1;
			}
			numeros[j] = eleito;
		}
		
		return numeros;
	}
}