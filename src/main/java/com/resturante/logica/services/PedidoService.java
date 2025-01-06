package com.resturante.logica.services;

import com.resturante.logica.models.Pedido;
import com.resturante.logica.repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    private final PedidoRepository repositorioPedido;

    @Autowired
    public PedidoService(PedidoRepository repositorio) {
        this.repositorioPedido = repositorio;
    }

    public void agregarPedido(Pedido pedido) {
        repositorioPedido.save(pedido);
    }

    public Optional<Pedido> obtenerPedido(Long id) {
        return repositorioPedido.findById(id);
    }

    public List<Pedido> listarPedidos() {
        return repositorioPedido.findAll();
    }

    public Pedido actualizarPedido(Long id, Pedido pedidoActualizado) {
        return repositorioPedido.findById(id).map(pedido -> {
            pedido.setFechaPedido(pedidoActualizado.getFechaPedido());
            pedido.setEstado(pedidoActualizado.getEstado());
            pedido.setCliente(pedidoActualizado.getCliente());
            pedido.setDetalles(pedidoActualizado.getDetalles());
            return repositorioPedido.save(pedido);
        }).orElseThrow(() -> new RuntimeException("Pedido con el id " + id + " no pudo ser actualizado"));
    }

    public void eliminarPedido(Long id) {
        repositorioPedido.deleteById(id);
    }
}
