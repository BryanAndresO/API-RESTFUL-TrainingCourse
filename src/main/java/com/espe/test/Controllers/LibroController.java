package com.espe.test.Controllers;

import com.espe.test.models.entities.Libro;
import com.espe.test.services.LibroService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Controlador REST para la gestión de libros.
 * Se implementa los endpoints del CRUD completo siguiendo principios RESTful.
 */
@RestController
@RequestMapping("/api/libros")
public class LibroController {

    @Autowired
    private LibroService service;

    /**
     * GET /api/libros - Lista todos los libros
     * 
     * @return Lista de libros con código 200 OK
     */
    @GetMapping
    public ResponseEntity<List<Libro>> listar() {
        return ResponseEntity.ok(service.buscarTodos());
    }

    /**
     * GET /api/libros/{id} - Busca un libro por ID
     * 
     * @param id ID del libro a buscar
     * @return Libro encontrado con código 200 OK, o 404 NOT FOUND si no existe
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        Optional<Libro> libroOptional = service.buscarPorId(id);
        if (libroOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(crearMensajeError("Libro no encontrado con ID: " + id));
        }
        return ResponseEntity.ok(libroOptional.get());
    }

    /**
     * POST /api/libros - Crea un nuevo libro
     * 
     * @param libro  Datos del libro a crear //Valida que los datos del libro sean
     *               correctos
     * @param result Resultado de validación //Valida que los datos del libro sean
     *               correctos
     * @return Libro creado con código 201 CREATED, o 400 BAD REQUEST si hay errores
     *         de validación
     */
    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody Libro libro, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(obtenerErroresValidacion(result));
        }
        Libro libroDB = service.guardar(libro);
        return ResponseEntity.status(HttpStatus.CREATED).body(libroDB);
    }

    /**
     * PUT /api/libros/{id} - Actualiza un libro existente
     * 
     * @param libro  Datos actualizados del libro //Valida que los datos del libro
     *               sean correctos
     * @param id     ID del libro a actualizar
     * @param result Resultado de validación //Valida que los datos del libro sean
     *               correctos
     * @return Libro actualizado con código 200 OK, o 404 NOT FOUND si no existe
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody Libro libro, @PathVariable Long id, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(obtenerErroresValidacion(result));
        }

        Optional<Libro> libroOptional = service.buscarPorId(id);
        if (libroOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(crearMensajeError("Libro no encontrado con ID: " + id));
        }

        Libro libroDB = libroOptional.get();
        libroDB.setTitulo(libro.getTitulo());
        libroDB.setAutor(libro.getAutor());
        libroDB.setGenero(libro.getGenero());

        return ResponseEntity.ok(service.guardar(libroDB));

    }

    /**
     * DELETE /api/libros/{id} - Elimina un libro
     * 
     * @param id ID del libro a eliminar //Valida que el libro exista
     * @return Código 204 NO CONTENT si se eliminó exitosamente, o 404 NOT FOUND si
     *         no existe
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        Optional<Libro> libroOptional = service.buscarPorId(id);
        if (libroOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(crearMensajeError("Libro no encontrado con ID: " + id));
        }
        service.eliminarPorId(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Extrae los errores de validación del BindingResult
     * 
     * @param result Resultado de la validación
     * @return Map con los errores de validación
     */
    private Map<String, Object> obtenerErroresValidacion(BindingResult result) {
        Map<String, Object> errores = new HashMap<>();
        errores.put("mensaje", "Error de validación");
        errores.put("errores", result.getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.toList()));
        return errores;
    }

    /**
     * Crea un mensaje de error en formato JSON
     * 
     * @param mensaje Mensaje de error
     * @return Map con el mensaje de error
     */
    private Map<String, String> crearMensajeError(String mensaje) {
        Map<String, String> error = new HashMap<>();
        error.put("mensaje", mensaje);
        return error;
    }
}
