package org.example;

/**
 * Representa un evento dentro del sistema de tráfico.
 *
 * Cada evento tiene una prioridad y atributos como congestión,
 * tiempo y nivel de riesgo. Se utiliza principalmente en el Heap
 * para procesar eventos según su prioridad.
 */
public class Evento implements Comparable<Evento> {

    private int id;
    private int prioridad;
    private int congestion;
    private long tiempo;
    private int riesgo;

    /**
     * Crea un evento con sus datos principales.
     */
    public Evento(int id, int prioridad, int congestion, long tiempo, int riesgo) {
        this.id = id;
        this.prioridad = prioridad;
        this.congestion = congestion;
        this.tiempo = tiempo;
        this.riesgo = riesgo;
    }

    /**
     * Identificador único del evento.
     */
    public int getId() { return id; }

    /**
     * Nivel de prioridad del evento.
     */
    public int getPrioridad() { return prioridad; }

    /**
     * Nivel de congestión asociado.
     */
    public int getCongestion() { return congestion; }

    /**
     * Marca de tiempo del evento.
     */
    public long getTiempo() { return tiempo; }

    /**
     * Nivel de riesgo del evento.
     */
    public int getRiesgo() { return riesgo; }

    /**
     * Permite cambiar la prioridad del evento.
     * Se usa especialmente junto con updatePriority en el heap.
     */
    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }

    /**
     * Define el orden natural de los eventos.
     * Se comparan por prioridad.
     */
    @Override
    public int compareTo(Evento o) {
        return Integer.compare(this.prioridad, o.prioridad);
    }

    /**
     * Dos eventos son iguales si tienen el mismo ID.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Evento)) return false;
        Evento e = (Evento) o;
        return this.id == e.id;
    }

    /**
     * Representación simple del evento para impresión.
     */
    @Override
    public String toString() {
        return "Evento{id=" + id + ", prioridad=" + prioridad + "}";
    }
}