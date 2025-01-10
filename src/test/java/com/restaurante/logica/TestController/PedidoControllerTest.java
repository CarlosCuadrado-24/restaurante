package com.restaurante.logica.TestController;

import com.resturante.logica.components.PedidoConverter;
import com.resturante.logica.controllers.PedidoController;
import com.resturante.logica.dto.DetallePedidoRegistroDTO;
import com.resturante.logica.dto.DetallePedidoRespuestaDTO;
import com.resturante.logica.dto.PedidoRegistroDTO;
import com.resturante.logica.dto.PedidoRespuestaDTO;
import com.resturante.logica.models.Cliente;
import com.resturante.logica.models.DetallePedido;
import com.resturante.logica.models.Menu;
import com.resturante.logica.models.Pedido;
import com.resturante.logica.models.Plato;
import com.resturante.logica.repositories.DetallePedidoRepository;
import com.resturante.logica.services.ClienteService;
import com.resturante.logica.services.DetallePedidoService;
import com.resturante.logica.services.PedidoService;
import com.resturante.logica.services.PlatoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDateTime;
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

public class PedidoControllerTest {

    private final WebTestClient webTestClient;
    private final PedidoService pedidoService;
    private final PlatoService platoService;
    private final ClienteService clienteService;
    private final DetallePedidoRepository detallePedidoRepository;
    private PedidoRegistroDTO pedidoRegistroDTO;
    private PedidoRespuestaDTO pedidoRespuestaDTO;
    private List<Pedido> pedidos;
    private Pedido pedido;

    public PedidoControllerTest() {
        this.pedidoService = mock(PedidoService.class);
        this.platoService = mock(PlatoService.class);
        this.clienteService = mock(ClienteService.class);
        this.detallePedidoRepository = mock(DetallePedidoRepository.class);
        PedidoConverter pedidoConverter = new PedidoConverter(platoService, clienteService, new DetallePedidoService(detallePedidoRepository));
        this.webTestClient = WebTestClient.bindToController(new PedidoController(pedidoService, pedidoConverter)).build();
    }

    @BeforeEach
    void setUp() {

        Cliente cliente = new Cliente(1L,"carlos","1007293610","carlos@gmail.com","3103692415","COMUN");
        Plato plato = new Plato(1L, "Plato 1", "Descripción 1", 10.0, "COMUN", new Menu(1L, "Menú 1"));

        DetallePedido detallePedido = new DetallePedido(1L, 2, 40.0, pedido, plato);
        List<DetallePedido> detallesPedido = List.of(detallePedido);

        pedido = new Pedido(1L, LocalDateTime.now(), "PENDIENTE", cliente, detallesPedido, 40.0);
        pedidos = List.of(pedido);

        pedidoRegistroDTO = new PedidoRegistroDTO(1L, List.of(new DetallePedidoRegistroDTO(1L, 2)));
        pedidoRespuestaDTO = new PedidoRespuestaDTO(1L, 1L, List.of(new DetallePedidoRespuestaDTO(2, 40.0, 1L, "Plato 1")), 40.0, "PENDIENTE", pedido.getFechaPedido());
    }

    @Test
    @DisplayName("Agregar Pedido")
    void agregarPedido() {
        doNothing().when(pedidoService).agregarPedido(any(Pedido.class));
        when(clienteService.obtenerCliente(anyLong())).thenReturn(Optional.of(new Cliente(1L,"carlos","1007293610","carlos@gmail.com","3103692415","COMUN")));
        when(platoService.obtenerPlato(anyLong())).thenReturn(Optional.of(new Plato(1L, "Plato 1", "Descripción", 20.0, "COMUN", new Menu())));

        webTestClient
                .post()
                .uri("/api/pedido")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(pedidoRegistroDTO)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .value(response -> assertEquals("Pedido agregado exitosamente.", response));

        ArgumentCaptor<Pedido> captor = ArgumentCaptor.forClass(Pedido.class);
        verify(pedidoService).agregarPedido(captor.capture());
        Pedido pedidoCapturado = captor.getValue();

        assertEquals(pedidoRegistroDTO.getIdCliente(), pedidoCapturado.getCliente().getId());
        assertEquals(1, pedidoCapturado.getDetalles().size());
        assertEquals(2, pedidoCapturado.getDetalles().get(0).getCantidad());
    }

