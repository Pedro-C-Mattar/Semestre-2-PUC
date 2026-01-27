#include <stdio.h>      
#include <stdlib.h>     
#include <string.h>      
#include <ctype.h>      
#include <time.h>       

// Limites máximos definidos
#define MAX_GAMES 1850  // Máximo de jogos no banco de dados
#define MAX_STR   512   // Tamanho máximo de uma string
#define MAX_LIST  50    // Máximo de elementos em listas internas 
#define TAM_TAB 21   // tamanho da tabela hash

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

typedef struct HashNode {
    Game *game;
    struct HashNode *next;
} HashNode;

typedef struct {
    HashNode *table[TAM_TAB];
} HashIndireta;

long comparacoesHash = 0;

// Inicializa tabela hash
void startHash(HashIndireta *h){
    for(int i = 0; i < TAM_TAB; i++)
        h->table[i] = NULL;
}

// Função ASCII: soma dos caracteres
int asciiSum(char *str){
    int sum = 0;
    for(int i = 0; str[i] != '\0'; i++){
        sum += (unsigned char)str[i];
    }
    return sum;
}

// Função hash
int hashFunc(char *name){
    return asciiSum(name) % TAM_TAB;
}

// Inserção na hash indireta (lista)
void inserirHash(HashIndireta *h, Game *g){
    int pos = hashFunc(g->name);

    HashNode *novo = (HashNode*) malloc(sizeof(HashNode));
    novo->game = g;
    novo->next = h->table[pos];
    h->table[pos] = novo;
}

// Pesquisa na hash indireta
int pesquisarHash(HashIndireta *h, char *name){
    int pos = hashFunc(name);
    HashNode *i = h->table[pos];

    while(i != NULL){
        comparacoesHash++;
        if(strcmp(i->game->name, name) == 0){
            printf(" (Posicao: %d) SIM\n", pos);
            return 1;
        }
        i = i->next;
    }
    printf(" (Posicao: %d) NAO\n", pos);
    return 0;
}

// ============================================
// Pilha DINÂMICA SIMPLESMENTE ENCADEADA DE GAMES
// ============================================
typedef struct Node {
    Game game;
    struct Node *next;
} Node;

typedef struct {
    Node *top;
    int size;
} Stack;

// Inicializa a pilha
void startStack(Stack *s) {
    s->top = NULL;
    s->size = 0;
}

// Empilha (insere no topo)
void push(Stack *s, Game g) {
    Node *newNode = (Node*) malloc(sizeof(Node));
    newNode->game = g;
    newNode->next = s->top;
    s->top = newNode;
    s->size++;
}

// Desempilha (remove do topo)
Game pop(Stack *s) {
    Game g;
    if (s->top == NULL) {
        strcpy(g.name, ""); // pilha vazia
        return g;
    }
    Node *tmp = s->top;
    g = tmp->game;
    s->top = tmp->next;
    free(tmp);
    s->size--;
    return g;
}

// Mostra recursivamente do fundo para o topo
void showRecursive(Node *node, int index) {
    if (node == NULL) return;
    showRecursive(node->next, index - 1);
    printf("[%d] ", index);
    mostrar(&node->game); // usa sua função completa de exibição
}

void showStack(Stack *s) {
    showRecursive(s->top, s->size - 1);
}

// ============================================
// LISTA DINÂMICA SIMPLESMENTE ENCADEADA
// ============================================

typedef struct Cell {
    Game *game;
    struct Cell *next;
} Cell;

typedef struct {
    Cell *first;
    Cell *last;
    int size;
} List;

// Inicializa a lista
void startList(List *l) {
    l->first = (Cell*) malloc(sizeof(Cell));
    l->first->next = NULL;
    l->last = l->first;
    l->size = 0;
}

// Insere no início
void insertBegin(List *l, Game *g) {
    Cell *tmp = (Cell*) malloc(sizeof(Cell));
    tmp->game = g;
    tmp->next = l->first->next;
    l->first->next = tmp;
    if (l->size == 0) l->last = tmp;
    l->size++;
}

// Insere no fim
void insertEnd(List *l, Game *g) {
    Cell *tmp = (Cell*) malloc(sizeof(Cell));
    tmp->game = g;
    tmp->next = NULL;
    l->last->next = tmp;
    l->last = tmp;
    l->size++;
}

// Insere em uma posição específica
void insertAt(List *l, int pos, Game *g) {
    if (pos < 0 || pos > l->size) {
        printf("Posição inválida: %d\n", pos);
        return;
    }

    Cell *i = l->first;
    for (int j = 0; j < pos; j++, i = i->next);

    Cell *tmp = (Cell*) malloc(sizeof(Cell));
    tmp->game = g;
    tmp->next = i->next;
    i->next = tmp;

    if (tmp->next == NULL) l->last = tmp;
    l->size++;
}

// Remove do início
Game* removeBegin(List *l) {
    if (l->first->next == NULL) return NULL;

    Cell *tmp = l->first->next;
    l->first->next = tmp->next;
    if (tmp == l->last) l->last = l->first;

    Game *g = tmp->game;
    free(tmp);
    l->size--;
    return g;
}

// Remove do fim
Game* removeEnd(List *l) {
    if (l->first->next == NULL) return NULL;

    Cell *i;
    for (i = l->first; i->next != l->last; i = i->next);

    Cell *tmp = l->last;
    Game *g = tmp->game;
    i->next = NULL;
    l->last = i;
    free(tmp);
    l->size--;
    return g;
}

