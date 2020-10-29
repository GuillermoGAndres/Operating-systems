#include <stdio.h>
#include <unistd.h>

int main() {
  printf("Prueba de proceso\n");

  pid_t pid_hijo = fork(); //Clona el proceso actual.
  pid_t pid_proceso = getpid(); // Obtiene PID del proceso actual.
  int pid_padre = getppid(); // Obtine PID del proceso padres.
   /*
   Habra tres ID's
   PIDPrograma que lo esta ejecutando(shell,padre) <-> programa actual
   programa actual(Padre) <-> programa hijo
   */
  printf("\tSe ha creado un proceso el ID del proceso es %d y el ID del proceso padre es: %d\n", pid_proceso, pid_padre);
 
  return 1;
}
