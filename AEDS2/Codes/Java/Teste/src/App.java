public class App {
    public static int maiuscula (String str){
        int cont = 0;
        int tam = str.length();
        
        for(int i = 0; i<tam; i++){
            if(str.charAt(i) >= 'A' && str.charAt(i) <= 'Z'){
                cont++;
            }
        }

        return cont;
    }


    public static int contarMaiusculas(String str, int index) {

        if (index == str.length()) {
            return 0;
        }

        int contagemAtual = (str.charAt(index) >= 'A' && str.charAt(index) <= 'Z') ? 1 : 0;

        return contagemAtual + contarMaiusculas(str, index + 1);
    }

}
