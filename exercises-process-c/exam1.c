#include <stdio.h>
#include <unistd.h>
#include <stdlib.h> // function exit()
#include <sys/wait.h>

int main() {
    int N = 2;
    int nuevo;
    for (int i = 0; i < N; ++i) {
	nuevo = fork();
	if(nuevo == 0) {
	    break;
	}
    }
    nuevo = fork();
    nuevo = fork();
    // This aomc commentary
    int comentar 
    printf("Soy el proceso %d y mi padre es %d\n", getpid(), getppid());
    return 0;

}
