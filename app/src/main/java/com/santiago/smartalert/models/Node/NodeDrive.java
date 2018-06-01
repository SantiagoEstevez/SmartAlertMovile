package com.santiago.smartalert.models.Node;

/**
 * Created by Santiago on 29/5/2018.
 */

public class NodeDrive {

    private String mount;
    private double espacioTotal;
    private double espacioDisponible;

    public NodeDrive(String mount, double espacioTotal, double espacioDisponible) {
        this.mount = mount;
        this.espacioTotal = espacioTotal;
        this.espacioDisponible = espacioDisponible;
    }

    public String getMount() {
        return mount;
    }

    public void setMount(String mount) {
        this.mount = mount;
    }

    public double getEspacioTotal() {
        return espacioTotal;
    }

    public void setEspacioTotal(double espacioTotal) {
        this.espacioTotal = espacioTotal;
    }

    public double getEspacioDisponible() {
        return espacioDisponible;
    }

    public void setEspacioDisponible(double espacioDisponible) {
        this.espacioDisponible = espacioDisponible;
    }
}
