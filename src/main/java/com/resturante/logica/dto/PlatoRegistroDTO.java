package com.resturante.logica.dto;

import com.resturante.logica.models.Menu;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlatoRegistroDTO {
    private String nombre;
    private String descripcion;
    private Double precio;
    private Long IdMenu;
}