// Remove de uma posição específica
Game* removeAt(List *l, int pos) {
    if (pos < 0 || pos >= l->size) {
        printf("Posição inválida: %d\n", pos);
        return NULL;
    }

    Cell *i = l->first;
    for (int j = 0; j < pos; j++, i = i->next);

    Cell *tmp = i->next;
    Game *g = tmp->game;
    i->next = tmp->next;

    if (tmp == l->last) l->last = i;
    free(tmp);
    l->size--;
    return g;
}

// Mostrar lista completa
void showList(List *l) {
    Cell *i = l->first->next;
    int pos = 0;
    while (i != NULL) {
        printf("[%d] ", pos++);
        mostrar(i->game);
        i = i->next;
    }
}

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

// Selection Sort por Name
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

// ===============
// No AVL
// ===============

typedef struct No {
    Game *game;
    struct No *esq;
    struct No *dir;
    int nivel;  
} No;

long comparacoes = 0; // contador global de comparações

int maxInt(int a, int b) {
    return (a > b) ? a : b;
}

int nivelNo(No *n) {
    return n ? n->nivel : 0;
}

No* criarNo(Game *g) {
    No *n = (No*) malloc(sizeof(No));
    n->game = g;
    n->esq = n->dir = NULL;
    n->nivel = 1;
    return n;
}

// Rotacoes de balanceamento
No* rotacaoDir(No *y) {
    No *x = y->esq;
    No *T2 = x->dir;

    x->dir = y;
    y->esq = T2;

    y->nivel = maxInt(nivelNo(y->esq), nivelNo(y->dir)) + 1;
    x->nivel = maxInt(nivelNo(x->esq), nivelNo(x->dir)) + 1;

    return x;
}

No* rotacaoEsq(No *x) {
    No *y = x->dir;
    No *T2 = y->esq;

    y->esq = x;
    x->dir = T2;

    x->nivel = maxInt(nivelNo(x->esq), nivelNo(x->dir)) + 1;
    y->nivel = maxInt(nivelNo(y->esq), nivelNo(y->dir)) + 1;

    return y;
}

int getBalance(No *n) {
    if (!n) return 0;
    return nivelNo(n->esq) - nivelNo(n->dir);
}

// Insert AVL

No* inserirAVL(No *i, Game *g) {
    if (i == NULL){
        return criarNo(g);
    }

    if (strcmp(g->name, i->game->name) < 0){
        i->esq = inserirAVL(i->esq, g);
    }else if (strcmp(g->name, i->game->name) > 0){
        i->dir = inserirAVL(i->dir, g);
    }else{
        return i; 
    }

    i->nivel = 1 + maxInt(nivelNo(i->esq), nivelNo(i->dir));

    int balance = getBalance(i);

    if (balance > 1 && strcmp(g->name, i->esq->game->name) < 0){
        return rotacaoDir(i);
    }

    if (balance < -1 && strcmp(g->name, i->dir->game->name) > 0){
        return rotacaoEsq(i);
    }

    if (balance > 1 && strcmp(g->name, i->esq->game->name) > 0) {
        i->esq = rotacaoEsq(i->esq);
        return rotacaoDir(i);
    }

    if (balance < -1 && strcmp(g->name, i->dir->game->name) < 0) {
        i->dir = rotacaoDir(i->dir);
        return rotacaoEsq(i);
    }

    return i;
}


// Pesquisa AVL com caminho

int pesquisarComCaminhoRec(No *i, char *nome) {
    if (i == NULL) {
        printf("NAO\n");
        return 0;
    }

    comparacoes++;
    int eq = strcmp(nome, i->game->name);
    if (strcmp(nome, i->game->name) == 0) {
        printf("SIM\n");
        return 1;
    }

    comparacoes++;
    if (strcmp(nome, i->game->name) < 0) {
        printf("esq ");
        return pesquisarComCaminhoRec(i->esq, nome);
    } else {
        printf("dir ");
        return pesquisarComCaminhoRec(i->dir, nome);
    }
}

void pesquisarComCaminho(No *raiz, char *nome) {
    printf("%s: raiz ", nome);
    pesquisarComCaminhoRec(raiz, nome);
}

// Principal

int main() {

    readDb(); // já existente no seu código

    clock_t start = clock();

    HashIndireta hash;
    startHash(&hash);

    char line[1024];

    // leitura dos IDs para inserir na hash
    while (fgets(line, sizeof(line), stdin) != NULL) {
        line[strcspn(line, "\r\n")] = '\0';
        trim(line);

        if (strcmp(line, "FIM") == 0) break;
        if (strlen(line) == 0) continue;

        int id = atoi(line);
        int pos = findID(id);

        if (pos != -1)
            inserirHash(&hash, &fullDB[pos]);
    }

    // PESQUISA

    while (fgets(line, sizeof(line), stdin) != NULL) {
        line[strcspn(line, "\r\n")] = '\0';
        trim(line);

        if (strcmp(line, "FIM") == 0) break;
        if (strlen(line) == 0) continue;

        printf("%s: ", line);
        pesquisarHash(&hash, line);
    }

    clock_t end = clock();
    double elapsed = (double)(end - start) / CLOCKS_PER_SEC;

    // LOG
    FILE *log = fopen("888302_hashIndireta.txt", "w");
    if (log) {
        fprintf(log, "888302 %.0f %ld\n", elapsed, comparacoesHash);
        fclose(log);
    } else {
        fprintf(stderr, "Erro ao escrever o arquivo de log\n");
    }

    return 0;
}

