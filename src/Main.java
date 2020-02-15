import Sort.InsertionSortAlgorithm;
import Sort.SelectionSortAlgorithm;
import Sort.SortAlgorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class Main {

    public static void main(String[] args) {

        int cores = Runtime.getRuntime().availableProcessors();
        Integer[] sample = new Integer[1000];
        Random r = new Random();
        for (int i = 0; i < sample.length; i++){
            sample[i] = r.nextInt(1000);
        }
        List<Integer> l = Arrays.asList(sample);

        SortManager<Integer> sm = new SortManager<>(Arrays.asList(sample), new InsertionSortAlgorithm(), 3);
        System.out.println(sm.sort());

    }



}
