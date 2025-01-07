package com.resturante.logica.components.strategy;

import com.resturante.logica.models.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class CalculoTotalContext {

    private final Map<String, CalculoTotalStrategy> estrategias;

    @Autowired
    public CalculoTotalContext(List<CalculoTotalStrategy> estrategias) {
        this.estrategias = estrategias.stream()
                .collect(Collectors.toMap(estrategia -> estrategia.getClass().getSimpleName(), estrategia -> estrategia));
    }

    public Double calcularTotal(Pedido pedido) {
        CalculoTotalStrategy estrategia = pedido.getCliente().getTipo().equals("FRECUENTE")
                ? estrategias.get("CalculoTotalFrecuente")
                : estrategias.get("CalculoTotalRegular");
        return estrategia.calcularTotal(pedido);
    }
    
}
