package com.santiago.smartalert.models.Node;

/**
 * Created by Santiago on 29/5/2018.
 */

public class NodeRAM {
    private int memoriaLibre;
    private int memoriaTotal;
    private int memoriaEnUso;

    public NodeRAM(int memoriaLibre, int memoriaTotal, int memoriaEnUso) {
        this.memoriaLibre = memoriaLibre;
        this.memoriaTotal = memoriaTotal;
        this.memoriaEnUso = memoriaEnUso;
    }

    public int getMemoriaLibre() {
        return memoriaLibre;
    }

    public void setMemoriaLibre(int memoriaLibre) {
        this.memoriaLibre = memoriaLibre;
    }

    public int getMemoriaTotal() {
        return memoriaTotal;
    }

    public void setMemoriaTotal(int memoriaTotal) {
        this.memoriaTotal = memoriaTotal;
    }

    public int getMemoriaEnUso() {
        return memoriaEnUso;
    }

    public void setMemoriaEnUso(int memoriaEnUso) {
        this.memoriaEnUso = memoriaEnUso;
    }
}
