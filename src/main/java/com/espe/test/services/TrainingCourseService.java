package com.espe.test.services;

import com.espe.test.models.entities.TrainingCourse;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz de servicio para la gestión de Training Courses.
 * Define las operaciones de negocio disponibles.
 */
public interface TrainingCourseService {

    /**
     * Busca todos los cursos de capacitación.
     *
     * @return Lista de todos los cursos
     */
    List<TrainingCourse> buscarTodos();

    /**
     * Busca un curso de capacitación por su ID.
     *
     * @param id ID del curso a buscar
     * @return Optional con el curso si existe, vacío si no
     */
    Optional<TrainingCourse> buscarPorId(Long id);

    /**
     * Guarda un curso de capacitación (crear o actualizar).
     *
     * @param course Curso a guardar
     * @return Curso guardado
     */
    TrainingCourse guardar(TrainingCourse course);

    /**
     * Elimina un curso de capacitación por su ID.
     *
     * @param id ID del curso a eliminar
     */
    void eliminarPorId(Long id);
}
