#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <string.h>

// Verifica se só há vogais
bool isVogalRec(char s[], int i) {
    if (s[i] == '\0') return true;
    char c = s[i];
    if (c >= 'A' && c <= 'Z') c = c - 'A' + 'a';
    if (c != 'a' && c != 'e' && c != 'i' && c != 'o' && c != 'u') return false;
    return isVogalRec(s, i + 1);
}

bool isVogal(char s[]) {
    return isVogalRec(s, 0);
}

// Verifica se só há consoantes
bool isConsoRec(char s[], int i) {
    if (s[i] == '\0') return true;
    char c = s[i];
    if (c >= 'A' && c <= 'Z') c = c - 'A' + 'a';
    if (c < 'a' || c > 'z') return false;
    if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u') return false;
    return isConsoRec(s, i + 1);
}

bool isConso(char s[]) {
    return isConsoRec(s, 0);
}

// Verifica se só há dígitos (inteiro)
bool isIntRec(char s[], int i) {
    if (s[i] == '\0') return true;
    if (s[i] < '0' || s[i] > '9') return false;
    return isIntRec(s, i + 1);
}

bool isInt(char s[]) {
    return isIntRec(s, 0);
}

// Verifica se é real (apenas um ponto ou vírgula permitido)
bool isRealRec(char s[], int i, int count) {
    if (s[i] == '\0') return true;
    if ((s[i] < '0' || s[i] > '9') && s[i] != '.' && s[i] != ',') return false;
    if (s[i] == '.' || s[i] == ',') {
        count++;
        if (count > 1) return false;
    }
    return isRealRec(s, i + 1, count);
}

bool isReal(char s[]) {
    return isRealRec(s, 0, 0);
}

int main() {
    char str[100];

    fgets(str, sizeof(str), stdin);
    str[strcspn(str, "\n")] = '\0';

    while (strcmp(str, "FIM") != 0) {

        if (isVogal(str)) {
            printf("SIM ");
        } else {
            printf("NAO ");
        }

        if (isConso(str)) {
            printf("SIM ");
        } else {
            printf("NAO ");
        }

        if (isInt(str)) {
            printf("SIM ");
        } else {
            printf("NAO ");
        }

        if (isReal(str)) {
            printf("SIM\n");
        } else {
            printf("NAO\n");
        }

        fgets(str, sizeof(str), stdin);
        str[strcspn(str, "\n")] = '\0';
    }

    return 0;
}
