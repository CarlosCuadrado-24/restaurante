package com.resturante.logica.services;

import com.resturante.logica.models.Cliente;
import com.resturante.logica.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    private final ClienteRepository repositorioCliente;

    @Autowired
    public ClienteService(ClienteRepository repositorio) {
        this.repositorioCliente = repositorio;
    }

    public void agregarCliente(Cliente cliente){
        repositorioCliente.save(cliente);
    }

    public Optional<Cliente> obtenerCliente(Long id){
        return repositorioCliente.findById(id);
    }

    public List<Cliente> listarClientes(){
        return repositorioCliente.findAll();
    }

    public Cliente actualizarCliente(Long id,Cliente clienteActualizado){
        return repositorioCliente.findById(id).map(x->{
            x.setNombre(clienteActualizado.getNombre());
            x.setCedula(clienteActualizado.getCedula());
            x.setCorreo(clienteActualizado.getCorreo());
            x.setTelefono(clienteActualizado.getTelefono());
            return repositorioCliente.save(x);
        }).orElseThrow(()-> new RuntimeException("Cliente con el id "+id+" no pudo ser actualizado"));
    }

    public void eliminarCliente(Long id){
        repositorioCliente.deleteById(id);
    }



}
