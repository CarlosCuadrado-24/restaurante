package com.resturante.logica.dto;


import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ClienteRespuestaDTO {

    private Long id;
    private String nombre;
    private String cedula;
    private String correo;
    private String telefono;
    private String tipo;

    public ClienteRespuestaDTO(Long id, String nombre, String cedula, String correo, String telefono, String tipo) {
        this.id = id;
        this.nombre = nombre;
        this.cedula = cedula;
        this.correo = correo;
        this.telefono = telefono;
        this.tipo = tipo;
    }

}
