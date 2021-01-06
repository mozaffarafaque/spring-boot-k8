package com.mozafaq.test.sparkapp.job;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class WordCounter {

    private static String MASTER = "";
    public void runJob(int topN, String fileLocation, String clientId) {

        String jobId = Optional.ofNullable(clientId).orElse(UUID.randomUUID().toString());
        String textFilePath = fileLocation;
        int top = topN;

        SparkConf conf = new SparkConf().setAppName("work-count-" + jobId).setMaster(MASTER);

        JavaSparkContext sparkContext = new JavaSparkContext(conf);

        JavaRDD<String> lines = sparkContext.textFile(textFilePath);

        JavaRDD<String> tokenizedLines =
                lines.filter(e -> e != null)
                        .map(e -> e.split(" "))
                        .filter(e -> e.length > 0)
                        .map(e -> Arrays.asList(e))
                        .flatMap(e -> e.iterator());

        JavaRDD<Tuple2<Integer, String>> hashTuples =
                tokenizedLines.filter(e -> e != null)
                        .map( e -> Tuple2.apply(e.hashCode(), e));

        JavaRDD<Tuple2<Integer, Integer>> counter = hashTuples.map(e -> Tuple2.apply(e._1, 1));
        JavaPairRDD<Integer, Integer> pairRDD = counter.mapToPair(e -> e);
        JavaPairRDD<Integer, Integer> summation =  pairRDD.reduceByKey((e1, e2) -> e1 + e2);

        JavaPairRDD<Integer, Integer> bySum =  summation.mapToPair(e -> e.swap());

        JavaPairRDD<Integer, Integer> sorted =  bySum.sortByKey(false);

        List<Tuple2<Integer, Integer>> topNList = sorted.top(top);

        System.out.println("[" + jobId + "] Top: " + top + ": " + topN + "\n" +
                topNList.stream()
                        .map(e -> e.toString())
                        .collect(Collectors.joining("\n"))
        );
    }

}
