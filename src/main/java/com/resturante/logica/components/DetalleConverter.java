package com.resturante.logica.components;

import com.resturante.logica.dto.DetallePedidoRespuestaDTO;
import com.resturante.logica.models.DetallePedido;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DetalleConverter {

    public static List<DetallePedidoRespuestaDTO> aRespuestasDTO(List<DetallePedido> detallePedidos){
        List<DetallePedidoRespuestaDTO> detallesRespuestaDTO = new ArrayList<>();
        detallePedidos.forEach(detallePedido ->
                        detallesRespuestaDTO.add(aRespuestaDTO(detallePedido))
                );
        return detallesRespuestaDTO;
    }

    public static DetallePedidoRespuestaDTO aRespuestaDTO(DetallePedido detalle){
        return new DetallePedidoRespuestaDTO(detalle.getCantidad(), detalle.getPrecio(), detalle.getPlato().getId(),detalle.getPlato().getNombre());
    }

}
