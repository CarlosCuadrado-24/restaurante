package com.resturante.logica.components.chainOfResponsibility;

import com.resturante.logica.models.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PedidoChain {
    private final List<PedidoHandler> handlers;

    @Autowired
    public PedidoChain(List<PedidoHandler> handlers) {
        this.handlers = handlers;
    }

    public void procesar(Pedido pedido) {
        for (PedidoHandler handler : handlers) {
            handler.manejar(pedido);
        }
    }
}
