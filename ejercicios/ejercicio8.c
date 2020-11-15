#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>

int main(){
    printf("Main process %d\n", getpid());
    int n;
    pid_t pid_child;
    printf("How many children: ");
    scanf("%d", &n);

    while( n > 0) {
	
	pid_child = fork(); // <- produce other child.
	
	switch(pid_child) {
	case -1:
	    printf("Error");
	    return -1;
	case 0: //Child
	    printf("Parent: %d Child %d: %d\n", getppid(), n, getpid() );
	    n--;
	    break;
	default://Parent
	    printf("Dead Parent : %d\n", getpid()); //Not more produce.
	    exit(0); //it should kill paretn because, it doesn't more children.
	    

	}
	    
    }
    


    
    return 0;
	
}
