package deque;

import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T> {
    private Comparator<T> comparator;

    public MaxArrayDeque(Comparator<T> c) {
        super();
        comparator = c;
    }

    private T find_max(Comparator<T> c) {
        if (this.isEmpty()) {
            return null;
        }
        T maxitem = this.get(0);
        for (int i = 1; i < this.size(); i += 1) {
            T now = this.get(i);
            if (c.compare(now, maxitem) > 0) {
                maxitem = now;
            }
        }
        return maxitem;
    }

    public T max() {
        return find_max(comparator);
    }

    public T max(Comparator<T> c) {
        return find_max(c);
    }
    
}


