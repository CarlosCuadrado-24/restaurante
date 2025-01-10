package com.resturante.logica.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    private String nombre;

    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Plato> platos;

    public Menu(Long id, String nombre, List<Plato> platos) {
        this.id = id;
        this.nombre = nombre;
        this.platos = platos;
    }

    public Menu(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Menu() {
    }
}
