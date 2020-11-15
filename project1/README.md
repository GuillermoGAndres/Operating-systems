# Backups 

## Usage
~~~
./backup directorio_respadar directorio_destino
~~~
Las entradas (rutas relativas o absoluta) pueden ser:
* Entrada estandar (teclado)
* Por parametros 
* Por un archivo

Para el formato de entrada de archivos, puede ser entre entre espacios:
~~~
myFIle.txt
/home/user/Documents/DirectorioRespaldo  /home/user/Documents/DirectorioDestino
~~~
O puede ser tambien escritos sobre una new line(\n):
~~~
myFIle.txt
/home/user/Documents/DirectorioRespaldo
/home/user/Documents/DirectorioDestino
~~~

## Compilacion
~~~
gcc backup.c -o backup
~~~
