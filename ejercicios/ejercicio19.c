#include <stdio.h>
#include <unistd.h>
#include <stdlib.h> // function exit()
#include <sys/wait.h>
/**
 * @author: Andres Urbano Guillermo Gerardo
 * Lo que hace el programa es crear todos los hijos y que cada hijo
 * trabaje en paralelo, mientras que el padre solo los va esperar.
 * La diferencia aqui es que el padre ya creo a todos sus hijos y entonces solo queda
 * esperarlos para ser liberados de PCB, mientras los hijos van haciendo sus tareas
 * en paralelo, el progreso de cada tarea dependera del tiempo que le da el procesador
 * a cada proceso.
 * 
 */
int main() {
    printf("Prueba proceso\n");
    int n;
    pid_t pid_hijo;
    printf("Numero de hijos: ");
    scanf("%d", &n);

    printf("Proceso principal %i\n", getpid());
    printf("================\n");
    int i=1;
    while( i <= n){
	pid_hijo = fork(); //1, 2, 3
	switch(pid_hijo) {
	case -1:
	    printf("\nError al crear el proceso\n");
	    break;
	case 0:
	    printf("\tSoy el proceso hijo %d con PID %d y mi padre es PID = %d\n\n\n", i,getpid(), getppid());
	    if( i == 1) {
		//Listar directorios
		system("ls -al");
		printf("\n\n\n");
	    } else if (i == 2){
		// Listar los procesos del  sistema
		system("ps");
		printf("\n\n\n");
	    } else if ( i == 3) {
		//Listar solo los proceos que se estan ejecutando
		system("sh -c ps -fea | grep ejercicio19.out");
		printf("\n\n\n");
	    }
	    exit(0);
	    break;
	default:
	    i++;
	    /* printf("Esperado al hijo PID=%d\n", pid_hijo); */
	    /* wait(NULL); */
	    /* printf("Termino hijo PID=%d\n\n", pid_hijo); */
	    break;
	}
    }

    /*
     * waitpid devuelve el PID del hijo
     * si kernel le envia un numero que  es menor que 0 significa que ya no hay
     *  hijos que esperar.
     */
    while( (pid_hijo = waitpid(-1, NULL,0)) > 0){
	printf("\nEL proceso con PID=%d ha terminado , ahora solo faltan %i hijos ",pid_hijo, --n);
    }


    printf("\n\tTermino el proceso principal %d\n", getpid());
    return 0;
}



  
