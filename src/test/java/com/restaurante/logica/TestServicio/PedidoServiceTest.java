package com.restaurante.logica.TestServicio;

import com.resturante.logica.components.chainOfResponsibility.PedidoChain;
import com.resturante.logica.components.mediator.PedidoMediator;
import com.resturante.logica.models.Cliente;
import com.resturante.logica.models.DetallePedido;
import com.resturante.logica.models.Menu;
import com.resturante.logica.models.Pedido;
import com.resturante.logica.models.Plato;
import com.resturante.logica.repositories.PedidoRepository;
import com.resturante.logica.services.PedidoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class PedidoServiceTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private PedidoMediator pedidoMediator;

    @Mock
    private PedidoChain pedidoChain;

    @InjectMocks
    private PedidoService pedidoService;

    private Pedido pedido;
    private Cliente cliente;
    private List<DetallePedido> detallesPedido;



    @BeforeEach
    void setUp() {
        cliente = new Cliente(1L, "Carlos", "1007293610", "carlos@gmail.com", "3103692415", "COMUN");

        Plato plato = new Plato(1L, "Plato 1", "Descripción 1", 10.0, "COMUN", new Menu(1L, "Menú 1"));
        DetallePedido detallePedido = new DetallePedido(1L, 2, 20.0, null, plato);
        detallesPedido = new ArrayList<>();
        detallesPedido.add(detallePedido);

        pedido = new Pedido(1L, LocalDateTime.now(), "PENDIENTE", cliente, detallesPedido, 40.0);
    }

    @Test
    @DisplayName("Agregar Pedido")
    void agregarPedido() {
        doNothing().when(pedidoChain).procesar(any(Pedido.class));
        doNothing().when(pedidoMediator).calcularTotal(any(Pedido.class));
        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedido);

        pedidoService.agregarPedido(pedido);

        verify(pedidoRepository, times(3)).save(pedido);
        verify(pedidoChain).procesar(pedido);
        verify(pedidoMediator).calcularTotal(pedido);
    }

    @Test
    @DisplayName("Obtener Pedido")
    void obtenerPedido() {
        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));

        Optional<Pedido> resultado = pedidoService.obtenerPedido(1L);

        assertTrue(resultado.isPresent());
        assertEquals(pedido.getId(), resultado.get().getId());
        verify(pedidoRepository).findById(1L);
    }

    @Test
    @DisplayName("Listar Pedidos")
    void listarPedidos() {
        when(pedidoRepository.findAll()).thenReturn(List.of(pedido));

        List<Pedido> resultado = pedidoService.listarPedidos();

        assertEquals(1, resultado.size());
        assertEquals(pedido.getId(), resultado.get(0).getId());
        verify(pedidoRepository).findAll();
    }

    @Test
    @DisplayName("Actualizar Pedido")
    void actualizarPedido() {

        List<DetallePedido> detallesPedidoMutable = new ArrayList<>(detallesPedido);


        Pedido pedidoActualizado = new Pedido(1L, LocalDateTime.now(), "CONFIRMADO", cliente, detallesPedidoMutable, 50.0);

        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));
        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedidoActualizado);


        doAnswer(invocation -> {
            Pedido pedidoArgument = invocation.getArgument(0);
            pedidoArgument.setTotal(50.0);
            return null;
        }).when(pedidoMediator).calcularTotal(any(Pedido.class));

        Pedido resultado = pedidoService.actualizarPedido(1L, pedidoActualizado);


        assertEquals("CONFIRMADO", resultado.getEstado());
        assertEquals(40.0, resultado.getTotal());
        verify(pedidoRepository, times(2)).save(any(Pedido.class));
        verify(pedidoChain).procesar(any(Pedido.class));
        verify(pedidoMediator).calcularTotal(any(Pedido.class));
    }

    @Test
    @DisplayName("Actualizar Pedido - Error si no existe")
    void actualizarPedidoError() {
        when(pedidoRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException excepcion = assertThrows(RuntimeException.class, () -> pedidoService.actualizarPedido(1L, pedido));

        assertEquals("Pedido con el id 1 no pudo ser actualizado", excepcion.getMessage());
        verify(pedidoRepository).findById(1L);
    }

    @Test
    @DisplayName("Eliminar Pedido")
    void eliminarPedido() {
        doNothing().when(pedidoRepository).deleteById(1L);

        pedidoService.eliminarPedido(1L);

        verify(pedidoRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Contar Pedidos por Cliente")
    void contarPedidosPorCliente() {
        when(pedidoRepository.contarPedidosPorClienteId(1L)).thenReturn(3L);

        Long resultado = pedidoService.contarPedidosPorCliente(1L);

        assertEquals(3L, resultado);
        verify(pedidoRepository).contarPedidosPorClienteId(1L);
    }



}
