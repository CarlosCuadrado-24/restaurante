package com.restaurante.logica.TestController;

import com.resturante.logica.components.PlatoConverter;
import com.resturante.logica.controllers.PlatoController;
import com.resturante.logica.dto.PlatoRegistroDTO;
import com.resturante.logica.dto.PlatoRespuestaDTO;
import com.resturante.logica.models.Menu;
import com.resturante.logica.models.Plato;
import com.resturante.logica.services.MenuService;
import com.resturante.logica.services.PlatoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

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

public class PlatoControllerTest {

    private final WebTestClient webTestClient;
    private final PlatoService platoService;
    private PlatoRegistroDTO platoRegistroDTO;
    private PlatoRespuestaDTO platoRespuestaDTO;
    private List<Plato> platos;
    private Plato plato;
    private final PlatoConverter platoConverter;
    private final MenuService menuService;

    public PlatoControllerTest() {
        this.platoService = mock(PlatoService.class);
        this.menuService = mock(MenuService.class); // Mock de MenuService
        this.platoConverter = new PlatoConverter(menuService); // Instancia real de PlatoConverter
        this.webTestClient = WebTestClient.bindToController(new PlatoController(platoService, platoConverter)).build();
    }

    @BeforeEach
    void setUp() {
        Menu menu = new Menu();
        menu.setId(1L);

        plato = new Plato(1L, "Plato 1", "Delicioso plato 1", 10.5, "COMUN", menu);
        platos = List.of(
                new Plato(1L, "Plato 1", "Delicioso plato 1", 10.5, "COMUN", menu),
                new Plato(2L, "Plato 2", "Delicioso plato 2", 15.0, "POPULAR", menu)
        );

        platoRespuestaDTO = new PlatoRespuestaDTO(1L, "Plato 1", "Delicioso plato 1", 10.5, "COMUN", 1L);
        platoRegistroDTO = new PlatoRegistroDTO("Plato 1", "Delicioso plato 1", 10.5, 1L);
        when(menuService.obtenerMenu(1L)).thenReturn(Optional.of(menu));
    }

    @Test
    @DisplayName("Crear Plato")
    void agregarPlato() {

        doNothing().when(platoService).agregarPlato(any(Plato.class));

        webTestClient
                .post()
                .uri("/api/plato")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(platoRegistroDTO)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .value(response -> assertEquals("Plato agregado exitosamente.", response));

        ArgumentCaptor<Plato> captor = ArgumentCaptor.forClass(Plato.class);
        verify(platoService).agregarPlato(captor.capture());
        Plato platoCapturado = captor.getValue();

        assertEquals(platoRegistroDTO.getNombre(), platoCapturado.getNombre());
        assertEquals(platoRegistroDTO.getDescripcion(), platoCapturado.getDescripcion());
        assertEquals(platoRegistroDTO.getPrecio(), platoCapturado.getPrecio());
    }

    @Test
    @DisplayName("Obtener Plato")
    void obtenerPlato() {

        when(platoService.obtenerPlato(anyLong())).thenReturn(Optional.of(plato));

        webTestClient
                .get()
                .uri("/api/plato/{id}", 1L)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(PlatoRespuestaDTO.class)
                .value(respuesta -> {
                    assertEquals(platoRespuestaDTO.getId(), respuesta.getId());
                    assertEquals(platoRespuestaDTO.getNombre(), respuesta.getNombre());
                    assertEquals(platoRespuestaDTO.getDescripcion(), respuesta.getDescripcion());
                    assertEquals(platoRespuestaDTO.getPrecio(), respuesta.getPrecio());
                    assertEquals(platoRespuestaDTO.getTipo(), respuesta.getTipo());
                });
        verify(platoService).obtenerPlato(anyLong());
    }

    @Test
    @DisplayName("Listar Platos")
    void listarPlatos() {

        when(platoService.listarPlatos()).thenReturn(platos);

        webTestClient
                .get()
                .uri("/api/plato")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(PlatoRespuestaDTO.class)
                .hasSize(2)
                .value(respuesta -> {
                    assertEquals(respuesta.get(0).getId(), platos.get(0).getId());
                    assertEquals(respuesta.get(0).getNombre(), platos.get(0).getNombre());
                    assertEquals(respuesta.get(0).getDescripcion(), platos.get(0).getDescripcion());
                    assertEquals(respuesta.get(0).getPrecio(), platos.get(0).getPrecio());
                    assertEquals(respuesta.get(0).getTipo(), platos.get(0).getTipo());
                    assertEquals(respuesta.get(0).getIdMenu(), platos.get(0).getMenu().getId());

                    assertEquals(respuesta.get(1).getId(), platos.get(1).getId());
                    assertEquals(respuesta.get(1).getNombre(), platos.get(1).getNombre());
                    assertEquals(respuesta.get(1).getDescripcion(), platos.get(1).getDescripcion());
                    assertEquals(respuesta.get(1).getPrecio(), platos.get(1).getPrecio());
                    assertEquals(respuesta.get(1).getTipo(), platos.get(1).getTipo());
                    assertEquals(respuesta.get(1).getIdMenu(), platos.get(1).getMenu().getId());
                });

        verify(platoService).listarPlatos();
    }

    @Test
    @DisplayName("Actualizar Plato")
    void actualizarPlato() {

        when(platoService.actualizarPlato(anyLong(), any(Plato.class))).thenReturn(plato);

        webTestClient
                .put()
                .uri("/api/plato/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(platoRegistroDTO)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(PlatoRespuestaDTO.class)
                .value(respuesta -> {
                    assertEquals(platoRespuestaDTO.getId(), respuesta.getId());
                    assertEquals(platoRespuestaDTO.getNombre(), respuesta.getNombre());
                    assertEquals(platoRespuestaDTO.getDescripcion(), respuesta.getDescripcion());
                });

        verify(platoService).actualizarPlato(anyLong(), any(Plato.class));
    }

    @Test
    @DisplayName("Actualizar Plato - Error NotFound")
    void actualizarPlatoError() {

        doThrow(new RuntimeException("Plato no encontrado"))
                .when(platoService).actualizarPlato(anyLong(), any(Plato.class));

        webTestClient
                .put()
                .uri("/api/plato/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(platoRegistroDTO)
                .exchange()
                .expectStatus().isNotFound();

        verify(platoService).actualizarPlato(anyLong(), any(Plato.class));
    }

    @Test
    @DisplayName("Eliminar Plato")
    void eliminarPlato() {

        doNothing().when(platoService).eliminarPlato(anyLong());

        webTestClient
                .delete()
                .uri("/api/plato/{id}", 1L)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .value(response -> assertEquals("Plato eliminado exitosamente.", response));

        verify(platoService).eliminarPlato(anyLong());
    }

}
