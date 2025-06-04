package org.example.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.model.Curso;
import org.example.util.DatabaseConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CursoDAO implements GenericDAO<Curso> {
    private static final Logger logger = LogManager.getLogger(CursoDAO.class);
    private final DatabaseConnection databaseConnection;

    public CursoDAO() {
        this.databaseConnection = DatabaseConnection.getInstance();
    }

    @Override
    public Curso save(Curso curso) throws Exception {
        logger.info("Guardando nuevo curso: {}", curso.getNombre());
        String sql = "INSERT INTO cursos (nombre, descripcion, cupo_maximo, activo) VALUES (?, ?, ?, ?)";
        try (var conn = databaseConnection.getConnection();
             var stmt = conn.prepareStatement(sql, new String[]{"id"})) {
            stmt.setString(1, curso.getNombre());
            stmt.setString(2, curso.getDescripcion());
            stmt.setInt(3, curso.getCupoMaximo());
            stmt.setBoolean(4, curso.getActivo());
            stmt.executeUpdate();

            try (var rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    curso.setId(rs.getLong(1));
                    logger.info("Curso guardado con ID: {}", curso.getId());
                }
            }
            return curso;
        } catch (Exception e) {
            logger.error("Error al guardar curso: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public Optional<Curso> findById(Long id) throws Exception {
        logger.info("Buscando curso con ID: {}", id);
        String sql = "SELECT * FROM cursos WHERE id = ?";
        try (var conn = databaseConnection.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (var rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToCurso(rs));
                }
            }
        } catch (Exception e) {
            logger.error("Error al buscar curso: {}", e.getMessage());
            throw e;
        }
        return Optional.empty();
    }

    @Override
    public List<Curso> findAll() throws Exception {
        logger.info("Listando todos los cursos");
        String sql = "SELECT * FROM cursos";
        List<Curso> cursos = new ArrayList<>();
        try (var conn = databaseConnection.getConnection();
             var stmt = conn.prepareStatement(sql);
             var rs = stmt.executeQuery()) {
            while (rs.next()) {
                cursos.add(mapResultSetToCurso(rs));
            }
            logger.info("Se encontraron {} cursos", cursos.size());
            return cursos;
        } catch (Exception e) {
            logger.error("Error al listar cursos: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public void update(Curso curso) throws Exception {
        logger.info("Actualizando curso con ID: {}", curso.getId());
        String sql = "UPDATE cursos SET nombre = ?, descripcion = ?, cupo_maximo = ?, activo = ? WHERE id = ?";
        try (var conn = databaseConnection.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, curso.getNombre());
            stmt.setString(2, curso.getDescripcion());
            stmt.setInt(3, curso.getCupoMaximo());
            stmt.setBoolean(4, curso.getActivo());
            stmt.setLong(5, curso.getId());
            stmt.executeUpdate();
            logger.info("Curso actualizado exitosamente");
        } catch (Exception e) {
            logger.error("Error al actualizar curso: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public void delete(Long id) throws Exception {
        logger.info("Eliminando curso con ID: {}", id);
        String sql = "DELETE FROM cursos WHERE id = ?";
        try (var conn = databaseConnection.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
            logger.info("Curso eliminado exitosamente");
        } catch (Exception e) {
            logger.error("Error al eliminar curso: {}", e.getMessage());
            throw e;
        }
    }

    private Curso mapResultSetToCurso(ResultSet rs) throws SQLException {
        Curso curso = new Curso();
        curso.setId(rs.getLong("id"));
        curso.setNombre(rs.getString("nombre"));
        curso.setDescripcion(rs.getString("descripcion"));
        curso.setCupoMaximo(rs.getInt("cupo_maximo"));
        curso.setActivo(rs.getBoolean("activo"));
        return curso;
    }
} 