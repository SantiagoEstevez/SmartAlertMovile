package com.santiago.smartalert.models.Notif;

/**
 * Created by Santiago on 27/5/2018.
 */

public class Notif {

    private int id_noti;
    private int id_evento_global;
    private int tipo;
    private String fecha_dispara;
    private boolean entregada;
    private String usuario_recibe;
    private String condicion_dispara;

    public String getCondicion_dispara() {
        return condicion_dispara;
    }

    public void setCondicion_dispara(String condicion_dispara) {
        this.condicion_dispara = condicion_dispara;
    }

    public Notif(int id_noti, int id_evento_global, int tipo, String fecha_dispara, boolean entregada, String usuario_recibe, String condicion_dispara) {
        this.id_noti = id_noti;
        this.id_evento_global = id_evento_global;
        this.tipo = tipo;
        this.fecha_dispara = fecha_dispara;
        this.entregada = entregada;
        this.usuario_recibe = usuario_recibe;
        this.condicion_dispara = condicion_dispara;
    }

    public int getId_noti() {
        return id_noti;
    }

    public void setId_noti(int id_noti) {
        this.id_noti = id_noti;
    }

    public int getId_evento_global() {
        return id_evento_global;
    }

    public void setId_evento_global(int id_evento_global) {
        this.id_evento_global = id_evento_global;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public String getFecha_dispara() {
        return fecha_dispara;
    }

    public void setFecha_dispara(String fecha_dispara) {
        this.fecha_dispara = fecha_dispara;
    }

    public boolean isEntregada() {
        return entregada;
    }

    public void setEntregada(boolean entregada) {
        this.entregada = entregada;
    }

    public String getUsuario_recibe() {
        return usuario_recibe;
    }

    public void setUsuario_recibe(String usuario_recibe) {
        this.usuario_recibe = usuario_recibe;
    }
}
