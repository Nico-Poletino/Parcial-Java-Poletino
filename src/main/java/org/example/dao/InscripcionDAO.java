package org.example.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.model.Inscripcion;
import org.example.util.DatabaseConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InscripcionDAO implements GenericDAO<Inscripcion> {
    private static final Logger logger = LogManager.getLogger(InscripcionDAO.class);
    private final DatabaseConnection databaseConnection;

    public InscripcionDAO() {
        this.databaseConnection = DatabaseConnection.getInstance();
    }

    @Override
    public Inscripcion save(Inscripcion inscripcion) throws Exception {
        String sql = "INSERT INTO inscripciones (estudiante_id, curso_id, fecha_inscripcion, activa) VALUES (?, ?, ?, ?)";
        
        try (var conn = databaseConnection.getConnection();
             var stmt = conn.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setLong(1, inscripcion.getEstudianteId());
            stmt.setLong(2, inscripcion.getCursoId());
            stmt.setTimestamp(3, Timestamp.valueOf(inscripcion.getFechaInscripcion()));
            stmt.setBoolean(4, inscripcion.getActiva());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("La creación de la inscripción falló, ninguna fila afectada.");
            }
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    inscripcion.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("La creación de la inscripción falló, no se obtuvo el ID.");
                }
            }
            
            logger.info("Inscripción creada con ID: {}", inscripcion.getId());
            return inscripcion;
            
        } catch (SQLException e) {
            logger.error("Error al guardar la inscripción", e);
            throw new Exception("Error al guardar la inscripción", e);
        }
    }

    @Override
    public Optional<Inscripcion> findById(Long id) throws Exception {
        String sql = "SELECT * FROM inscripciones WHERE id = ?";
        
        try (var conn = databaseConnection.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToInscripcion(rs));
                }
            }
            
            return Optional.empty();
            
        } catch (SQLException e) {
            logger.error("Error al buscar la inscripción por ID", e);
            throw new Exception("Error al buscar la inscripción", e);
        }
    }

    @Override
    public List<Inscripcion> findAll() throws Exception {
        String sql = "SELECT * FROM inscripciones";
        List<Inscripcion> inscripciones = new ArrayList<>();
        
        try (var conn = databaseConnection.getConnection();
             var stmt = conn.prepareStatement(sql);
             var rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                inscripciones.add(mapResultSetToInscripcion(rs));
            }
            
            return inscripciones;
            
        } catch (SQLException e) {
            logger.error("Error al obtener todas las inscripciones", e);
            throw new Exception("Error al obtener las inscripciones", e);
        }
    }

    @Override
    public void update(Inscripcion inscripcion) throws Exception {
        String sql = "UPDATE inscripciones SET estudiante_id = ?, curso_id = ?, fecha_inscripcion = ?, activa = ? WHERE id = ?";
        
        try (var conn = databaseConnection.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, inscripcion.getEstudianteId());
            stmt.setLong(2, inscripcion.getCursoId());
            stmt.setTimestamp(3, Timestamp.valueOf(inscripcion.getFechaInscripcion()));
            stmt.setBoolean(4, inscripcion.getActiva());
            stmt.setLong(5, inscripcion.getId());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("La actualización de la inscripción falló, ninguna fila afectada.");
            }
            
            logger.info("Inscripción actualizada con ID: {}", inscripcion.getId());
            
        } catch (SQLException e) {
            logger.error("Error al actualizar la inscripción", e);
            throw new Exception("Error al actualizar la inscripción", e);
        }
    }

    @Override
    public void delete(Long id) throws Exception {
        String sql = "DELETE FROM inscripciones WHERE id = ?";
        
        try (var conn = databaseConnection.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("La eliminación de la inscripción falló, ninguna fila afectada.");
            }
            
            logger.info("Inscripción eliminada con ID: {}", id);
            
        } catch (SQLException e) {
            logger.error("Error al eliminar la inscripción", e);
            throw new Exception("Error al eliminar la inscripción", e);
        }
    }

    public List<Inscripcion> findByEstudianteId(Long estudianteId) throws Exception {
        String sql = "SELECT * FROM inscripciones WHERE estudiante_id = ?";
        List<Inscripcion> inscripciones = new ArrayList<>();
        
        try (var conn = databaseConnection.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, estudianteId);
            
            try (var rs = stmt.executeQuery()) {
                while (rs.next()) {
                    inscripciones.add(mapResultSetToInscripcion(rs));
                }
            }
            
            return inscripciones;
            
        } catch (SQLException e) {
            logger.error("Error al buscar inscripciones por estudiante ID", e);
            throw new Exception("Error al buscar inscripciones por estudiante", e);
        }
    }

    public List<Inscripcion> findByCursoId(Long cursoId) throws Exception {
        String sql = "SELECT * FROM inscripciones WHERE curso_id = ?";
        List<Inscripcion> inscripciones = new ArrayList<>();
        
        try (var conn = databaseConnection.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, cursoId);
            
            try (var rs = stmt.executeQuery()) {
                while (rs.next()) {
                    inscripciones.add(mapResultSetToInscripcion(rs));
                }
            }
            
            return inscripciones;
            
        } catch (SQLException e) {
            logger.error("Error al buscar inscripciones por curso ID", e);
            throw new Exception("Error al buscar inscripciones por curso", e);
        }
    }

    private Inscripcion mapResultSetToInscripcion(ResultSet rs) throws SQLException {
        Inscripcion inscripcion = new Inscripcion();
        inscripcion.setId(rs.getLong("id"));
        inscripcion.setEstudianteId(rs.getLong("estudiante_id"));
        inscripcion.setCursoId(rs.getLong("curso_id"));
        inscripcion.setFechaInscripcion(rs.getTimestamp("fecha_inscripcion").toLocalDateTime());
        inscripcion.setActiva(rs.getBoolean("activa"));
        return inscripcion;
    }
}