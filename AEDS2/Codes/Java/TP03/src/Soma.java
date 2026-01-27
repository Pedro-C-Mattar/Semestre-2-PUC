import java.util.Scanner;

public class Soma {

    public static int somar(int n) {
        if (n < 10) { // condição de parada
            return n; // retorna o último dígito
        }
        return (n % 10) + somar(n / 10); // soma o último dígito e chama recursivamente
    }

    public static int soma(int n) {
        return somar(n);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();

        while (!str.equals("FIM")) { // loop até encontrar "FIM"
            int num = Integer.parseInt(str); // converte para inteiro
            System.out.println(soma(num)); // imprime a soma dos dígitos

            str = sc.nextLine();
        }

        sc.close();
    }
}

