package org.example;

import java.util.Comparator;

public class EventoComparators {

    public static Comparator<Evento> porPrioridad =
            Comparator.comparingInt(Evento::getPrioridad);

    public static Comparator<Evento> porCongestion =
            Comparator.comparingInt(Evento::getCongestion);

    public static Comparator<Evento> porTiempo =
            Comparator.comparingLong(Evento::getTiempo);

    public static Comparator<Evento> porRiesgo =
            Comparator.comparingInt(Evento::getRiesgo);

    public static Comparator<Evento> porId =
            Comparator.comparingInt(Evento::getId);
}