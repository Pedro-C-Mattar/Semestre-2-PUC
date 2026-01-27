import java.util.*;

public class SortSort {
    public static void swap(int v[], int a, int b){
        int temp = v[a];
        v[a] = v[b];
        v[b] = temp;
    }
    
    public static void sort(int array[], int n, int m) {
		for (int i = 0; i < (n - 1); i++) {
			int menor = i;
			for (int j = (i + 1); j < n; j++){
				if ((array[j] % m) < (array[menor] % m)){
					menor = j;
				}
                if((array[j] % m) == (array[menor] % m)){
                    if ((array[j] % 2 != 0) && (array[menor] % 2 == 0)) {
                        menor = j;
                    } 
                    else if ((array[j] % 2 != 0) && (array[menor] % 2 != 0)) {
                        if (array[j] > array[menor]) {
                            menor = j;
                        }
                    } 
                    else if ((array[j] % 2 == 0) && (array[menor] % 2 == 0)) {

                        if (array[j] < array[menor]) {
                            menor = j;
                        }
                    }
                }
                
			}
			swap(array, menor, i);
		}
	}
    
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        int n = 1;
        int m = 1;

        while(n != 0 && m != 0){
            
            n = sc.nextInt();
            m = sc.nextInt();
            System.out.println(n + " " + m);

            if(n != 0 && m != 0){
                int array[] = new int[n];

                for(int i = 0; i < n; i++){
                    array[i] = sc.nextInt();
                }

                sort(array, n, m);

                for(int i = 0; i < n; i++){
                    System.out.println(array[i]);
                }
            }
            
        }

        sc.close();

    }
}
