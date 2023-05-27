package deque;

import edu.princeton.cs.algs4.In;
import org.junit.Test;

import static org.junit.Assert.*;

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;


/** Performs some basic linked list tests. */
public class ArrayDequeTest {

    @Test
    /** Adds a few things to the list, checking isEmpty() and size() are correct,
     * finally printing the results.
     *
     * && is the "and" operation. */
    public void addIsEmptySizeTest() {

        ArrayDeque<String> lld1 = new ArrayDeque<>();

		assertTrue("A newly initialized LLDeque should be empty", lld1.isEmpty());
		lld1.addFirst("front");

		// The && operator is the same as "and" in Python.
		// It's a binary operator that returns true if both arguments true, and false otherwise.
        assertEquals(1, lld1.size());
        assertFalse("lld1 should now contain 1 item", lld1.isEmpty());

		lld1.addLast("middle");
		assertEquals(2, lld1.size());

		lld1.addLast("back");
		assertEquals(3, lld1.size());

		System.out.println("Printing out deque: ");
		lld1.printDeque();
    }

    @Test
    /** Adds an item, then removes an item, and ensures that dll is empty afterward. */
    public void addRemoveTest() {

        ArrayDeque<Integer> lld1 = new ArrayDeque<>();
		// should be empty
		assertTrue("lld1 should be empty upon initialization", lld1.isEmpty());

		lld1.addFirst(10);
		// should not be empty
		assertFalse("lld1 should contain 1 item", lld1.isEmpty());

		lld1.removeFirst();
		// should be empty
		assertTrue("lld1 should be empty after removal", lld1.isEmpty());
    }

    @Test
    /* Tests removing from an empty deque */
    public void removeEmptyTest() {

        ArrayDeque<Integer> lld1 = new ArrayDeque<>();
        lld1.addFirst(3);

        lld1.removeLast();
        lld1.removeFirst();
        lld1.removeLast();
        lld1.removeFirst();

        int size = lld1.size();
        String errorMsg = "  Bad size returned when removing from empty deque.\n";
        errorMsg += "  student size() returned " + size + "\n";
        errorMsg += "  actual size() returned 0\n";

        assertEquals(errorMsg, 0, size);
    }

    @Test
    /* Check if you can create ArrayDeques with different parameterized types*/
    public void multipleParamTest() {

        ArrayDeque<String>  lld1 = new ArrayDeque<>();
        ArrayDeque<Double>  lld2 = new ArrayDeque<>();
        ArrayDeque<Boolean> lld3 = new ArrayDeque<>();

        lld1.addFirst("string");
        lld2.addFirst(3.14159);
        lld3.addFirst(true);

        String s = lld1.removeFirst();
        double d = lld2.removeFirst();
        boolean b = lld3.removeFirst();
    }

    @Test
    /* check if null is return when removing from an empty ArrayDeque. */
    public void emptyNullReturnTest() {

        ArrayDeque<Integer> lld1 = new ArrayDeque<>();

        boolean passed1 = false;
        boolean passed2 = false;
        assertNull("Should return null when removeFirst is called on an empty Deque,", lld1.removeFirst());
        assertNull("Should return null when removeLast is called on an empty Deque,", lld1.removeLast());

    }

    @Test
    /* Add large number of elements to deque; check if order is correct. */
    public void bigLLDequeTest() {

        ArrayDeque<Integer> lld1 = new ArrayDeque<>();
        for (int i = 0; i < 100000; i++) {
            lld1.addLast(i);
        }

        for (double i = 0; i < 50000; i++) {
            assertEquals("Should have the same value", i, (double) lld1.removeFirst(), 0.0);
        }

        for (double i = 99999; i > 50000; i--) {
            assertEquals("Should have the same value", i, (double) lld1.removeLast(), 0.0);
        }

    }

    @Test
    public void getTest() {
        ArrayDeque<Integer> lld1 = new ArrayDeque<>();
        lld1.addFirst(1);
        lld1.addFirst(2);
        lld1.addFirst(3);
        lld1.addFirst(4);
        lld1.addLast(4);
        lld1.printDeque();
        assertEquals((int) lld1.get(0), 4);
        assertEquals((int) lld1.get(1), 3);
        assertEquals((int) lld1.get(2), 2);
        assertEquals((int) lld1.get(3), 1);
        assertEquals((int) lld1.get(4), 4);
    }

    @Test
    public void addFirstTest() {
        ArrayDeque<Integer> lld1 = new ArrayDeque<>();
        lld1.addFirst(4);
        lld1.addFirst(3);
        lld1.addFirst(2);
        lld1.addFirst(1);
        assertEquals((int) lld1.get(0), 1);
        assertEquals((int) lld1.get(1), 2);
        assertEquals((int) lld1.get(2), 3);
        assertEquals((int) lld1.get(3), 4);
    }

