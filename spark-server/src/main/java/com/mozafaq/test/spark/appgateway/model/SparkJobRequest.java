package com.mozafaq.test.spark.appgateway.model;

public class SparkJobRequest {

    private int topN;
    private String fileLocation;
    private String clientId;
    private String masterEndpoint;

    public int getTopN() {
        return topN;
    }

    public void setTopN(int topN) {
        this.topN = topN;
    }

    public String getFileLocation() {
        return fileLocation;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getMasterEndpoint() {
        return masterEndpoint;
    }

    public void setMasterEndpoint(String masterEndpoint) {
        this.masterEndpoint = masterEndpoint;
    }
}
