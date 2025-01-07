package com.resturante.logica.controllers;

import com.resturante.logica.components.PlatoConverter;
import com.resturante.logica.dto.PlatoRegistroDTO;
import com.resturante.logica.dto.PlatoRespuestaDTO;
import com.resturante.logica.models.Plato;
import com.resturante.logica.services.PlatoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/plato")
public class PlatoController {

    private final PlatoService platoService;
    private final PlatoConverter platoConverter;

    @Autowired
    public PlatoController(PlatoService platoService, PlatoConverter platoConverter) {
        this.platoService = platoService;
        this.platoConverter = platoConverter;
    }

    @PostMapping
    public ResponseEntity<String> agregarPlato(@RequestBody PlatoRegistroDTO platoRegistro) {
        Plato plato = platoConverter.aEntidad(platoRegistro);
        platoService.agregarPlato(plato);
        return ResponseEntity.ok("Plato agregado exitosamente.");
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlatoRespuestaDTO> obtenerPlato(@PathVariable Long id) {
        return platoService.obtenerPlato(id).map(plato -> ResponseEntity.ok(platoConverter.aRespuestaDTO(plato)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<PlatoRespuestaDTO>> listarPlatos() {
        List<Plato> platos = platoService.listarPlatos();
        List<PlatoRespuestaDTO> response = platos.stream()
                .map(PlatoConverter::aRespuestaDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlatoRespuestaDTO> actualizarPlato(@PathVariable Long id, @RequestBody PlatoRegistroDTO platoRegistro) {
        try {
            Plato platoActualizado = platoConverter.aEntidad(id, platoRegistro);
            platoService.actualizarPlato(id, platoActualizado);
            return ResponseEntity.ok(platoConverter.aRespuestaDTO(platoActualizado));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarPlato(@PathVariable Long id) {
        platoService.eliminarPlato(id);
        return ResponseEntity.ok("Plato eliminado exitosamente.");
    }

}
