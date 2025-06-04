package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.dao.CursoDAO;
import org.example.dao.EstudianteDAO;
import org.example.dao.InscripcionDAO;
import org.example.model.Curso;
import org.example.model.Estudiante;
import org.example.model.Inscripcion;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);
    private static final EstudianteDAO estudianteDAO = new EstudianteDAO();
    private static final CursoDAO cursoDAO = new CursoDAO();
    private static final InscripcionDAO inscripcionDAO = new InscripcionDAO();
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void main(String[] args) {
        logger.info("Iniciando");
        
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                try {
                    mostrarMenuPrincipal();
                    String input = scanner.nextLine();
                    if (input == null || input.trim().isEmpty()) {
                        System.out.println("Ingrese una opcion valida.");
                        continue;
                    }
                    
                    int opcion = Integer.parseInt(input.trim());
                    
                    switch (opcion) {
                        case 1 -> gestionarEstudiantes(scanner);
                        case 2 -> gestionarCursos(scanner);
                        case 3 -> gestionarInscripciones(scanner);
                        case 0 -> {
                            logger.info("Finalizando");
                            return;
                        }
                        default -> System.out.println("Opcion no valida");
                    }
                } catch (NumberFormatException e) {
                    logger.error("Error: entrada no valida", e);
                    System.out.println("Ingrese un numero valido.");
                } catch (Exception e) {
                    logger.error("Error en el menu principal", e);
                    System.out.println("Error: " + e.getMessage());
                }
            }
        }
    }

    private static void mostrarMenuPrincipal() {
        System.out.println("Bienvenido al Sistema de Gestion Escolar!");
        System.out.println("MENU PRINCIPAL");
        System.out.println("1- Gestionar Estudiantes");
        System.out.println("2- Gestionar Cursos");
        System.out.println("3- Gestionar Inscripciones");
        System.out.println("0- Salir");
        System.out.print("Seleccione una opcion: ");
    }

    private static void gestionarEstudiantes(Scanner scanner) {
        while (true) {
            System.out.println("\nGESTION DE ESTUDIANTES");
            System.out.println("1- Crear estudiante");
            System.out.println("2- Listar estudiantes");
            System.out.println("3- Buscar estudiante por ID");
            System.out.println("4- Actualizar estudiante");
            System.out.println("5- Eliminar estudiante");
            System.out.println("0- Volver al menu principal");
            System.out.print("Seleccione una opcion: ");

            try {
                String input = scanner.nextLine();
                if (input == null || input.trim().isEmpty()) {
                    System.out.println("Ingrese una opcion valida.");
                    continue;
                }
                
                int opcion = Integer.parseInt(input.trim());
                
                switch (opcion) {
                    case 1 -> crearEstudiante(scanner);
                    case 2 -> listarEstudiantes();
                    case 3 -> buscarEstudiantePorId(scanner);
                    case 4 -> actualizarEstudiante(scanner);
                    case 5 -> eliminarEstudiante(scanner);
                    case 0 -> {
                        return;
                    }
                    default -> System.out.println("Opcion no valida");
                }
            } catch (NumberFormatException e) {
                logger.error("Error: entrada no valida", e);
                System.out.println("Ingrese un numero valido.");
            } catch (Exception e) {
                logger.error("Error en gestion de estudiantes", e);
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void gestionarCursos(Scanner scanner) {
        while (true) {
            System.out.println("\nGESTION DE CURSOS");
            System.out.println("1- Crear curso");
            System.out.println("2- Listar cursos");
            System.out.println("3- Buscar curso por ID");
            System.out.println("4- Actualizar curso");
            System.out.println("5- Eliminar curso");
            System.out.println("0- Volver al menu principal");
            System.out.print("Seleccione una opcion: ");

            try {
                String input = scanner.nextLine();
                if (input == null || input.trim().isEmpty()) {
                    System.out.println("Ingrese una opcion valida.");
                    continue;
                }
                
                int opcion = Integer.parseInt(input.trim());
                
                switch (opcion) {
                    case 1 -> crearCurso(scanner);
                    case 2 -> listarCursos();
                    case 3 -> buscarCursoPorId(scanner);
                    case 4 -> actualizarCurso(scanner);
                    case 5 -> eliminarCurso(scanner);
                    case 0 -> {
                        return;
                    }
                    default -> System.out.println("Opcion no valida");
                }
            } catch (NumberFormatException e) {
                logger.error("Error: entrada no valida", e);
                System.out.println("Ingrese un numero valido.");
            } catch (Exception e) {
                logger.error("Error en gestion de cursos", e);
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void gestionarInscripciones(Scanner scanner) {
        while (true) {
            System.out.println("\nGESTION DE INSCRIPCIONES");
            System.out.println("1- Crear inscripcion");
            System.out.println("2- Listar inscripciones");
            System.out.println("3- Buscar inscripcion por ID");
            System.out.println("4- Listar inscripciones por estudiante");
            System.out.println("5- Listar inscripciones por curso");
            System.out.println("6- Cancelar inscripcion");
            System.out.println("0- Volver al menu principal");
            System.out.print("Seleccione una opcion: ");

            try {
                String input = scanner.nextLine();
                if (input == null || input.trim().isEmpty()) {
                    System.out.println("Ingrese una opcion valida.");
                    continue;
                }
                
                int opcion = Integer.parseInt(input.trim());
                
                switch (opcion) {
                    case 1 -> crearInscripcion(scanner);
                    case 2 -> listarInscripciones();
                    case 3 -> buscarInscripcionPorId(scanner);
                    case 4 -> listarInscripcionesPorEstudiante(scanner);
                    case 5 -> listarInscripcionesPorCurso(scanner);
                    case 6 -> cancelarInscripcion(scanner);
                    case 0 -> {
                        return;
                    }
                    default -> System.out.println("Opcion no valida");
                }
            } catch (NumberFormatException e) {
                logger.error("Error: entrada no valida", e);
                System.out.println("Ingrese un numero valido.");
            } catch (Exception e) {
                logger.error("Error en gestion de inscripciones", e);
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void crearEstudiante(Scanner scanner) throws Exception {
        System.out.println("\nCREAR ESTUDIANTE");
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine().trim();
        System.out.print("Apellido: ");
        String apellido = scanner.nextLine().trim();
        System.out.print("Email: ");
        String email = scanner.nextLine().trim();
        System.out.print("Fecha de nacimiento (Dia/Mes/Año): ");
        System.out.println("Use / para dividir Dia/Mes/Año");
        String fechaNacStr = scanner.nextLine().trim();
        
        Estudiante estudiante = new Estudiante();
        estudiante.setNombre(nombre);
        estudiante.setApellido(apellido);
        estudiante.setEmail(email);
        estudiante.setFechaNacimiento(LocalDate.parse(fechaNacStr, dateFormatter));
        
        estudianteDAO.save(estudiante);
        System.out.println("Estudiante creado exitosamente con ID: " + estudiante.getId());
    }

    private static void listarEstudiantes() throws Exception {
        System.out.println("\nLISTA DE ESTUDIANTES");
        List<Estudiante> estudiantes = estudianteDAO.findAll();
        if (estudiantes.isEmpty()) {
            System.out.println("No hay estudiantes registrados");
            return;
        }
        
        for (Estudiante estudiante : estudiantes) {
            System.out.println(estudiante);
        }
    }

    private static void buscarEstudiantePorId(Scanner scanner) throws Exception {
        System.out.print("\nIngrese el ID del estudiante: ");
        String input = scanner.nextLine().trim();
        if (input.isEmpty()) {
            System.out.println("ID invalido");
            return;
        }
        Long id = Long.parseLong(input);
        
        estudianteDAO.findById(id).ifPresentOrElse(
            estudiante -> System.out.println(estudiante),
            () -> System.out.println("Estudiante no encontrado")
        );
    }

    private static void actualizarEstudiante(Scanner scanner) throws Exception {
        System.out.print("\nIngrese el ID del estudiante a actualizar: ");
        String input = scanner.nextLine().trim();
        if (input.isEmpty()) {
            System.out.println("ID invalido");
            return;
        }
        Long id = Long.parseLong(input);
        
        var estudianteOpt = estudianteDAO.findById(id);
        if (estudianteOpt.isEmpty()) {
            System.out.println("Estudiante no encontrado");
            return;
        }
        
        var estudiante = estudianteOpt.get();
        System.out.println("Datos actuales: " + estudiante);
        
        System.out.print("Nuevo nombre (Enter para mantener actual): ");
        String nombre = scanner.nextLine().trim();
        if (!nombre.isEmpty()) estudiante.setNombre(nombre);
        
        System.out.print("Nuevo apellido (Enter para mantener actual): ");
        String apellido = scanner.nextLine().trim();
        if (!apellido.isEmpty()) estudiante.setApellido(apellido);
        
        System.out.print("Nuevo email (Enter para mantener actual): ");
        String email = scanner.nextLine().trim();
        if (!email.isEmpty()) estudiante.setEmail(email);
        
        System.out.print("Nueva fecha de nacimiento (Dia/Mes/Año) (Enter para mantener actual): ");
        String fechaNacStr = scanner.nextLine().trim();
        if (!fechaNacStr.isEmpty()) {
            estudiante.setFechaNacimiento(LocalDate.parse(fechaNacStr, dateFormatter));
        }
        
        estudianteDAO.update(estudiante);
        System.out.println("Estudiante actualizado exitosamente");
    }

    private static void eliminarEstudiante(Scanner scanner) throws Exception {
        System.out.print("\nIngrese el ID del estudiante a eliminar: ");
        String input = scanner.nextLine().trim();
        if (input.isEmpty()) {
            System.out.println("ID invalido");
            return;
        }
        Long id = Long.parseLong(input);
        
        if (estudianteDAO.findById(id).isEmpty()) {
            System.out.println("Estudiante no encontrado");
            return;
        }
        
        System.out.print("¿Esta seguro de eliminar el estudiante? (s/n): ");
        String confirmacion = scanner.nextLine().trim().toLowerCase();
        if (confirmacion.equals("s")) {
            estudianteDAO.delete(id);
            System.out.println("Estudiante eliminado");
        }
    }

    private static void crearCurso(Scanner scanner) throws Exception {
        System.out.println("\nCREAR CURSO");
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine().trim();
        System.out.print("Descripcion: ");
        String descripcion = scanner.nextLine().trim();
        System.out.print("Cupo maximo: ");
        String cupoStr = scanner.nextLine().trim();
        if (cupoStr.isEmpty()) {
            System.out.println("Cupo maximo invalido");
            return;
        }
        int cupoMaximo = Integer.parseInt(cupoStr);
        
        Curso curso = new Curso();
        curso.setNombre(nombre);
        curso.setDescripcion(descripcion);
        curso.setCupoMaximo(cupoMaximo);
        curso.setActivo(true);
        
        cursoDAO.save(curso);
        System.out.println("Curso creado exitosamente con ID: " + curso.getId());
    }

    private static void listarCursos() throws Exception {
        System.out.println("\nLISTA DE CURSOS");
        List<Curso> cursos = cursoDAO.findAll();
        if (cursos.isEmpty()) {
            System.out.println("No hay cursos registrados");
            return;
        }
        
        for (Curso curso : cursos) {
            System.out.println(curso);
        }
    }

    private static void buscarCursoPorId(Scanner scanner) throws Exception {
        System.out.print("\nIngrese el ID del curso: ");
        String input = scanner.nextLine().trim();
        if (input.isEmpty()) {
            System.out.println("ID invalido");
            return;
        }
        Long id = Long.parseLong(input);
        
        cursoDAO.findById(id).ifPresentOrElse(
            curso -> System.out.println(curso),
            () -> System.out.println("Curso no encontrado")
        );
    }

    private static void actualizarCurso(Scanner scanner) throws Exception {
        System.out.print("\nIngrese el ID del curso a actualizar: ");
        String input = scanner.nextLine().trim();
        if (input.isEmpty()) {
            System.out.println("ID invalido");
            return;
        }
        Long id = Long.parseLong(input);
        
        var cursoOpt = cursoDAO.findById(id);
        if (cursoOpt.isEmpty()) {
            System.out.println("Curso no encontrado");
            return;
        }
        
        var curso = cursoOpt.get();
        System.out.println("Datos actuales: " + curso);
        
        System.out.print("Nuevo nombre (Enter para mantener actual): ");
        String nombre = scanner.nextLine().trim();
        if (!nombre.isEmpty()) curso.setNombre(nombre);
        
        System.out.print("Nueva descripcion (Enter para mantener actual): ");
        String descripcion = scanner.nextLine().trim();
        if (!descripcion.isEmpty()) curso.setDescripcion(descripcion);
        
        System.out.print("Nuevo cupo maximo (Enter para mantener actual): ");
        String cupoMaximoStr = scanner.nextLine().trim();
        if (!cupoMaximoStr.isEmpty()) {
            curso.setCupoMaximo(Integer.parseInt(cupoMaximoStr));
        }
        
        System.out.print("Esta activo? (s/n) (Enter para mantener actual): ");
        String activoStr = scanner.nextLine().trim().toLowerCase();
        if (!activoStr.isEmpty()) {
            curso.setActivo(activoStr.equals("s"));
        }
        
        cursoDAO.update(curso);
        System.out.println("Curso actualizado exitosamente");
    }

    private static void eliminarCurso(Scanner scanner) throws Exception {
        System.out.print("\nIngrese el ID del curso a eliminar: ");
        String input = scanner.nextLine().trim();
        if (input.isEmpty()) {
            System.out.println("ID invalido");
            return;
        }
        Long id = Long.parseLong(input);
        
        if (cursoDAO.findById(id).isEmpty()) {
            System.out.println("Curso no encontrado");
            return;
        }
        
        System.out.print("¿Esta seguro de eliminar el curso? (s/n): ");
        String confirmacion = scanner.nextLine().trim().toLowerCase();
        if (confirmacion.equals("s")) {
            cursoDAO.delete(id);
            System.out.println("Curso eliminado");
        }
    }

    private static void crearInscripcion(Scanner scanner) throws Exception {
        System.out.println("\nCREAR INSCRIPCION");
        System.out.print("ID del estudiante: ");
        String estudianteIdStr = scanner.nextLine().trim();
        if (estudianteIdStr.isEmpty()) {
            System.out.println("ID de estudiante invalido");
            return;
        }
        Long estudianteId = Long.parseLong(estudianteIdStr);
        
        System.out.print("ID del curso: ");
        String cursoIdStr = scanner.nextLine().trim();
        if (cursoIdStr.isEmpty()) {
            System.out.println("ID de curso invalido");
            return;
        }
        Long cursoId = Long.parseLong(cursoIdStr);
        
        if (estudianteDAO.findById(estudianteId).isEmpty()) {
            System.out.println("Estudiante no encontrado");
            return;
        }
        
        if (cursoDAO.findById(cursoId).isEmpty()) {
            System.out.println("Curso no encontrado");
            return;
        }
        
        Inscripcion inscripcion = new Inscripcion();
        inscripcion.setEstudianteId(estudianteId);
        inscripcion.setCursoId(cursoId);
        inscripcion.setFechaInscripcion(LocalDateTime.now());
        inscripcion.setActiva(true);
        
        inscripcionDAO.save(inscripcion);
        System.out.println("Inscripcion creada exitosamente con ID: " + inscripcion.getId());
    }

    private static void listarInscripciones() throws Exception {
        System.out.println("\nLISTA DE INSCRIPCIONES");
        List<Inscripcion> inscripciones = inscripcionDAO.findAll();
        if (inscripciones.isEmpty()) {
            System.out.println("No hay inscripciones registradas");
            return;
        }
        
        for (Inscripcion inscripcion : inscripciones) {
            System.out.println(inscripcion);
        }
    }

    private static void buscarInscripcionPorId(Scanner scanner) throws Exception {
        System.out.print("\nIngrese el ID de la inscripcion: ");
        String input = scanner.nextLine().trim();
        if (input.isEmpty()) {
            System.out.println("ID invalido");
            return;
        }
        Long id = Long.parseLong(input);
        
        inscripcionDAO.findById(id).ifPresentOrElse(
            inscripcion -> System.out.println(inscripcion),
            () -> System.out.println("Inscripcion no encontrada")
        );
    }

    private static void listarInscripcionesPorEstudiante(Scanner scanner) throws Exception {
        System.out.print("\nIngrese el ID del estudiante: ");
        String input = scanner.nextLine().trim();
        if (input.isEmpty()) {
            System.out.println("ID invalido");
            return;
        }
        Long estudianteId = Long.parseLong(input);
        
        List<Inscripcion> inscripciones = inscripcionDAO.findByEstudianteId(estudianteId);
        if (inscripciones.isEmpty()) {
            System.out.println("No hay inscripciones para este estudiante");
            return;
        }
        
        for (Inscripcion inscripcion : inscripciones) {
            System.out.println(inscripcion);
        }
    }

    private static void listarInscripcionesPorCurso(Scanner scanner) throws Exception {
        System.out.print("\nIngrese el ID del curso: ");
        String input = scanner.nextLine().trim();
        if (input.isEmpty()) {
            System.out.println("ID invalido");
            return;
        }
        Long cursoId = Long.parseLong(input);
        
        List<Inscripcion> inscripciones = inscripcionDAO.findByCursoId(cursoId);
        if (inscripciones.isEmpty()) {
            System.out.println("No hay inscripciones para este curso");
            return;
        }
        
        for (Inscripcion inscripcion : inscripciones) {
            System.out.println(inscripcion);
        }
    }

    private static void cancelarInscripcion(Scanner scanner) throws Exception {
        System.out.print("\nIngrese el ID de la inscripcion a cancelar: ");
        String input = scanner.nextLine().trim();
        if (input.isEmpty()) {
            System.out.println("ID invalido");
            return;
        }
        Long id = Long.parseLong(input);
        
        var inscripcionOpt = inscripcionDAO.findById(id);
        if (inscripcionOpt.isEmpty()) {
            System.out.println("Inscripcion no encontrada");
            return;
        }
        
        var inscripcion = inscripcionOpt.get();
        System.out.println("Datos de la inscripcion: " + inscripcion);
        
        System.out.print("¿Esta seguro de cancelar la inscripcion? (s/n): ");
        String confirmacion = scanner.nextLine().trim().toLowerCase();
        if (confirmacion.equals("s")) {
            inscripcion.setActiva(false);
            inscripcionDAO.update(inscripcion);
            System.out.println("Inscripcion cancelada exitosamente");
        }
    }
}