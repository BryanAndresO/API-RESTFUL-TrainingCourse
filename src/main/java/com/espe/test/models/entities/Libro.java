package com.espe.test.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad que representa un Libro en el sistema.
 * Implementa las validaciones necesarias para garantizar la integridad de los
 * datos.
 */
@Entity
@Table(name = "libro")
@Data // Generas automaticamente los getters y setters
@NoArgsConstructor // Genera constructor sin argumentos
@AllArgsConstructor // Genera constructor con argumentos
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El título es obligatorio")
    @Size(min = 1, max = 200, message = "El título debe tener entre 1 y 200 caracteres")
    @Column(nullable = false, length = 200)
    private String titulo;

    @NotBlank(message = "El autor es obligatorio")
    @Size(min = 1, max = 100, message = "El autor debe tener entre 1 y 100 caracteres")
    @Column(nullable = false, length = 100)
    private String autor;

    @NotBlank(message = "El género es obligatorio")
    @Size(min = 1, max = 50, message = "El género debe tener entre 1 y 50 caracteres")
    @Column(nullable = false, length = 50)
    private String genero;
}