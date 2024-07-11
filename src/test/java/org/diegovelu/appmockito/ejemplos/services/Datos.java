package org.diegovelu.appmockito.ejemplos.services;

import org.diegovelu.appmockito.ejemplos.models.Examen;

import java.util.Arrays;
import java.util.List;

public class Datos {

    public final static List<Examen> EXAMENES = Arrays.asList(
            new Examen(5L,"Matemáticas"),
            new Examen(2L,"Historia"),
            new Examen(3L,"Ciencias"));

    public final static List<String> PREGUNTAS = Arrays.asList(
            "mate","esp","automatas","paralela","virtualizacion"
    );

    public final static Examen EXAMEN = new Examen(null,"Fisica");
}
