import java.util.*;

public class Anagrama {

    public static String Lower(String str){ // cria a funcao para converter tudo para minusculo
        String result = new String();
        int tam = str.length();

        for (int i = 0; i < tam; i++) { // cria o loop para converter
            char c = str.charAt(i);

            if (c >= 'A' && c <= 'Z') { // condicao para ver se tem maiusculas
                result += (char)(c + 32); // converte para minuscula
            } else {
                result += c; // repete o caractere que ja esta em minusculo
            }
        }

        return result; // retorna a nova string convertida
    }

    public static void Swap(char arr[], int a, int b){ // cria o metodo de swap
        char temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }

    public static boolean Anagram(String splt1, String splt2){ // cria a funcao para verificar se e anagrama
        if (splt1.length() != splt2.length()) { // condicao que verifica se possuem o mesmo tamanho
            return false;
        }

        char arry1[] = splt1.toCharArray();
        char arry2[] = splt2.toCharArray();
        int tam = splt1.length();

        for(int i = 0; i < tam - 1; i++){ // cria o selection sort para ordenar as strings
            int menor1 = i;
            int menor2 = i;
            for(int j = i; j < tam; j++){
                if(arry1[menor1] > arry1[j]){
                    menor1 = j;
                }
                if(arry2[menor2] > arry2[j]){
                    menor2 = j;
                }
            }
            Swap(arry1, menor1, i);
            Swap(arry2, menor2, i);
        }

        for(int i = 0; i < tam; i++){ // loop para verifica se as strings tem os mesmos caracteres(anagrama)
            if(arry1[i] != arry2[i]){ // condicao de verificar se tem caractere diferente
                return false; // se sim, nao e anagrama e retorna false
            }
        }

        return true; // se e anagrama, retorna true

    }

    public static void main(String[] args) throws Exception {
         Scanner sc = new Scanner(System.in);
         String s = new String();
         s = sc.nextLine();

        while(!((s.length()) == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M')){ // cria o loop para correr todas as entradas
            if(!((s.length()) == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M')){ // condicao para evitar repeticao a mais
                s = Lower(s); 
                String sp[] = s.split(" - ");
                
                if(sp.length == 2){ // condicao verifica se as strings tem o mesmo tamanho
                    if(Anagram(sp[0], sp[1])){ // condicao para chamar a funcao anagram e verificar retorna true
                        MyIO.println("SIM"); // se sim, imprime SIM
                    }
                    else{
                        MyIO.println("NÃO"); // se nao, imprime NAO
                    }
                }

            }
            s = sc.nextLine();
        }

        sc.close();
    }
}
