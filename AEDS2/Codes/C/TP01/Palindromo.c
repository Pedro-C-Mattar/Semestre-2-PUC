#include <stdio.h>
#include <stdbool.h>
#include <string.h>

bool Palindro(char s[], int i){
    if(i > (strlen(s)-1)){ // condicao de fim da recursao
        return true; // se sim, retorna true
    }
    if(s[i] != s[strlen(s)-1-i]){ // verifica se e diferente
        return false; // se sim, retorna false
    }

    return Palindro(s, i+1); // se nao cair na condicao de diferente, retorna a propria funcao para recursividade, ate correr todo a string
}

bool Palin(char s[]){   // cria a funcao para chamar a funcao Palindro
    return Palindro(s, 0);
}

int main(){
    char str[100];
    fgets(str, sizeof(str), stdin);
    str[strcspn(str, "\n")] = '\0'; // limpa o buffer

    while(strcmp(str, "FIM") != 0){ // cria o loop para correr as entradas
        if(strcmp(str, "FIM") != 0){ // condicao para evitar repeticoes a mais
            if(Palin(str)){ // condicao para chamar a funcao de palindromo e ver se e true
                printf("SIM\n"); // se true, imprime SIM
            }
            else{
                printf("NAO\n"); // se false, imprime NAO
            }
        }
        fgets(str, sizeof(str), stdin);
        str[strcspn(str, "\n")] = '\0'; // limpa o buffer
    }
}
