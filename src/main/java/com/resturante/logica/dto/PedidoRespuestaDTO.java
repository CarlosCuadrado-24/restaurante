package com.resturante.logica.dto;


import lombok.Getter;
import lombok.Setter;


import java.util.List;


@Getter
@Setter
public class PedidoRespuestaDTO {
    private Long IdCliente;
    private List<DetallePedidoRespuestaDTO> detalles;

    public PedidoRespuestaDTO(Long idCliente, List<DetallePedidoRespuestaDTO> detalles) {
        this.IdCliente = idCliente;
        this.detalles = detalles;
    }
}
