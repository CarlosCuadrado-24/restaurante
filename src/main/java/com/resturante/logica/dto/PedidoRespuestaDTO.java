package com.resturante.logica.dto;


import lombok.Getter;
import lombok.Setter;


import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
public class PedidoRespuestaDTO {
    private Long id;
    private Long IdCliente;
    private List<DetallePedidoRespuestaDTO> detalles;
    private Double total;
    private String estado;
    private LocalDateTime fechaPedido;

    public PedidoRespuestaDTO(Long id, Long idCliente, List<DetallePedidoRespuestaDTO> detalles, Double total, String estado, LocalDateTime fechaPedido) {
        this.id = id;
        IdCliente = idCliente;
        this.detalles = detalles;
        this.total = total;
        this.estado = estado;
        this.fechaPedido = fechaPedido;
    }
}
