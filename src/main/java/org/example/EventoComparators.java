package org.example;

import java.util.Comparator;

/**
 * Contiene distintos criterios de comparación para objetos Evento.
 *
 * Permite cambiar dinámicamente la forma en que se ordenan los eventos,
 * por ejemplo en el Heap, según prioridad, congestión, tiempo o riesgo.
 */
public class EventoComparators {

    /**
     * Ordena eventos por prioridad.
     */
    public static Comparator<Evento> porPrioridad =
            Comparator.comparingInt(Evento::getPrioridad);

    /**
     * Ordena eventos por nivel de congestión.
     */
    public static Comparator<Evento> porCongestion =
            Comparator.comparingInt(Evento::getCongestion);

    /**
     * Ordena eventos por tiempo (timestamp).
     */
    public static Comparator<Evento> porTiempo =
            Comparator.comparingLong(Evento::getTiempo);

    /**
     * Ordena eventos por nivel de riesgo.
     */
    public static Comparator<Evento> porRiesgo =
            Comparator.comparingInt(Evento::getRiesgo);

    /**
     * Ordena eventos por ID.
     */
    public static Comparator<Evento> porId =
            Comparator.comparingInt(Evento::getId);
}