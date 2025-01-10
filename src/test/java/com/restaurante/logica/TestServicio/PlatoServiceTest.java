package com.restaurante.logica.TestServicio;

import com.resturante.logica.models.Menu;
import com.resturante.logica.models.Plato;
import com.resturante.logica.repositories.PlatoRepository;
import com.resturante.logica.services.PlatoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PlatoServiceTest {
    @Mock
    private PlatoRepository platoRepository;

    @InjectMocks
    private PlatoService platoService;

    private Plato plato;
    private Menu menu;
    private List<Plato> platos;

    @BeforeEach
    void setUp() {
        menu = new Menu(1L, "Menu Principal", new ArrayList<>());
        plato = new Plato(1L, "Plato 1", "Delicioso plato 1", 10.5, "COMUN", menu);
        platos = new ArrayList<>(Arrays.asList(
                plato,
                new Plato(2L, "Plato 2", "Delicioso plato 2", 15.0, "POPULAR", menu)
        ));
    }

    @Test
    @DisplayName("Agregar Plato")
    void agregarPlato() {
        when(platoRepository.save(any(Plato.class))).thenReturn(plato);

        platoService.agregarPlato(plato);

        ArgumentCaptor<Plato> captor = ArgumentCaptor.forClass(Plato.class);
        verify(platoRepository).save(captor.capture());
        Plato platoCapturado = captor.getValue();

        assertEquals(plato.getNombre(), platoCapturado.getNombre());
        assertEquals(plato.getDescripcion(), platoCapturado.getDescripcion());
        assertEquals(plato.getPrecio(), platoCapturado.getPrecio());
        assertEquals(plato.getTipo(), platoCapturado.getTipo());
    }

    @Test
    @DisplayName("Obtener Plato por ID")
    void obtenerPlato() {
        when(platoRepository.findById(1L)).thenReturn(Optional.of(plato));

        Optional<Plato> resultado = platoService.obtenerPlato(1L);

        assertTrue(resultado.isPresent());
        assertEquals(plato.getNombre(), resultado.get().getNombre());
        verify(platoRepository).findById(1L);
    }

    @Test
    @DisplayName("Listar Platos")
    void listarPlatos() {
        when(platoRepository.findAll()).thenReturn(platos);

        List<Plato> resultado = platoService.listarPlatos();

        assertEquals(2, resultado.size());
        assertEquals(platos.get(0).getNombre(), resultado.get(0).getNombre());
        assertEquals(platos.get(1).getNombre(), resultado.get(1).getNombre());
        verify(platoRepository).findAll();
    }

    @Test
    @DisplayName("Actualizar Plato")
    void actualizarPlato() {
        Plato platoActualizado = new Plato(null, "Plato Actualizado", "Descripción Actualizada", 12.0, "COMUN", menu);

        when(platoRepository.findById(1L)).thenReturn(Optional.of(plato));
        when(platoRepository.save(any(Plato.class))).thenReturn(platoActualizado);

        Plato resultado = platoService.actualizarPlato(1L, platoActualizado);

        assertEquals(platoActualizado.getNombre(), resultado.getNombre());
        assertEquals(platoActualizado.getDescripcion(), resultado.getDescripcion());
        assertEquals(platoActualizado.getPrecio(), resultado.getPrecio());
        assertEquals(platoActualizado.getTipo(), resultado.getTipo());
        verify(platoRepository).findById(1L);
        verify(platoRepository).save(any(Plato.class));
    }

    @Test
    @DisplayName("Actualizar Plato - Error")
    void actualizarPlatoError() {
        when(platoRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException excepcion = assertThrows(RuntimeException.class,
                () -> platoService.actualizarPlato(1L, plato));

        assertEquals("Plato con el id 1 no pudo ser actualizado", excepcion.getMessage());
        verify(platoRepository).findById(1L);
        verify(platoRepository, never()).save(any(Plato.class));
    }

    @Test
    @DisplayName("Eliminar Plato")
    void eliminarPlato() {
        doNothing().when(platoRepository).deleteById(1L);

        platoService.eliminarPlato(1L);

        verify(platoRepository).deleteById(1L);
    }


}
