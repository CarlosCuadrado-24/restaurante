package com.resturante.logica.components;

import com.resturante.logica.dto.PedidoRegistroDTO;
import com.resturante.logica.dto.PedidoRespuestaDTO;
import com.resturante.logica.models.Cliente;
import com.resturante.logica.models.DetallePedido;
import com.resturante.logica.models.Pedido;
import com.resturante.logica.models.Plato;
import com.resturante.logica.services.ClienteService;
import com.resturante.logica.services.DetallePedidoService;
import com.resturante.logica.services.PlatoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PedidoConverter {

    private final PlatoService platoService;
    private final ClienteService clienteService;
    private final DetallePedidoService detallePedidoService;

    @Autowired
    public PedidoConverter(PlatoService platoService, ClienteService clienteService, DetallePedidoService detallePedidoService) {
        this.platoService = platoService;
        this.clienteService = clienteService;
        this.detallePedidoService = detallePedidoService;
    }


    public PedidoRespuestaDTO aRespuestaDTO(Pedido pedido) {
        return new PedidoRespuestaDTO(pedido.getId(),pedido.getCliente().getId(),DetalleConverter.aRespuestasDTO(pedido.getDetalles()), pedido.getTotal(), pedido.getEstado(),pedido.getFechaPedido());
    }

    public Pedido aEntidad(PedidoRegistroDTO pedidoRegistroDTO) {

        Cliente cliente = clienteService.obtenerCliente(pedidoRegistroDTO.getIdCliente())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + pedidoRegistroDTO.getIdCliente()));

        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);

        List<DetallePedido> detalles = getDetallePedidos(pedidoRegistroDTO, pedido);

        pedido.setDetalles(detalles);
        return pedido;
    }

    private List<DetallePedido> getDetallePedidos(PedidoRegistroDTO pedidoRegistroDTO, Pedido pedido) {
        return pedidoRegistroDTO.getDetalles().stream().map(detalleDTO -> {
            Plato plato = platoService.obtenerPlato(detalleDTO.getIdPlato())
                    .orElseThrow(() -> new RuntimeException("Plato no encontrado con ID: " + detalleDTO.getIdPlato()));

            DetallePedido detalle = new DetallePedido();
            detalle.setPlato(plato);
            detalle.setPedido(pedido);
            detalle.setCantidad(detalleDTO.getCantidad());
            detalle.setPrecio(plato.getPrecio() * detalleDTO.getCantidad());
            return detalle;
        }).collect(Collectors.toList());
    }

    public Pedido aEntidad(Long id,PedidoRegistroDTO pedidoRegistroDTO) {
        Pedido pedido = aEntidad(pedidoRegistroDTO);
        pedido.setId(id);
        return pedido;
    }

}
