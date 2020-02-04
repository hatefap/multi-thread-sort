import Sort.SelectionSortAlgorithm;
import Sort.SortAlgorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

public class Main {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        int cores = Runtime.getRuntime().availableProcessors();
        Integer[] sample = new Integer[]{20, 35, -15, 7, 55, 1, -22};
        List<Integer> l = Arrays.asList(sample);

        CyclicBarrier barrier = new CyclicBarrier(cores);
        ExecutorService service = Executors.newFixedThreadPool(cores);
        List<Worker<Integer>> workers = new ArrayList<>();
        List<Future<List<Integer>>> results = new ArrayList<>();

        SortAlgorithm sorter = new SelectionSortAlgorithm();

        for (int i = 0; i < cores; i++){
            workers.add(new Worker<>(barrier, sorter, l));
        }

        for (int i = 0 ; i < cores; i++){
            results.add(service.submit(workers.get(i)));
        }

        System.out.println(results.get(3).get());


        service.shutdown();
    }



}
