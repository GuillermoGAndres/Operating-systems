#include <stdio.h>
#include <unistd.h>
#include <stdlib.h> // function exit()
#include <sys/wait.h>

/**
 * Ejercicio propuesto
 * Lo que hace ahora es indicar cuantos hijos le faltan al padre por esperar y ademas
 * indica cual hijo fue el que llego.
 * Proceso
 * El padre crea a sus hijos para que trabejen paralalemente y los espera, cada vez
 * que termine un hijo la funcion waitpid regresara el pid del hijo, el cual el 
 * padre lo imprimira para indicar cual hijo llego y cuantos le faltan por esperar.
 */
int main() {
    printf("Prueba proceso\n");
    int n;
    pid_t pid_hijo;
    printf("Numero de hijos : ");
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
	printf("\t%d Soy el proceso hijo con PID %d y mi padre es PID = %d\n", i,getpid(), getppid());
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

    while( (pid_hijo = waitpid(-1, NULL,0)) > 0){
	printf("\nPadre, llego el hijo  %d, solo espero  a... %i hijos ",pid_hijo, --n);
    }


    printf("\n\tTermino el proceso principal %d\n", getpid());
    return 0;
}



  
