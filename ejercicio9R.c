#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>

/* Create a new program that create n process child and at the same time every child should be "x" process child*/ 
int main(){
    printf("Main process %d\n", getpid());
    int n;
    pid_t pid_child;
    printf("How many children: ");
    scanf("%d", &n);
    int count_child = 1;
    
    while( count_child <= n) {
	
	pid_child = fork(); // <- produce other child.
	
	if(pid_child < 0) {
	    printf("Error");
	    return -1;
	}else if( pid_child == 0){ //Child

	    printf("Padre(pid=%d) -> hijo %d(pid=%d)\n", getppid(),count_child, getpid() );
	    for(count_child = 0; count_child < 2; count_child++){
		switch(pid_child = fork()) {
		case -1:
		    printf("error");
		    return -1;
		case 0:
		    printf("\tHijo (pid=%d) -> nieto%d (pid=%d)\n", getppid(), count_child+1, getpid());
		    exit(0);
		default:
		    sleep(1);
		    break;
	   
		} 	
	    }
	    
	    exit(0);
	}else {//Parent
	    sleep(2);
	    count_child++;
	}
	    
    }

    


    
    return 0;
	
}
