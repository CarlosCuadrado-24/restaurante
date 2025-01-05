package com.resturante.logica.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteRegistroDTO {
    private String nombre;
    private String cedula;
    private String correo;
    private String telefono;
}
