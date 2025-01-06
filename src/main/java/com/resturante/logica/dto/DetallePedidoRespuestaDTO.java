package com.resturante.logica.dto;

import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
public class DetallePedidoRespuestaDTO {
    private Integer cantidad;
    private Double precio;
    private Long idPlato;
    private String nombrePlato;

    public DetallePedidoRespuestaDTO(Integer cantidad, Double precio, Long idPlato, String nombrePlato) {
        this.cantidad = cantidad;
        this.precio = precio;
        this.idPlato = idPlato;
        this.nombrePlato = nombrePlato;
    }
}
