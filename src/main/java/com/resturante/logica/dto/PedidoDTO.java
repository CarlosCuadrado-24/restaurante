package com.resturante.logica.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PedidoDTO {

    private LocalDateTime fechaPedido;
    private String estado;
    private Double precio;

}
