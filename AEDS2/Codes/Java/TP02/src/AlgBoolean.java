import java.util.*;

public class AlgBoolean {
    
    // Converte um caractere numérico para seu valor inteiro correspondente
    public static int parseInt(char input) {
        int cont = input - 48; // Subtrai 48 (código ASCII de '0') para converter char para int
        return cont;
    }

    // Converte um caractere '0' ou '1' para boolean (false/true)
    public static boolean parseBoolean(char input) {
        return input != '0'; // '0' = false, qualquer outro caractere = true
    }

    // Cria uma substring manualmente (equivalente ao método substring da String)
    public static String substring(String input, int start, int end) {
        String newStr = new String();

        for (int i = start; i < end; i++) {
            newStr += input.charAt(i); // Concatena caractere por caractere
        }

        return newStr;
    }

    // Substitui uma parte da string por outra substring
    public static String replace(String input, int star, int end, String subReplace) {
        // Pega a parte antes da substituição
        String newStr1 = substring(input, 0, star);
        // Pega a parte depois da substituição
        String newStr2 = substring(input, end, input.length());

        // Retorna as duas partes com a substring de substituição no meio
        return newStr1 + subReplace + newStr2;
    }

    // Extrai os argumentos (valores 0/1) de dentro de parênteses para operações lógicas
    public static char[] getArgs(String input, int index) {
        int contador = 0;

        // Conta quantos argumentos (0/1) existem dentro dos parênteses
        for (int i = index + 1; input.charAt(i) != ')'; i++) {
            if (input.charAt(i) == '0' || input.charAt(i) == '1') {
                contador++;
            }
        }

        // Cria array com o tamanho correto
        char[] args = new char[contador];

        // Preenche o array com os argumentos
        for (int i = index + 1, j = 0; input.charAt(i) != ')'; i++) {
            if (input.charAt(i) == '0' || input.charAt(i) == '1') {
                args[j] = input.charAt(i);
                j++;
            }
        }

        return args;
    }

    // Operação NOT lógico - inverte o valor
    public static char not(char[] args) {
        if (args[0] == '0') {
            return '1';
        } else if (args[0] == '1') {
            return '0';
        }
        return 0; // Retorno padrão (nunca deve acontecer com entrada válida)
    }

    // Operação AND lógico - retorna '1' apenas se TODOS os argumentos forem '1'
    public static char and(char[] args) {
        for (int i = 0; i < args.length; i++) {
            if (args[i] == '0') {
                return '0'; // Se encontrar qualquer '0', retorna '0'
            }
        }
        return '1'; // Se todos forem '1', retorna '1'
    }

    // Operação OR lógico - retorna '1' se PELO MENOS UM argumento for '1'
    public static char or(char[] args) {
        for (int i = 0; i < args.length; i++) {
            if (args[i] == '1') {
                return '1'; // Se encontrar qualquer '1', retorna '1'
            }
        }
        return '0'; // Se todos forem '0', retorna '0'
    }

    // Função principal que resolve a expressão booleana
    public static String equacionando(String input) throws Exception {
        char[] args;
        char result;
        int end;

        // Processa a string da direita para a esquerda (para lidar com parênteses aninhados)
        for (int i = (input.length() - 1); i >= 0; i--) {
            if (input.charAt(i) == '(') {
                // Verifica qual operação está sendo realizada
                switch (input.charAt(i - 1)) {
                    case 'd': // AND operation
                        args = getArgs(input, i); // Pega os argumentos
                        result = and(args); // Executa operação AND

                        // Encontra o final dos parênteses
                        for (end = i; input.charAt(end) != ')'; end++)
                            ;
                        // Substitui toda a expressão AND pelo resultado
                        input = replace(input, i - 3, end + 1, result + "");
                        i -= 3; // Ajusta o índice após a substituição
                        break;
                        
                    case 't': // NOT operation
                        args = getArgs(input, i); // Pega os argumentos
                        result = not(args); // Executa operação NOT
                        // NOT tem formato fixo "not()" - 3 caracteres antes, 3 depois
                        input = replace(input, i - 3, i + 3, result + "");
                        i -= 3; // Ajusta o índice após a substituição
                        break;
                        
                    case 'r': // OR operation
                        args = getArgs(input, i); // Pega os argumentos
                        result = or(args); // Executa operação OR

                        // Encontra o final dos parênteses
                        for (end = i; input.charAt(end) != ')'; end++)
                            ;
                        // Substitui toda a expressão OR pelo resultado
                        input = replace(input, i - 2, end + 1, result + "");
                        i -= 2; // Ajusta o índice após a substituição
                        break;

                    default:
                        throw new Exception("Erro"); // Operação desconhecida
                }
            }
        }

        // Retorna o resultado final (apenas o primeiro caractere)
        return substring(input, 0, 1);
    }

    // Método principal - ponto de entrada do programa
    public static void main(String[] args) throws Exception {
        Scanner scanf = new Scanner(System.in);
        String input = scanf.nextLine(); // Lê a primeira linha

        // Loop principal - executa até encontrar '0' no início
        while (input.charAt(0) != '0') {
            // Pega o número de variáveis (A, B, C)
            int cont = parseInt(input.charAt(0));

            boolean[] array = new boolean[cont]; // Array para valores das variáveis

            // Processa os valores das variáveis baseado no número de variáveis
            if (cont == 2) {
                array[0] = parseBoolean(input.charAt(2)); // Valor de A
                array[1] = parseBoolean(input.charAt(4)); // Valor de B
                input = substring(input, 6, input.length()); // Pega apenas a expressão

            } else if (cont == 3) {
                array[0] = parseBoolean(input.charAt(2)); // Valor de A
                array[1] = parseBoolean(input.charAt(4)); // Valor de B
                array[2] = parseBoolean(input.charAt(6)); // Valor de C
                input = substring(input, 8, input.length()); // Pega apenas a expressão
            }

            // Substitui as variáveis (A, B, C) por seus valores (0/1) na expressão
            for (int i = 0; i < input.length(); i++) {
                if (input.charAt(i) == 'A') {
                    input = replace(input, i, i + 1, array[0] == false ? "0" : "1");
                } else if (input.charAt(i) == 'B') {
                    input = replace(input, i, i + 1, array[1] == false ? "0" : "1");
                } else if (input.charAt(i) == 'C') {
                    input = replace(input, i, i + 1, array[2] == false ? "0" : "1");
                }
            }
            
            // Resolve a expressão booleana
            input = equacionando(input);
            // Imprime o resultado
            System.out.println(input);

            // Lê próxima linha
            input = scanf.nextLine();
        }

        scanf.close(); // Fecha o scanner
    }
}