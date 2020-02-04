package Sort;

import java.util.List;

public class BubbleSortAlgorithm implements SortAlgorithm {


    @Override
    public <E extends Comparable<E>> List<E> sort(List<E> elements) {
        for (int lastIndex = elements.size() - 1; lastIndex > 0; lastIndex--){
            for (int i = 0; i < lastIndex; i++){
                int result = elements.get(i).compareTo(elements.get(i + 1));
                if (result > 0){
                    E temp = elements.get(i);
                    elements.set(i, elements.get(i + 1));
                    elements.set(i + 1, temp);
                }

            }
        }
        return elements;
    }
}
