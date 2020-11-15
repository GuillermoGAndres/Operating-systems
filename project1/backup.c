/******************************************************************************************/
/* Backup
   Realiza un respaldo periodico de un directorio a traves de crear 
   un proceso hijo que realice esa tarea, comunicacion entre padre e hijo
   sera implmentada con pipes.
   @author Andres Urbano Guillermo Gerardo.                                   
   Datos de entrada:
   -El directorio a respaldar 
   -Directorio donde se almacenará el respaldo

*/
/******************************************************************************************/
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>
#include <time.h>
#include <string.h>

void leerRutas(char* ruta_respaldo, char* ruta_destino);
void respaldar(int total_ficheros, char* nombre_fichero);


int main(int argc, char *argv[]) {
    
    char ruta_respaldo[1000];
    char ruta_destino[1000];
    int pipe1[2], pipe2[2];
    pid_t pid_hijo;

    /* 
     * Si entra al if significa que solo ejecuto el nombre del programa,
     * Por lo tanto solo existe dos posibles formas de  obtener los datos.
     * 1) Entrada estandar
     * 2) Por archivo
    */
    if (argc == 1) {	
	//Esto solo devuelven las rutas de respaldo y destino escritas correctamente
	// con su formato arrglos de caracteres pero no valida si existen esas rutas;
	leerRutas(ruta_respaldo, ruta_destino);
	/* printf("Salinda\n"); */
	/* printf("Ruta respaldo: %s\n" , ruta_respaldo); */
	/* printf("Ruta destiono: %s\n" , ruta_destino);	 */
	
    }else {
	/*
	 * Aqui significa que escribio parametros al programa, por lo tanto
	 * nos esta dando las rutas de los directorios de respaldo y destino 
	 */
	strcpy(ruta_respaldo, argv[1]);
	strcpy(ruta_destino, argv[2]);
	/* printf("Ruta respaldo: %s\n" , ruta_respaldo); */
	/* printf("Ruta destiono: %s\n" , ruta_destino); */
	
    }
    
    // 1) Genera un archivo con la lista de los nombres de los archivos
    // del directorio a respaldar y el numero total de archivos
    // Crea el file automaticamente, si ya existe lo eliminar y lo
    // vuelve a crear actualizando con los numeros valores
    //system("ls > lista_archivos.txt");
    //system("ls | wc -l >> lista_archivos.txt");

        
    pid_hijo = fork();
    int num_files = 5; // Numero temporal por el momento para pruebas.
    int contador = 0;
    
    while( contador <= num_files){
	
	if(pid_hijo == 0) {
	    // Realizar tareas del padre
	    
	} else if (pid_hijo > 0) {
	    // Realizar tareas del hijo
	    //El mismo realizara el backup por cada archivo
	    respaldar(num_files , "foo.txt");

	} else {
	    // Hubo un fallo en creacion del proceso
	    perror("Hubo un error en creacion de un proceso");
	}
	contador++;
    }
    
    return 0;
}

void respaldar(int total_ficheros, char* nombre_fichero) {
    printf("total : %d\n", total_ficheros);
    printf("Fichero: %s\n", nombre_fichero);
    
}



/**
 * Esto solo devuelven las rutas de respaldo y destino escritas correctamente
 * con su formato arrglos de caracteres pero no valida si existen esas rutas
 * Return un arreglo de rutas
 */
void leerRutas(char* ruta_respaldo, char* ruta_destino) {
    //char* rutas[2];
    //char ruta_respaldo[1000];
    //char ruta_destino[1000];
    int resp;
    int flag = 1;
    while(flag){
	printf("\n\tMenu\n");
	printf("1) Entrada estandar (teclado)\n");
	printf("2) Por archivo\n");
	printf("Ingresar numero del metodo(1 o 2): ");
	scanf("%d", &resp);
	if (resp == 1) {
	    // Leemos por entrada estandar.
	    printf("Ingrese la ubicacion de su directorio que va a ser respaldado: ");
	    scanf("%s", ruta_respaldo);
	    printf("Ingrese la ubicacion destino donde se guardara el respaldo: ");
	    scanf("%s", ruta_destino);
		
	    printf("Ruta respaldo: %s\n", ruta_respaldo);
	    printf("Ruta destino: %s\n", ruta_destino);
	    flag = 0;
	} else if ( resp == 2) {
	    // Leemos por archivo .
	    char filename[1000];
	    printf("Ingrese la ubicacion y nombre del archivo (ruta relativa o absoluta): ");
	    scanf("%s", filename);
	    //printf("Su nombre es : %s\n", file_name);
	    FILE* file = fopen(filename, "r");
	    if (file == NULL) {
		printf("No se pudo abrir correctamente su archivo.\n");
		printf("Verificar si existe.\n");
		flag = 1;
	    } else {

		fscanf(file, "%s", ruta_respaldo);
		printf("Ruta respaldo: %s\n", ruta_respaldo);
		    
		fscanf(file, "%s", ruta_destino);
		printf("Ruta destino: %s\n", ruta_destino);
		flag = 0;
	    }
	} else {
	    // Numero incorrecto.
	    printf("Ingreso un numero no valido\n");
	}
    }

}

