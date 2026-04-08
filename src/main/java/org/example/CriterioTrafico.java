package org.example;

/**
 * Interfaz funcional para definir criterios personalizados.
 *
 * Permite evaluar elementos (por ejemplo eventos) usando
 * una condición externa basada en un umbral.
 *
 * Se usa junto con expresiones lambda.
 */
@FunctionalInterface
public interface CriterioTrafico<T> {

    /**
     * Evalúa si un elemento cumple con el criterio definido.
     */
    boolean evaluar(T t, int umbral);
}