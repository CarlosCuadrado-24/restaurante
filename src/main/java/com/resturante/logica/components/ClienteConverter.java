package com.resturante.logica.components;

import com.resturante.logica.dto.ClienteDTO;
import com.resturante.logica.models.Cliente;
import org.springframework.stereotype.Component;

@Component
public class ClienteConverter {
    public ClienteDTO aDTO(Cliente cliente) {
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setNombre(cliente.getNombre());
        clienteDTO.setCedula(cliente.getCedula());
        clienteDTO.setCorreo(cliente.getCorreo());
        clienteDTO.setTelefono(cliente.getTelefono());
        return clienteDTO;
    }

    public Cliente aEntidad(ClienteDTO clienteDTO) {
        Cliente cliente = new Cliente();
        cliente.setNombre(clienteDTO.getNombre());
        cliente.setCedula(clienteDTO.getCedula());
        cliente.setCorreo(clienteDTO.getCorreo());
        cliente.setTelefono(clienteDTO.getTelefono());
        return cliente;
    }
}
