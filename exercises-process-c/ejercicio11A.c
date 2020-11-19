#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
/*Que cada nieto comparta su tareas*/

void do_table(int begin, int table) {
    for(int i=begin; i <=10; i++){
	printf("\t%d x %d = %d\n", table, i, table*i);
    }
}
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
	    // Reutilzo las variables y las sobreescribo.
	    printf("Padre(pid=%d) -> hijo %d(pid=%d)\n", getppid(),count_child, getpid() );
	    int table = count_child;
	    for(count_child = 0; count_child < 2; count_child++){
		pid_child = fork();
	       
		switch(pid_child) {
		case -1:
		    printf("error");
		    return -1;
		case 0:
		    printf("\tHijo (pid=%d) -> nieto%d (pid=%d)\n", getppid(), count_child+1, getpid());
		    /* for(int i=1; i <= 10; i++ ) { */
		    /* 	printf("\t%d x %d = %d\n", table, i, table*i); */
		    /* } */
		    if(count_child == 0)
			do_table(1,5,  table);
		    else
			do_table(5,10,table)
		    
		    exit(0);
		default:
		    sleep(1);
		    break;
		} 	
	    }
	    exit(0);
	    break;
	}else {//Parent
	    sleep(2);
	    count_child++;
	}
	    
    }
    
    return 0;
	
}
