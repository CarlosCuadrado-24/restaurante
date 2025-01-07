package com.resturante.logica.components.mediator;

import com.resturante.logica.models.Pedido;
import com.resturante.logica.services.DetallePedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PedidoMediatorImpl implements PedidoMediator{
    private final DetallePedidoService detallePedidoService;

    @Autowired
    public PedidoMediatorImpl(DetallePedidoService detallePedidoService) {
        this.detallePedidoService = detallePedidoService;
    }

    @Override
    public void calcularTotal(Pedido pedido) {
        Double total = detallePedidoService.obtenerTotalPorPedidoId(pedido.getId());
        if(pedido.getCliente().getTipo().equals("FRECUENTE")){
             total = total - (total*0.0238);
        }
        pedido.setTotal(total != null ? total : 0.0);
    }

    private Double validarDescuento(Pedido pedido){
        if(pedido.getCliente().getTipo().equals("FRECUENTE")){
            return 0.0238;
        }else{
            return 0.0;
        }
    }
}
