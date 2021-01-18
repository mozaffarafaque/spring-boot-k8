package com.mozafaq.test.spark.appgateway;


import java.util.*;

public class LauncherRequest {

    private String main;
    private String master;
    private String jobId;
    private String deployMode;

    private long timeoutMillis = 20_000l;

    private Map<String, String> configurations = new HashMap<>();
    private List<String> arguments = new ArrayList<>();

    public String getMain() {
        return main;
    }

    public LauncherRequest setMain(String main) {
        this.main = main;
        return this;
    }

    public String getMaster() {
        return master;
    }

    public LauncherRequest setMaster(String master) {
        this.master = master;
        return this;
    }

    public String getJobId() {
        return jobId;
    }

    public LauncherRequest setJobId(String jobId) {
        this.jobId = jobId;
        return this;
    }

    public String getDeployMode() {
        return deployMode;
    }

    public LauncherRequest setDeployMode(String deployMode) {
        this.deployMode = deployMode;
        return this;
    }

    public Map<String, String> getConfigurations() {
        return  Collections.unmodifiableMap(configurations);
    }

    public LauncherRequest addConfigurations(String key, String value) {
        if (key != null && value != null) {
            this.configurations.put(key, value);
        }
        return this;
    }

    public  LauncherRequest addArguments(String arg) {
        if (arg != null) {
            arguments.add(arg);
        }
        return this;
    }

    public List<String> getArguments() {
        return  Collections.unmodifiableList(arguments);
    }

    public long getTimeoutMillis() {
        return timeoutMillis;
    }

    public void setTimeoutMillis(long timeoutMillis) {
        this.timeoutMillis = timeoutMillis;
    }
}
