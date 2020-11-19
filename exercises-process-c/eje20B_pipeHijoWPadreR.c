
//Programa donde el hijo le manda un mensaje al padre
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <sys/wait.h>

int main(){
  int pfd[2];
  char msg[256];
  pid_t pid;
  int status;
	
  //system ("clear");

  pipe(pfd); // Se crea el PIPE
  printf("Padre: %i", getpid());
  printf("\n=======================================\n");
  pid = fork(); // Creamos un proceso
  switch(pid){
  case -1: // Error
    printf("No se ha podido crear un hijo \n");
    exit(-1);
    break;
  case 0: // Hijo
    close(pfd[0]); // Cierra el descriptor que no va a usar. El de lectura
    printf("\n\tHijo(pid=%i), mensaje a enviar al padre: \n", getpid());
    fgets(msg,256 ,stdin);
    write(pfd[1], msg, sizeof(msg));
    printf("\nTermino el hijo\n");
    close(pfd[1]);
    exit(0);
    break;
  default: // Padre
    close(pfd[1]); //Cierra el descriptor de escritura
    printf("\nPadre(pid= %i), esperando mensaje del hijo\n", getpid());
    read(pfd[0], msg, sizeof(msg));     
    printf("\nPadre(pid= %i), lee mensaje del pipe: %s", getpid(), msg);
    close(pfd[0]);
    break;
  }
  wait(&status);
  printf("\nTermino el proceso padre...\n");
  return 0;
} 
