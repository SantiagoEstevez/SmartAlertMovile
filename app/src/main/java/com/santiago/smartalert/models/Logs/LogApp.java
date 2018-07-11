package com.santiago.smartalert.models.Logs;

public class LogApp {
    private String timeReported;
    private String fromHost;
    private String fromHostIp;
    private String programName;
    private String sysLogSeverityText;
    private String rawMessage;

    public LogApp(String timeReported, String fromHost, String fromHostIp, String programName, String sysLogSeverityText, String rawMessage) {
        this.timeReported = timeReported;
        this.fromHost = fromHost;
        this.fromHostIp = fromHostIp;
        this.programName = programName;
        this.sysLogSeverityText = sysLogSeverityText;
        this.rawMessage = rawMessage;
    }

    public String getTimeReported() {
        return timeReported;
    }

    public void setTimeReported(String timeReported) {
        this.timeReported = timeReported;
    }

    public String getFromHost() {
        return fromHost;
    }

    public void setFromHost(String fromHost) {
        this.fromHost = fromHost;
    }

    public String getFromHostIp() {
        return fromHostIp;
    }

    public void setFromHostIp(String fromHostIp) {
        this.fromHostIp = fromHostIp;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public String getSysLogSeverityText() {
        return sysLogSeverityText;
    }

    public void setSysLogSeverityText(String sysLogSeverityText) {
        this.sysLogSeverityText = sysLogSeverityText;
    }

    public String getRawMessage() {
        return rawMessage;
    }

    public void setRawMessage(String rawMessage) {
        this.rawMessage = rawMessage;
    }
}
