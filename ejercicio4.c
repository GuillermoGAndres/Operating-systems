#include <stdio.h>
#include <unistd.h>

int main() {
  printf("Prueba de proceso\n");

  printf("Yo soy el:  %d\n", getpid()); //Proceso actual
  fork(); //Cada proceso se clona
  fork(); //Tendriamos una arbol con  3 hijo3 y raiz
  fork();
  
  /*
           ()
      /     \      \
     ()      ()    ()
    / \     /
   ()  ()  ()
   /
   () 

  */
  
  /* int idHijo = getpid(); */
  /* int idPadre = getppid(); */
  printf("\tSe ha creado un proceso Padre: %d Hijo: %d:\n", getppid(), getpid());
  
  
  return 1;
}
