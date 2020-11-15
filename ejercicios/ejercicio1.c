#include <stdio.h>
#include <unistd.h>

int main() {
  printf("Prueba de proceso\n");

  pid_t idHijo = fork(); //Clona el proceso.
  printf("\tSe creado ha un proceso\n"); //Imprimiria un mensaje por cada proceso.
  
  return 0;
}