    @Test
    @DisplayName("Obtener Pedido")
    void obtenerPedido() {
        when(pedidoService.obtenerPedido(anyLong())).thenReturn(Optional.of(pedido));

        webTestClient
                .get()
                .uri("/api/pedido/{id}", 1L)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(PedidoRespuestaDTO.class)
                .value(respuesta -> {
                    assertEquals(pedidoRespuestaDTO.getId(), respuesta.getId());
                    assertEquals(pedidoRespuestaDTO.getIdCliente(), respuesta.getIdCliente());
                    assertEquals(pedidoRespuestaDTO.getTotal(), respuesta.getTotal());
                    assertEquals(pedidoRespuestaDTO.getEstado(), respuesta.getEstado());
                });

        verify(pedidoService).obtenerPedido(anyLong());
    }

    @Test
    @DisplayName("Listar Pedidos")
    void listarPedidos() {
        when(pedidoService.listarPedidos()).thenReturn(pedidos);

        webTestClient
                .get()
                .uri("/api/pedido")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(PedidoRespuestaDTO.class)
                .hasSize(1)
                .value(respuesta -> {
                    assertEquals(pedidoRespuestaDTO.getId(), respuesta.get(0).getId());
                    assertEquals(pedidoRespuestaDTO.getIdCliente(), respuesta.get(0).getIdCliente());
                    assertEquals(pedidoRespuestaDTO.getTotal(), respuesta.get(0).getTotal());
                });

        verify(pedidoService).listarPedidos();
    }

    @Test
    @DisplayName("Actualizar Pedido")
    void actualizarPedido() {
        when(clienteService.obtenerCliente(anyLong())).thenReturn(Optional.of(new Cliente(1L,"carlos","1007293610","carlos@gmail.com","3103692415","COMUN")));
        when(platoService.obtenerPlato(anyLong())).thenReturn(Optional.of(new Plato(1L, "Plato 1", "Descripción", 20.0, "COMUN", new Menu())));
        when(pedidoService.actualizarPedido(anyLong(), any(Pedido.class))).thenReturn(pedido);
        when(pedidoService.obtenerPedido(anyLong())).thenReturn(Optional.of(pedido));

        webTestClient
                .put()
                .uri("/api/pedido/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(pedidoRegistroDTO)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(PedidoRespuestaDTO.class)
                .value(respuesta -> {
                    assertEquals(pedidoRespuestaDTO.getId(), respuesta.getId());
                    assertEquals(pedidoRespuestaDTO.getIdCliente(), respuesta.getIdCliente());
                });

        verify(pedidoService).actualizarPedido(anyLong(), any(Pedido.class));
    }

    @Test
    @DisplayName("Caso de error para actualizar Pedido - Pedido no encontrado")
    void actualizarPedidoError() {

        doThrow(new RuntimeException("Pedido no encontrado"))
                .when(pedidoService).actualizarPedido(anyLong(), any(Pedido.class));

        webTestClient
                .put()
                .uri("/api/pedido/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(pedidoRegistroDTO)
                .exchange()
                .expectStatus().isNotFound();

    }

    @Test
    @DisplayName("Eliminar Pedido")
    void eliminarPedido() {
        doNothing().when(pedidoService).eliminarPedido(anyLong());

        webTestClient
                .delete()
                .uri("/api/pedido/{id}", 1L)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .value(response -> assertEquals("Pedido eliminado exitosamente.", response));

        verify(pedidoService).eliminarPedido(anyLong());
    }

}
