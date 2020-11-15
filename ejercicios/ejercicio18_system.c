
//Programa que hace uso de las llamadas al sistema system
//Ejemplifica la ejecucion de comandos de Linux

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>

int main()
{
    int pid; 	
    //    system("clear");		
    printf("CREANDO UN HIJO, pid del padre = %d \n", getpid());
    pid = fork();	//Se crea un proceso hijo

    if(pid < 0){
	printf("Error: no se creo el proce hijo");
	exit(0);
    }
    else if(pid == 0)
    {
	printf("\n-----------tarea del hijo (pid=%i): COMANDO  head-------\n", getpid());
	system("head -5 e*14*.c");
	printf("\nDURMIENDO....\n");
	sleep(5);
	printf("Termino el hijo...");
	printf("\n-----------------------------------------------------\n");
    }
    else { //PADRE
	printf ("\nPADRE: Esperando a que termine mi hijo (pid=%i)", pid);
	wait(NULL);
	printf("\nPADRE: Ya termino mi hijo (pid=%i", pid);
    }
    printf("\n(Proceso:%i) ULTIMO CODIGO A EJECUTAR!!", getpid());
    printf("\nPid = (%d) esta terminando el proceso MAIN...\n", getpid());

    if (pid == 0)
	printf("\nAhora si PADRE DESPIERTA!!!\n");	
    else
	printf("\nTERMINANDO EL PADRE...\n");
    return 0;
}
