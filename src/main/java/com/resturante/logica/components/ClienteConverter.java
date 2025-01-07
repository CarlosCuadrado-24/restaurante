package com.resturante.logica.components;

import com.resturante.logica.dto.ClienteRegistroDTO;
import com.resturante.logica.dto.ClienteRespuestaDTO;
import com.resturante.logica.models.Cliente;
import org.springframework.stereotype.Component;

@Component
public class ClienteConverter {

    public static ClienteRegistroDTO aRegistroDTO(Cliente cliente) {
        ClienteRegistroDTO clienteRegistroDTO = new ClienteRegistroDTO();
        clienteRegistroDTO.setNombre(cliente.getNombre());
        clienteRegistroDTO.setCedula(cliente.getCedula());
        clienteRegistroDTO.setCorreo(cliente.getCorreo());
        clienteRegistroDTO.setTelefono(cliente.getTelefono());
        return clienteRegistroDTO;
    }

    public static ClienteRespuestaDTO aRespuestaDTO(Cliente cliente) {
        return new ClienteRespuestaDTO(
                cliente.getId(),
                cliente.getNombre(),
                cliente.getCedula(),
                cliente.getCorreo(),
                cliente.getTelefono(),
                cliente.getTipo()
        );
    }

    public static Cliente aEntidad(Long id, ClienteRegistroDTO clienteRegistroDTO) {
        Cliente cliente = new Cliente();
        cliente.setId(id);
        cliente.setNombre(clienteRegistroDTO.getNombre());
        cliente.setCedula(clienteRegistroDTO.getCedula());
        cliente.setCorreo(clienteRegistroDTO.getCorreo());
        cliente.setTelefono(clienteRegistroDTO.getTelefono());
        return cliente;
    }

    public static Cliente aEntidad(ClienteRegistroDTO clienteRegistroDTO) {
        Cliente cliente = new Cliente();
        cliente.setNombre(clienteRegistroDTO.getNombre());
        cliente.setCedula(clienteRegistroDTO.getCedula());
        cliente.setCorreo(clienteRegistroDTO.getCorreo());
        cliente.setTelefono(clienteRegistroDTO.getTelefono());
        return cliente;
    }

}
