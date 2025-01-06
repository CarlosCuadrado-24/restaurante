package com.resturante.logica.dto;

import com.resturante.logica.models.Menu;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlatoRespuestaDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private Double precio;
    private String tipo;
    private Long IdMenu;

    public PlatoRespuestaDTO(Long id, String nombre, String descripcion, Double precio, String tipo, Long IdMenu) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.tipo = tipo;
        this.IdMenu = IdMenu;
    }

}
