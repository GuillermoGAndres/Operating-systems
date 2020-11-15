#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <sys/wait.h>
int main() {
  pid_t pid;
  time_t t;
  int status;
  system("clear");
  if ((pid = fork()) < 0)
    perror("fork() error");
  else if (pid == 0) {
    time(&t);
    printf("\t--->Hijo(pid= %d) inicia su trabajo de 3 segundos a las %s", (int) getpid(), ctime(&t));
    sleep(3);
    time(&t);
    printf("\t--->Hijo (pid=%d) con padre(pid= %i)termino a las %s", getpid(),getppid(),ctime(&t));
    exit(42);
  }
  else {
    printf("El padre (pid=%d) creo un ---> hijo (pid = %d)\n",getpid(),pid);
    time(&t);
    printf("El padre (pid=%d) dormira 2 segundos a las %s", getpid(),ctime(&t));
    sleep(2);
    time(&t);
    printf("El padre (pid= %d) inicia la espera (wait) de un hijo a las %s", getpid(),ctime(&t));
    if ((pid = wait(&status)) == -1)
      perror("wait() error");
    else {
      time(&t);
      printf("Padre: ya termino el hijo con pid = %d\n",pid);
      printf("\tEl padre sale del wait a las %s", ctime(&t));
      if (WIFEXITED(status))
        printf("\tEl hijo termino con un status de: %d\n", WEXITSTATUS(status));
      else if (WIFSIGNALED(status))
        printf("\tEl hijo terminado por una señal (signal) %d\n",WTERMSIG(status));
      else if (WIFSTOPPED(status))
        printf("\tEl hijo fue suspendido (stopped by signal) %d\n", WSTOPSIG(status));
      else puts("\tError: se desconoce la razon por la que el hijo termino");
    }
  }
  printf("____________________________________________________\n");

  return 0;
}