package com.resturante.logica.components;

import com.resturante.logica.dto.PlatoDTO;
import com.resturante.logica.models.Plato;
import org.springframework.stereotype.Component;

@Component
public class PlatoConverter {

    public PlatoDTO aDTO(Plato plato) {
        PlatoDTO platoDTO = new PlatoDTO();
        platoDTO.setNombre(plato.getNombre());
        platoDTO.setDescripcion(plato.getDescripcion());
        platoDTO.setPrecio(plato.getPrecio());
        return platoDTO;
    }

    public Plato aEntidad(PlatoDTO platoDTO) {
        Plato plato = new Plato();
        plato.setNombre(platoDTO.getNombre());
        plato.setDescripcion(platoDTO.getDescripcion());
        plato.setPrecio(platoDTO.getPrecio());
        return plato;
    }

}
