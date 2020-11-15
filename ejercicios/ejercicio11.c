#include <stdio.h>
#include <unistd.h>
#include <stdlib.h> // function exit()

/*Write program that creates child only , one parent and "n" child.
 * (No hay numeracion) , ejercicio que yo hice
 * el 11 seria el 11A que es uno que hicimos en clase espontaneio
 * a partir de ahi, se persion la enuracion con los ejercicios de las
 * imagenes.
*/

int main() {
    int n;
    pid_t pid_hijo;
    printf("Numero de hijos: ");
    scanf("%d", &n);

    printf("Proceso principal %i\n", getpid());
    printf("================\n");
    int i = 1;
    while( i <= n){
	pid_hijo = fork(); // 1, 2, 3, 4, ..., n children.
	switch(pid_hijo) {
	case -1:
	    printf("\nError al crear el proceso\n");
	    break;
	case 0:
	    printf("Soy el proceso hijo %d con PID %d y mi padre es PID = %d\n", i,getpid(), getppid());	    
	    exit(0); // x, x, x, x , ... , finished process child.
	    break;
	default:
	    //Enter  parent
	    i++;
	    break;
	}
      
    }

    /* printf("\n\tTermino el proceso %d\n", getpid()); */
    return 0;
}

  
