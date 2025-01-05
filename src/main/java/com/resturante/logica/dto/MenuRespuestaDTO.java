package com.resturante.logica.dto;

import org.springframework.stereotype.Component;

@Component
public class MenuRespuestaDTO {

    private Long id;
    private String nombre;

    public MenuRespuestaDTO(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }
}
