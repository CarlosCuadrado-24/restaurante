package com.resturante.logica.components.chainOfResponsibility;

import com.resturante.logica.models.DetallePedido;
import com.resturante.logica.models.Pedido;
import com.resturante.logica.models.Plato;
import com.resturante.logica.services.DetallePedidoService;
import com.resturante.logica.services.PlatoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PlatoPopularHandler implements PedidoHandler{
    private final PlatoService platoService;
    private final DetallePedidoService detallePedidoService;

    @Autowired
    public PlatoPopularHandler(PlatoService platoService, DetallePedidoService detallePedidoService) {
        this.platoService = platoService;
        this.detallePedidoService = detallePedidoService;
    }

    @Override
    public void manejar(Pedido pedido) {
        for (DetallePedido detalle : pedido.getDetalles()){
            Long TotalpedidosPlato = detallePedidoService.contarPorPlatoId(detalle.getPlato().getId());
            if(TotalpedidosPlato>100){
                Plato plato = detalle.getPlato();
                plato.setTipo("POPULAR");
                platoService.actualizarPlato(plato.getId(),plato);
            }
        }
    }
}
