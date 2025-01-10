package com.resturante.logica.models;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class DetallePedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    private Integer cantidad;
    private Double precio;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "plato_id")
    private Plato plato;


    public DetallePedido() {
    }

    public DetallePedido(Long id, Integer cantidad, Double precio, Pedido pedido, Plato plato) {
        this.id = id;
        this.cantidad = cantidad;
        this.precio = precio;
        this.pedido = pedido;
        this.plato = plato;
    }
}
