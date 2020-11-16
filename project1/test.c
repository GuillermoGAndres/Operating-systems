#include <stdio.h>

int main() {
    char buffer[1000];
    FILE* file = fopen("lista_archivos.txt", "r");
    int max_lineas;
    fscanf(file, "%d", &max_lineas);
    int i = 1;
    while( i <= max_lineas){
	fscanf(file, "%s", buffer);
	printf("line %d : %s\n", i,  buffer);
	i++;
    }
}
