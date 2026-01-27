import java.util.Scanner;
import java.util.Random;
//import java.util.*;

public class AlterRondom {

    public static String Alterar(String str, Random rd){
        char ori = (char) ('a' + (Math.abs(rd.nextInt()) % 26)); // gera a letra original a ser trocada
        char nov = (char) ('a' + (Math.abs(rd.nextInt()) % 26)); // gera a letra que vai substituir a original
        int tam = str.length();
        String alter = new String();

        for(int i = 0; i < tam; i++){ // cria o loop para correr a string e gerar a nova string alterada
            if(str.charAt(i) == ori){ // condicao para verificar se o caractere e o escolhido randomicamente
                alter += nov; // se sim, substitui pelo substituto gerado
            }
            else{
                alter += str.charAt(i); // se nao, apenas replica o caractere original
            }
        }

        return alter; // retorna a nova string
    }
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        Random r = new Random(); // cria o gerador de aleatorio
        r.setSeed(4); // configura a seed do gerador
        String s = new String();
        s = sc.nextLine();

        while(!((s.length()) == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M')){ // cria o loop para correr todas as entradas
            if(!((s.length()) == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M')){ // condicao para evitar repeticao a mais
                System.out.println(Alterar(s, r)); // chama a funcao de alterar e imprime a string alterada
            }
            s = sc.nextLine();
        }

        sc.close();
    }
}
