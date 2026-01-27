import java.util.*;

class Passageiro{
    String nome;
    char sigla;
    int custo;
}

public class Van{

    static void boSort(Passageiro p[], int n){
        for(int i = 0; i < n-1; i++){
            for (int j = 0; j < n - 1 - i; j++){
                if(p[j].custo > p[j+1].custo){
                    swap(p, j, j+1);
                }else if(p[j].custo == p[j+1].custo){
                    if(p[j].sigla > p[j+1].sigla){
                        swap(p, j, j+1);
                    }else if(p[j].nome.compareToIgnoreCase(p[j+1].nome) > 0){
                        swap(p, j, j+1);
                    }
                }
            }
        }
    }

    static void swap(Passageiro p[], int a, int b){
        Passageiro tmp = p[a];
        p[a] = p[b];
        p[b] = tmp;
    }


    static public void main(String[] args){
        Scanner sc = new Scanner(System.in);
        while(sc.hasNext()){
            int num = sc.nextInt();
            sc.nextLine();
            Passageiro p[] = new Passageiro[num];

            for(int i = 0; i < num; i++){
                p[i] = new Passageiro();
                String linha = sc.nextLine();
                String inf[] = linha.split(" ");
                p[i].nome = inf[0];
                p[i].sigla = inf[1].charAt(0);
                p[i].custo = Integer.parseInt(inf[2]);
            }

            boSort(p, num);

            for(int i = 0; i < num; i++){
                System.out.println(p[i].nome);
            }

        }

        sc.close();

    }
}