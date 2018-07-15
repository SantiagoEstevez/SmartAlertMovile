package com.santiago.smartalert.models.Event;

public class EventType {
    private int id_tipo;
    private String nombre_tipo;
    private String descripcion;

    public EventType(int id_tipo, String nombre_tipo, String descripcion) {
        this.id_tipo = id_tipo;
        this.nombre_tipo = nombre_tipo;
        this.descripcion = descripcion;
    }

    public int getId_tipo() {
        return id_tipo;
    }

    public void setId_tipo(int id_tipo) {
        this.id_tipo = id_tipo;
    }

    public String getNombre_tipo() {
        return nombre_tipo;
    }

    public void setNombre_tipo(String nombre_tipo) {
        this.nombre_tipo = nombre_tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
