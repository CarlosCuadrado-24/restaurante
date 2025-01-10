package com.resturante.logica.services;

import com.resturante.logica.repositories.DetallePedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DetallePedidoService {
    private final DetallePedidoRepository detallePedidoRepository;

    @Autowired
    public DetallePedidoService(DetallePedidoRepository detallePedidoRepository) {
        this.detallePedidoRepository = detallePedidoRepository;
    }

    public Double obtenerTotalPorPedidoId(Long pedidoId) {
        return detallePedidoRepository.obtenerSumaPreciosPorPedidoId(pedidoId);
    }

    public Long contarPorPlatoId(Long platoId){
       return detallePedidoRepository.contarCantPlato(platoId);
    }

}
