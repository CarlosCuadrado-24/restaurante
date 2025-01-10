package com.resturante.logica.services;

import com.resturante.logica.models.Menu;
import com.resturante.logica.repositories.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MenuService {
    private final MenuRepository repositoriomenu;

    @Autowired
    public MenuService(MenuRepository repositoriomenu) {
        this.repositoriomenu = repositoriomenu;
    }

    public void agregarMenu(Menu menu){
        repositoriomenu.save(menu);
    }

    public Optional<Menu> obtenerMenu(Long id){
        return repositoriomenu.findById(id);
    }

    public List<Menu> listarMenus(){
        return repositoriomenu.findAll();
    }

    public Menu actualizarMenu(Long id, Menu menuActualizado) {
        return repositoriomenu.findById(id).map(menu -> {
            menu.setNombre(menuActualizado.getNombre());
            return repositoriomenu.save(menu);
        }).orElseThrow(() -> new RuntimeException("Menu con el id " + id + " no existe."));
    }

    public void eliminarMenu(Long id){
        repositoriomenu.deleteById(id);
    }

}
