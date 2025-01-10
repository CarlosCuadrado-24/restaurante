package com.resturante.logica.controllers;


import com.resturante.logica.components.MenuConverter;
import com.resturante.logica.dto.MenuRegistroDTO;
import com.resturante.logica.dto.MenuRespuestaDTO;
import com.resturante.logica.models.Menu;
import com.resturante.logica.services.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/menu")
public class MenuController {

    private final MenuService menuService;

    @Autowired
    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @PostMapping
    public ResponseEntity<String> agregarMenu(@RequestBody MenuRegistroDTO menuRegistroDTO){
        Menu menu = MenuConverter.aEntidad(menuRegistroDTO);
        menuService.agregarMenu(menu);
        return ResponseEntity.ok("Menu agregado exitosamente.");
    }

    @GetMapping("/{id}")
    public ResponseEntity<MenuRespuestaDTO>obtenerMenu(@PathVariable Long id){
        return menuService.obtenerMenu(id).map(menu -> ResponseEntity.ok(MenuConverter.aRespuestaDTO(menu))).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<MenuRespuestaDTO>> listarMenus(){
        List<Menu> menus = menuService.listarMenus();
        List<MenuRespuestaDTO> response = menus.stream()
                .map(MenuConverter::aRespuestaDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MenuRespuestaDTO> actualizarMenu(@PathVariable Long id, @RequestBody MenuRegistroDTO menuRegistro) {
        try {
            Menu menuActualizado = MenuConverter.aEntidad(id, menuRegistro);
            Menu menuPersistido = menuService.actualizarMenu(id, menuActualizado);
            return ResponseEntity.ok(MenuConverter.aRespuestaDTO(menuPersistido));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarMenu(@PathVariable Long id){
        menuService.eliminarMenu(id);
        return ResponseEntity.ok("Menu eliminado exitosamente.");
    }

}
