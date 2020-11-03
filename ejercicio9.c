#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>

int main(){
    printf("Main process %d\n", getpid());
    int n;
    pid_t pid_child;
    printf("How many children: ");
    scanf("%d", &n);
    int num_child = 2; //Esta variable es compartida por cada uno de sus hijos.
    while( n > 0) {
	
	pid_child = fork(); // <- produce other child.
	
	if(pid_child < 0) {
	    printf("Error");
	    return -1;
	}else if( pid_child == 0){ //Child
	    int pid_nieto;
	    while(num_child > 0){
		pid_nieto = fork();
		switch(pid_nieto) {
		case -1:
		    printf("error");
		    return -1;
		case 0:
		    printf("\tHijo%d (pid=%d) -> nieto%d (pid=%d)\n",n, getppid(), num_child, getpid());
		    exit(0);
		default:
		    num_child--;
	   
		} 	
	    }
	    exit(0);
	}else {//Parent
	    printf("Padre(pid=%d) -> hijo %d(pid=%d)\n", getpid(),n, pid_child);
	    n--;
	}
	    
    }

    


    
    return 0;
	
}
