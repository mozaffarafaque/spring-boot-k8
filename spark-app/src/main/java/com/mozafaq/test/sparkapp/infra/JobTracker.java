package com.mozafaq.test.sparkapp.infra;

import com.mozafaq.test.springboot.utilslib.CommandExec;
import org.apache.spark.scheduler.*;

import java.util.Date;

public class JobTracker extends SparkListener {

    public static String DEFAULT_ENDPOINT = "http://spark-master-server:9013/progress-event";

    private String jobId = "Default-Unknown";
    private String remoteEndpoint = null;
    public void onStageCompleted(final SparkListenerStageCompleted stageCompleted) {

        System.out.println("INFO ["  + new Date().toInstant() + "][onStageCompleted] " + stageCompleted);
    }

    public void onStageSubmitted(final SparkListenerStageSubmitted stageSubmitted) {
        System.out.println("INFO ["  + new Date().toInstant() + "][onStageSubmitted] " + stageSubmitted);
    }

    public void onTaskStart(final SparkListenerTaskStart taskStart) {
        System.out.println("INFO ["  + new Date().toInstant() + "][onTaskStart] " + taskStart);
    }

    public void onTaskGettingResult(final SparkListenerTaskGettingResult taskGettingResult) {
        System.out.println("INFO ["  + new Date().toInstant() + "][onTaskGettingResult] " + taskGettingResult);
    }

    public void onTaskEnd(final SparkListenerTaskEnd taskEnd) {
        System.out.println("INFO ["  + new Date().toInstant() + "][onTaskEnd] " + taskEnd);
    }

    public void onJobStart(final SparkListenerJobStart jobStart) {
        System.out.println("INFO ["  + new Date().toInstant() + "][onJobStart] " + jobStart);
    }

    public void onJobEnd(final SparkListenerJobEnd jobEnd) {
        System.out.println("INFO ["  + new Date().toInstant() + "][onJobEnd] " + jobEnd);
    }

    public void onEnvironmentUpdate(final SparkListenerEnvironmentUpdate environmentUpdate) {
        System.out.println("INFO ["  + new Date().toInstant() + "][onEnvironmentUpdate] " + environmentUpdate);
    }

    public void onBlockManagerAdded(final SparkListenerBlockManagerAdded blockManagerAdded) {
        System.out.println("INFO ["  + new Date().toInstant() + "][onBlockManagerAdded] " + blockManagerAdded);
    }

    public void onBlockManagerRemoved(final SparkListenerBlockManagerRemoved blockManagerRemoved) {
        System.out.println("INFO ["  + new Date().toInstant() + "][onBlockManagerRemoved] " + blockManagerRemoved);
    }

    public void onUnpersistRDD(final SparkListenerUnpersistRDD unpersistRDD) {
        System.out.println("INFO ["  + new Date().toInstant() + "][onUnpersistRDD] " + unpersistRDD);
    }

    public void onApplicationStart(final SparkListenerApplicationStart applicationStart) {
        System.out.println("INFO ["  + new Date().toInstant() + "][onApplicationStart] " + applicationStart);
    }

    public void onApplicationEnd(final SparkListenerApplicationEnd applicationEnd) {
        System.out.println("INFO ["  + new Date().toInstant() + "][onApplicationEnd] " + applicationEnd);
    }

    public void onExecutorMetricsUpdate(final SparkListenerExecutorMetricsUpdate executorMetricsUpdate) {
        System.out.println("INFO ["  + new Date().toInstant() + "][onExecutorMetricsUpdate] " + executorMetricsUpdate);
    }

    public void onStageExecutorMetrics(final SparkListenerStageExecutorMetrics executorMetrics) {
        System.out.println("INFO ["  + new Date().toInstant() + "][onStageExecutorMetrics] " + executorMetrics);
    }

    public void onExecutorAdded(final SparkListenerExecutorAdded executorAdded) {
        System.out.println("INFO ["  + new Date().toInstant() + "][onExecutorAdded] " + executorAdded);
    }

    public void onExecutorRemoved(final SparkListenerExecutorRemoved executorRemoved) {
        System.out.println("INFO ["  + new Date().toInstant() + "][onExecutorRemoved] " + executorRemoved);
    }

    public void onExecutorBlacklisted(final SparkListenerExecutorBlacklisted executorBlacklisted) {
        System.out.println("INFO ["  + new Date().toInstant() + "][onExecutorBlacklisted] " + executorBlacklisted);
    }

    public void onExecutorBlacklistedForStage(final SparkListenerExecutorBlacklistedForStage executorBlacklistedForStage) {
        System.out.println("INFO ["  + new Date().toInstant() + "][onExecutorBlacklistedForStage] " + executorBlacklistedForStage);
    }

    public void onNodeBlacklistedForStage(final SparkListenerNodeBlacklistedForStage nodeBlacklistedForStage) {
        System.out.println("INFO ["  + new Date().toInstant() + "][onNodeBlacklistedForStage] " + nodeBlacklistedForStage);
    }

    public void onExecutorUnblacklisted(final SparkListenerExecutorUnblacklisted executorUnblacklisted) {
        System.out.println("INFO ["  + new Date().toInstant() + "][onExecutorUnblacklisted] " + executorUnblacklisted);
    }

    public void onNodeBlacklisted(final SparkListenerNodeBlacklisted nodeBlacklisted) {
        System.out.println("INFO ["  + new Date().toInstant() + "][onNodeBlacklisted] " + nodeBlacklisted);
    }

    public void onNodeUnblacklisted(final SparkListenerNodeUnblacklisted nodeUnblacklisted) {
        System.out.println("INFO ["  + new Date().toInstant() + "][onNodeUnblacklisted] " + nodeUnblacklisted);
    }

    public void onBlockUpdated(final SparkListenerBlockUpdated blockUpdated) {
        System.out.println("INFO ["  + new Date().toInstant() + "][onBlockUpdated] " + blockUpdated);
    }

    public void onSpeculativeTaskSubmitted(final SparkListenerSpeculativeTaskSubmitted speculativeTask) {
        System.out.println("INFO ["  + new Date().toInstant() + "][onSpeculativeTaskSubmitted] " + speculativeTask);
    }

    public void onOtherEvent(final SparkListenerEvent event) {
        System.out.println("INFO ["  + new Date().toInstant() + "][onOtherEvent] " + event);
    }

    private void runCommand(String event, Object progress) {
        System.out.println("INFO ["  + new Date().toInstant() + "][" + event + "] " + progress);

        if (remoteEndpoint == null) {

            System.out.println("Not executing command...");
        }

        CommandExec exec = new CommandExec();
        int statusCode = exec.executeCommand(new String[]{"curl", "-XGET", "--max-time", "15",
                remoteEndpoint + "?" + "eventName=" + event + "&progress=" + jobId + "-Status:" + progress},
                10_000);

        System.out.println("INFO ["  + new Date().toInstant() + "][" + event + "] " + progress + ", Status code: " + statusCode);
    }

    public JobTracker() {
    }

    public JobTracker(String jobId, String remoteEndpoint) {

        this.remoteEndpoint = remoteEndpoint;
        this.jobId = jobId;
    }
}
