package com.resturante.logica.components.chainOfResponsibility;

import com.resturante.logica.models.Pedido;

public interface PedidoHandler {
    void manejar(Pedido pedido);
}
