package com.resturante.logica.repositories;

import com.resturante.logica.models.Pedido;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido,Long> {
}
