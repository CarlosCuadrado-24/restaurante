package com.resturante.logica.components;

import com.resturante.logica.dto.PlatoRegistroDTO;
import com.resturante.logica.models.Plato;
import org.springframework.stereotype.Component;

@Component
public class PlatoConverter {

    public static PlatoRegistroDTO aDTO(Plato plato) {
        PlatoRegistroDTO platoRegistroDTO = new PlatoRegistroDTO();
        platoRegistroDTO.setNombre(plato.getNombre());
        platoRegistroDTO.setDescripcion(plato.getDescripcion());
        platoRegistroDTO.setPrecio(plato.getPrecio());
        return platoRegistroDTO;
    }

    public static Plato aEntidad(PlatoRegistroDTO platoRegistroDTO) {
        Plato plato = new Plato();
        plato.setNombre(platoRegistroDTO.getNombre());
        plato.setDescripcion(platoRegistroDTO.getDescripcion());
        plato.setPrecio(platoRegistroDTO.getPrecio());
        return plato;
    }

}
