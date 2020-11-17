/******************************************************************************************/
/* Backup
   Realiza un respaldo periodico de un directorio a traves de crear 
   un proceso hijo que realice esa tarea, comunicacion entre padre e hijo
   sera implmentada con pipes.
   @author 
   Andres Urbano Guillermo Gerardo.                                   
   Guadarrama Ortega Cesar Alejandro.
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

void leer_rutas(char* ruta_respaldo, char* ruta_destino);
void crear_directorio(char* ruta_destino);
void crear_lista_archivos(char* ruta_respaldo);
void respaldar(char* nombre_fichero, char* ruta_respaldo, char* ruta_destino);


int main(int argc, char *argv[]) {
    
    char ruta_respaldo[1000];
    char ruta_destino[1000];
    int pipe_padre_hijo[2], pipe_hijo_padre[2];
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
	leer_rutas(ruta_respaldo, ruta_destino);
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
    // 1-1) Genera un archivo con la lista de los nombres de los archivos
    // del directorio a respaldar y el numero total de archivos

    crear_lista_archivos(ruta_respaldo);

    //2) Crear directorio de respaldo, si el directorio de respaldo ya existe
    // debera de eliminarlo.
    // Hasta el momento tenemos las rutas pero no sabesmo si exiten.

    crear_directorio(ruta_destino);

    /* 
      3) Enviar al hijo el numero total de archivos a respaldar
      y cada uno de los nombres de los archivos a respaldar, uno a la vez.
      En este momento creamos nuestros pipes para comunicacion padre-hijo.
      
      El pipe_padre_hijo 
      padre escribe al -> hijo   
      hijo lee del -> padre
      
      El pipe_hijo_padre
      hijo escribe al -> padre
      padre lee del -> hijo
    */

    // Capturando la posible exception de crear un pipe
    if(pipe(pipe_padre_hijo) < 0 || pipe(pipe_hijo_padre) < 0) {
	perror("\nError al crear el pipe");
	exit(2);
    }
    
    char num_files_char[4];
    int num_files;
    char msg[1000];
    char linea[1000];
   
    /* printf("num_files_char: %d\n", num_files); */

    pid_hijo = fork();
	
    if(pid_hijo > 0) { // PROCESO PADRE
	// Realizar tareas del padre
	close(pipe_padre_hijo[0]); //  Cierro el descriptor de lectura, porque va escribir.
	close(pipe_hijo_padre[1]); // Cierro el descriptor de escritura, porque va escuchar(leer).
	FILE* lista_archivos = fopen("lista_archivos.txt", "r");
	// Capturamos la posible exepcion por si no exite el archivo;
	if(lista_archivos == NULL) {
	    printf("No es posible abrir el archivo lista_archivos.txt");
	    exit(-3);
	}
	
	fscanf(lista_archivos, "%s", num_files_char); // Aqui dos caractes el numero y \0.
	//printf("num_files_char: %s\n", num_files_char);
	//printf("num_files_int: %ld\n", strlen(num_files_char));
	num_files = atoi(num_files_char);
	// Mando el el numero de archivos al pipe padre-hijo
	printf("Padre(pid= %i), Enviar mensaje a mi hijo de hacer un respaldo... \n", getpid());
	int num_bytes_escritos = write(pipe_padre_hijo[1], num_files_char, strlen(num_files_char) );
	//printf("Padre(pid=%i) numeros de bytes escritos : %d\n",getpid(), num_bytes_escritos);

	// Con esto el hjo solo el numero y porque existe la posibilidad
	// que el padre esta mas tiempo en el procesador y empiece a ejecutar todos los
	// write escribiendolos en el buffer, entonces en el momento de leer con el hijo
	// funcion read va a tomar todo una cadena de nombres todas juntas.
	sleep(2);
	
	// 4) El padre estara en un ciclo leyendo el nombre del archivo y
	// enviandoselo al hijo
	int i;
	printf("Va entrar al for\n");
	for(i=0; i < num_files; i++) {
	    fscanf(lista_archivos, "%s", linea);
	    printf("Padre(pid=%d): Linea %d que le voy a mandar al hijo: %s\n",getpid(),i+1, linea);
	    //printf("entrar al write\n");
	    num_bytes_escritos = write(pipe_padre_hijo[1], linea, strlen(linea) + 1);
	    //printf("Padre(pid=%d): Numeros de bytes escritos en la %d linea: %d\n", getpid(),i+1, num_bytes_escritos);
	    sleep(2);
	    //printf("Salir write\n");
	    
	}
	printf("Salio del for\n");
	// Eschucar la respuesta del hijo para concoer si ya termino y con cuantos archivos.
	/* read(pipe_hijo_padre[0], msg , 500); */
	/* printf("Padre(pid=%d): Mensaje de mi hijo: %s\n", getpid(), msg); */
	// Leer el numero de respaldados
	//sleep(2); //Esperas para que el hijo te mande el total, sino vovera a leer lo que estaba en el buffer.
	int num;
	read(pipe_hijo_padre[0], &num , sizeof(num));
	printf("Padre(pid=%d): Recibi por el hijo un TOTAL de %d archivos respaldados con exito\n", getpid(), num);
	/* read(pipe_hijo_padre[0], msg , 500); */
	/* printf("Padre(pid=%d): Recibi el TOTAL de %s archivos respaldados con exito\n", getpid(), msg); */

	
	//Imprimir el listado de directorios de respaldo.
	char command[1000] = "ls -l ";
	strcat(command, ruta_destino);
	system(command);
	printf("%d\t ARCHIVOS RESPALDADOS\n", num);
	printf("\n-----Termino proceso padre (pid=%d)-----\n", getpid());
	
	// Cerramos conexiones
	fclose(lista_archivos);
	close(pipe_padre_hijo[1]); // Cierro el descriptor de escritura porque ya no lo voy ocupar.
	close(pipe_hijo_padre[0]); // Cierro el descriptor de lectura.                             
	    
    } else if (pid_hijo == 0) { // PROCESO HIJO
	// Realizar tareas del hijo
	close(pipe_hijo_padre[0]); // Cierro el descriptor lectura. porque va escribir.
	close(pipe_padre_hijo[1]); //Cierro el descriptor de escritura, por que va leer(escuchar). 
	//El mismo realizara el backup por cada archivo
	printf("\tHijo(pid=%i) Esperando mensaje de mi padre...\n", getpid());

	// Aqui le digo lee del pipe padre y tienes un maximo de 256 caracteres si es menor toma solo eso.
	int num_bytes_leidos = read(pipe_padre_hijo[0],msg,500); // (file_descriptor, messageAlmacenar, cantMaxBytes) */
	//printf("\tHijo(pid=%d), numero de bytes leidos: %d\n", getpid(), num_bytes_leidos);
	printf("\tHijo(pid=%i), el mensaje enviado de mi padre es: %s\n", getpid(), msg);
	num_files = atoi(msg);
	printf("\tHijo(pid=%d), Total de archivo enviados de mi padre: %d\n", getpid(), num_files);
	sleep(1);
	int i;
	for(i = 0; i < num_files; i++) {
	    num_bytes_leidos = read(pipe_padre_hijo[0] , msg, 500);
	    printf("\tHijo(pid=%d), Texto enviado de mi padre: %s\n", getpid(), msg);
	    printf("\tHijo(pid=%i), respaldando el archivo %s ................ pendientes %d\n", getpid(), msg, num_files-i); //contador decendente de numero de archivos
	    respaldar(msg,ruta_respaldo, ruta_destino); // funcion de respaldar para hacer el backup al nuevo respaldo
	}

	// Mandar mensaje al padre que termino y numeros respaldados;
	write(pipe_hijo_padre[1], &num_files, sizeof(num_files));
	
	//char msg_terminado[1000] = "Padre ya termine de respaldar los archivos";
	//write(pipe_hijo_padre[1], msg_terminado, strlen(msg_terminado)+1);
	//sleep(1); // Me espero un rato para que lea el mensaje y lo digiera.
	//char otraFrase[1000] = "Hola mundo";
	//write(pipe_hijo_padre[1], otraFrase, strlen(otraFrase)+1);

	//Mandar mensaje que termino.
	printf("\n------Termina proceso hijo(pid=%d)--------\n", getpid());

	//respaldar(num_files,msg,ruta_destino,ruta_respaldo);
	//Cerramos conexiones
	close(pipe_hijo_padre[1]); // Cierro el descriptor de escritura porque ya no lo voy ocupar.
	close(pipe_padre_hijo[0]); // Cierro el descriptor de lectura.
	    
    } else {
	// Hubo un fallo en creacion del proceso
	perror("Hubo un error en creacion de un proceso");
	exit(-1);
    }

    
    return 0;
}

