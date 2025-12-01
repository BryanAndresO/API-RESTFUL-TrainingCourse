package com.espe.test.Controllers;

import com.espe.test.models.entities.TrainingCourse;
import com.espe.test.services.TrainingCourseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

// Controlador REST para gestionar Training Courses
@RestController
@RequestMapping("/api/training-courses")
public class TrainingCourseController {

    private TrainingCourseService service;

    @Autowired
    public TrainingCourseController(TrainingCourseService service) {
        this.service = service;
    }

    // Listar todos los cursos
    @GetMapping
    public ResponseEntity<List<TrainingCourse>> listar() {
        List<TrainingCourse> cursos = service.buscarTodos();
        return ResponseEntity.ok(cursos);
    }

    // Buscar un curso por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        Optional<TrainingCourse> courseOptional = service.buscarPorId(id);

        if (courseOptional.isEmpty()) {
            Map<String, String> error = crearMensajeError("Curso de capacitación no encontrado con ID: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }

        return ResponseEntity.ok(courseOptional.get());
    }

    // Crear un nuevo curso
    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody TrainingCourse course, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, Object> errores = obtenerErroresValidacion(result);
            return ResponseEntity.badRequest().body(errores);
        }

        TrainingCourse coursDB = service.guardar(course);
        return ResponseEntity.status(HttpStatus.CREATED).body(coursDB);
    }

    // Actualizar un curso existente
    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody TrainingCourse course, @PathVariable Long id,
            BindingResult result) {
        if (result.hasErrors()) {
            Map<String, Object> errores = obtenerErroresValidacion(result);
            return ResponseEntity.badRequest().body(errores);
        }

        Optional<TrainingCourse> courseOptional = service.buscarPorId(id);

        if (courseOptional.isEmpty()) {
            Map<String, String> error = crearMensajeError("Curso de capacitación no encontrado con ID: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }

        TrainingCourse courseDB = courseOptional.get();
        courseDB.setCourseName(course.getCourseName());
        courseDB.setInstructor(course.getInstructor());
        courseDB.setDurationHours(course.getDurationHours());
        courseDB.setCategory(course.getCategory());
        courseDB.setStatus(course.getStatus());

        TrainingCourse cursoActualizado = service.guardar(courseDB);
        return ResponseEntity.ok(cursoActualizado);
    }

    // Eliminar un curso
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        Optional<TrainingCourse> courseOptional = service.buscarPorId(id);

        if (courseOptional.isEmpty()) {
            Map<String, String> error = crearMensajeError("Curso de capacitación no encontrado con ID: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }

        service.eliminarPorId(id);
        return ResponseEntity.noContent().build();
    }

    // Obtener errores de validación
    private Map<String, Object> obtenerErroresValidacion(BindingResult result) {
        Map<String, Object> errores = new HashMap<>();
        List<String> listaErrores = new ArrayList<>();

        for (FieldError error : result.getFieldErrors()) {
            String mensajeError = error.getField() + ": " + error.getDefaultMessage();
            listaErrores.add(mensajeError);
        }

        errores.put("mensaje", "Error de validación");
        errores.put("errores", listaErrores);
        return errores;
    }

    // Crear mensaje de error
    private Map<String, String> crearMensajeError(String mensaje) {
        Map<String, String> error = new HashMap<>();
        error.put("mensaje", mensaje);
        return error;
    }
}
