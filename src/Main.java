import Sort.InsertionSortAlgorithm;
import Sort.SelectionSortAlgorithm;
import Sort.SortAlgorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

public class Main {

    public static void main(String[] args) {

        int cores = Runtime.getRuntime().availableProcessors();
        Integer[] sample = new Integer[]{20, 35, -15, 7, 55, 1, -22};
        List<Integer> l = Arrays.asList(sample);

        SortManager<Integer> sm = new SortManager<>(Arrays.asList(sample), new SelectionSortAlgorithm(), 2);
        System.out.println(sm.sort());

    }



}
