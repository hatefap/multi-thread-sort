import Sort.SortAlgorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.concurrent.*;

public class SortManager<E extends Comparable<E>> {

    private List<E> inputList;
    private int numberOfSlice;
    private SortAlgorithm sorter;
    private int chuckSize;

    public SortManager(List<E> inputList, SortAlgorithm sorter, int numberOfSlice) {
        this.inputList = inputList;
        this.sorter = sorter;
        this.chuckSize = inputList.size() / numberOfSlice + 1;
        this.numberOfSlice = numberOfSlice;
    }


    public List<E> sort(){
        List<List<E>> sortedSlices = new ArrayList<>();
        System.out.println(numberOfSlice);
        List<List<E>> slices = chunkList(inputList, chuckSize);
        System.out.println(slices.size());
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

        service.shutdown();
        return mergeList(sortedSlices);
    }


    private class HeapNode{
        E value;
        int index;
        int pointer;
        HeapNode(E e, int index, int pointer) {
            this.value = e;
            this.index = index;
            this.pointer = pointer;

        }
    }

    private List<E> mergeList(List<List<E>> sortedLists){
        System.out.println(sortedLists);
        List<E> finalResult = new ArrayList<>(inputList.size());
        PriorityQueue<HeapNode> minHeap = new PriorityQueue<>(sortedLists.size(), (HeapNode a, HeapNode b) -> (a.value.compareTo(b.value)));
        for(int index = 0; index < sortedLists.size(); index++){
            minHeap.add(new HeapNode(sortedLists.get(index).get(0), index, 0));
        }

        for(int i = 0; i < inputList.size(); i++){
            HeapNode node = minHeap.poll();
            if(node != null){
                finalResult.add(node.value);
                if (node.pointer  < sortedLists.get(node.index).size() - 1){
                    node.pointer += 1;
                    minHeap.add(new HeapNode(sortedLists.get(node.index).get(node.pointer), node.index, node.pointer));
                }

            }
        }
        return finalResult;
    }

    private List<List<E>> chunkList(List<E> list, int chunkSize) {
        if (chunkSize <= 0) {
            throw new IllegalArgumentException("Invalid chunk size: " + chunkSize);
        }

        if (chunkSize > list.size()) {
            chunkSize = list.size();
        }
        List<List<E>> chunkList = new ArrayList<>(list.size() / chunkSize);
        for (int i = 0; i < list.size(); i += chunkSize) {
            chunkList.add(list.subList(i, i + chunkSize >= list.size() ? list.size() : i + chunkSize));
        }
        return chunkList;
    }
}
