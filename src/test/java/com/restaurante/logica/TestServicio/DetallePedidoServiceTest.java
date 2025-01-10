package com.restaurante.logica.TestServicio;

import com.resturante.logica.repositories.DetallePedidoRepository;
import com.resturante.logica.services.DetallePedidoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DetallePedidoServiceTest {

    @Mock
    private DetallePedidoRepository detallePedidoRepository;

    @InjectMocks
    private DetallePedidoService detallePedidoService;

    @Test
    @DisplayName("Obtener Total por Pedido ID")
    void obtenerTotalPorPedidoId() {
        Long pedidoId = 1L;
        Double totalEsperado = 120.50;

        when(detallePedidoRepository.obtenerSumaPreciosPorPedidoId(pedidoId)).thenReturn(totalEsperado);

        Double totalObtenido = detallePedidoService.obtenerTotalPorPedidoId(pedidoId);

        assertEquals(totalEsperado, totalObtenido);
        verify(detallePedidoRepository).obtenerSumaPreciosPorPedidoId(pedidoId);
    }

    @Test
    @DisplayName("Contar Cantidad por Plato ID")
    void contarPorPlatoId() {
        Long platoId = 2L;
        Long cantidadEsperada = 5L;

        when(detallePedidoRepository.contarCantPlato(platoId)).thenReturn(cantidadEsperada);

        Long cantidadObtenida = detallePedidoService.contarPorPlatoId(platoId);

        assertEquals(cantidadEsperada, cantidadObtenida);
        verify(detallePedidoRepository).contarCantPlato(platoId);
    }


}
