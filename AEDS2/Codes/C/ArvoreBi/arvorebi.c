#include <stdio.h>
#include <stdlib.h>
#define bool   short
#define true   1
#define false  0

typedef struct No {
    int elemento;
	struct No *esq, *dir;
} No;

No* raiz;

void start() {
   raiz = NULL;
}

No* novoNo(int elemento) {
   No* novo = (No*) malloc(sizeof(No));
   novo->elemento = elemento;
   novo->esq = NULL;
   novo->dir = NULL;
   return novo;
}

void inserir(int x) {
 inserirRec(x, &raiz);
}
void inserirRec(int x, No** i) {
 if (*i == NULL) {
 *i = novoNo(x);
 } else if (x < (*i)->elemento) {
 inserirRec(x, &((*i)->esq));
 } else if (x > (*i)->elemento) {
 inserirRec(x, &((*i)->dir));
 } else {
 errx(1, "Erro ao inserir!");
 }
}

bool pesquisar(int x) {
   return pesquisarRec(x, raiz);
}
bool pesquisarRec(int x, No* i) {
   bool resp;
   if (i == NULL) {
      resp = false;

   } else if (x == i->elemento) {
      resp = true;

   } else if (x < i->elemento) {
      resp = pesquisarRec(x, i->esq);

   } else {
      resp = pesquisarRec(x, i->dir);
   }
   return resp;
}

void caminharCentral() {
   printf("[ ");
   caminharCentralRec(raiz);
   printf("]\n");
}
void caminharCentralRec(No* i) {
   if (i != NULL) {
      caminharCentralRec(i->esq);
      printf("%d ", i->elemento);
      caminharCentralRec(i->dir);
   }
}

void caminharPre() {
   printf("[ ");
   caminharPreRec(raiz);
   printf("]\n");
}
void caminharPreRec(No* i) {
   if (i != NULL) {
      printf("%d ", i->elemento);
      caminharPreRec(i->esq);
      caminharPreRec(i->dir);
   }
}

void caminharPos() {
   printf("[ ");
   caminharPosRec(raiz);
   printf("]\n");
}
void caminharPosRec(No* i) {
   if (i != NULL) {
      caminharPosRec(i->esq);
      caminharPosRec(i->dir);
      printf("%d ", i->elemento);
   }
}


int main(){
    start();

    inserir(40);
    inserir(20);
    inserir(60);
    inserir(10);
    inserir(50);
    inserir(25);

    caminharCentral();
    caminharPos();
    caminharPre();

    pesquisar(25);
    pesquisar(15);

}