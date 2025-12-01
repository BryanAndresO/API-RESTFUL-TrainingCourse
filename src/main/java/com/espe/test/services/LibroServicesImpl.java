package com.espe.test.services;

import com.espe.test.models.entities.Libro;
import com.espe.test.repositories.LibroRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class LibroServicesImpl implements LibroService {

    @Autowired
    private LibroRepository libroRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Libro> buscarTodos() {
        return (List<Libro>) libroRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Libro> buscarPorId(Long id) {
        return libroRepository.findById(id);
    }

    @Override
    @Transactional
    public Libro guardar(Libro libro) {
        return libroRepository.save(libro);
    }

    @Override
    @Transactional
    public void eliminarPorId(Long id) {
        libroRepository.deleteById(id);
    }
}
