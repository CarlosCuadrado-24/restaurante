package com.resturante.logica.dto;

import com.resturante.logica.models.Pedido;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class ClienteRespuestaDTO {

    private Long id;
    private String nombre;
    private String cedula;
    private String correo;
    private String telefono;
    private String tipo;
    private List<Pedido> pedidos;

    public ClienteRespuestaDTO(Long id, String nombre, String cedula, String correo, String telefono, String tipo, List<Pedido> pedidos) {
        this.id = id;
        this.nombre = nombre;
        this.cedula = cedula;
        this.correo = correo;
        this.telefono = telefono;
        this.tipo = tipo;
        this.pedidos = pedidos;
    }

}
