package deque;

import org.junit.Test;
import java.util.Comparator;
import static org.junit.Assert.assertEquals;

public class MaxArrayDequeTest {
    /** compare int in normal way */
    private static class IntComparator implements Comparator<Integer> {
        @Override
        public int compare(Integer num1, Integer num2) {
            return num1 - num2;
        }
    }

    /** compare int in reverse way (min) */
    private static class IntComparator2 implements Comparator<Integer> {
        @Override
        public int compare(Integer num1, Integer num2) {
            return num2 - num1;
        }
    }

    /** compare string by dictionary way */
    private static class StringComparator implements Comparator<String> {
        @Override
        public int compare(String s1, String s2) {
            return s1.compareTo(s2);
        }
    }

    /** compare string by length */
    private static class StringComparator2 implements Comparator<String> {
        @Override
        public int compare(String s1, String s2) {
            return s1.length() - s2.length();
        }
    }

    @Test
    public void maxintTest() {
        MaxArrayDeque<Integer> test = new MaxArrayDeque<>(new IntComparator());
        test.addFirst(5);
        test.addFirst(4);
        test.addFirst(3);
        test.addFirst(2);
        assertEquals(5, (int) test.max());
    }

    @Test
    public void mininttest() {
        MaxArrayDeque<Integer> test = new MaxArrayDeque<>(new IntComparator());
        test.addFirst(5);
        test.addFirst(4);
        test.addFirst(3);
        test.addFirst(2);
        assertEquals(2, (int) test.max(new IntComparator2()));
    }

    @Test
    public void maxStringTest() {
        MaxArrayDeque<String> test = new MaxArrayDeque<>(new StringComparator());
        test.addLast("test12");
        test.addLast("test1234");
        test.addLast("Test123");
        assertEquals("test1234", test.max());
    }

    @Test
    public void LongestStringTest() {
        MaxArrayDeque<String> test = new MaxArrayDeque<>(new StringComparator());
        test.addLast("test12");
        test.addLast("test1234");
        test.addLast("Test123");
        assertEquals("test1234", test.max(new StringComparator2()));
    }
}
