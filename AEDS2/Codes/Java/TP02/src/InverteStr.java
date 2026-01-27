import java.util.Scanner;
//import java.util.*;

public class InverteStr {

    public static String Invert(String str){ // cria a funcao de inverter a string
        String inversa = new String();
        int tam = str.length();

        for(int i = tam-1; i >= 0; i--){ // cria o loop para correr a string e gerar a nova invertida
            inversa += str.charAt(i); // coloca os caracteres da string original em ordem invertida, baseado no i, na nova string
        }

        return inversa; // retorna a nova string

    }
    public static void main(String[] args) throws Exception {
         Scanner sc = new Scanner(System.in);
         String s = new String();
         s = sc.nextLine();

        while(!((s.length()) == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M')){ // cria o loop para correr todas as entradas
            if(!((s.length()) == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M')){ // condicao para evitar repeticao a mais
                System.out.println(Invert(s)); // chama a funcao de invert e imprime a nova string invertida
            }
            s = sc.nextLine();
        }

        sc.close();
    }
}