    @Test
    public void addLastTest() {
        ArrayDeque<Integer> lld1 = new ArrayDeque<>();
        lld1.addLast(1);
        lld1.addLast(2);
        lld1.addLast(3);
        lld1.addLast(4);
        assertEquals((int) lld1.get(0), 1);
        assertEquals((int) lld1.get(1), 2);
        assertEquals((int) lld1.get(2), 3);
        assertEquals((int) lld1.get(3), 4);
    }

    @Test
    public void removeTest() {
        ArrayDeque<Integer> lld1 = new ArrayDeque<>();
        lld1.addLast(1);
        lld1.addLast(2);
        lld1.addLast(3);
        lld1.addLast(4);
        lld1.removeFirst();
        lld1.removeFirst();
        lld1.addFirst(2);
        lld1.addFirst(1);
        lld1.addFirst(4);
        lld1.addFirst(3);
        lld1.addFirst(2);
        lld1.addFirst(1);
        lld1.removeFirst();
        lld1.removeFirst();
        lld1.addFirst(2);
        lld1.addFirst(1);
        assertEquals((int) lld1.removeFirst() , 1);
        assertEquals((int) lld1.removeFirst() , 2);
        assertEquals((int) lld1.removeFirst() , 3);
        assertEquals((int) lld1.removeFirst() , 4);
        //assertEquals((int) lld1.removeFirst() , 5);
        assertEquals((int) lld1.removeLast() , 4);
        assertEquals((int) lld1.removeLast() , 3);
        assertEquals((int) lld1.removeLast() , 2);
        assertEquals((int) lld1.removeLast() , 1);

    }

    @Test
    public void randomizedTest() {
        ArrayDeque<Integer> L = new ArrayDeque<>();
        LinkedListDeque<Integer> B = new LinkedListDeque<>();

        int N = 1000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 5);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                L.addLast(randVal);
                B.addLast(randVal);
                //System.out.println("addLast(" + randVal + ")");
            } else if (operationNumber == 1) {
                // size
                int Lsize = L.size();
                int Bsize = B.size();
                assertEquals(Lsize, Bsize);
                //System.out.println("size: " + size);
            } else if (operationNumber == 2) {
                if (L.size() > 0 && B.size() > 0) {
                    int num = StdRandom.uniform(0, L.size());
                    int Llast = L.get(num);
                    int Blast = B.get(num);
                    assertEquals(Llast, Blast);
                    //System.out.println("removelast(" + last + ")");
                }
            } else if (operationNumber == 3) {
                if (L.size() > 0 && B.size() > 0) {
                    assertEquals(L.removeLast(), B.removeLast());}
            } else if (operationNumber == 4) {
                if (L.size() > 0 && B.size() > 0) {
                    assertEquals(L.removeFirst(), B.removeFirst());}
            }
        }
    }

    @Test
    public void IteratorTest() {
        ArrayDeque<Integer> lld1 = new ArrayDeque<>();
        lld1.addLast(1);
        lld1.addLast(2);
        lld1.addLast(3);
        lld1.addLast(4);
        Iterator<Integer> lld1Iterator = lld1.iterator();
        for (int i = 0; i < lld1.size(); i += 1) {
            assertTrue(lld1Iterator.hasNext());
            assertEquals(lld1.get(i), lld1Iterator.next());
        }
        assertFalse(lld1Iterator.hasNext());

        int i = 0;
        for (int item : lld1) {
            assertEquals((int) lld1.get(i), item);
            i += 1;
        }
    }

    @Test
    public void equalsTest() {
        ArrayDeque<Integer> first = new ArrayDeque<>();
        first.addFirst(4);
        first.addFirst(3);
        first.addFirst(2);
        first.addFirst(1);

        ArrayDeque<Integer> second = new ArrayDeque<>();
        second.addFirst(4);
        second.addFirst(3);
        second.addFirst(2);
        second.addFirst(1);

        // test with a same ArrayDeque
        assertTrue(first.equals(second));

        // test with self
        assertTrue(first.equals(first));

        // test with size not equal Deque
        second.removeLast();
        assertFalse(first.equals(second));

        // test with not equal Deque
        second.addLast(5);
        assertFalse(first.equals(second));

        // test with null
        assertFalse(first.equals(null));

        ArrayDequecircle<Integer> third = new ArrayDequecircle<>();
        third.addFirst(4);
        third.addFirst(3);
        third.addFirst(2);
        third.addFirst(1);

        // test with other deque implement
        assertTrue(first.equals(third));

        third.removeLast();
        assertFalse(first.equals(third));

        third.addLast(5);
        assertFalse(first.equals(third));
    }
}
