package com.resturante.logica.components.strategy;

import com.resturante.logica.models.Pedido;
import com.resturante.logica.services.DetallePedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CalculoTotalRegular implements CalculoTotalStrategy{
    private final DetallePedidoService detallePedidoService;

    @Autowired
    public CalculoTotalRegular(DetallePedidoService detallePedidoService) {
        this.detallePedidoService = detallePedidoService;
    }

    @Override
    public Double calcularTotal(Pedido pedido) {
        return detallePedidoService.obtenerTotalPorPedidoId(pedido.getId());
    }
}
