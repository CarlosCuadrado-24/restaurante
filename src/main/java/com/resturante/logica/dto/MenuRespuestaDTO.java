package com.resturante.logica.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;


@Getter
@Setter
public class MenuRespuestaDTO {

    private Long id;
    private String nombre;

    public MenuRespuestaDTO(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }
}
