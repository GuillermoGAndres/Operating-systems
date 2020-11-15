//Programa donde el padre manda un mensaje al hijo
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <sys/wait.h>

/*Enviar un mensaje al padre*/
int main(){
    int pfd[2];
    char msg[256]; // message
    pid_t pid;
    int status;
	 
    //system ("clear");
	
    //padre crea  la tuberia (pipe)
    if (pipe(pfd) < 0) { 
	perror("\nError al crear el pipe");
	exit(1);
    }

    pid = fork(); // creamos un proceso
    switch(pid){
    case -1: // Error
	printf("No se ha podido crear un hijo \n");
	exit(-1);
	break;
    case 0: // Hijo
	close(pfd[1]); // Cierra el descriptor de escritura que no va a usar.
	printf("\n\tHijo(pid=%i) esperando mensaje de mi padre...\n", getpid());
	read(pfd[0],msg,256); // (file_descriptor, messageAlmacenar, cantidadALeer)
	printf("\n\tHijo(pid=%i), lee mensaje del pipe: %s\n", getpid(), msg);
	//Termino de leer ahora le toca cerrarlo
	close(pfd[0]); // Cierra su canal de lectura.
	exit(0);
	break;
    default: // Padre 
	close(pfd[0]); // Cierra el descriptor de lectura que no va a usar.
	printf("\nPadre(pid= %i), mensaje a enviar: ", getpid());
	fgets(msg,256,stdin); // Obtienes mensaje de la entrada estandar.
	write(pfd[1],msg,sizeof(msg)); //(archivo al cual va escribir, mensaje, tamaño en bytes)
	close(pfd[1]);
	break;
    }
    wait(&status);
    printf("\nTermino mi hijo con status: %i, Termino el proceso padre...\n", status);
    return 0;
}
