package org.example;

public class Evento {

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
    public String toString() {
        return "Evento{id=" + id + ", prioridad=" + prioridad + "}";
    }
}