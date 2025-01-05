package com.resturante.logica.controllers;

import com.resturante.logica.components.ClienteConverter;
import com.resturante.logica.dto.ClienteRegistroDTO;
import com.resturante.logica.dto.ClienteRespuestaDTO;
import com.resturante.logica.models.Cliente;
import com.resturante.logica.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/cliente")
public class ClienteController {

    private final ClienteService clienteService;

    @Autowired
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping
    public ResponseEntity<String> agregarCliente(@RequestBody ClienteRegistroDTO clienteRegistro){
        Cliente cliente = ClienteConverter.aEntidad(clienteRegistro);
        clienteService.agregarCliente(cliente);
        return ResponseEntity.ok("Cliente agregado exitosamente.");
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteRespuestaDTO>obtenerCliente(@PathVariable Long id){
        return clienteService.obtenerCliente(id).map(cliente -> ResponseEntity.ok(ClienteConverter.aRespuestaDTO(cliente))).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<ClienteRespuestaDTO>> listarClientes(){
        List<Cliente> clientes = clienteService.listarClientes();
        List<ClienteRespuestaDTO> response = clientes.stream()
                .map(ClienteConverter::aRespuestaDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteRespuestaDTO> actualizarCliente(@PathVariable Long id, @RequestBody ClienteRegistroDTO clienteRegistro) {
        try {
            Cliente clienteActualizado = ClienteConverter.aEntidad(id,clienteRegistro);
            clienteService.actualizarCliente(id,clienteActualizado);
            return ResponseEntity.ok(ClienteConverter.aRespuestaDTO(clienteActualizado));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarCliente(@PathVariable Long id){
        clienteService.eliminarCliente(id);
        return ResponseEntity.ok("Cliente eliminado exitosamente.");
    }



}
