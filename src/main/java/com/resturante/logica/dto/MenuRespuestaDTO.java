package com.resturante.logica.dto;

import com.resturante.logica.models.Plato;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;


@Getter
@Setter
public class MenuRespuestaDTO {

    private Long id;
    private String nombre;
    private List<PlatoRespuestaDTO> platos;

    public MenuRespuestaDTO(Long id, String nombre, List<PlatoRespuestaDTO> platos) {
        this.id = id;
        this.nombre = nombre;
        this.platos = platos;
    }
}
