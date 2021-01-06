package com.mozafaq.test.spark.appgateway;

public class SparkJobRequest {

    private String clientId;
    private String jobName;
    private String bucketName;
    private String bucketPath;
    private int topN;
    private String textFileLocation;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getBucketPath() {
        return bucketPath;
    }

    public void setBucketPath(String bucketPath) {
        this.bucketPath = bucketPath;
    }

    public int getTopN() {
        return topN;
    }

    public void setTopN(int topN) {
        this.topN = topN;
    }

    public String getTextFileLocation() {
        return textFileLocation;
    }

    public void setTextFileLocation(String textFileLocation) {
        this.textFileLocation = textFileLocation;
    }
}
