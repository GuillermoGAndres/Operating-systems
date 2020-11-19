#include <stdio.h>
#include <unistd.h>
#include <stdlib.h> // function exit()
#include <sys/wait.h>

/**
 * Este seria el ejercicio 12 en la enumeracion de los ejercicios si hay confusion
 * Lo que hace el programa es indicar cuantos hijos te faltan por llegar.
 * El padre crea a sus hijos para que trabaje paralelamente, y se queda esperando
 * a ellos, cuando termine uno y llegue a la funcion waitpid debe indicar cuantos
 * hijos faltar por llegar.
 *
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

    while(waitpid(-1, NULL,0) > 0){
	printf("\nPadre, llego un hijo, solo espero  a ... %i hijos ",--n);
    }


    printf("\n\tTermino el proceso principal %d\n", getpid());
    return 0;
}



  
