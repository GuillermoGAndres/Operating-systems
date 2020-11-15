/******************************************************************************************/
/* Backup
   Realiza un respaldo periodico de un directorio a traves de crear 
   un proceso hijo que realice esa tarea, comunicacion entre padre e hijo
   sera implmentada con pipes.
   @author Andres Urbano Guillermo Gerardo.                                   
   Datos de entrada:
   -El directorio a respaldar 
   -Directorio donde se almacenará el respaldo

*/
/******************************************************************************************/
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>
#include <time.h>
void respaldar(int total_ficheros, char* nombre_fichero);

int main(int argc, char *argv[]) {

    int pipe1[2], pipe2[2];
    pid_t pid_hijo;

    pid_hijo = fork();
    int num_files = 5; // Numero temporal por el momento para pruebas.
    int contador = 0;
    
    while( contador <= num_files){
	
	if(pid_hijo == 0) {
	    // Realizar tareas del padre
	    
	} else if (pid_hijo > 0) {
	    // Realizar tareas del hijo
	    //El mismo realizara el backup por cada archivo
	    respaldar(num_files , "foo.txt"); 

	} else {
	    // Hubo un fallo en creacion del proceso
	    perror("Hubo un error en creacion de un proceso");
	}
	contador++;
    }
    
    return 0;
}

void respaldar(int total_ficheros, char* nombre_fichero) {
    printf("total : %d\n", total_ficheros);
    printf("Fichero: %s\n", nombre_fichero);
    
}
