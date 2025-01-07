package com.resturante.logica.components.chainOfResponsibility;

import com.resturante.logica.models.Cliente;
import com.resturante.logica.models.Pedido;
import com.resturante.logica.services.ClienteService;
import com.resturante.logica.services.PedidoService;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClienteFrecuenteHandler implements PedidoHandler{

    private final ClienteService clienteService;
    private final ObjectProvider<PedidoService> pedidoServiceProvider;

    @Autowired
    public ClienteFrecuenteHandler(ClienteService clienteService, ObjectProvider<PedidoService> pedidoServiceProvider) {
        this.clienteService = clienteService;
        this.pedidoServiceProvider = pedidoServiceProvider;
    }

    @Override
    public void manejar(Pedido pedido) {
        PedidoService pedidoService = pedidoServiceProvider.getIfAvailable();
        if (pedidoService != null) {
            Long totalPedidosCliente = pedidoService.contarPedidosPorCliente(pedido.getCliente().getId());
            if (totalPedidosCliente > 10) {
                Cliente cliente = pedido.getCliente();
                cliente.setTipo("FRECUENTE");
                clienteService.actualizarCliente(cliente.getId(), cliente);
            }
        }else{
            System.out.println("Error pedidoService null");
        }
    }
}
