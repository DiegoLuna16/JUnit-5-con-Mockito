package org.diegovelu.appmockito.ejemplos.repositories;

import org.diegovelu.appmockito.ejemplos.models.Examen;
import java.util.List;

public interface ExamenRepository {
    Examen guardar(Examen examen);
    List<Examen> findAll();
}
