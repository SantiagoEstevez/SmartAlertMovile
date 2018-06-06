package com.santiago.smartalert.models.Event;

/**
 * Created by Santiago on 5/6/2018.
 */

public class Event {
    private String nombreEvento;
    private int idEvento;
    private boolean activo;

    public Event(String nombreEvento, int idEvento, boolean activo) {
        this.nombreEvento = nombreEvento;
        this.idEvento = idEvento;
        this.activo = activo;
    }

    public String getNombreEvento() {
        return nombreEvento;
    }

    public void setNombreEvento(String nombreEvento) {
        this.nombreEvento = nombreEvento;
    }

    public int getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(int idEvento) {
        this.idEvento = idEvento;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
