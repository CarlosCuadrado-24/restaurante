package com.resturante.logica.repositories;

import com.resturante.logica.models.DetallePedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DetallePedidoRepository extends JpaRepository<DetallePedido,Long> {

    @Query("SELECT SUM(dp.precio) FROM DetallePedido dp WHERE dp.pedido.id = :pedidoId")
    Double obtenerSumaPreciosPorPedidoId(@Param("pedidoId") Long pedidoId);

    @Query("SELECT SUM(dp.cantidad) FROM DetallePedido dp WHERE dp.plato.id = :platoId")
    Long contarCantPlato(@Param("platoId") Long platoId);

}
