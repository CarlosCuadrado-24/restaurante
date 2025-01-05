package com.resturante.logica.components;

import com.resturante.logica.dto.PedidoDTO;
import com.resturante.logica.models.Pedido;
import org.springframework.stereotype.Component;

@Component
public class PedidoConverter {

    public PedidoDTO aDTO(Pedido pedido) {
        PedidoDTO pedidoDTO = new PedidoDTO();
        //pedidoDTO.setFechaPedido(pedido.getFechaPedido());
        pedidoDTO.setEstado(pedido.getEstado());
        //pedidoDTO.setPrecio(pedido.getPrecio());
        return pedidoDTO;
    }

    public Pedido aEntidad(PedidoDTO pedidoDTO) {
        Pedido pedido = new Pedido();
        //pedido.setFechaPedido(pedidoDTO.getFechaPedido());
        pedido.setEstado(pedidoDTO.getEstado());
        //pedido.setPrecio(pedidoDTO.getPrecio());
        return pedido;
    }
}
