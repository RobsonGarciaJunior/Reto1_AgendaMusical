# Reto1_AgendaMusical
# Built With (construido con)

Tecnologías que se han utilizado para crear toda la capa del servidor:
Spring Boot 3.1.4
MySQL

# Getting Started

A continuación, se listara una serie de pasos a seguir para poder llevar a cabo la instalación del proyecto:

# Prerrequisites (Prerrequisitos para arrancar el proyecto)

Antes de meterse a la propia instalación, se necesita una serie de requisitos. Entre ellos, se encuentra la instalación de MySQL Workbench, y para tener unos datos predefinidos con los que podamos hacer pruebas a la hora de instalar el servidor, se necesitará crear una base de datos con las tablas y campos necesarios, con la ayuda del diagrama E-R realizado.

Después de la creación de las tablas con sus campos respectivos, tendremos que realizar unos inserts rellenando los campos (En el campo de la id estará como null, ya que en el script de la base de datos indicaremos que es un número que se pone automáticamente).
Installation (Instalación)

Como se trata de un proyecto que estamos instalando y no uno que estamos construyendo desde cero, estará subido a github. Por lo tanto, se tendría que hacer lo siguiente:

1. Vamos al repositorio donde se encuentra el proyecto
2. Lo clonamos clicando en el botón llamado “<> Code”
3. Accedemos a git bash en la carpeta de “eclipse-workspace”
4. En git bash, escribimos “git clone (el link que hemos clonado del repositorio de github)”
5. Una vez instalado después de cumplir los pasos ya indicados, si es necesario, se deberá modificar las propiedades en el archivo “application.properties”
- spring.datasource.url: aquí se indicará la ruta de la base de datos de la aplicación
- spring.datasource.username: se deberá introducir el nombre del usuario de la base de datos
- spring.datasource.password: la contraseña del usuario indicado en el username
- spring.datasource.driver-class-name: aqui se debera de indicar el conductor que conectará la aplicación con los datos de su base de datos
- server.port: el puerto que se está utilizando para la base de datos

Una vez realizados estos pasos, la aplicación debería de estar completamente instalada.
# Usage (uso)

Una vez instalada la aplicación en eclipse, para arrancar el servidor tendremos que acceder al núcleo de la aplicación, ubicado en la carpeta src/main/java.

Cuando estemos situados en la dirección indicada, encontraremos una clase con el nombre Reto1ServerApplication.java. Entramos, y desde ahí podremos iniciar la aplicación clicando el botón de run situado en la parte superior izquierda de Eclipse.

Después, podremos acceder a Postman, una aplicación que se utiliza para comprobar que las rutas de un servidor funcionan correctamente, y también es capaz de comprobar los errores que puede dar una petición errónea.

En Postman haremos una carpeta nueva sobre el proyecto,, crearemos las peticiones de GET, PUT y POST necesarias, e indicaremos en cada una la ruta de la siguiente forma:

http://(ip):(puerto)/api

Hay que tener en cuenta que la ip y el puerto deberan de ser las mismas a las indicadas anteriormente en el application.properties.

Por ejemplo, si utilizamos esta ruta con una petición GET http://(ip):(puerto)/api/songs, nos tendrá que devolver todas las canciones disponibles en la base de datos del servidor

# API Documentation (Documentación)
Con la API, aparte de las peticiones que se pueden probar en Postman, también se podrán consultar varias mas peticiones con el siguiente link:

http://10.5.7.176:8065/api/
(El servidor tendrá que estar en uso si se quieren probar estas peticiones)

# Contact (Contacto)
Iker Portela (iker.portelapl@elorrieta-errekamari.com)
Robson Garcia (robson.garciaju@elorrieta-errekamari.com)
Jon Gallego (jon.gallegoaz@elorrieta-errekamari.com)

# License (Licencia)
Distributed under the MIT License.

