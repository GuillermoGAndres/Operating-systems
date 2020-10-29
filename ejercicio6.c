#include <stdio.h>
#include <unistd.h>
#include <stdlib.h> // function exit()

int main() {
    printf("Prueba proceso\n");
    int n;
    pid_t pid_hijo;
    printf("Numero de hijos :");
    scanf("%d", &n);

    printf("Proceso principal %i\n", getpid());
    printf("================\n");

    while( n > 0){
      switch(pid_hijo = fork()) {
      case -1:
	printf("\nError al crear el proceso\n");
	break;
      case 0:
	printf("%d Soy el proceso hijo con PID %d y mi padre es PID = %d\n", n,getpid(), getppid());
	exit(0);
	break;
      default:
	n--;
	break;
      }
    }

    printf("\n\tTermino el proceso %d\n", getpid());

    
   

    return 0;
}

El padre crea la variable i?
La variable i la comparten padre e hijos?
Las modificaciones que hace el padre, se reflejan en las variables de los hijos?
Las modificaciones que hace el hijo, las ve el padre?
Las modificaciones que hace un hijo en la variable i, la pueden ver todos los hermanos?


  
