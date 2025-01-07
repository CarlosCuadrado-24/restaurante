package com.resturante.logica.components;

import com.resturante.logica.dto.PlatoRegistroDTO;
import com.resturante.logica.dto.PlatoRespuestaDTO;
import com.resturante.logica.models.Plato;
import com.resturante.logica.services.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PlatoConverter {

    private final MenuService menuService;

    @Autowired
    public PlatoConverter(MenuService menuService) {
        this.menuService = menuService;
    }


    public static List<PlatoRespuestaDTO> aRespuestasDTO(List<Plato> platos) {
        List<PlatoRespuestaDTO> platosRespuestaDTO = new ArrayList<>();
        platos.forEach(plato -> platosRespuestaDTO.add(aRespuestaDTO(plato)));
        return platosRespuestaDTO;
    }

    public static PlatoRespuestaDTO aRespuestaDTO(Plato plato) {
        return new PlatoRespuestaDTO(plato.getId(),plato.getNombre(), plato.getDescripcion(), plato.getPrecio(), plato.getTipo(), plato.getMenu().getId());
    }

    public Plato aEntidad(PlatoRegistroDTO platoRegistroDTO) {
        Plato plato = new Plato();
        plato.setNombre(platoRegistroDTO.getNombre());
        plato.setDescripcion(platoRegistroDTO.getDescripcion());
        plato.setPrecio(platoRegistroDTO.getPrecio());
        plato.setMenu(menuService.obtenerMenu(platoRegistroDTO.getIdMenu())
                .orElseThrow(() -> new RuntimeException("Menu no encontrado con ID: " + platoRegistroDTO.getIdMenu())));
        return plato;
    }

    public Plato aEntidad(Long id, PlatoRegistroDTO platoRegistroDTO) {
        Plato plato = aEntidad(platoRegistroDTO);
        plato.setId(id);
        return plato;
    }

}
