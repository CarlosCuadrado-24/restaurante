package com.resturante.logica.services;

import com.resturante.logica.components.chainOfResponsibility.PedidoChain;
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
    private final PedidoChain pedidoChain;

    @Autowired
    public PedidoService(PedidoRepository repositorioPedido, PedidoMediator pedidoMediator, PedidoChain pedidoChain) {
        this.repositorioPedido = repositorioPedido;
        this.pedidoMediator = pedidoMediator;
        this.pedidoChain = pedidoChain;
    }

    public void agregarPedido(Pedido pedido) {
        repositorioPedido.save(pedido); //guardado normal
        pedidoChain.procesar(pedido); //Patron Chain
        pedidoMediator.calcularTotal(pedido); //patron Mediator
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

    public Long contarPedidosPorCliente(Long idCliente){
       return repositorioPedido.contarPedidosPorClienteId(idCliente);
    }

}
