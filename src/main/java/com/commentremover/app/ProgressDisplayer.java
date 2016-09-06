package com.commentremover.app;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ProgressDisplayer {

    private static final String OUTPUT_STEP = "*\t";

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    private volatile boolean isNotFinished = true;

    private int starCount;

    public void displayProgressByDots() {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("PROCESS STARTED... It may take a while (based on your project size). Please be patient.\n");

                while (isNotFinished) {
                    if (++starCount % 10 == 0) {
                        starCount = 0;
                        System.out.println(OUTPUT_STEP);
                    } else {
                        System.out.print(OUTPUT_STEP);
                    }

                    try {
                        Thread.sleep(350L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                System.out.println("\n\nPROCESS SUCCESSFULLY DONE!");
            }
        });
    }

    public void stopDisplayingProgress() {
        isNotFinished = false;
        try {
            executorService.shutdown();
            executorService.awaitTermination(400L, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
