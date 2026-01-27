#include <stdio.h>      // Entrada e saída (printf, scanf, fopen)
#include <stdlib.h>     // Conversão numérica, malloc, etc.
#include <string.h>     // Manipulação de strings (strcpy, strtok)
#include <ctype.h>      // Funções de caracteres (isspace, isdigit, etc.)
#include <time.h>       // Medição de tempo (clock)

// Limites máximos definidos
#define MAX_GAMES 1850  // Máximo de jogos no banco de dados
#define MAX_STR   512   // Tamanho máximo de uma string
#define MAX_LIST  50    // Máximo de elementos em listas internas (idiomas, gêneros, etc.)

// Estrutura que representa um jogo
typedef struct {
    int id;
    char name[MAX_STR];
    char releaseDate[11]; // formato dd/mm/yyyy
    int estimatedOwners;
    float price;
    char supportedLanguages[MAX_LIST][MAX_STR];
    int langCount;
    int metacriticScore;
    float userScore;
    int achievements;
    char publishers[MAX_LIST][MAX_STR];
    int pubCount;
    char developers[MAX_LIST][MAX_STR];
    int devCount;
    char categories[MAX_LIST][MAX_STR];
    int catCount;
    char genres[MAX_LIST][MAX_STR];
    int genCount;
    char tags[MAX_LIST][MAX_STR];
    int tagCount;
} Game;

// Vetor com todos os jogos e contador global
Game fullDB[MAX_GAMES];
int gameCount = 0;
Game dbaux[MAX_GAMES];
int contaux = 0;

// Remove espaços no início e no final de uma string
void trim(char *str) {
    // Remove espaços no início movendo a string para a esquerda
    char *start = str;
    while (*start && isspace((unsigned char)*start)) start++;
    if (start != str) memmove(str, start, strlen(start) + 1);

    // Remove espaços no final
    char *end = str + strlen(str) - 1;
    while (end >= str && isspace((unsigned char)*end)) {
        *end = '\0';
        end--;
    }
}

// Divide uma string separada por vírgulas e preenche uma lista
void splitArray(char list[MAX_LIST][MAX_STR], int *count, char *input) {
    *count = 0;
    if (input == NULL || strlen(input) == 0) return;

    // Divide pelos separadores ','
    char *token = strtok(input, ",");
    while (token != NULL && *count < MAX_LIST) {
        trim(token); // remove espaços

        // Remove colchetes externos [ e ]
        int l = strlen(token);
        if (l > 0 && token[0] == '[') {
            memmove(token, token + 1, l);
            // atualiza tamanho
            l = strlen(token);
        }
        if (l > 0 && token[l - 1] == ']') {
            token[l - 1] = '\0';
            l--;
        }

        // Remove aspas simples ou duplas no início e no fim, se existirem
        if (l > 0 && (token[0] == '\'' || token[0] == '"')) {
            memmove(token, token + 1, l);
            l = strlen(token);
        }
        if (l > 0 && (token[l - 1] == '\'' || token[l - 1] == '"')) {
            token[l - 1] = '\0';
            l--;
        }

        strcpy(list[*count], token); // copia para a lista
        (*count)++;
        token = strtok(NULL, ","); // próximo item
    }
}

// Converte datas de formatos variados para dd/mm/yyyy
void formatDate(char *input, char *output) {
    // Caso padrão
    strcpy(output, "01/01/0001");

    // Exemplo: "Apr 12, 2012"
    if (strchr(input, ' ') && strchr(input, ',')) {
        char monthStr[4];
        int day, year;
        sscanf(input, "%3s %d, %d", monthStr, &day, &year); // lê partes
        int month = 1;
        const char *months = "JanFebMarAprMayJunJulAugSepOctNovDec";
        char *p = strstr(months, monthStr); // localiza o mês
        if (p)
            month = (p - months) / 3 + 1;
        sprintf(output, "%02d/%02d/%04d", day, month, year);
    }
    // Exemplo: "Apr 2012"
    else if (strchr(input, ' ')) {
        char monthStr[4];
        int year;
        sscanf(input, "%3s %d", monthStr, &year);
        int month = 1;
        const char *months = "JanFebMarAprMayJunJulAugSepOctNovDec";
        char *p = strstr(months, monthStr);
        if (p)
            month = (p - months) / 3 + 1;
        sprintf(output, "01/%02d/%04d", month, year);
    }
    // Exemplo: "2012"
    else if (strlen(input) == 4) {
        sprintf(output, "01/01/%s", input);
    }
}

