#include <stdio.h>
#include <unistd.h>

int main() {
    printf("Prueba proceso\n");
    printf("Yo soy el proceso principal:  %d\n", getpid()); //Proceso actual 
    pid_t pid_my_son = fork();
    
    if( pid_my_son == -1 ) {
        printf("No se pudo crear un proceso");
        return -1;
    } else if (pid_my_son == 0) {
        printf("Soy el proceso hijo con PID %d y mi padre es PID = %d", getpid(), getppid());
    } else {
        printf("Soy el proceso padre con PID = %d y mi hijo es PID = %d" , getpid(), pid_my_son);
    }

    printf("\n\tTermino el proceso %d\n", getpid());

    return 0;
}
