package com.resturante.logica.dto;

import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
public class DetallePedidoRegistroDTO {
    private Long IdPlato;
    private Integer cantidad;

    public DetallePedidoRegistroDTO(Long idPlato, Integer cantidad) {
        IdPlato = idPlato;
        this.cantidad = cantidad;
    }
}
