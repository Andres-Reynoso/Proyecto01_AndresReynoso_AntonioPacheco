package org.example;

public class Interseccion implements Comparable<Interseccion> {

    private int id;
    private String nombre;
    private int nivelCongestion;
    private String distrito;
    private String zona;
    private String avenida;

    public Interseccion(int id, String nombre, int nivelCongestion,
                        String distrito, String zona, String avenida) {
        this.id = id;
        this.nombre = nombre;
        this.nivelCongestion = nivelCongestion;
        this.distrito = distrito;
        this.zona = zona;
        this.avenida = avenida;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public int getNivelCongestion() { return nivelCongestion; }
    public String getDistrito() { return distrito; }
    public String getZona() { return zona; }
    public String getAvenida() { return avenida; }

    @Override
    public int compareTo(Interseccion o) {
        return Integer.compare(this.id, o.id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Interseccion)) return false;
        Interseccion i = (Interseccion) o;
        return this.id == i.id;
    }

    @Override
    public String toString() {
        return "Interseccion{id=" + id + ", nombre=" + nombre +
                ", congestion=" + nivelCongestion + ", distrito=" + distrito + "}";
    }
}