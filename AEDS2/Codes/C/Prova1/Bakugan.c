#include <stdio.h>
#include <stdlib.h>

int main(){
    int r = 1;
    scanf("%d", &r);

    while (r != 0)
    {

        int *m = (int*)malloc(r*sizeof(int));
        int *l = (int*)malloc(r*sizeof(int));
        int soma1 = 0;
        int soma2 = 0;
        int cont1 = 0;
        int cont2 = 0;

        for (int i = 0; i < r; i++)
        {
            scanf("%d", &m[i]);
        }

        for (int i = 0; i < r; i++)
        {
            scanf("%d", &l[i]);
        }

        for(int i = 0; i < r; i++){
            soma1 += m[i];
            soma2 += l[i];
            if(m[i] == m[i+1] && m[i+1] == m[i+2]){
                cont1++;
            }
            if(l[i] == l[i+1] && m[i+1] == l[i+2]){
                cont2++;
            }
             
        }
        if (cont1 > 0 && cont2 < cont1){
            soma1 += 30;
        }
        else if (cont2 > 0 && cont1 < cont2){
            soma2 += 30;
        }

        if(soma1 > soma2){
            printf("M\n");
        }else if(soma2 > soma1){
            printf("L\n");
        }
        else if(soma1 == soma2){
            printf("T\n");
        }
        
        free(m);
        free(l);

        scanf("%d", &r);
    }

}