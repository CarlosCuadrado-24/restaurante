package com.resturante.logica.components;

import com.resturante.logica.dto.PedidoRegistroDTO;
import com.resturante.logica.models.Pedido;
import org.springframework.stereotype.Component;

@Component
public class PedidoConverter {

    public static PedidoRegistroDTO aDTO(Pedido pedido) {
        PedidoRegistroDTO pedidoRegistroDTO = new PedidoRegistroDTO();
        //pedidoDTO.setFechaPedido(pedido.getFechaPedido());
        pedidoRegistroDTO.setEstado(pedido.getEstado());
        //pedidoDTO.setPrecio(pedido.getPrecio());
        return pedidoRegistroDTO;
    }

    public static Pedido aEntidad(PedidoRegistroDTO pedidoRegistroDTO) {
        Pedido pedido = new Pedido();
        //pedido.setFechaPedido(pedidoDTO.getFechaPedido());
        pedido.setEstado(pedidoRegistroDTO.getEstado());
        //pedido.setPrecio(pedidoDTO.getPrecio());
        return pedido;
    }
}
