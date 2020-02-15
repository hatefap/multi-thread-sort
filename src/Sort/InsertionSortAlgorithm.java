package Sort;

import java.util.List;

public class InsertionSortAlgorithm implements SortAlgorithm {
    @Override
    public <E extends Comparable<E>> List<E> sort(List<E> elements) {
        for (int unsortedIndex = 1; unsortedIndex < elements.size(); unsortedIndex++){
            E unsortedElement = elements.get(unsortedIndex);
            int sortedIndex = unsortedIndex;
            for (; sortedIndex > 0
                    && elements.get(sortedIndex - 1).compareTo(unsortedElement) > 0; sortedIndex--){
                elements.set(sortedIndex, elements.get(sortedIndex - 1));
                }
                elements.set(sortedIndex, unsortedElement);
            }

            return elements;
        }

    }

