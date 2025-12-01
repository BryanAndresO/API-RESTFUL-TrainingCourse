package com.espe.test.services;

import com.espe.test.models.entities.Libro;

import java.util.List;
import java.util.Optional;

/**
 * Interface de servicio para la gestión de libros.
 * Define las operaciones de negocio disponibles para la entidad Libro.
 * 
 * @author Tu Nombre
 * @version 1.0
 */
public interface LibroService {

    /**
     * Obtiene todos los libros registrados en el sistema.
     * 
     * @return Lista de todos los libros
     */
    List<Libro> buscarTodos();

    /**
     * Busca un libro específico por su ID.
     * 
     * @param id ID del libro a buscar
     * @return Optional que contiene el libro si existe, vacío si no
     */
    Optional<Libro> buscarPorId(Long id);

    /**
     * Guarda un libro nuevo o actualiza uno existente.
     * Si el libro tiene ID y existe, lo actualiza. Si no tiene ID, lo crea.
     * 
     * @param libro Libro a guardar o actualizar
     * @return Libro guardado con ID asignado
     */
    Libro guardar(Libro libro);

    /**
     * Elimina un libro por su ID.
     * 
     * @param id ID del libro a eliminar
     */
    void eliminarPorId(Long id);
}
