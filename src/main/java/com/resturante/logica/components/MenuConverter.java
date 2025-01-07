package com.resturante.logica.components;

import com.resturante.logica.dto.MenuRegistroDTO;
import com.resturante.logica.dto.MenuRespuestaDTO;
import com.resturante.logica.models.Menu;
import org.springframework.stereotype.Component;

@Component
public class MenuConverter {


    public static MenuRespuestaDTO aRespuestaDTO(Menu menu){
        return new MenuRespuestaDTO(menu.getId(),menu.getNombre(),PlatoConverter.aRespuestasDTO(menu.getPlatos()));
    }

    public static Menu aEntidad (Long id,MenuRegistroDTO menuRegistroDTO){
        Menu menu = new Menu();
        menu.setId(id);
        menu.setNombre(menuRegistroDTO.getNombre());
        return menu;
    }

    public static Menu aEntidad (MenuRegistroDTO menuRegistroDTO){
        Menu menu = new Menu();
        menu.setNombre(menuRegistroDTO.getNombre());
        return menu;
    }

}
