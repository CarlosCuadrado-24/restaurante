package com.resturante.logica.repositories;

import com.resturante.logica.models.Pedido;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PedidoRepository extends JpaRepository<Pedido,Long> {
    @Query("SELECT COUNT(p.cliente.id) FROM Pedido p WHERE p.cliente.id = :clienteId")
    Long contarPedidosPorClienteId(@Param("clienteId") Long clienteId);
}
