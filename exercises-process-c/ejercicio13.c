#include <stdio.h>
#include <unistd.h>
#include <stdlib.h> // function exit()
#include <sys/wait.h>
/**
 * Este en la lamina seria el ejercicio 11 en su eneumeracion, por hay confusion.
 * Lo que hace el programa es crear todos los hijos y que cada hijo
 * trabaje en paralelo, mientras que padre solo los va esperar a cada uno de ellos.
 * La diferencia aqui es el padre ya creo a todos sus hijos y entonces solo queda
 * esperarlos para ser liberados de PCB, mientras los hijos van haciendo sus tareas
 * en paralelo, cada tarea progresara dependiendo del tiempo que le el procesador en 
 * cada quantus.
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

	switch(pid_hijo = fork()) {
	case -1:
	    printf("\nError al crear el proceso\n");
	    break;
	case 0:
	    printf("\tSoy el proceso hijo %d con PID %d y mi padre es PID = %d\n", i,getpid(), getppid());
	    printf("\tVoy a dormir PID=%d\n", getpid());
	    sleep(2);
	    printf("\tYa desperte! PID=%d\n", getpid());
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
     * waitpid @return devuelve el PID del hijo, simepre serra mayor 0
     * si kernel le envia un numero que  es menor que 0 significa que ya no hay
     *  hijos que esperar
     */
    while(waitpid(-1, NULL,0) > 0);


    printf("\n\tTermino el proceso principal %d\n", getpid());
    return 0;
}



  
