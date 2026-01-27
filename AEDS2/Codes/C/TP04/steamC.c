#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

#define MAX_GAMES 1850
#define MAX_STR   512
#define MAX_LIST  50

typedef struct {
    int id;
    char name[MAX_STR];
    char releaseDate[11]; // dd/mm/yyyy
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

Game fullDB[MAX_GAMES];
int gameCount = 0;

void trim(char *str) {
    while (isspace(*str)) str++;
    char *end = str + strlen(str) - 1;
    while (end > str && isspace(*end)) *end-- = '\0';
}

void splitArray(char list[MAX_LIST][MAX_STR], int *count, char *input) {
    *count = 0;
    if (input == NULL || strlen(input) == 0) return;

    char *token = strtok(input, ",");
    while (token != NULL && *count < MAX_LIST) {
        trim(token);
        int len = strlen(token);
        if (token[0] == '\'' || token[0] == '"')
            memmove(token, token + 1, len - 1);
        if (token[strlen(token) - 1] == '\'' || token[strlen(token) - 1] == '"')
            token[strlen(token) - 1] = '\0';
        strcpy(list[*count], token);
        (*count)++;
        token = strtok(NULL, ",");
    }
}

void formatDate(char *input, char *output) {
    // Default
    strcpy(output, "01/01/0001");

    if (strchr(input, ' ') && strchr(input, ',')) {
        // Ex: "Apr 12, 2012"
        char monthStr[4];
        int day, year;
        sscanf(input, "%3s %d, %d", monthStr, &day, &year);
        int month = 1;
        const char *months = "JanFebMarAprMayJunJulAugSepOctNovDec";
        char *p = strstr(months, monthStr);
        if (p)
            month = (p - months) / 3 + 1;
        sprintf(output, "%02d/%02d/%04d", day, month, year);
    }
    else if (strchr(input, ' ')) {
        // Ex: "Apr 2012"
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
    else if (strlen(input) == 4) {
        // Ex: "2012"
        sprintf(output, "01/01/%s", input);
    }
}

void parseCSVLine(char *line, Game *game) {
    char newLine[MAX_STR * 2] = "";
    int insideQuotes = 0;

    for (int i = 0; line[i] != '\0'; i++) {
        char c = line[i];
        if (c == '"')
            insideQuotes = !insideQuotes;
        else if (c == ',' && !insideQuotes)
            strcat(newLine, ";");
        else if (c != '"')
            strncat(newLine, &c, 1);
    }

    char *token;
    char *splitted[14];
    int i = 0;

    token = strtok(newLine, ";");
    while (token != NULL && i < 14) {
        splitted[i++] = token;
        token = strtok(NULL, ";");
    }

    if (i < 9) return;

    game->id = atoi(splitted[0]);
    strcpy(game->name, splitted[1]);
    formatDate(splitted[2], game->releaseDate);
    game->estimatedOwners = atoi(splitted[3]);
    game->price = atof(splitted[4]);
    splitArray(game->supportedLanguages, &game->langCount, splitted[5]);
    game->metacriticScore = atoi(splitted[6]);
    game->userScore = atof(splitted[7]);
    game->achievements = atoi(splitted[8]);

    splitArray(game->publishers, &game->pubCount, splitted[9]);
    splitArray(game->developers, &game->devCount, splitted[10]);
    splitArray(game->categories, &game->catCount, splitted[11]);
    splitArray(game->genres, &game->genCount, splitted[12]);
    splitArray(game->tags, &game->tagCount, splitted[13]);
}

void readDb() {
    FILE *file = fopen("/tmp/games.csv", "r");
    if (!file) {
        printf("Arquivo não encontrado!\n");
        return;
    }

    char line[MAX_STR * 2];
    fgets(line, sizeof(line), file); // pular cabeçalho

    while (fgets(line, sizeof(line), file) && gameCount < MAX_GAMES) {
        line[strcspn(line, "\r\n")] = '\0';
        parseCSVLine(line, &fullDB[gameCount]);
        gameCount++;
    }

    fclose(file);
}

int findID(int id) {
    for (int i = 0; i < gameCount; i++) {
        if (fullDB[i].id == id) return i;
    }
    return -1;
}

void mostrar(Game *g) {
    printf("=> %d ## %s ## %s ## %d ## %.2f ## [", 
        g->id, g->name, g->releaseDate, g->estimatedOwners, g->price);

    for (int i = 0; i < g->langCount; i++) {
        printf("%s%s", g->supportedLanguages[i], (i < g->langCount - 1 ? ", " : ""));
    }

    printf("] ## %d ## %.1f ## %d ## [", g->metacriticScore, g->userScore, g->achievements);
    for (int i = 0; i < g->pubCount; i++) printf("%s%s", g->publishers[i], (i < g->pubCount - 1 ? ", " : ""));
    printf("] ## [");
    for (int i = 0; i < g->devCount; i++) printf("%s%s", g->developers[i], (i < g->devCount - 1 ? ", " : ""));
    printf("] ## [");
    for (int i = 0; i < g->catCount; i++) printf("%s%s", g->categories[i], (i < g->catCount - 1 ? ", " : ""));
    printf("] ## [");
    for (int i = 0; i < g->genCount; i++) printf("%s%s", g->genres[i], (i < g->genCount - 1 ? ", " : ""));
    printf("] ## [");
    for (int i = 0; i < g->tagCount; i++) printf("%s%s", g->tags[i], (i < g->tagCount - 1 ? ", " : ""));
    printf("] ##\n");
}

int main() {
    readDb();

    char input[32];
    while (1) {
        scanf("%s", input);
        if (strcmp(input, "FIM") == 0) break;
        int id = atoi(input);
        int pos = findID(id);
        if (pos != -1)
            mostrar(&fullDB[pos]);
    }

    return 0;
}
