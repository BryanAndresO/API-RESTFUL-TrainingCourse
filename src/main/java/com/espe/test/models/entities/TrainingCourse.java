package com.espe.test.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Entidad que representa un Curso de Capacitación (Training Course) en el
 * sistema.
 * Implementa las validaciones necesarias para garantizar la integridad de los
 * datos.
 */
@Entity
@Table(name = "training_course")
public class TrainingCourse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del curso es obligatorio")
    @Size(min = 1, max = 200, message = "El nombre del curso debe tener entre 1 y 200 caracteres")
    @Column(nullable = false, length = 200, name = "course_name")
    private String courseName;

    @NotBlank(message = "El instructor es obligatorio")
    @Size(min = 1, max = 100, message = "El instructor debe tener entre 1 y 100 caracteres")
    @Column(nullable = false, length = 100)
    private String instructor;

    @NotNull(message = "La duración en horas es obligatoria")
    @Min(value = 1, message = "La duración debe ser al menos 1 hora")
    @Column(nullable = false, name = "duration_hours")
    private Integer durationHours;

    @NotBlank(message = "La categoría es obligatoria")
    @Size(min = 1, max = 100, message = "La categoría debe tener entre 1 y 100 caracteres")
    @Column(nullable = false, length = 100)
    private String category;

    @NotBlank(message = "El estado es obligatorio")
    @Size(min = 1, max = 50, message = "El estado debe tener entre 1 y 50 caracteres")
    @Column(nullable = false, length = 50)
    private String status;

    // Constructor sin argumentos
    public TrainingCourse() {
    }

    // Constructor con todos los argumentos
    public TrainingCourse(Long id, String courseName, String instructor, Integer durationHours,
            String category, String status) {
        this.id = id;
        this.courseName = courseName;
        this.instructor = instructor;
        this.durationHours = durationHours;
        this.category = category;
        this.status = status;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public Integer getDurationHours() {
        return durationHours;
    }

    public void setDurationHours(Integer durationHours) {
        this.durationHours = durationHours;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "TrainingCourse{" +
                "id=" + id +
                ", courseName='" + courseName + '\'' +
                ", instructor='" + instructor + '\'' +
                ", durationHours=" + durationHours +
                ", category='" + category + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
