import java.util.Scanner;
//import java.util.*;

public class Palindromo {

    public static boolean Palindro(String str){ // cria a funcao de identificar um palindromo 
        int i = 0; 
        int j = str.length() - 1; 

        while(i < j){ // cria o loop para percorrer a string
            if(str.charAt(i) != str.charAt(j)){ // cria a condicao para verificar a string com sua inversa
                return false; // se houver diferenca ele retorna false
            }
            i++; 
            j--;
        }

        return true; // se nao cair nenhuma vez no if, quer dizer que e um palindromo, e retorna true
    }
    public static void main(String[] args) throws Exception {
         Scanner sc = new Scanner(System.in);
         String s = new String();
         s = sc.nextLine();

        while(!((s.length()) == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M')){ // cria o loop para correr todas as entradas
            if(!((s.length()) == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M')){ // condicao para evitar repeticao a mais
                if(Palindro(s)){ // condicao para chamar a funcao de palindromo e ver se e true
                    System.out.println("SIM"); // se sim, imprime SIM
                }
                else{
                    System.out.println("NAO"); // se nao, imprime NAO
                }
            }
            s = sc.nextLine();
        }

        sc.close();
    }
}
