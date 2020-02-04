package Sort;

import java.util.List;

public class SelectionSortAlgorithm implements SortAlgorithm {
    @Override
    public <E extends Comparable<E>> List<E> sort(List<E> elements) {
        for (int lastIndex = elements.size() - 1; lastIndex > 0; lastIndex--){
            int largestIndexSoFar = 0;
            for (int i = 1; i <= lastIndex; i++){
                if(elements.get(largestIndexSoFar).compareTo(elements.get(i)) < 0){
                    largestIndexSoFar = i;
                }
            }
            E temp = elements.get(lastIndex);
            elements.set(lastIndex, elements.get(largestIndexSoFar));
            elements.set(largestIndexSoFar, temp);
        }
        return elements;
    }
}
