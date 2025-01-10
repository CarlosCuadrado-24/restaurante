package com.resturante.logica.dto;

import lombok.Getter;
import lombok.Setter;


import java.util.List;


@Getter
@Setter
public class PedidoRegistroDTO {
    private Long IdCliente;
    private List<DetallePedidoRegistroDTO> detalles;

    public PedidoRegistroDTO(Long idCliente, List<DetallePedidoRegistroDTO> detalles) {
        IdCliente = idCliente;
        this.detalles = detalles;
    }

}
