package Sort;

import java.util.List;

public interface SortAlgorithm {
    <E extends Comparable<E>> List<E> sort(List<E> elements);
}
