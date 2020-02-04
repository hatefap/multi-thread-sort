import Sort.SortAlgorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class SortManager<E extends Comparable<E>> {

    private List<E> inputList;
    private int numberOfSlice;
    private SortAlgorithm sorter;

    public SortManager(List<E> inputList, SortAlgorithm sorter, int numberOfSlice) {
        this.inputList = inputList;
        this.sorter = sorter;
        this.numberOfSlice = numberOfSlice;
    }


    public List<List<E>> sort(){
        List<List<E>> sortedSlices = new ArrayList<>();
        List<List<E>> slices = chunkList(inputList, numberOfSlice);
        CyclicBarrier barrier = new CyclicBarrier(numberOfSlice);
        ExecutorService service = Executors.newFixedThreadPool(numberOfSlice);

        List<Worker<E>> workers = new ArrayList<>(numberOfSlice);
        List<Future<List<E>>> results = new ArrayList<>(numberOfSlice);

        for (List<E> e : slices){
            workers.add(new Worker<>(barrier, sorter, e));
        }

        for (Worker<E> w : workers){
            results.add(service.submit(w));
        }

        try {
            for (Future<List<E>> f : results) {
                sortedSlices.add(f.get());
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return sortedSlices;
    }


    private <E> List<E> mergeList(List<List<E>> sortedLists){
        return null;
    }

    private  <E> List<List<E>> chunkList(List<E> list, int chunkSize) {
        if (chunkSize <= 0) {
            throw new IllegalArgumentException("Invalid chunk size: " + chunkSize);
        }

        if (chunkSize > list.size()) {
            chunkSize = list.size();
        }
        List<List<E>> chunkList = new ArrayList<>(list.size() / chunkSize);
        for (int i = 0; i < list.size(); i += chunkSize) {
            chunkList.add(list.subList(i, i + chunkSize >= list.size() ? list.size()-1 : i + chunkSize));
        }
        return chunkList;
    }
}
