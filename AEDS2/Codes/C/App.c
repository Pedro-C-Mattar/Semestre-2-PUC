#include <stdio.h>
#include <string.h>

int maiuscula(char str[]){
    int cont = 0;
    int tam = strlen(str);
    
    for(int i = 0; i<tam; i++){
        if(str[i] >= 'A' && str[i] <= 'Z'){
                cont++;
        }
    }

    return cont;
}

int contarMaiusculas(char str[], int index) {
    
    if (index == strlen(str)) {
        return 0;
    }

    int contagemAtual = (str[index] >= 'A' && str[index] <= 'Z') ? 1 : 0;

    return contagemAtual + contarMaiusculas(str, index + 1);
}

int main(){

    printf("Numero de maiusculas: %d", maiuscula("AtlEtIcO"));

    printf("\nNumero de maiusculas: %d", contarMaiusculas("AtlEtIcO", 0));
}