package org.example;

/**
 * Representa una intersección dentro de la ciudad.
 *
 * Contiene información básica como ubicación (distrito, zona, avenida)
 * y nivel de congestión. Se usa como elemento en estructuras como
 * BST y AVL, donde se ordena por ID.
 */
public class Interseccion implements Comparable<Interseccion> {

    private int id;
    private String nombre;
    private int nivelCongestion;
    private String distrito;
    private String zona;
    private String avenida;

    /**
     * Crea una nueva intersección con sus datos principales.
     */
    public Interseccion(int id, String nombre, int nivelCongestion,
                        String distrito, String zona, String avenida) {
        this.id = id;
        this.nombre = nombre;
        this.nivelCongestion = nivelCongestion;
        this.distrito = distrito;
        this.zona = zona;
        this.avenida = avenida;
    }

    /**
     * Devuelve el ID único de la intersección.
     */
    public int getId() { return id; }

    /**
     * Devuelve el nombre de la intersección.
     */
    public String getNombre() { return nombre; }

    /**
     * Devuelve el nivel de congestión actual.
     */
    public int getNivelCongestion() { return nivelCongestion; }

    /**
     * Devuelve el distrito al que pertenece.
     */
    public String getDistrito() { return distrito; }

    /**
     * Devuelve la zona de la intersección.
     */
    public String getZona() { return zona; }

    /**
     * Devuelve la avenida asociada.
     */
    public String getAvenida() { return avenida; }

    /**
     * Define el orden natural de las intersecciones.
     * Se comparan únicamente por ID.
     */
    @Override
    public int compareTo(Interseccion o) {
        return Integer.compare(this.id, o.id);
    }

    /**
     * Dos intersecciones se consideran iguales si tienen el mismo ID.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Interseccion)) return false;
        Interseccion i = (Interseccion) o;
        return this.id == i.id;
    }

    /**
     * Representación en texto para impresión o depuración.
     */
    @Override
    public String toString() {
        return "Interseccion{id=" + id + ", nombre=" + nombre +
                ", congestion=" + nivelCongestion + ", distrito=" + distrito + "}";
    }
}