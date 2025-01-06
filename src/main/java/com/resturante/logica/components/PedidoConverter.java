package com.resturante.logica.components;

import com.resturante.logica.dto.PedidoRegistroDTO;
import com.resturante.logica.dto.PedidoRespuestaDTO;
import com.resturante.logica.dto.PlatoRegistroDTO;
import com.resturante.logica.models.Cliente;
import com.resturante.logica.models.DetallePedido;
import com.resturante.logica.models.Pedido;
import com.resturante.logica.models.Plato;
import com.resturante.logica.services.ClienteService;
import com.resturante.logica.services.PedidoService;
import com.resturante.logica.services.PlatoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PedidoConverter {

    private final PlatoService platoService;
    private final ClienteService clienteService;


    @Autowired
    public PedidoConverter(PlatoService platoService, ClienteService clienteService) {
        this.platoService = platoService;
        this.clienteService = clienteService;
    }

    public PedidoRespuestaDTO aRespuestaDTO(Pedido pedido) {
        return new PedidoRespuestaDTO(pedido.getCliente().getId(),DetalleConverter.aRespuestasDTO(pedido.getDetalles()));
    }

    public Pedido aEntidad(PedidoRegistroDTO pedidoRegistroDTO) {
        // Obtener y validar el cliente
        Cliente cliente = clienteService.obtenerCliente(pedidoRegistroDTO.getIdCliente())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + pedidoRegistroDTO.getIdCliente()));

        // Crear el pedido
        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);

        // Procesar detalles del pedido
        List<DetallePedido> detalles = getDetallePedidos(pedidoRegistroDTO, pedido);

        // Asociar los detalles al pedido
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
