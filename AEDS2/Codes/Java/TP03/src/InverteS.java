import java.util.Scanner;

public class InverteS {

    public static String invert(String str) {
        if (str.length() <= 1) {  // Caso base: se a string tiver tamanho 0 ou 1, já está invertida
            return str;
        }
        
        return str.charAt(str.length() - 1) + invert(str.substring(0, str.length() - 1)); // Pega o último caractere e concatena com a inversão do restante
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();

        while (!s.equals("FIM")) {
            System.out.println(invert(s));
            s = sc.nextLine();
        }

        sc.close();
    }
}
