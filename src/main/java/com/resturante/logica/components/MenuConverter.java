package com.resturante.logica.components;

import com.resturante.logica.dto.MenuDTO;
import com.resturante.logica.models.Menu;
import org.springframework.stereotype.Component;

@Component
public class MenuConverter {

    public MenuDTO aDTO (Menu menu){
        MenuDTO menuDTO = new MenuDTO();
        menuDTO.setNombre(menu.getNombre());
        return menuDTO;
    }

    public Menu aEntidad (MenuDTO menuDTO){
        Menu menu = new Menu();
        menu.setNombre(menuDTO.getNombre());
        return menu;
    }

}
