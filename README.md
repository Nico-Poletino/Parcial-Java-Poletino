*Sistema de Gestión Escolar
    Este es un sistema de gestión escolar desarrollado en Java que permite administrar estudiantes, cursos e inscripciones. El sistema utiliza una base de datos H2 para almacenar la información de manera persistente.

*Características Principales
 ºGestión completa de estudiantes (CRUD)
 ºGestión de cursos académicos (CRUD)
 ºSistema de inscripciones
 ºPersistencia de datos usando H2 Database
 ºInterfaz por consola intuitiva

*Base de Datos
    El sistema utiliza H2 Database en modo archivo, lo que significa que los datos persisten entre ejecuciones. La base de datos se almacena en el directorio `./data/escuela`.


*Cómo Usar la Aplicación
Ejecutar la Aplicación

.\gradlew.bat run

Menú Principal
La aplicación presenta un menú principal con las siguientes opciones:

1- **Gestionar Estudiantes**
   - Crear nuevo estudiante
   - Listar todos los estudiantes
   - Buscar estudiante por ID
   - Actualizar información de estudiante
   - Eliminar estudiante

2- **Gestionar Cursos**
   - Crear nuevo curso
   - Listar todos los cursos
   - Buscar curso por ID
   - Actualizar información de curso
   - Eliminar curso

3- **Gestionar Inscripciones**
   - Crear nueva inscripción
   - Listar todas las inscripciones
   - Buscar inscripción por ID
   - Listar inscripciones por estudiante
   - Listar inscripciones por curso
   - Cancelar inscripción

*Uso Básico

1- Para crear un estudiante:
   - Seleccione opción 1 en el menú principal
   - Seleccione opción 1 en el menú de estudiantes
   - Complete los datos solicitados (nombre, apellido, email, fecha de nacimiento)

2- Para crear un curso:
   - Seleccione opción 2 en el menú principal
   - Seleccione opción 1 en el menú de cursos
   - Complete los datos solicitados (nombre, descripción, cupo máximo)

3- Para inscribir un estudiante en un curso:
   - Seleccione opción 3 en el menú principal
   - Seleccione opción 1 en el menú de inscripciones
   - Ingrese el ID del estudiante y del curso

*Notas Importantes

- Los IDs son generados automáticamente por el sistema
- Las fechas deben ingresarse en formato dd/MM/yyyy
- Los datos se guardan automáticamente en la base de datos
- Puede salir del programa en cualquier momento seleccionando la opción 0 