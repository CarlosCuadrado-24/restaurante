package com.resturante.logica.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DetallePedidoService {
    private final com.resturante.logica.repositories.DetallePedidoRepository detallePedidoRepository;

    @Autowired
    public DetallePedidoService(com.resturante.logica.repositories.DetallePedidoRepository detallePedidoRepository) {
        this.detallePedidoRepository = detallePedidoRepository;
    }

    public Double obtenerTotalPorPedidoId(Long pedidoId) {
        return detallePedidoRepository.obtenerSumaPreciosPorPedidoId(pedidoId);
    }

}
