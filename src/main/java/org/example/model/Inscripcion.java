package org.example.model;

import java.time.LocalDateTime;

public class Inscripcion {
    private Long id;
    private Long estudianteId;
    private Long cursoId;
    private LocalDateTime fechaInscripcion;
    private boolean activa;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEstudianteId() {
        return estudianteId;
    }

    public void setEstudianteId(Long estudianteId) {
        if (estudianteId == null) {
            throw new IllegalArgumentException("El ID del estudiante es obligatorio");
        }
        this.estudianteId = estudianteId;
    }

    public Long getCursoId() {
        return cursoId;
    }

    public void setCursoId(Long cursoId) {
        if (cursoId == null) {
            throw new IllegalArgumentException("El ID del curso es obligatorio");
        }
        this.cursoId = cursoId;
    }

    public LocalDateTime getFechaInscripcion() {
        return fechaInscripcion;
    }

    public void setFechaInscripcion(LocalDateTime fechaInscripcion) {
        if (fechaInscripcion == null) {
            throw new IllegalArgumentException("La fecha de inscripcion es obligatoria");
        }
        this.fechaInscripcion = fechaInscripcion;
    }

    public boolean getActiva() {
        return activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }

    @Override
    public String toString() {
        return "Inscripcion{" +
                "id=" + id +
                ", estudianteId=" + estudianteId +
                ", cursoId=" + cursoId +
                ", fechaInscripcion=" + fechaInscripcion +
                ", activa=" + activa +
                '}';
    }
} 