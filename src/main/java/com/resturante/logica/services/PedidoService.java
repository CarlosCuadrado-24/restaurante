package com.resturante.logica.services;

import com.resturante.logica.components.mediator.PedidoMediator;
import com.resturante.logica.models.Pedido;
import com.resturante.logica.repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    private final PedidoRepository repositorioPedido;
    private final PedidoMediator pedidoMediator;

    @Autowired
    public PedidoService(PedidoRepository repositorioPedido, PedidoMediator pedidoMediator) {
        this.repositorioPedido = repositorioPedido;
        this.pedidoMediator = pedidoMediator;
    }

    public void agregarPedido(Pedido pedido) {
        repositorioPedido.save(pedido);
        pedidoMediator.calcularTotal(pedido);
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
            pedido.setTotal(pedidoActualizado.getTotal());
            pedidoMediator.calcularTotal(pedido);
            return repositorioPedido.save(pedido);
        }).orElseThrow(() -> new RuntimeException("Pedido con el id " + id + " no pudo ser actualizado"));
    }

    public void eliminarPedido(Long id) {
        repositorioPedido.deleteById(id);
    }
}
