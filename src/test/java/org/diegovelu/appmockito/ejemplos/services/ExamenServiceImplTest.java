package org.diegovelu.appmockito.ejemplos.services;

import org.diegovelu.appmockito.ejemplos.models.Examen;
import org.diegovelu.appmockito.ejemplos.repositories.ExamenRepository;
import org.diegovelu.appmockito.ejemplos.repositories.PreguntaRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ExamenServiceImplTest {

    @Mock
    ExamenRepository repository;
    @Mock
    PreguntaRepository preguntasRepository;

    @InjectMocks
    ExamenServiceImpl service;



//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        repository = mock(ExamenRepository.class);
//        preguntasRepository = mock (PreguntaRepository.class);
//        service = new ExamenServiceImpl(repository,preguntasRepository);
//
//    }

    @Test
    void findExamenPorNombre() {

        when(repository.findAll()).thenReturn(Datos.EXAMENES);
        Optional<Examen> examen = service.findExamenPorNombre("Matemáticas");
        assertTrue(examen.isPresent());
        assertEquals(5L, examen.orElseThrow().getId());
        assertEquals("Matemáticas", examen.orElseThrow().getNombre());

    }

    @Test
    void findExamenPorNombreListaVacia() {

        ExamenRepository repository = mock(ExamenRepository.class);
        ExamenService service = new ExamenServiceImpl(repository,preguntasRepository);
        List<Examen> datos = Collections.emptyList();
        when(repository.findAll()).thenReturn(datos);
        Optional<Examen> examen = service.findExamenPorNombre("Matemáticas");
        assertFalse(examen.isPresent());

    }

    @Test
    void testPreguntasExamen() {
        when(repository.findAll()).thenReturn(Datos.EXAMENES);
        when(preguntasRepository.findPreguntasPorExamenId(anyLong())).thenReturn(Datos.PREGUNTAS);
        Examen examen = service.findExamenPorNombreConPreguntas("Matemáticas");
        assertEquals(5,examen.getPreguntas().size());
        assertTrue(examen.getPreguntas().contains("esp"));
    }

    @Test
    void testPreguntasExamenVerify() {
        when(repository.findAll()).thenReturn(Datos.EXAMENES);
        when(preguntasRepository.findPreguntasPorExamenId(anyLong())).thenReturn(Datos.PREGUNTAS);
        Examen examen = service.findExamenPorNombreConPreguntas("Matemáticas");
        assertEquals(5,examen.getPreguntas().size());
        assertTrue(examen.getPreguntas().contains("esp"));
        verify(repository).findAll();
        verify(preguntasRepository).findPreguntasPorExamenId(5L);
    }

    @Test
    void testNoExisteExamenVerify() {
        //given
        when(repository.findAll()).thenReturn(Collections.emptyList());
        when(preguntasRepository.findPreguntasPorExamenId(anyLong())).thenReturn(Datos.PREGUNTAS);
        //given
        Examen examen = service.findExamenPorNombreConPreguntas("Matemáticas");
        //then
        assertNull(examen);
        verify(repository).findAll();
        verify(preguntasRepository).findPreguntasPorExamenId(5L);
    }

    @Test
    void testGuardarExamen() {
        // Given
        Examen newExamen = Datos.EXAMEN;
        newExamen.setPreguntas(Datos.PREGUNTAS);

        when(repository.guardar(any(Examen.class))).then(new Answer<Examen>() {
            Long secuencia = 8L;
            @Override
            public Examen answer(InvocationOnMock invocationOnMock) throws Throwable {
                Examen examen = invocationOnMock.getArgument(0);
                examen.setId(secuencia++);
                return examen;
            }
        });

        //When
        Examen examen = service.guardar(newExamen);

        //Then
        assertNotNull(examen.getId());
        assertEquals(8L,examen.getId());
        assertEquals("Fisica",examen.getNombre());

        verify(repository).guardar(any(Examen.class));
        verify(preguntasRepository).guardarVarias(anyList());

    }
}