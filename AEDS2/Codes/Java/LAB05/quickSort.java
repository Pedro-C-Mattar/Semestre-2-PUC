import java.util.*;

public class quickSort {
    public static void swap(int[] array, int a, int b){
        int tmp = array[a];
        array[a] = array[b];
        array[b] = tmp;
    }
    
    public static void quicksortMeio(Geracao g, int[] arr, int esq, int dir) {
        int i = esq, j = dir;
        int pivo = arr[(dir+esq)/2];

        while (i <= j) {
            g.comparacoes++;

            while (arr[i] < pivo) {
                g.comparacoes++;
                i++;
            }
            g.comparacoes++;

            while (arr[j] > pivo) {
                g.comparacoes++;
                j--;
            }
            g.comparacoes++;

            g.comparacoes++;
            if (i <= j) {
                swap(arr, i, j);
                g.movimentacoes += 3;
                i++;
                j--;
            }
        }
        g.comparacoes++;

        g.comparacoes++;
        if (esq < j)  quicksortMeio(g, arr, esq, j);
        g.comparacoes++;
        if (i < dir)  quicksortMeio(g, arr, i, dir);
    }

    public static void quicksortInicio(Geracao g, int[] arr, int esq, int dir) {
        int i = esq, j = dir;
        int pivo = arr[esq];

        while (i <= j) {
            g.comparacoes++;

            while (arr[i] < pivo) {
                g.comparacoes++;
                i++;
            }
            g.comparacoes++;

            while (arr[j] > pivo) {
                g.comparacoes++;
                j--;
            }
            g.comparacoes++;

            g.comparacoes++;
            if (i <= j) {
                swap(arr, i, j);
                g.movimentacoes += 3;
                i++;
                j--;
            }
        }
        g.comparacoes++;

        g.comparacoes++;
        if (esq < j)  quicksortInicio(g, arr, esq, j);
        g.comparacoes++;
        if (i < dir)  quicksortInicio(g, arr, i, dir);
    }

    public static void quicksortFim(Geracao g, int[] arr, int esq, int dir) {
        int i = esq, j = dir;
        int pivo = arr[dir];

        while (i <= j) {
            g.comparacoes++;

            while (arr[i] < pivo) {
                g.comparacoes++;
                i++;
            }
            g.comparacoes++;

            while (arr[j] > pivo) {
                g.comparacoes++;
                j--;
            }
            g.comparacoes++;

            g.comparacoes++;
            if (i <= j) {
                swap(arr, i, j);
                g.movimentacoes += 3;
                i++;
                j--;
            }
        }
        g.comparacoes++;

        g.comparacoes++;
        if (esq < j)  quicksortFim(g, arr, esq, j);
        g.comparacoes++;
        if (i < dir)  quicksortFim(g, arr, i, dir);
    }

    public static void quicksortAleatorio(Geracao g, int[] arr, int esq, int dir) {
        Random r = new Random();
        int i = esq, j = dir;
        int pivo = arr[Math.abs(r.nextInt((dir + 1) - esq) + esq)];

        while (i <= j) {
            g.comparacoes++;

            while (arr[i] < pivo) {
                g.comparacoes++;
                i++;
            }
            g.comparacoes++;

            while (arr[j] > pivo) {
                g.comparacoes++;
                j--;
            }
            g.comparacoes++;

            g.comparacoes++;
            if (i <= j) {
                swap(arr, i, j);
                g.movimentacoes += 3;
                i++;
                j--;
            }
        }
        g.comparacoes++;

        g.comparacoes++;
        if (esq < j)  quicksortAleatorio(g, arr, esq, j);
        g.comparacoes++;
        if (i < dir)  quicksortAleatorio(g, arr, i, dir);
    }

    public static void quicksortMediana(Geracao g, int[] arr, int esq, int dir) {
        int[] mediana = new int[3];
        mediana[0] = esq;
        mediana[1] = (dir + esq) / 2;
        mediana[2] = dir;

        int medianaValor;
        if ((mediana[0] > mediana[1] && mediana[0] < mediana[2]) || (mediana[0] < mediana[1] && mediana[0] > mediana[2])){
            medianaValor = mediana[0];
        }
        else if ((mediana[1] > mediana[0] && mediana[1] < mediana[2]) || (mediana[1] < mediana[0] && mediana[1] > mediana[2])) {
            medianaValor = mediana[1];
        }
        else{
            medianaValor = mediana[2];
        }

        int i = esq, j = dir;
        int pivo = arr[medianaValor];

        while (i <= j) {
            g.comparacoes++;
            
            while (arr[i] < pivo) {
                g.comparacoes++;
                i++;
            }
            g.comparacoes++;

            while (arr[j] > pivo) {
                g.comparacoes++;
                j--;
            }
            g.comparacoes++;

            g.comparacoes++;
            if (i <= j) {
                swap(arr, i, j);
                g.movimentacoes += 3;
                i++;
                j--;
            }
        }
        g.comparacoes++;

        g.comparacoes++;
        if (esq < j)  quicksortMediana(g, arr, esq, j);
        g.comparacoes++;
        if (i < dir)  quicksortMediana(g, arr, i, dir);
    }

    public static void main(String[] args){
        Geracao g = new Geracao(100);
        int e = 0;
        int d = (g.n - 1);

        g.crescente();
        //g.aleatorio();

        g.mostrar();

        double inicio = g.now();

        //quicksortMeio(g, g.array, e, d);
        //quicksortInicio(g, g.array, e, d);
        //quicksortFim(g, g.array, e, d);
        //quicksortAleatorio(g, g.array, e, d);
        quicksortMediana(g, g.array, e, d);

        double fim = g.now();
       
        System.out.println("-------------------------------------");
        System.out.println("Tempo para ordenar: " + (fim-inicio)/1000.0 + " s.");
        System.out.println("-------------------------------------");
        System.out.println("Comparacoes: " + g.comparacoes);
        System.out.println("-------------------------------------");
        System.out.println("Movimentacoes: " + g.movimentacoes);
        System.out.println("-------------------------------------");
        
        g.mostrar();
    }
}
