package com.resturante.logica.components.strategy;

import com.resturante.logica.models.Pedido;

public interface CalculoTotalStrategy {
    Double calcularTotal(Pedido pedido);
}
