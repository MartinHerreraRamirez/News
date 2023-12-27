# Primera Parte

> [!NOTE]
> * 
> Proyecto Java 8 utilizando Spring, Spring Boot, Spring Security, Spring Web, Spring Data JPA, Lombok,  MySQL Driver, Thymeleaf,  CSS, Bootstrap.

## Detalles

### SITIO DE NOTICIAS
El objetivo de este ejercicio consiste en el desarrollo de un sistema web en Java utilizando una base de datos MySQL, JPA Repository para persistir objetos y Spring Boot como framework de desarrollo web. Creación de la Base de Datos MySQL. Crear el esquema sobre el cual operará el sistema de noticias. Para esto, en el IDE de base de datos que esté utilizando (por ejemplo, Workbench) ejecute la sentencia:

```sql
CREATE DATABASE news;
```

## Paquetes del Proyecto
_Los paquetes que se deben utilizar para el proyecto se deben estructurar de la siguiente manera:_
#### • vistas: en este paquete se almacenarán aquellas clases que se utilizarán como vistas con el usuario.
#### • controladores: en este paquete se almacenarán aquellas clases que se utilizarán para mediar entre la vista con el usuario y las capas inferiores.
#### • servicios: en este paquete se almacenarán aquellas clases que llevarán adelante lógica del negocio.
#### • repositorios: en este paquete se crearán los repositorios que servirán como interfaces entre el modelo de objetos y la base de datos relacional.
#### • entidades: en este paquete se almacenarán aquellas clases que es necesario persistir en la base de datos.

## Capa entidades
Spring utiliza una anotación para identificar aquellas clases que serán entidades y repositorios.
Todas las entidades deben estar marcadas con la anotación @Entity y los repositorios con la
anotación @Repository, los repositorios heredarán la interfaz JPARepository, que nos dará todos
los métodos para persistir, editar, eliminar, etc.

## Entidad Noticia
La entidad Noticia modela las noticias que se publicarán en la web. En esta entidad, el atributo “título” contiene el nombre con el cuál vamos a encontrar la noticia, mientras que el atributo “cuerpo” contiene toda la información que queremos que el usuario pueda leer.
El repositorio que persiste a esta entidad (NoticiaRepositorio) debe contener los métodos necesarios para guardar/actualizar noticias en la base de datos, realizar consultas o dar de baja según corresponda.

## Capa de Servicios
Spring utiliza una anotación para identificar aquellas clases que serán servicios. Todos los servicios deben estar marcados con la anotación **@Service** Esta clase tiene la responsabilidad de llevar adelante las funcionalidades necesarias para administrar noticias (consulta, creación, modificación y dar de baja).

## Capa de Comunicación
Spring utiliza una anotación para identificar aquellas clases que serán controladores. Todos los controladores deben estar marcados con la anotación **@Controller**.

## NoticiaControlador
Esta clase tiene la responsabilidad de llevar adelante las funcionalidades necesarias para operar con la vista del usuario diseñada para la gestión de noticias (guardar/modificar noticia, listar noticias, dar de baja, etc).

## Capa de Vistas
Esta capa tiene la responsabilidad de llevar adelante las funcionalidades necesarias para interactuar con el usuario. Las vistas para este proyecto tienen que estar desarrolladas en HTML5 y se debe utilizar la biblioteca Thymeleaf y CSS para implementar las plantillas. Además, se debe utilizar el framework de Bootstrap para los componentes.

> [!IMPORTANT]
> Se deben diseñar y crear todas las vistas web necesarias para llevar a cabo las siguientes funcionalidades:
> #### • Vista inicio: en esta vista deben estar las tarjetas(bootstrap) con el título de cada noticia, ordenadas de más reciente a más antigua.
>
> #### • Vista noticia: en esta vista tendremos el acceso a la noticia completa (cuerpo). Es la vista que se abre cuando hacemos click en alguna tarjeta de la vista inicio.
>
> #### • Vista panelAdmin: en esta vista es donde gestionaremos las noticias. Aquí encontraremos los formularios necesarios para crear, modificar o eliminar una Noticia.

# Segunda Parte

## Detalles

El cliente nos envió nuevos requerimientos para la aplicación web que desarrollamos en la guía anterior. Es por esto que vamos a continuar trabajando sobre el mismo proyecto y usando la misma base de datos para completar nuestro EggNews.
Además de las entidades que ya teníamos, crearemos tres nuevas entidades: Usuario, Periodista y Administrador.

## Entidad Usuario
La entidad Usuario modela los usuarios que se registran y loguean en la web. Esta entidad debe poseer los siguientes atributos:
#### • String nombreUsuario
#### • String password
#### • Date fecha de alta.
#### • Rol role
#### • Boolean activo

> [!IMPORTANT]
> Un Usuario debe loguearse para ver las noticias, y perderá la posibilidad de ingresar a la vista panelAdmin.
>_Las entidades Periodista y Administrador deben extender de Usuario._

## Entidad Periodista
_La entidad Periodista tendrá como atributos extras:_
#### • ArrayList<Noticia> misNoticias
#### • Integer sueldoMensual.

> [!IMPORTANT]
> Un Periodista debe loguearse en el sistema para poder acceder a crear y modificar Noticias.
> Esta acción deberá realizarse desde la vista panelAdmin.

## Entidad Administrador
_La entidad Administrador podrá:_

> [!IMPORTANT]
> #### • Podrá crear, modificar y eliminar noticias
> #### • Dar de alta o baja a Periodistas (modificar el atributo activo).
> #### • Indicar cuál va a ser el sueldoMensual de cada Periodista.
> #### Un Administrador debe loguearse en el sistema para poder acceder a sus funcionalidades.

