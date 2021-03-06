package com.santiago.smartalert.models.Node;

/**
 * Created by Santiago on 29/5/2018.
 */

public class NodeHead {

    private String distro;
    private String ipAddress;
    private String ipPublica;
    private int cantCpus;
    private int totalRAM;

    public NodeHead(String distro, String ipAddress, String ipPublica, int cantCpus, int totalRAM) {
        this.distro = distro;
        this.ipAddress = ipAddress;
        this.ipPublica = ipPublica;
        this.cantCpus = cantCpus;
        this.totalRAM = totalRAM;
    }

    public String getDistro() {
        return distro;
    }

    public void setDistro(String distro) {
        this.distro = distro;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getIpPublica() {
        return ipPublica;
    }

    public void setIpPublica(String ipPublica) {
        this.ipPublica = ipPublica;
    }

    public int getCantCpus() {
        return cantCpus;
    }

    public void setCantCpus(int cantCpus) {
        this.cantCpus = cantCpus;
    }

    public int getTotalRAM() {
        return totalRAM;
    }

    public void setTotalRAM(int totalRAM) {
        this.totalRAM = totalRAM;
    }
}
