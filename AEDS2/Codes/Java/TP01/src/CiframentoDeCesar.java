import java.util.Scanner;
//import java.util.*;

public class CiframentoDeCesar {

    public static String Cifra(String str){ // cria a funcao de converter para cifra de Cesar (chave 3)
        String cesar = new String();
        int tam = str.length();

        for(int i = 0; i < tam; i++){ // cria o loop para correr a string e gerar a nova em crifra de Cesar
            char c = str.charAt(i);
            if(c >= 0 && c <= 177){
                cesar += ((char)(c + 3)); // muda o caractere para o respectivo da cifra e adiciona na nova string
            }
            else{
                cesar += c;
            }
        }

        return cesar; // retorna a nova string

    }
    public static void main(String[] args) throws Exception {
         Scanner sc = new Scanner(System.in);
         String s = new String();
         s = sc.nextLine();

        while(!((s.length()) == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M')){ // cria o loop para correr todas as entradas
            if(!((s.length()) == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M')){ // condicao para evitar repeticao a mais
                System.out.println(Cifra(s)); // chama a funcao de cifra e imprime a nova string em cifra de Cesar
            }
            s = sc.nextLine();
        }

        sc.close();
    }
}


