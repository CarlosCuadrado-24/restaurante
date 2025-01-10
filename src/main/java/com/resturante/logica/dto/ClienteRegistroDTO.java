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

    public ClienteRegistroDTO(String nombre, String cedula, String correo, String telefono) {
        this.nombre = nombre;
        this.cedula = cedula;
        this.correo = correo;
        this.telefono = telefono;
    }

    public ClienteRegistroDTO() {
    }
}
