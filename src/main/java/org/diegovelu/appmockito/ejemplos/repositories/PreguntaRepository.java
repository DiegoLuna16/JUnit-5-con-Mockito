package org.diegovelu.appmockito.ejemplos.repositories;

import org.diegovelu.appmockito.ejemplos.models.Examen;

import java.util.List;

public interface PreguntaRepository {
    List<String> findPreguntasPorExamenId(Long id);
    void guardarVarias(List<String> preguntas);
}