// Lê uma linha do CSV e transforma em um struct Game
void parseCSVLine(char *line, Game *game) {
    char newLine[MAX_STR * 2] = "";
    int insideQuotes = 0;

    // Substitui vírgulas por ';' apenas fora de aspas
    for (int i = 0; line[i] != '\0'; i++) {
        char c = line[i];
        if (c == '"')
            insideQuotes = !insideQuotes; // alterna flag
        else if (c == ',' && !insideQuotes)
            strcat(newLine, ";"); // troca por ';'
        else if (c != '"')
            strncat(newLine, &c, 1); // copia caractere
    }

    // Divide a linha em até 14 partes
    char *token;
    char *splitted[14];
    int i = 0;

    token = strtok(newLine, ";");
    while (token != NULL && i < 14) {
        splitted[i++] = token;
        token = strtok(NULL, ";");
    }

    if (i < 9) return; // linha incompleta, ignora

    // Atribui campos ao struct
    game->id = atoi(splitted[0]);
    strcpy(game->name, splitted[1]);
    formatDate(splitted[2], game->releaseDate);
    game->estimatedOwners = atoi(splitted[3]);
    game->price = atof(splitted[4]);
    splitArray(game->supportedLanguages, &game->langCount, splitted[5]);
    game->metacriticScore = atoi(splitted[6]);
    game->userScore = atof(splitted[7]);
    game->achievements = atoi(splitted[8]);

    // Campos compostos (listas)
    splitArray(game->publishers, &game->pubCount, splitted[9]);
    splitArray(game->developers, &game->devCount, splitted[10]);
    splitArray(game->categories, &game->catCount, splitted[11]);
    splitArray(game->genres, &game->genCount, splitted[12]);
    splitArray(game->tags, &game->tagCount, splitted[13]);
}

// Lê todo o arquivo CSV e preenche o vetor fullDB
void readDb() {
    FILE *file = fopen("/tmp/games.csv", "r");
    if (!file) {
        printf("Arquivo não encontrado!\n");
        return;
    }

    char line[MAX_STR * 2];
    fgets(line, sizeof(line), file); // ignora cabeçalho

    // Lê linha a linha até o fim
    while (fgets(line, sizeof(line), file) && gameCount < MAX_GAMES) {
        line[strcspn(line, "\r\n")] = '\0'; // remove quebra de linha
        parseCSVLine(line, &fullDB[gameCount]); // converte e salva
        gameCount++;
    }

    fclose(file);
}

// Busca o índice de um jogo pelo ID
int findID(int id) {
    for (int i = 0; i < gameCount; i++) {
        if (fullDB[i].id == id) return i; // encontrado
    }
    return -1; // não encontrado
}

// Exibe os dados formatados de um jogo
void mostrar(Game *g) {
    printf("=> %d ## %s ## %s ## %d ## %.2f ## [", 
        g->id, g->name, g->releaseDate, g->estimatedOwners, g->price);

    // Idiomas
    for (int i = 0; i < g->langCount; i++) {
        printf("%s%s", g->supportedLanguages[i], (i < g->langCount - 1 ? ", " : ""));
    }

    // Metacritic, notas, conquistas
    printf("] ## %d ## %.1f ## %d ## [", g->metacriticScore, g->userScore, g->achievements);

    // Publishers
    for (int i = 0; i < g->pubCount; i++)
        printf("%s%s", g->publishers[i], (i < g->pubCount - 1 ? ", " : ""));
    printf("] ## [");

    // Developers
    for (int i = 0; i < g->devCount; i++)
        printf("%s%s", g->developers[i], (i < g->devCount - 1 ? ", " : ""));
    printf("] ## [");

    // Categorias
    for (int i = 0; i < g->catCount; i++)
        printf("%s%s", g->categories[i], (i < g->catCount - 1 ? ", " : ""));
    printf("] ## [");

    // Gêneros
    for (int i = 0; i < g->genCount; i++)
        printf("%s%s", g->genres[i], (i < g->genCount - 1 ? ", " : ""));
    printf("] ## [");

    // Tags
    for (int i = 0; i < g->tagCount; i++)
        printf("%s%s", g->tags[i], (i < g->tagCount - 1 ? ", " : ""));
    printf("] ##\n");
}

