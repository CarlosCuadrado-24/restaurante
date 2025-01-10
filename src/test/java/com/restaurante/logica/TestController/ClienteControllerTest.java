package com.restaurante.logica.TestController;

import com.resturante.logica.controllers.ClienteController;
import com.resturante.logica.dto.ClienteRegistroDTO;
import com.resturante.logica.dto.ClienteRespuestaDTO;
import com.resturante.logica.models.Cliente;
import com.resturante.logica.services.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

public class ClienteControllerTest {

    private static final Logger log = LoggerFactory.getLogger(ClienteControllerTest.class);
    private final WebTestClient webTestClient;
    private final ClienteService clienteService;
    private ClienteRegistroDTO clienteRegistroDTO;
    private ClienteRespuestaDTO clienteRespuestaDTO;
    private List<Cliente> clientes;
    private Cliente cliente;

    public ClienteControllerTest() {
        this.clienteService = mock(ClienteService.class);
        this.webTestClient = WebTestClient.bindToController(new ClienteController(clienteService)).build();
    }

    @BeforeEach
    void setUp(){
         clienteRegistroDTO = new ClienteRegistroDTO("carlos","1007293610","carlos@gmail.com","3103692415");
         clienteRespuestaDTO = new ClienteRespuestaDTO(1L,"carlos","1007293610","carlos@gmail.com","3103692415","COMUN");
        cliente = new Cliente(1L,"carlos","1007293610","carlos@gmail.com","3103692415","COMUN");
        clientes = List.of(
                new Cliente(1L, "Carlos Pérez", "1007293610", "carlos.perez@gmail.com", "3103692415", "COMUN"),
                new Cliente(2L, "María López", "1008374621", "maria.lopez@gmail.com", "3204587963", "FRECUENTE")
        );
    }

    @Test
    @DisplayName("Crear Cliente")
    void agregarCliente(){

        doNothing().when(clienteService).agregarCliente(any(Cliente.class));

        webTestClient
                .post()
                .uri("/api/cliente")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(clienteRegistroDTO)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .value(response -> assertEquals("Cliente agregado exitosamente.",response));

        ArgumentCaptor<Cliente> captor = ArgumentCaptor.forClass(Cliente.class);
        verify(clienteService).agregarCliente(captor.capture());
        Cliente clienteCapturado = captor.getValue();

        assertEquals(clienteCapturado.getNombre(),cliente.getNombre());
        assertEquals(clienteCapturado.getCedula(),cliente.getCedula());
        assertEquals(clienteCapturado.getCorreo(),cliente.getCorreo());
        assertEquals(clienteCapturado.getTelefono(),cliente.getTelefono());

    }

    @Test
    @DisplayName("Obtener Cliente")
    void obtenerCliente(){

       when(clienteService.obtenerCliente(anyLong())).thenReturn(Optional.of(cliente));

        webTestClient
                .get()
                .uri("/api/cliente/{id}",1L)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(ClienteRespuestaDTO.class)
                .value(prueba -> {
                    assertEquals(prueba.getId(),clienteRespuestaDTO.getId());
                    assertEquals(prueba.getNombre(), clienteRespuestaDTO.getNombre());
                    assertEquals(prueba.getCorreo(), clienteRespuestaDTO.getCorreo());
                    assertEquals(prueba.getTelefono(), clienteRespuestaDTO.getTelefono());
                    assertEquals(prueba.getTipo(), clienteRespuestaDTO.getTipo());
                });

        verify(clienteService).obtenerCliente(anyLong());

    }

    @Test
    @DisplayName("listar Clientes")
    void listarClientes(){

        when(clienteService.listarClientes()).thenReturn(clientes);

        webTestClient
                .get()
                .uri("/api/cliente")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(ClienteRespuestaDTO.class)
                .hasSize(2)
                .value(respuesta -> {
                    assertEquals(respuesta.get(0).getId(), clientes.get(0).getId());
                    assertEquals(respuesta.get(0).getNombre(), clientes.get(0).getNombre());
                    assertEquals(respuesta.get(0).getCorreo(), clientes.get(0).getCorreo());
                    assertEquals(respuesta.get(0).getTelefono(), clientes.get(0).getTelefono());
                    assertEquals(respuesta.get(0).getTipo(), clientes.get(0).getTipo());

                    assertEquals(respuesta.get(1).getId(), clientes.get(1).getId());
                    assertEquals(respuesta.get(1).getNombre(), clientes.get(1).getNombre());
                    assertEquals(respuesta.get(1).getCorreo(), clientes.get(1).getCorreo());
                    assertEquals(respuesta.get(1).getTelefono(), clientes.get(1).getTelefono());
                    assertEquals(respuesta.get(1).getTipo(), clientes.get(1).getTipo());
                });

        verify(clienteService).listarClientes();

    }

    @Test
    @DisplayName("Actualizar Cliente")
    void ActualizarCliente(){


        when(clienteService.actualizarCliente(anyLong(),any(Cliente.class))).thenReturn(cliente);

        webTestClient
                .put()
                .uri("/api/cliente/{id}",1L)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(clienteRegistroDTO)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(ClienteRespuestaDTO.class)
                .value(prueba -> {
                    assertEquals(prueba.getId(),clienteRespuestaDTO.getId());
                    assertEquals(prueba.getNombre(), clienteRespuestaDTO.getNombre());
                    assertEquals(prueba.getCorreo(), clienteRespuestaDTO.getCorreo());
                    assertEquals(prueba.getTelefono(), clienteRespuestaDTO.getTelefono());
                    assertEquals(prueba.getTipo(), clienteRespuestaDTO.getTipo());
                });

        verify(clienteService).actualizarCliente(anyLong(),any(Cliente.class));
    }

    @Test
    @DisplayName("caso de error para actualizar Cliente")
    void ActualizarClienteError(){


        doThrow(new RuntimeException("Cliente no encontrado"))
                .when(clienteService)
                .actualizarCliente(anyLong(), any(Cliente.class));

        webTestClient
                .put()
                .uri("/api/cliente/{id}",99L)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(clienteRegistroDTO)
                .exchange()
                .expectStatus().isNotFound();


        verify(clienteService).actualizarCliente(anyLong(),any(Cliente.class));
    }

    @Test
    @DisplayName("Eliminar cliente")
    void eliminarCliente() {

        doNothing().when(clienteService).eliminarCliente(anyLong());

        webTestClient.delete()
                .uri("/api/cliente/{id}", 1L)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .value(response -> assertEquals("Cliente eliminado exitosamente.",response));

        Mockito.verify(clienteService).eliminarCliente(anyLong());
    }

}
