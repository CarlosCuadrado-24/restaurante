package com.resturante.logica.components;

import com.resturante.logica.dto.MenuRegistroDTO;
import com.resturante.logica.models.Menu;
import org.springframework.stereotype.Component;

@Component
public class MenuConverter {

    public static MenuRegistroDTO aDTO (Menu menu){
        MenuRegistroDTO menuRegistroDTO = new MenuRegistroDTO();
        menuRegistroDTO.setNombre(menu.getNombre());
        return menuRegistroDTO;
    }

    public static Menu aEntidad (MenuRegistroDTO menuRegistroDTO){
        Menu menu = new Menu();
        menu.setNombre(menuRegistroDTO.getNombre());
        return menu;
    }

}