/**
 * El hijo que resibio la lista de archivos se la asignamos al parametro fichero 
 * Ya tenemos la ruta del directorio deseado para el respaldo y lo asignamos al parametro des
 * Como final regresa una operacion al sistema con el comando completo
 * con su formato arrglos de caracteres pero no valida si existen esas rutas.
 */
void respaldar(char* nombre_fichero, char* ruta_respaldo, char* ruta_destino) {
    printf("Fichero: %s\n", nombre_fichero); //verificamos el nombre del archivo
    printf("Destino: %s\n", ruta_destino); // verificamos el destino del archivo
    char comando[1000] = "cp ";  // variable para almacenar toda la cadena

    //Comando a ejecutar
    // cp /home/directorioRespaldo/fichero.c  /home/ditectorioDestino/
    strcat(comando, ruta_respaldo);
    //Capturando posible error si no se coloca como ultimo caracter / para directorio respaldo.
    // printf("Ultimo caracter ruta_respaldo %c\n", ruta_respaldo[strlen(ruta_respaldo) - 1]);
    if(ruta_respaldo[strlen(ruta_respaldo) - 1] != '/'){
	//Le ponemos ese caracter nosotros.
	strcat(comando, "/");
    }
    strcat(comando, nombre_fichero);
    strcat(comando, " ");
    strcat(comando, ruta_destino);
    
    /* char* aux = nombre_fichero; // variable aux para no perder el fichero por parte del hijo */
    /* char* destino = ruta_destino; // varible destino para no perder la referencia */
    /* sprintf(comando,"cp %s %s",aux,ruta_destino); //concatenacion de las cadenas para hacer el comando dejando espacios */
    printf("Comando completo copiar directorio: %s\n",comando); //imprimimos el comando completo
    int status = system(comando); //llamada al system
}


