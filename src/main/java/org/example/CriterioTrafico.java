    package org.example;

    @FunctionalInterface
    public interface CriterioTrafico<T> {
        boolean evaluar(T t, int umbral);
    }