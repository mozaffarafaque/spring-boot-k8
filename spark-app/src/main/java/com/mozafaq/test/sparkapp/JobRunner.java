package com.mozafaq.test.sparkapp;

import com.mozafaq.test.sparkapp.job.WordCounter;

public class JobRunner {

    public static void main(String[] args) {

        if (args.length != 4) {
            throw new IllegalArgumentException("No. of arguments must be 4 " +
                    "(url ,file location, clientId, top)");
        }
        WordCounter wordCounter = new WordCounter();
        wordCounter.runJob(args[0], Integer.parseInt(args[3]), args[1], args[2]);

        System.out.println(".....Job Submitted.....");
    }
}
