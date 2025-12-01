package com.espe.test.repositories;

import com.espe.test.models.entities.Libro;
import org.springframework.data.repository.CrudRepository;
import  org.springframework.transaction.annotation.Transactional;

@Transactional

public interface LibroRepository extends CrudRepository<Libro, Long> {
}
