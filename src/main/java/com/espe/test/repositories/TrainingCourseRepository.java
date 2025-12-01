package com.espe.test.repositories;

import com.espe.test.models.entities.TrainingCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para la gestión de Training Courses.
 * Extiende JpaRepository para proporcionar operaciones CRUD básicas.
 */
@Repository
public interface TrainingCourseRepository extends JpaRepository<TrainingCourse, Long> {
}
