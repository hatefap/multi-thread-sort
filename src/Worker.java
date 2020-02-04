import Sort.SortAlgorithm;

import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.Callable;
import java.util.concurrent.CyclicBarrier;

public class Worker<E extends Comparable<E>> implements Callable<List<E>> {

    private CyclicBarrier barrier;
    private SortAlgorithm sorter;
    private List<E> inputList;

    public Worker(CyclicBarrier barrier, SortAlgorithm sorter, List<E> inputList) {
        this.barrier = barrier;
        this.sorter = sorter;
        this.inputList = inputList;
    }

    @Override
    public List<E> call() {
        List<E> sortedList = sorter.sort(inputList);
        try{
            barrier.await();
        } catch (InterruptedException | BrokenBarrierException e){
            e.printStackTrace();
        }
        return sortedList;
    }
}
