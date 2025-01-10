package com.restaurante.logica.TestServicio;

import com.resturante.logica.models.Cliente;
import com.resturante.logica.repositories.ClienteRepository;
import com.resturante.logica.services.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
public class ClienteServicesTest {
    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    private Cliente cliente;
    private List<Cliente> clientes;

    @BeforeEach
    void setUp() {
        cliente = new Cliente(1L, "Carlos Pérez", "1007293610", "carlos.perez@gmail.com", "3103692415", "COMUN");
        clientes = List.of(
                cliente,
                new Cliente(2L, "María López", "1008374621", "maria.lopez@gmail.com", "3204587963", "FRECUENTE")
        );
    }

    @Test
    @DisplayName("Agregar Cliente")
    void agregarCliente() {
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        clienteService.agregarCliente(cliente);

        ArgumentCaptor<Cliente> captor = ArgumentCaptor.forClass(Cliente.class);
        verify(clienteRepository).save(captor.capture());
        Cliente clienteCapturado = captor.getValue();

        assertEquals(cliente.getNombre(), clienteCapturado.getNombre());
        assertEquals(cliente.getCedula(), clienteCapturado.getCedula());
        assertEquals(cliente.getCorreo(), clienteCapturado.getCorreo());
        assertEquals(cliente.getTelefono(), clienteCapturado.getTelefono());
    }

    @Test
    @DisplayName("Obtener Cliente por ID")
    void obtenerCliente() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

        Optional<Cliente> resultado = clienteService.obtenerCliente(1L);

        assertTrue(resultado.isPresent());
        assertEquals(cliente.getNombre(), resultado.get().getNombre());
        verify(clienteRepository).findById(1L);
    }

    @Test
    @DisplayName("Listar Clientes")
    void listarClientes() {
        when(clienteRepository.findAll()).thenReturn(clientes);

        List<Cliente> resultado = clienteService.listarClientes();

        assertEquals(2, resultado.size());
        assertEquals(clientes.get(0).getNombre(), resultado.get(0).getNombre());
        assertEquals(clientes.get(1).getNombre(), resultado.get(1).getNombre());
        verify(clienteRepository).findAll();
    }

    @Test
    @DisplayName("Actualizar Cliente")
    void actualizarCliente() {
        Cliente clienteActualizado = new Cliente(null, "Carlos Gómez", "1007293610", "carlos.gomez@gmail.com", "3103692415", "COMUN");
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(clienteRepository.save(any(Cliente.class))).thenReturn(clienteActualizado);

        Cliente resultado = clienteService.actualizarCliente(1L, clienteActualizado);

        assertEquals(clienteActualizado.getNombre(), resultado.getNombre());
        assertEquals(clienteActualizado.getCorreo(), resultado.getCorreo());
        verify(clienteRepository).findById(1L);
        verify(clienteRepository).save(any(Cliente.class));
    }

    @Test
    @DisplayName("Actualizar Cliente - Caso de Error")
    void actualizarClienteError() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException excepcion = assertThrows(RuntimeException.class,
                () -> clienteService.actualizarCliente(1L, cliente));

        assertEquals("Cliente con el id 1 no pudo ser actualizado", excepcion.getMessage());
        verify(clienteRepository).findById(1L);
        verify(clienteRepository, never()).save(any(Cliente.class));
    }

    @Test
    @DisplayName("Eliminar Cliente")
    void eliminarCliente() {
        doNothing().when(clienteRepository).deleteById(1L);

        clienteService.eliminarCliente(1L);

        verify(clienteRepository).deleteById(1L);
    }



}
