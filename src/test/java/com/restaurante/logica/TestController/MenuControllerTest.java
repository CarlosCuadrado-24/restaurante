package com.restaurante.logica.TestController;

import com.resturante.logica.controllers.MenuController;
import com.resturante.logica.dto.MenuRegistroDTO;
import com.resturante.logica.dto.MenuRespuestaDTO;
import com.resturante.logica.models.Menu;
import com.resturante.logica.services.MenuService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MenuControllerTest {

    private final WebTestClient webTestClient;
    private final MenuService menuService;
    private MenuRegistroDTO menuRegistroDTO;
    private MenuRespuestaDTO menuRespuestaDTO;
    private List<Menu> menus;
    private Menu menu;

    public MenuControllerTest() {
        this.menuService = mock(MenuService.class);
        this.webTestClient = WebTestClient.bindToController(new MenuController(menuService)).build();
    }

    @BeforeEach
    void setUp() {
        menuRegistroDTO = new MenuRegistroDTO("Menu Principal");
        menuRespuestaDTO = new MenuRespuestaDTO(1L, "Menu Principal", List.of());
        menu = new Menu(1L, "Menu Principal", new ArrayList<>());
        menus = List.of(
                new Menu(1L, "Menu Principal", new ArrayList<>()),
                new Menu(2L, "Menu Secundario", new ArrayList<>())
        );
    }

    @Test
    @DisplayName("Crear Menu")
    void agregarMenu() {

        doNothing().when(menuService).agregarMenu(any(Menu.class));

        webTestClient
                .post()
                .uri("/api/menu")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(menuRegistroDTO)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .value(response -> assertEquals("Menu agregado exitosamente.", response));

        ArgumentCaptor<Menu> captor = ArgumentCaptor.forClass(Menu.class);
        verify(menuService).agregarMenu(captor.capture());
        Menu menuCapturado = captor.getValue();

        assertEquals(menuRegistroDTO.getNombre(), menuCapturado.getNombre());
    }

    @Test
    @DisplayName("Obtener Menu")
    void obtenerMenu() {

        when(menuService.obtenerMenu(anyLong())).thenReturn(Optional.of(menu));

        webTestClient
                .get()
                .uri("/api/menu/{id}", 1L)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(MenuRespuestaDTO.class)
                .value(respuesta -> {
                    assertEquals(menuRespuestaDTO.getId(), respuesta.getId());
                    assertEquals(menuRespuestaDTO.getNombre(), respuesta.getNombre());
                    assertEquals(menuRespuestaDTO.getPlatos().size(), respuesta.getPlatos().size());
                });

        verify(menuService).obtenerMenu(anyLong());
    }

    @Test
    @DisplayName("Listar Menus")
    void listarMenus() {

        when(menuService.listarMenus()).thenReturn(menus);

        webTestClient
                .get()
                .uri("/api/menu")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(MenuRespuestaDTO.class)
                .hasSize(2)
                .value(respuesta -> {
                    assertEquals(respuesta.get(0).getId(), menus.get(0).getId());
                    assertEquals(respuesta.get(0).getNombre(), menus.get(0).getNombre());
                    assertEquals(respuesta.get(0).getPlatos(), menus.get(0).getPlatos());
                    assertEquals(respuesta.get(1).getId(), menus.get(1).getId());
                    assertEquals(respuesta.get(1).getNombre(), menus.get(1).getNombre());
                    assertEquals(respuesta.get(1).getPlatos(), menus.get(1).getPlatos());
                });

        verify(menuService).listarMenus();
    }

    @Test
    @DisplayName("Actualizar Menu")
    void actualizarMenu() {

        when(menuService.actualizarMenu(anyLong(), any(Menu.class))).thenReturn(menu);

        webTestClient
                .put()
                .uri("/api/menu/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(menuRegistroDTO)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(MenuRespuestaDTO.class)
                .value(respuesta -> {
                    assertEquals(menuRespuestaDTO.getId(), respuesta.getId());
                    assertEquals(menuRespuestaDTO.getNombre(), respuesta.getNombre());
                    assertEquals(menuRespuestaDTO.getPlatos(),respuesta.getPlatos());
                });

        verify(menuService).actualizarMenu(anyLong(), any(Menu.class));
    }

    @Test
    @DisplayName("caso de error actualizar Menu")
    void actualizarMenuError() {

        when(menuService.actualizarMenu(anyLong(), any(Menu.class))).thenReturn(menu);
        doThrow(new RuntimeException("menu no encontrado"))
                .when(menuService)
                .actualizarMenu(anyLong(), any(Menu.class));

        webTestClient
                .put()
                .uri("/api/menu/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(menuRegistroDTO)
                .exchange()
                .expectStatus().isNotFound();


        verify(menuService).actualizarMenu(anyLong(), any(Menu.class));
    }


    @Test
    @DisplayName("Eliminar Menu")
    void eliminarMenu() {

        doNothing().when(menuService).eliminarMenu(anyLong());

        webTestClient
                .delete()
                .uri("/api/menu/{id}", 1L)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .value(response -> assertEquals("Menu eliminado exitosamente.", response));

        verify(menuService).eliminarMenu(anyLong());
    }

}