/**
 * Lee del usuario las rutas de respaldo y destiono, ya sea de entrada 
 * estandar o archivo.
 * Escribe por referencia las rutas de respaldo y destino escritas correctamente
 * con su formato arrglos de caracteres pero no valida si existen esas rutas.
 */
void leer_rutas(char* ruta_respaldo, char* ruta_destino) {
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
	    // Captura la posible excepcion si no se pudo abrir.
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
		fclose(file);
	    }

	} else {
	    // Numero incorrecto.
	    printf("Ingreso un numero no valido\n");
	}
    }

}


/**
 * Genera un archivo con la lista de los nombres de archivos del
 * directorio a respaldar y el numero total de archivos.
 */
void crear_lista_archivos(char* ruta_respaldo) {
    // Crea un archivo automaticamente, si ya existe lo eliminar y lo
    // vuelve a crear actualizando con los numeros valores
    printf("Padre(pid=%d): Generando lista de archivos a respaldar (lista_archivos.txt)\n", getpid());
    //    system("rm lista_archivos.txt");
    char comandoCompleto[1000] = "ls ";
    char comandoAux[1000] = "";
    strcat(comandoCompleto, ruta_respaldo);
    char *ads = strcat(comandoAux, comandoCompleto);
    printf("Comando completo: %s\n", ads );
    system(strcat(comandoAux," | wc -l > lista_archivos.txt"));
    //strcat(comandoAux, comandoCompleto);
    system(strcat(comandoCompleto," >> lista_archivos.txt"));
}


/**
 * Crea el directorio donde se guardaran los respaldos,
 * si ya existe lo elimina.
 * El nombre correpondera con la fecha actual del sistema.
 */
void crear_directorio(char* ruta_destino) {
    time_t tiempo;
    char comando[1000];
    // Eliminar directorio
    printf("Padre(pid=%d): Eliminando respaldo viejo si es que existe ...\n", getpid());
    sleep(1);
    strcpy(comando, "rm -rvf ");
    strcat(comando, ruta_destino);
    printf("Comando completo: %s\n", comando);
    // Un valor cero significa que se ejecuto el comando exitosamente
    // Un numero distinto de cero, es que algo ocurrio mal.
    int status = system(comando);
    //printf("Status; %d\n", status);

    // Crea directorio
    printf("Creando un directio para su respaldo ...\n");
    sleep(2);
    strcpy(comando, "mkdir ");
    strcat(comando, ruta_destino);
    time(&tiempo);
    char* fecha = ctime(&tiempo);
    /* printf("Fecha: %s", fecha);     */
    /* //Elimina los espacios en blanco */
    /* for (int i = 0; i < strlen(fecha); ++i) { */
    /* 	if(fecha[i] == ' '){ */
    /* 	    fecha[i] = '_'; */
    /* 	} */
    /* } */
    /* //printf("Fecha: %s\n", fecha);     */
    /* strcat(comando, fecha); */
    printf("Comando completo: %s\n\n", comando);
    system(comando);    
}
