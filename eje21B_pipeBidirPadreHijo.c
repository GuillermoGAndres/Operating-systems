/*
  COMUNICACION BIDIRECCIONAL:
  En el padre:
  - el lado de lectura de a[0].
  - el lado de escritura de b[1].
  En el hijo:
  - el lado de escritura de a[1]. // Escribe por a
  - el lado de lectura de b[0]. //Escucha por b
*/


#include <string.h>
#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>
#include <sys/wait.h>

/* En pipe a el padre escucha lo del hijo, entonces el pipa a el hijo escribe
 *  En el pipe b el padre escribe al hijo, y el hijo escucha
 */

#define SIZE 512
/**
 * Programa donde el padre como el hijo de envien mensajes
 */
int main( int argc, char **argv )
{
  pid_t pid;
  int a[2], b[2]; // Creamos dos pipes para que sea bidireccional
  char buffer[SIZE];

  //Verificamos si crearon correctamente
  if(pipe(a) < -1) {
    perror("\nError al crear el primer pipe");
    exit(1);
  }
  if(pipe(b) < -1) {
    perror("\Error al crear el segundo pipe");
    exit(2);
  }

  //system ("clear");
  pid = fork(); // Creamos un hijo.
  switch(pid){
  case -1: // Error
    printf("No se ha podido crear un hijo \n");
    exit(-1);
    break;
    
  case 0: // Hijo
    // Cierra el descriptor que no va a usar para los pipes
    // Lo que dice que en el pipe a solo voy a escribir.
    // Y en pipe b solo leer
    close(a[0]); // Solo va escribir
    close(b[1]); // Solo leeer

    // Escucha el mensaje del padres
    printf("\nHijo(pid=%i) esperando mensaje de mi padre...\n", getpid());
    read(b[0], buffer, 256); // Lei del pipe b ,  lo impresionante es le estas pasando valores al proceso, que es mensaje.
    printf("\nHijo(pid=%i), lee mensaje del pipe: %s\n", getpid(), buffer);

    // Ahora vamos nosotros escribir, entonce utlizaremos el pipe a, le contestamos al padre
    write(a[1],"<--- Hola padre, recibi mensaje...",SIZE);
    sleep(2);
    close(b[0]); // Termine de leer del pipe b
    close(a[1]); // Termine de leer del pipe a
    exit(0); 
    break;
    
  default: // Padre
    //Cierra el descriptor
    close(a[1]); // En en el pipe a en padre va escuchar a su hijo, la respuesta
    close(b[0]); // Y en el pipe b va escribir el mensaje del hijo.
    //printf("\nPadre(pid= %i), mensaje a enviar: ", getpid());
    //fgets(buffer,SIZE,stdin);
    
    // Le mandamos un mensaje al hijo
    write(b[1], "---> Hola hijo, te escucho", SIZE);

    // Leemos el mensaje del hijo
    read(a[0], buffer, SIZE);
    printf("\npadre(pid=%i), lee mensaje del pipe: %s\n", getpid(), buffer);

    close(a[0]);
    close(b[1]);
    break;
  } 
  wait(NULL);
  printf("\nTermino el proceso padre...\n");
  return 0;
}
