#include <stdio.h>
#include <unistd.h>
#include <stdlib.h> // function exit()
#include <sys/wait.h>

/**
 * Ejercicio propuesto (enumrado como el 14 en los ejercicios)
 * Lo que que el programa es poner a trabajar a cada hijo
 * Cada hijo imprimirra su propia serie y despues se dormiar segun 
 * que numero de hijo es.
 */

void aTrabajarHijo(int tiempo) {
    int i=0;
    for (i = 0; i < 10; ++i) {
	printf("(PID=%d) = %i\n", getpid(), i);
    }
    sleep(tiempo);
    printf("\tTermine PID=%d\n", getpid());

}
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
	aTrabajarHijo(i);
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
