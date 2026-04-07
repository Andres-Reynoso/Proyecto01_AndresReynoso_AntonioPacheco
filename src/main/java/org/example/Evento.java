package org.example;

public class Evento implements Comparable<Evento> {

    private int id;
    private int prioridad;
    private int congestion;
    private long tiempo;
    private int riesgo;

    public Evento(int id, int prioridad, int congestion, long tiempo, int riesgo) {
        this.id = id;
        this.prioridad = prioridad;
        this.congestion = congestion;
        this.tiempo = tiempo;
        this.riesgo = riesgo;
    }

    public int getId() { return id; }
    public int getPrioridad() { return prioridad; }
    public int getCongestion() { return congestion; }
    public long getTiempo() { return tiempo; }
    public int getRiesgo() { return riesgo; }

    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }

    @Override
    public int compareTo(Evento o) {
        return Integer.compare(this.prioridad, o.prioridad);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Evento)) return false;
        Evento e = (Evento) o;
        return this.id == e.id;
    }

    @Override
    public String toString() {
        return "Evento{id=" + id + ", prioridad=" + prioridad + "}";
    }
}