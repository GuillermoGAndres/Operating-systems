#include <stdio.h>
#include <unistd.h>
#include <stdlib.h> // function exit()
#include <sys/wait.h>

/**
 * (Ejercicion hecho en clase espontaneo, no tiene numeracion asi fue el 12)
 * Programa que crea hijos y espera por ellos.
 * Al momento de crear de un hijo y llega al caso default de switch
 * utilza la funcion wait para esperar a su hijo, hasta que termine su
 * hijo y lo libere de PCB continua su ejecucion, y todo esto lo hace mediante
 * la funcion wait.
 * Con exit termina el proceso hijo y lo prepara la fase eliminacion, pase el 
 * codigo de salida  exit_code al la tabla de sistema operativo para que el padre
 * sea enterado para luego el padre tome ese exit code para liberarlo con wait.
 * el proceso hijo se queda en un proceso zombi hasta que sea liberardo por el padre
 * o si es el caso por el proceso init si es que padre acaba antes.
 * En este caso con wait, el padre es que lo libera del PCB (Bloque control de proceso)
 * y continua su ejecucion.
 * Eg.
 * Asi es como funciona la shell cuando lanza algunos programas y los espera y no puedes
 * ejecutar mas comanado.
 * Por con emacs cuando lo abres en una terminal o apache Linux cuando lo ejecutar en tu terminal.
 */
int main() {
    printf("Prueba proceso\n");
    int n;
    pid_t pid_hijo;
    printf("Numero de hijos: ");
    scanf("%d", &n);

    printf("Proceso principal %i\n", getpid());
    printf("================\n");
    int i = 1;
    while(  i <= n){

	switch(pid_hijo = fork()) {
	case -1:
	    printf("\nError al crear el proceso\n");  
	    break;
	case 0:
	    printf("\tSoy el proceso hijo %d con PID %d y mi padre es PID = %d\n", i,getpid(), getppid());
	    sleep(2);
	    exit(0);
	    break;
	default:
	    printf("Esperado al hijo %d PID=%d\n", i, pid_hijo);
	    wait(NULL);
	    printf("Termino hijo PID=%d\n\n", pid_hijo);
	    i++;
	    break;
	}
    }
    //waitpid(-1, null,0);

    printf("\n\tA una linea de terminar el proceso %d\n", getpid());
    return 0;
}



  
