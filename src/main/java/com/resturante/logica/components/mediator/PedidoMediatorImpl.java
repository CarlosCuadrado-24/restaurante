package com.resturante.logica.components.mediator;

import com.resturante.logica.components.strategy.CalculoTotalContext;
import com.resturante.logica.models.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PedidoMediatorImpl implements PedidoMediator{

    private final CalculoTotalContext calculoTotalContext;

    @Autowired
    public PedidoMediatorImpl(CalculoTotalContext calculoTotalContext) {
        this.calculoTotalContext = calculoTotalContext;
    }

    @Override
    public void calcularTotal(Pedido pedido) {
        Double total = calculoTotalContext.calcularTotal(pedido);
        pedido.setTotal(total);
    }

}
