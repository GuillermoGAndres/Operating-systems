#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>

int main(){
    printf("Main process %d\n", getpid());
    int n;
    pid_t pid_child;
    printf("How many children: ");
    scanf("%d", &n);
    int table = 1;
    while( n > 0) {
	pid_child = fork();
	
	switch(pid_child) {
	case -1:
	    printf("Error");
	    return -1;
	case 0: //Child
	    printf("Parent: %d Child %d: %d\n", getppid(), n, getpid() );
	    for(int i = 1; i <= 10; i++){
		printf("%d x %i = %d\n", table,i, table*i  );
	    }
	    printf("Dead child : %d\n", getpid()); 
	    exit(0);
	default://Parent
	    n--;
	    table++;

	}
	    
    }
    


    
    return 0;
	
}
