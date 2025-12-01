package com.espe.test.services;

import com.espe.test.models.entities.TrainingCourse;
import com.espe.test.repositories.TrainingCourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Implementación del servicio de Training Courses.
 * Contiene la lógica de negocio para la gestión de cursos de capacitación.
 */
@Service
public class TrainingCourseServiceImpl implements TrainingCourseService {

    private TrainingCourseRepository repository;

    @Autowired
    public TrainingCourseServiceImpl(TrainingCourseRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TrainingCourse> buscarTodos() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TrainingCourse> buscarPorId(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public TrainingCourse guardar(TrainingCourse course) {
        return repository.save(course);
    }

    @Override
    @Transactional
    public void eliminarPorId(Long id) {
        repository.deleteById(id);
    }
}
