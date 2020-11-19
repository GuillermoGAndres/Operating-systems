#include <stdio.h>
#include <unistd.h>

int main() {
  printf("Prueba de proceso\n");

  printf("Yo soy el procesos inicial:  %d\n", getpid()); //Proceso actual
  fork(); //Cada proceso se clona
  fork(); //Tendriamos una arbol con  3 hijos y raiz
  /*
       ()
      /  \
     ()  ()
    /
   ()
  */
  
  /* int idHijo = getpid(); */
  /* int idPadre = getppid(); */
  printf("\tSe ha creado un proceso Padre: %d Hijo: %d:\n", getppid(), getpid());
  
  
  return 1;
}
