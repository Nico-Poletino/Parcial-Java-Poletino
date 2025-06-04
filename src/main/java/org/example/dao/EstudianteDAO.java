package org.example.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.model.Estudiante;
import org.example.util.DatabaseConnection;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EstudianteDAO implements GenericDAO<Estudiante> {
    private static final Logger logger = LogManager.getLogger(EstudianteDAO.class);
    private final DatabaseConnection databaseConnection;

    public EstudianteDAO() {
        this.databaseConnection = DatabaseConnection.getInstance();
    }

    @Override
    public Estudiante save(Estudiante estudiante) throws Exception {
        String sql = "INSERT INTO estudiantes (nombre, apellido, email, fecha_nacimiento) VALUES (?, ?, ?, ?)";
        
        try (var conn = databaseConnection.getConnection();
             var stmt = conn.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, estudiante.getNombre());
            stmt.setString(2, estudiante.getApellido());
            stmt.setString(3, estudiante.getEmail());
            stmt.setDate(4, estudiante.getFechaNacimiento() != null ? 
                        Date.valueOf(estudiante.getFechaNacimiento()) : null);
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("La creación del estudiante falló, ninguna fila afectada.");
            }
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    estudiante.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("La creación del estudiante falló, no se obtuvo el ID.");
                }
            }
            
            logger.info("Estudiante creado con ID: {}", estudiante.getId());
            return estudiante;
            
        } catch (SQLException e) {
            logger.error("Error al guardar el estudiante", e);
            throw new Exception("Error al guardar el estudiante", e);
        }
    }

    @Override
    public Optional<Estudiante> findById(Long id) throws Exception {
        String sql = "SELECT * FROM estudiantes WHERE id = ?";
        
        try (var conn = databaseConnection.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToEstudiante(rs));
                }
            }
            
            return Optional.empty();
            
        } catch (SQLException e) {
            logger.error("Error al buscar el estudiante por ID", e);
            throw new Exception("Error al buscar el estudiante", e);
        }
    }

    @Override
    public List<Estudiante> findAll() throws Exception {
        String sql = "SELECT * FROM estudiantes";
        List<Estudiante> estudiantes = new ArrayList<>();
        
        try (var conn = databaseConnection.getConnection();
             var stmt = conn.prepareStatement(sql);
             var rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                estudiantes.add(mapResultSetToEstudiante(rs));
            }
            
            return estudiantes;
            
        } catch (SQLException e) {
            logger.error("Error al obtener todos los estudiantes", e);
            throw new Exception("Error al obtener los estudiantes", e);
        }
    }

    @Override
    public void update(Estudiante estudiante) throws Exception {
        String sql = "UPDATE estudiantes SET nombre = ?, apellido = ?, email = ?, fecha_nacimiento = ? WHERE id = ?";
        
        try (var conn = databaseConnection.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, estudiante.getNombre());
            stmt.setString(2, estudiante.getApellido());
            stmt.setString(3, estudiante.getEmail());
            stmt.setDate(4, estudiante.getFechaNacimiento() != null ? 
                        Date.valueOf(estudiante.getFechaNacimiento()) : null);
            stmt.setLong(5, estudiante.getId());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("La actualización del estudiante falló, ninguna fila afectada.");
            }
            
            logger.info("Estudiante actualizado con ID: {}", estudiante.getId());
            
        } catch (SQLException e) {
            logger.error("Error al actualizar el estudiante", e);
            throw new Exception("Error al actualizar el estudiante", e);
        }
    }

    @Override
    public void delete(Long id) throws Exception {
        String sql = "DELETE FROM estudiantes WHERE id = ?";
        
        try (var conn = databaseConnection.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("La eliminación del estudiante falló, ninguna fila afectada.");
            }
            
            logger.info("Estudiante eliminado con ID: {}", id);
            
        } catch (SQLException e) {
            logger.error("Error al eliminar el estudiante", e);
            throw new Exception("Error al eliminar el estudiante", e);
        }
    }

    private Estudiante mapResultSetToEstudiante(ResultSet rs) throws SQLException {
        Estudiante estudiante = new Estudiante();
        estudiante.setId(rs.getLong("id"));
        estudiante.setNombre(rs.getString("nombre"));
        estudiante.setApellido(rs.getString("apellido"));
        estudiante.setEmail(rs.getString("email"));
        Date fechaNacimiento = rs.getDate("fecha_nacimiento");
        if (fechaNacimiento != null) {
            estudiante.setFechaNacimiento(fechaNacimiento.toLocalDate());
        }
        return estudiante;
    }
} 