// Copia um jogo para o array auxiliar (cópia de struct é suficiente)
void passarDados(Game *g){
    if (contaux < MAX_GAMES) {
        dbaux[contaux] = *g; // cópia direta da struct
        contaux++;
    }
}

// Selection Sort por Name. Conta comparações (strcmp) e movimentos (atribuições de struct ao executar swap).
// Aqui consideramos cada atribuição de struct como 1 movimentação; um swap realiza 3 atribuições.
void selectionSortByName(Game arr[], int n, long *comparisons, long *movements) {
    *comparisons = 0;
    *movements = 0;

    for (int i = 0; i < n - 1; i++) {
        int minIdx = i;
        for (int j = i + 1; j < n; j++) {
            (*comparisons)++;
            if (strcmp(arr[j].name, arr[minIdx].name) < 0) {
                minIdx = j;
            }
        }
        (*comparisons)++;
        if (minIdx != i) {
            Game tmp = arr[minIdx];
            arr[minIdx] = arr[i];
            arr[i] = tmp;
            *movements += 3; // 3 atribuições de struct para o swap
        }
    }
}

// Converte data no formato dd/mm/yyyy para inteiro YYYYMMDD para facilitar comparação
int dateToInt(const char *date) {
    int d = 1, m = 1, y = 1;
    if (sscanf(date, "%d/%d/%d", &d, &m, &y) == 3) {
        return y * 10000 + m * 100 + d;
    }
    return 1010001; // valor padrão caso parsing falhe
}

// Compara dois jogos por releaseDate; em caso de empate, desempata por id.
// Incrementa *comparisons para cada comparação entre elementos que for realizada.
int compareByReleaseDate(const Game *a, const Game *b, long *comparisons) {
    int da = dateToInt(a->releaseDate);
    int db = dateToInt(b->releaseDate);
    (*comparisons)++; // comparação entre datas
    if (da < db) return -1;
    if (da > db) return 1;
    // datas iguais: desempata por id
    (*comparisons)++; // comparação entre ids (critério secundário)
    if (a->id < b->id) return -1;
    if (a->id > b->id) return 1;
    return 0;
}

// Troca dois elementos do array e contabiliza movimentações (3 atribuições por swap)
void swapGames(Game *a, Game *b, long *movements) {
    Game tmp = *a;
    *a = *b;
    *b = tmp;
    *movements += 3;
}

// Quicksort recursivo para Game[] usando partição Hoare com pivô no meio
void quicksortRecInline(Game arr[], int low, int high, long *comparisons, long *movements) {
    if (low >= high) return;

    Game pivot = arr[(low + high) / 2];
    int i = low;
    int j = high;

    while (i <= j) {
        while (compareByReleaseDate(&arr[i], &pivot, comparisons) < 0) i++;
        while (compareByReleaseDate(&arr[j], &pivot, comparisons) > 0) j--;
        if (i <= j) {
            if (i != j) swapGames(&arr[i], &arr[j], movements);
            i++;
            j--;
        }
    }

    if (low < j) quicksortRecInline(arr, low, j, comparisons, movements);
    if (i < high) quicksortRecInline(arr, i, high, comparisons, movements);
}

// Função principal
int main() {
    readDb(); // lê o banco de dados de /tmp/games.csv

    char input[32];
    while (1) {
        if (scanf("%s", input) != 1) break; // lê entrada do usuário
        if (strcmp(input, "FIM") == 0)  // condição de parada
            break;
        int id = atoi(input);           // converte para número
        int pos = findID(id);           // busca pelo ID
        if (pos != -1){
            passarDados(&fullDB[pos]);
        }

    }

    long comparisons = 0;
    long movements = 0;
    clock_t start = clock();
    quicksortRecInline(dbaux, 0, contaux-1, &comparisons, &movements);
    //selectionSortByName(dbaux, contaux, &comparisons, &movements);
    clock_t end = clock();
    double elapsed = (double)(end - start) / CLOCKS_PER_SEC;

    // Imprime registros ordenados
    for (int i = 0; i < contaux; i++) {
        mostrar(&dbaux[i]);
    }

    // Cria arquivo de log na pasta corrente
    //FILE *log = fopen("888302_selection.txt", "w");
    FILE *log = fopen("888302_quicksort.txt", "w");
    if (log) {
        fprintf(log, "888302\t%ld\t%ld\t%.6f\n", comparisons, movements, elapsed);
        fclose(log);
    }

    return 0;
}
