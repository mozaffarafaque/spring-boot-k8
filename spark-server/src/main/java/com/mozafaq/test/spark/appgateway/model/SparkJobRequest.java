package com.mozafaq.test.spark.appgateway.model;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class SparkJobRequest {

    private String jobId;
    private String masterEndpoint;
    private String mainClass;
    private String deployMode;
    private Map<String, String> confs;
    private List<String> arguments;
    private long timeoutMillis = 20_2000l;

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getMasterEndpoint() {
        return masterEndpoint;
    }

    public void setMasterEndpoint(String masterEndpoint) {
        this.masterEndpoint = masterEndpoint;
    }

    public String getMainClass() {
        return mainClass;
    }

    public void setMainClass(String mainClass) {
        this.mainClass = mainClass;
    }

    public String getDeployMode() {
        return deployMode;
    }

    public void setDeployMode(String deployMode) {
        this.deployMode = deployMode;
    }

    public Map<String, String> getConfs() {
        return confs == null ? Collections.emptyMap() : confs;
    }

    public void setConfs(Map<String, String> confs) {
        this.confs = confs;
    }

    public List<String> getArguments() {
        return arguments == null ? Collections.emptyList() : arguments;
    }

    public void setArguments(List<String> arguments) {
        this.arguments = arguments;
    }


    public long getTimeoutMillis() {
        return timeoutMillis;
    }

    public void setTimeoutMillis(long timeoutMillis) {
        this.timeoutMillis = timeoutMillis;
    }

    @Override
    public String toString() {
        return "SparkJobRequest{" +
                "jobId='" + jobId + '\'' +
                ", masterEndpoint='" + masterEndpoint + '\'' +
                ", mainClass='" + mainClass + '\'' +
                ", deployMode='" + deployMode + '\'' +
                ", confs=" + confs +
                ", arguments=" + arguments +
                ", timeoutMillis=" + timeoutMillis +
                '}';
    }
}
