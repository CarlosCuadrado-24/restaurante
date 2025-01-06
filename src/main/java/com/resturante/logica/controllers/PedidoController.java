package com.resturante.logica.controllers;

import com.resturante.logica.components.PedidoConverter;
import com.resturante.logica.dto.PedidoRegistroDTO;
import com.resturante.logica.dto.PedidoRespuestaDTO;
import com.resturante.logica.models.Pedido;
import com.resturante.logica.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pedido")
public class PedidoController {

    private final PedidoService pedidoService;
    private final PedidoConverter pedidoConverter;

    @Autowired
    public PedidoController(PedidoService pedidoService, PedidoConverter pedidoConverter) {
        this.pedidoService = pedidoService;
        this.pedidoConverter = pedidoConverter;
    }

    @PostMapping
    public ResponseEntity<String> agregarPedido(@RequestBody PedidoRegistroDTO pedidoRegistro) {
        Pedido pedido = pedidoConverter.aEntidad(pedidoRegistro);
        pedidoService.agregarPedido(pedido);
        return ResponseEntity.ok("Pedido agregado exitosamente.");
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoRespuestaDTO> obtenerPedido(@PathVariable Long id) {
        return pedidoService.obtenerPedido(id).map(pedido -> ResponseEntity.ok(pedidoConverter.aRespuestaDTO(pedido)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<PedidoRespuestaDTO>> listarPedidos() {
        List<Pedido> pedidos = pedidoService.listarPedidos();
        List<PedidoRespuestaDTO> response = pedidos.stream()
                .map(pedidoConverter::aRespuestaDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PedidoRespuestaDTO> actualizarPedido(@PathVariable Long id, @RequestBody PedidoRegistroDTO pedidoRegistro) {
        try {
            Pedido pedidoActualizado = pedidoConverter.aEntidad(id, pedidoRegistro);
            pedidoService.actualizarPedido(id, pedidoActualizado);
            return ResponseEntity.ok(pedidoConverter.aRespuestaDTO(pedidoActualizado));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarPedido(@PathVariable Long id) {
        pedidoService.eliminarPedido(id);
        return ResponseEntity.ok("Pedido eliminado exitosamente.");
    }

}
