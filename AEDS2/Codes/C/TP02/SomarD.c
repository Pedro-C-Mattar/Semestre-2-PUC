#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int Somar(int n){
    if(n < 10){ // condicao para o fim da recursao
        return n; // retorna o ultimo digito
    }

    return (n % 10) + Somar(n / 10); // retorna a soma dos digitos
}

int Soma(int n){   // cria a funcao que chama a funcao de somar os digitos
    return Somar(n);
}

int main(){
    char str[100];
    fgets(str, sizeof(str), stdin);
    str[strcspn(str, "\n")] = '\0'; // limpa o buffer

    while(strcmp(str, "FIM") != 0){ // cria o loop para correr as entradas
        if(strcmp(str, "FIM") != 0){ // condicao para evitar repeticoes a mais
            int num = atoi(str); // converte a string para int
            printf("%d\n", Soma(num)); // imprime a soma dos digitos
        }
        fgets(str, sizeof(str), stdin);
        str[strcspn(str, "\n")] = '\0'; // limpa o buffer
    }
}
