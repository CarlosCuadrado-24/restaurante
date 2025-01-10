package com.restaurante.logica.TestServicio;

import com.resturante.logica.models.Menu;
import com.resturante.logica.repositories.MenuRepository;
import com.resturante.logica.services.MenuService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MenuServiceTest {

    @Mock
    private MenuRepository menuRepository;

    @InjectMocks
    private MenuService menuService;

    private Menu menu;
    private List<Menu> menus;

    @BeforeEach
    void setUp() {
        menu = new Menu(1L, "Menu Principal", new ArrayList<>());
        menus = List.of(
                menu,
                new Menu(2L, "Menu Secundario", new ArrayList<>())
        );
    }

    @Test
    @DisplayName("Agregar Menu")
    void agregarMenu() {
        when(menuRepository.save(any(Menu.class))).thenReturn(menu);

        menuService.agregarMenu(menu);

        ArgumentCaptor<Menu> captor = ArgumentCaptor.forClass(Menu.class);
        verify(menuRepository).save(captor.capture());
        Menu menuCapturado = captor.getValue();

        assertEquals(menu.getNombre(), menuCapturado.getNombre());
    }

    @Test
    @DisplayName("Obtener Menu por ID")
    void obtenerMenu() {
        when(menuRepository.findById(1L)).thenReturn(Optional.of(menu));

        Optional<Menu> resultado = menuService.obtenerMenu(1L);

        assertTrue(resultado.isPresent());
        assertEquals(menu.getNombre(), resultado.get().getNombre());
        verify(menuRepository).findById(1L);
    }

    @Test
    @DisplayName("Listar Menus")
    void listarMenus() {
        when(menuRepository.findAll()).thenReturn(menus);

        List<Menu> resultado = menuService.listarMenus();

        assertEquals(2, resultado.size());
        assertEquals(menus.get(0).getNombre(), resultado.get(0).getNombre());
        assertEquals(menus.get(1).getNombre(), resultado.get(1).getNombre());
        verify(menuRepository).findAll();
    }

    @Test
    @DisplayName("Actualizar Menu")
    void actualizarMenu() {
        Menu menuActualizado = new Menu(null, "Menu Actualizado", new ArrayList<>());

        when(menuRepository.findById(1L)).thenReturn(Optional.of(menu));
        when(menuRepository.save(any(Menu.class))).thenReturn(menuActualizado);

        Menu resultado = menuService.actualizarMenu(1L, menuActualizado);

        assertEquals(menuActualizado.getNombre(), resultado.getNombre());
        verify(menuRepository).findById(1L);
        verify(menuRepository).save(any(Menu.class));
    }

    @Test
    @DisplayName("Actualizar Menu - Error")
    void actualizarMenuError() {
        when(menuRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException excepcion = assertThrows(RuntimeException.class,
                () -> menuService.actualizarMenu(1L, menu));

        assertEquals("Menu con el id 1 no existe.", excepcion.getMessage());
        verify(menuRepository).findById(1L);
        verify(menuRepository, never()).save(any(Menu.class));
    }

    @Test
    @DisplayName("Eliminar Menu")
    void eliminarMenu() {
        doNothing().when(menuRepository).deleteById(1L);

        menuService.eliminarMenu(1L);

        verify(menuRepository).deleteById(1L);
    }


}
