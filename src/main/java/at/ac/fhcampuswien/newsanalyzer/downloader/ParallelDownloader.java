package at.ac.fhcampuswien.newsanalyzer.downloader;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ParallelDownloader extends Downloader {

    ExecutorService executer = Executors.newFixedThreadPool(10);

    @Override
    public int process(List<String> urls) {
        int count = 0;
        long startPar = System.currentTimeMillis();
        for (String url : urls) {
            try {
                Future<?> future = executer.submit(() -> {
                    saveUrl2File(url);
                });
                count++;
            } catch (Exception e) {
                System.out.println("Not found, can't download");
            }
        }
        long endPar = System.currentTimeMillis();
        System.out.println("Download time for parallel: " + ((endPar - startPar)) + " milliseconds");
        //shutdown threads
        executer.shutdown();
        return count;
    }
}
