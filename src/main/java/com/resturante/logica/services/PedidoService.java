package com.resturante.logica.services;

import com.resturante.logica.components.chainOfResponsibility.PedidoChain;
import com.resturante.logica.components.mediator.PedidoMediator;
import com.resturante.logica.models.DetallePedido;
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
    private final PedidoChain pedidoChain;

    @Autowired
    public PedidoService(PedidoRepository repositorioPedido, PedidoMediator pedidoMediator, PedidoChain pedidoChain) {
        this.repositorioPedido = repositorioPedido;
        this.pedidoMediator = pedidoMediator;
        this.pedidoChain = pedidoChain;
    }

    public void agregarPedido(Pedido pedido) {
        repositorioPedido.save(pedido);
        pedidoChain.procesar(pedido);
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

        return repositorioPedido.findById(id).map(pedidoExistente -> {

            pedidoExistente.setEstado(pedidoActualizado.getEstado());
            pedidoExistente.setCliente(pedidoActualizado.getCliente());
            pedidoExistente.getDetalles().clear();
            for (DetallePedido detalle : pedidoActualizado.getDetalles()) {
                detalle.setPedido(pedidoExistente);
                pedidoExistente.getDetalles().add(detalle);
            }
            pedidoExistente = repositorioPedido.save(pedidoExistente);
            pedidoChain.procesar(pedidoExistente);
            pedidoMediator.calcularTotal(pedidoExistente);
            pedidoExistente = repositorioPedido.save(pedidoExistente);
            return obtenerPedido(pedidoExistente.getId()).orElseThrow(() -> new RuntimeException("Pedido con el id " + id + " no pudo ser encontrado"));
        }).orElseThrow(() -> new RuntimeException("Pedido con el id " + id + " no pudo ser actualizado"));
    }

    public void eliminarPedido(Long id) {
        repositorioPedido.deleteById(id);
    }

    public Long contarPedidosPorCliente(Long idCliente){
       return repositorioPedido.contarPedidosPorClienteId(idCliente);
    }

}
