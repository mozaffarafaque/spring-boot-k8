package com.mozafaq.test.springboot.utilslib;


public class CommandResponse {

    private int exitStatus;

    private String description;

    public CommandResponse() {

    }

    public int getExitStatus() {
        return exitStatus;
    }

    public void setExitStatus(int exitStatus) {
        this.exitStatus = exitStatus;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(getClass().getSimpleName());
        builder.append(", exitStatus=");
        builder.append(exitStatus);
        builder.append(", description=");
        builder.append(description);
        builder.append(']');
        return builder.toString();
    }

}

