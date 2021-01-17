package com.mozafaq.test.sparkapp;

import com.mozafaq.test.sparkapp.job.WordCounter;

public class JobRunner {

    public static void main(String[] args) {

        if (args.length != 3) {
            throw new IllegalArgumentException("No. of arguments must be 3 " +
                    "(file location, clientId, top)");
        }
        WordCounter wordCounter = new WordCounter();
        wordCounter.runJob(Integer.parseInt(args[2]), args[0], args[1]);

        System.out.println(".....Job Submitted.....");
    }
}
