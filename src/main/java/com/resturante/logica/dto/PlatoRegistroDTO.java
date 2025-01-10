package com.resturante.logica.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlatoRegistroDTO {
    private String nombre;
    private String descripcion;
    private Double precio;
    private Long IdMenu;

    public PlatoRegistroDTO(String nombre, String descripcion, Double precio, Long idMenu) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        IdMenu = idMenu;
    }

    public PlatoRegistroDTO() {
    }
}
