#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <string.h>

bool isVogal(char s[]){ // cria a funcao de verificar se e so vogal
    int tam = strlen(s);

    for(int i = 0; i < tam; i++) { // cria o loop para fazer a verificacao
        char c = s[i];
        if (c >= 'A' && c <= 'Z') { // condicao para ver se tem maiuscula
            c = c - 'A' + 'a'; // se sim, converte para minusculo
        }
        if (c != 'a' && c != 'e' && c != 'i' && c != 'o' && c != 'u') { // condicao para ver se nao e vogal
            return false; // se nao for, retorna false
        }
    }
    return true; // se for so vogal, retorna true
}

bool isConso(char s[]){ // cria a funcao de verificar se e so consoante
    int tam = strlen(s);

    for(int i = 0; i < tam; i++) {// cria o loop para fazer a verificacao
        char c = s[i];
        if (c >= 'A' && c <= 'Z') {// condicao para ver se tem maiuscula
            c = c - 'A' + 'a'; // se sim, converte para minusculo
        }
        if (c < 'a' || c > 'z') {
            return false;
        }
        if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u') { // condicao para ver se e vogal
            return false; // se for, retorna false
        }
    }
    return true; // se for so consoante, retorna true
}

bool isInt(char s[]){ // cria a funcao de verificar se e inteiro
    int tam = strlen(s);

    for(int i = 0; i < tam; i++) { // cria o loop para fazer a verificacao
        if (s[i] < '0' || s[i] > '9') { // condicao para verificar se o digito nao e numero
            return false; // se nao for, retorna false
        }
    }
    return true; // se tudo formar um numero inteiro, retorna true
}

bool isReal(char s[]){ // cria a funcao de verificar se e inteiro
    int tam = strlen(s);

    int cont = 0;

    for(int i = 0; i < tam; i++) { // cria o loop para fazer a verificacao
        if ((s[i] < '0' || s[i] > '9') && s[i] != '.' && s[i] != ',') { // condicao para verificar se o digito nao e numero  . ,
            return false; // se nao for, retorna false
        }
        if (s[i] == '.' || s[i] == ',') { // condicao para verificar se encontar . ,
            cont++; // se sim, adiciona no contador
            if (cont > 1) { // condicao para verificar se tem mais de um . ,
                return false; //se tiver, retorna false
            }
        }
    }
    return true; // se tudo formar um numero real, retorna true
}
int main(){
    char str[100];
    fgets(str, sizeof(str), stdin);
    str[strcspn(str, "\n")] = '\0'; // limpa o buffer

    while(strcmp(str, "FIM") != 0){ // cria o loop para correr as entradas
        if(strcmp(str, "FIM") != 0){ // condicao para evitar repeticoes a mais
            if(isVogal(str)){ // condicao para chamar a funcao isVogal e verificar se so tem vogal
                printf("SIM "); // se sim, imprime SIM
            }
            else{
                printf("NAO "); // se nao, retorna NAO
            }
            if(isConso(str)){ // condicao para chamar a funcao isConso e verificar se so tem consoante
                printf("SIM "); // se sim, imprime SIM
            }
            else{
                printf("NAO "); // se nao, retorna NAO
            }
            if(isInt(str)){ // condicao para chamar a funcao isInt e verificar se e um inteiro
                printf("SIM "); // se sim, imprime SIM
            }
            else{
                printf("NAO "); // se nao, retorna NAO
            }
            if(isReal(str)){ // condicao para chamar a funcao isReal e verificar se e real
                printf("SIM\n"); // se sim, imprime SIM
            }
            else{
                printf("NAO\n"); // se nao, retorna NAO
            }
        }
        fgets(str, sizeof(str), stdin);
        str[strcspn(str, "\n")] = '\0'; // limpa o buffer
    }
}