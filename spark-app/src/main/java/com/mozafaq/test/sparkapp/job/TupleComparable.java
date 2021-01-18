package com.mozafaq.test.sparkapp.job;

import scala.Serializable;
import scala.Tuple2;

import java.util.Comparator;
import java.util.Random;

public class TupleComparable implements Comparator<Tuple2<Integer, Integer>>, Serializable {


    private boolean isDelayEnabled = Boolean.parseBoolean(System.getProperty("is.delay.enabled"));
    private Random r = new Random();
    private double randomProbability = 0.1;
    private long wait = 500l;

    @Override
    public int compare(Tuple2<Integer, Integer> o1, Tuple2<Integer, Integer> o2) {

        if (isDelayEnabled && r.nextDouble() < randomProbability) {
            try {
                Thread.sleep(wait);
            } catch (InterruptedException e) {
                System.err.println(e);
            }
        }
        return o1._1 - o2._1;
    }
}
