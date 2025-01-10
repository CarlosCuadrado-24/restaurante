package com.resturante.logica.services;

import com.resturante.logica.models.Plato;
import com.resturante.logica.repositories.PlatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlatoService {

    private final PlatoRepository repositorioPlato;

    @Autowired
    public PlatoService(PlatoRepository repositorio) {
        this.repositorioPlato = repositorio;
    }

    public void agregarPlato(Plato plato) {
        repositorioPlato.save(plato);
    }

    public Optional<Plato> obtenerPlato(Long id) {
        return repositorioPlato.findById(id);
    }

    public List<Plato> listarPlatos() {
        return repositorioPlato.findAll();
    }

    public Plato actualizarPlato(Long id, Plato platoActualizado) {
        return repositorioPlato.findById(id).map(plato -> {
            plato.setNombre(platoActualizado.getNombre());
            plato.setDescripcion(platoActualizado.getDescripcion());
            plato.setPrecio(platoActualizado.getPrecio());
            plato.setTipo(platoActualizado.getTipo());
            plato.setMenu(platoActualizado.getMenu());
            return repositorioPlato.save(plato);
        }).orElseThrow(() -> new RuntimeException("Plato con el id " + id + " no pudo ser actualizado"));
    }


    public void eliminarPlato(Long id) {
        repositorioPlato.deleteById(id);
    }

}
