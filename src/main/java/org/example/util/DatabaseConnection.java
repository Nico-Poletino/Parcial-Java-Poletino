package org.example.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final Logger logger = LogManager.getLogger(DatabaseConnection.class);
    
    private static final String URL = "jdbc:h2:file:./data/escuela";
    private static final String USER = "sa";
    private static final String PASSWORD = "";
    
    private static DatabaseConnection instance;

    private DatabaseConnection() {
        try {
            Class.forName("org.h2.Driver");
            logger.info("Driver H2 inicializado correctamente");
        } catch (ClassNotFoundException e) {
            logger.error("Error al cargar el driver H2", e);
            throw new RuntimeException("Error al inicializar la conexion", e);
        }
    }

    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            logger.debug("Nueva conexion establecida");
            createTablesIfNotExist(conn);
            return conn;
        } catch (SQLException e) {
            logger.error("Error al obtener conexion", e);
            throw e;
        }
    }
    
    private void createTablesIfNotExist(Connection conn) throws SQLException {
        try (var stmt = conn.createStatement()) {
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS estudiantes (
                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                    nombre VARCHAR(100) NOT NULL,
                    apellido VARCHAR(100) NOT NULL,
                    email VARCHAR(100) UNIQUE NOT NULL,
                    fecha_nacimiento DATE
                )
            """);
            
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS cursos (
                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                    nombre VARCHAR(100) NOT NULL,
                    descripcion TEXT,
                    cupo_maximo INT NOT NULL,
                    activo BOOLEAN DEFAULT TRUE
                )
            """);
            
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS inscripciones (
                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                    estudiante_id BIGINT NOT NULL,
                    curso_id BIGINT NOT NULL,
                    fecha_inscripcion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    activa BOOLEAN DEFAULT TRUE,
                    FOREIGN KEY (estudiante_id) REFERENCES estudiantes(id),
                    FOREIGN KEY (curso_id) REFERENCES cursos(id)
                )
            """);
            
            logger.info("Tablas creadas o verificadas correctamente");
        } catch (SQLException e) {
            logger.error("Error al crear las tablas", e);
            throw e;
        }
    }
}